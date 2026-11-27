# Hospital Equipment Tracking V2 — Entity Layer README

> 学习笔记 + 可重复使用的开发流程 | 2026-11-26
> Java 21 · Spring Boot 3.5.13 · Hibernate 6.6 · MySQL 8 · Jakarta Persistence

## 1. 从零开始：下一次如何开发 Entity Layer

Entity 不是先写 Java 再猜数据库结构。本项目采用 Database First：

1. 明确 Business Rules：哪些对象存在、谁负责谁、哪些状态合法。
2. 画 ERD：确定 Cardinality（1:1、1:N、N:M）、Optionality、PK、FK。
3. 写 Schema：确定物理表名、字段名、类型、NOT NULL、UNIQUE、CHECK、FK、Index。
4. 导入 Seed，验证数据并 COMMIT；另开连接检查数据确实存在。
5. 建立 Spring Boot 项目，选择 Spring Data JPA、MySQL Driver 等依赖，配置 datasource。
6. 从被依赖的 Entity 开始，先定义需要的 Enum，再写 Entity。
7. 对照每个 SQL 字段检查 Java 类型、Column、Relationship、Nullable。
8. 编译，再用 Hibernate `validate` 启动验证 Mapping。
9. Commit/Push；下一阶段 Repository，随后 DTO、Service、Controller、Security 和测试。

本项目 package：`com.qt.hospital_equipment_tracking.model`，Enum 位于 `model.enums`。所有 Entity 放在 Spring Boot 主类 package 的子树内，便于自动扫描。

## 2. JPA、Hibernate、Spring Data JPA 的区别

| 技术 | 职责 |
|---|---|
| JPA / Jakarta Persistence | ORM 标准，定义 Entity、Relationship、Persistence Context 等接口和 Annotation |
| Hibernate | JPA 实现，负责对象与关系表映射、生成 SQL、加载对象、Dirty Checking |
| Spring Data JPA | Repository 抽象，减少 CRUD 和查询样板代码 |
| JDBC / MySQL Driver | 执行数据库通信 |
| HikariCP | 管理数据库连接池 |

ORM = Object Relational Mapping。Java 操作对象，Hibernate 将其转换为数据库读写；不是数据库直接保存 Java 对象。

```text
Controller → Service → Repository → JPA/Hibernate → JDBC → MySQL
                         Entity ↔ Database Table
```

Entity 生命周期：Transient（new 出来未管理）、Managed（Persistence Context 管理）、Detached（脱离上下文）、Removed（标记删除）。Managed 对象在事务中修改后可通过 Dirty Checking 在 flush 时产生 UPDATE。flush 不等于 commit；事务仍可 rollback。`save()` 不保证立即执行全部 SQL。

## 3. 基础 Entity 写法与 Annotation

```java
package com.qt.hospital_equipment_tracking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public Room() {}

    public Room(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) {
        this.department = department;
    }
}
```

| Annotation / 写法 | 意义 |
|---|---|
| `@Entity` | 注册为持久化实体 |
| `@Table(name="rooms")` | 显式指定物理表名 |
| `@Id` | 主键；放在 field 上表示采用 field access |
| `@GeneratedValue(IDENTITY)` | 使用数据库自增主键；新对象 id 为 null |
| `@Column(name=...)` | 指定物理列名；本项目 snake_case 显式映射 |
| `nullable=false` | 非空列映射元数据，不是 HTTP 输入验证 |
| `length=100` | 字符列长度元数据，不会自动截断或校验用户输入 |
| `unique=true` | 唯一性映射元数据；真实约束由 Schema 保证 |
| `columnDefinition="TEXT"` | 指定 SQL 类型定义，本项目用于维修 description/notes |
| `updatable=false` | Hibernate 生成普通 UPDATE 时排除该列，不是数据库权限控制 |
| `@Transient` | 不持久化的字段（如需要定义 derived property 时） |

V2 规范：Long id（BIGINT），public no-arg constructor（JPA 至少要求 public/protected），可选业务 constructor，不传 id/version/自动审计时间；手写 getter/setter，不使用 Lombok；id/version/自动审计时间不提供普通 setter；不默认生成 equals/hashCode/toString（关联递归、LAZY 加载、生成主键变化可能带来问题）。这不是说 Entity 永远不能实现这些方法，而是需要专门设计身份语义。

类型对照：BIGINT→Long，VARCHAR/TEXT→String，BOOLEAN→Boolean，DATETIME→LocalDateTime，VARCHAR 状态→Enum + STRING。包装类型允许 null，必填仍靠约束；primitive boolean 默认 false，可能掩盖未赋值。

## 4. Relationship Mapping：先看 FK，再决定 Java 写法

### 4.1 ManyToOne：多个 Room 属于一个 Department

```java
@ManyToOne(fetch = FetchType.EAGER, optional = false)
@JoinColumn(name = "department_id", nullable = false)
private Department department;
```

SQL 存的是 `rooms.department_id`（Long FK），Java 保存的是 Department reference。Hibernate 负责转换；不用额外写 `private Long departmentId`，也不用在 Entity field 中调用 `department.getId()`。DTO 可以只传 departmentId，由 Service 查找 Department。

### 4.2 OneToMany：Department 反向访问 Room 和 Equipment

```java
@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
private List<Room> rooms = new ArrayList<>();

@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
private List<Equipment> equipments = new ArrayList<>();
```

- `mappedBy` 是对方 Java field 名 `department`，不是 SQL 列名 `department_id`。
- Room/Equipment 是 JPA owning side（持有 FK），Department 是 inverse side。业务上的 Owning Department 与 JPA owning side 是两个概念。
- List 不会成为 departments 的数据库列。Hibernate 根据 FK 加载集合。
- `List` 是接口，`ArrayList` 是初始化实现；Hibernate 可替换为自己的 persistent collection。
- `new ArrayList<>()` 只初始化内存集合，不会插入数据库；不使用 final，也不提供整个 collection setter。
- 双向关系必须保持内存两侧一致；仅修改 inverse collection 不保证 FK 更新。持久化关系以 owning side 为准。

### 4.3 OneToOne：UserAccount 与 Person

```java
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "person_id", unique = true)
private Person person;
```

一个账号最多关联一个 Person，一个 Person 最多关联一个账号。FK 可空，UNIQUE 防止多个账号关联同一非空 person_id。Person 可以没有登录账号，账号也可以没有 Person。V2 不在 Person 增加反向 UserAccount。

### 4.4 ManyToMany（通用知识，V2 未使用）

N:M 通常需要 Join Table，两侧可用 `@ManyToMany`，owner 使用 `@JoinTable`，inverse 使用 `mappedBy`。如果关联本身有时间、操作者、状态等字段，应考虑独立 Entity，而非简单 ManyToMany。本项目历史记录就是独立 Entity。

## 5. Fetch Strategy、Cascade、Optional：三个独立问题

| 概念 | 回答的问题 |
|---|---|
| Direction / mappedBy | 从哪边可以导航？哪边维护 FK？ |
| Fetch | 什么时候加载关联对象？ |
| Cascade | persist/remove 等操作是否传播？ |
| Optional / nullable | 关系能否不存在？数据库 FK 能否为空？ |

### Fetch

JPA 默认：ManyToOne/OneToOne EAGER；OneToMany/ManyToMany LAZY。EAGER 要求及时加载，不保证一条 JOIN SQL；LAZY 是延迟加载提示，具体实现可能提前加载。两侧可以都 EAGER，但通常成本高。LAZY 对象脱离 Persistence Context 后访问可能产生 LazyInitializationException。

V2 已确认：**Room→Department EAGER；其他所有 to-one LAZY；所有 to-many LAZY。** Department 是唯一拥有反向集合的 Entity（rooms/equipments）。Room 不维护设备集合，Person 不维护设备/账号集合，Equipment 不维护历史/维修集合；通过 Repository 按需查询。

N+1：查询 200 台 Equipment 后逐个访问关联可能额外产生大量 SQL。EAGER 也可能 N+1。后续根据具体查询使用 fetch join、EntityGraph、DTO projection、batch fetching；不要为解决一个页面而全局改 EAGER。分页结合 collection fetch join 需要谨慎。

### Cascade

`PERSIST` 传播新增，`MERGE` 传播合并，`REMOVE` 传播删除，`REFRESH` 刷新，`DETACH` 脱离，`ALL` 包含全部。`orphanRemoval=true` 可删除从父关联中移除的子对象，和 cascade 不完全相同。JPA cascade 与 SQL `ON DELETE CASCADE` 不同。

V2 不设置 cascade / orphanRemoval：Department、Room、Person、Equipment、Account 和历史各自有独立生命周期。删除账号不应删除 Person；删除设备不应自动清除审计记录。Schema FK 未配置级联删除，仍被引用的父记录通常不能删除。

### Optional

`@ManyToOne(optional=false)` 是对象关系必需；`@JoinColumn(nullable=false)` 是 FK 非空映射。必需关系同时写两者；可选关系使用默认 true。Optional 与 Fetch/Cascade 无关，也不表示删除父对象时自动置 null。

## 6. 九个 Entity 的设计与字段清单

所有表均使用 BIGINT 自增 PK。下表省略共同 id；? 表示 nullable。所有 FK 都映射为对象 reference。

| Entity / Table | Scalar fields（Java） | Relationships |
|---|---|---|
| Department / departments | name, departmentType | rooms、equipments（inverse lists） |
| Room / rooms | name | department（required） |
| Person / people | firstName, lastName, employeeId?, active | department? |
| UserAccount / user_accounts | username, password, userRole, enabled | person?（unique） |
| Equipment / equipments | name, assetTag, serialNumber, type, category, mobile, requiresCleaning, usageStatus, cleaningStatus, repairStatus, assignedAt?, lastMaintenanceAt?, version | department required, currentRoom?, assignedPerson? |
| EquipmentStatusHistory / equipment_status_histories | action, from/toUsageStatus?, from/toCleaningStatus?, from/toRepairStatus?, changedAt, notes? | equipment, changedByUser（required） |
| EquipmentMovementHistory / equipment_movement_histories | movedAt, notes? | equipment, fromRoom?, toRoom, movedByUser |
| EquipmentAssignmentHistory / equipment_assignment_histories | action, changedAt, notes? | equipment, fromPerson?, toPerson?, changedByUser |
| MaintenanceRecord / maintenance_records | maintenanceType, maintenanceStatus, scheduledAt?, startedAt?, completedAt?, canceledAt?, performedBy?, description?, notes?, createdAt, updatedAt | equipment, requestedByPerson（required） |

字段长度：Department/Room name 100；Person firstName/lastName 100、employeeId 50 unique；Account username 100 unique、password 255；Equipment name 150、assetTag/serialNumber 100 unique；type/category/departmentType/maintenanceType 50；各状态、UserRole、AssignmentAction 30；EquipmentStatusAction 50；History notes 500；performedBy 150；维修 description/notes TEXT。以 `01_schema.sql` 为最终物理字段依据。

### 业务设计为什么这样做

- Equipment.department 固定归属；currentRoom.department 表示当前位置所属部门，两者可以不同。移动不改变 owning department。
- Person.department 是人员所属部门，不等于设备责任归属。Assignment 独立于使用、清洁、维修。
- Person 表示现实人员；UserAccount 表示系统操作者。历史 actor 使用 Account；维修 requester 使用 Person；password 只存 BCrypt hash，不返回前端。
- StatusHistory 每次实际 lifecycle 状态变更一条记录，同时记录所有变化维度；未变化的 from/to 均为 null，不使用 operationId/optionalId。
- MovementHistory 记录 from/to Room，不重复存 department snapshot；首次位置登记 fromRoom 可空。
- AssignmentHistory：ASSIGN null→A，UNASSIGN A→null，REASSIGN A→B；不能 A→A。
- History 在业务层 append-only；有 setter 不代表允许提供 update/delete API。
- MaintenanceRecord 记录维修流程，Equipment.lastMaintenanceAt 是最后完成维修时间；完成维修不自动改变清洁状态、位置或责任人。

## 7. Enum Mapping 与 Seed 检查

```java
@Enumerated(EnumType.STRING)
@Column(name = "usage_status", nullable = false, length = 30)
private UsageStatus usageStatus;
```

STRING 保存 `IDLE` 等 enum.name()；ORDINAL 保存序号，调整 Enum 顺序可能破坏旧数据，因此本项目统一 STRING。DB VARCHAR 不自动知道 Java Enum；Schema validate 不逐行读取数据。大小写、拼写必须匹配；顺序不重要。重命名 Enum 需要同步迁移数据和约束。

| Enum | Values |
|---|---|
| DepartmentType | SURGERY, CSPD, PRE_OP, RADIOLOGY, ICU, MATERIALS_MANAGEMENT, EVS, PHARMACY, LAB, CLINIC |
| UserRole | ADMIN, STAFF, GUEST |
| EquipmentType | ANESTHESIA_MACHINE, AUTOCLAVE, C_ARM, CASE_CART, INFUSION_PUMP, PATIENT_MONITOR, STRETCHER, ULTRASOUND, WASHER_DISINFECTOR, WHEELCHAIR |
| EquipmentCategory | IMAGING, LIFE_SUPPORT, LOGISTICS, PROCEDURE, STERILE_PROCESSING |
| UsageStatus | IDLE, IN_USE |
| CleaningStatus | NOT_REQUIRED, CLEAN, DIRTY, IN_CLEANING |
| RepairStatus | ACTIVE, IN_REPAIR |
| EquipmentStatusAction | START_USE, END_USE, REPORT_CONTAMINATION, START_CLEANING, COMPLETE_CLEANING, CANCEL_CLEANING, START_MAINTENANCE, COMPLETE_MAINTENANCE |
| AssignmentAction | ASSIGN, UNASSIGN, REASSIGN |
| MaintenanceType | CALIBRATION, CORRECTIVE, INSPECTION, PREVENTIVE |
| MaintenanceStatus | SCHEDULED, IN_PROGRESS, COMPLETED, CANCELED |

```sql
SELECT DISTINCT department_type FROM departments ORDER BY department_type;
SELECT DISTINCT user_role FROM user_accounts ORDER BY user_role;
SELECT DISTINCT type, category FROM equipments ORDER BY category, type;
SELECT DISTINCT usage_status, cleaning_status, repair_status FROM equipments;
SELECT DISTINCT action FROM equipment_status_histories;
SELECT DISTINCT action FROM equipment_assignment_histories;
SELECT DISTINCT maintenance_type, maintenance_status FROM maintenance_records;
```

实际 CSV 已核对：上述分类/角色值一致；StatusHistory seed 有七种 action，未包含 REPORT_CONTAMINATION，不代表应从设计中删除。Type/category 当前 seed 对应：IMAGING(C_ARM, ULTRASOUND)，LIFE_SUPPORT(INFUSION_PUMP, PATIENT_MONITOR)，LOGISTICS(CASE_CART, STRETCHER, WHEELCHAIR)，PROCEDURE(ANESTHESIA_MACHINE)，STERILE_PROCESSING(AUTOCLAVE, WASHER_DISINFECTOR)。是否在 Service 强制对应关系仍待确认。

## 8. 状态模型与业务规则的边界

| Repair | Usage | Cleaning | Available |
|---|---|---|---|
| ACTIVE | IDLE | CLEAN / NOT_REQUIRED | Yes |
| ACTIVE | IDLE | DIRTY / IN_CLEANING | No |
| ACTIVE | IN_USE | CLEAN / NOT_REQUIRED | No |
| IN_REPAIR | IDLE | CLEAN / DIRTY / NOT_REQUIRED | No |

共九种合法组合；requiresCleaning=false 仅允许 NOT_REQUIRED；true 不允许 NOT_REQUIRED。Available 是 derived property，不存独立字段。

START_USE 要求 available；END_USE 变 IDLE，需清洁则 DIRTY；清洁 DIRTY→IN_CLEANING→CLEAN，取消清洁回 DIRTY；维修启动进入 IN_REPAIR，成功完成恢复 ACTIVE 并保留清洁状态。Movement 至少要求 IDLE、mobile、目标不同；Assignment 目标 Person active。每次状态变更和历史写入应在同一事务内。

待确认：REPORT_CONTAMINATION 是否必须 ACTIVE；移动是否必须 ACTIVE；进行中维修取消/失败策略；维修并发锁策略；type/category 强制关系。不要把这些建议当成已确认规则。已否决 OUT_OF_SERVICE、独立 Return to Service、operationId、optionalId、purchaseDate、Person.role、Room composite key、单一 EquipmentStatus。

## 9. 日期、审计字段与 Optimistic Locking

```java
@Column(name = "assigned_at")
private LocalDateTime assignedAt;

@CreationTimestamp
@Column(name = "created_at", nullable = false, updatable = false)
private LocalDateTime createdAt;

@UpdateTimestamp
@Column(name = "updated_at", nullable = false)
private LocalDateTime updatedAt;

@Version
@Column(nullable = false)
private Long version;
```

`@CreationTimestamp`、`@UpdateTimestamp` 来自 `org.hibernate.annotations`，不是 JPA 标准；本项目用于 MaintenanceRecord 审计字段。默认 Hibernate 在 JVM 侧生成时间，不是 MySQL trigger；直接 SQL 更新不一定同步 updated_at。scheduledAt、startedAt、completedAt、canceledAt、changedAt 等业务时间由 Service 按事件赋值。LocalDateTime 无时区，对应 MySQL DATETIME；JSON 常见 ISO 本地时间格式，前端不要误认为 UTC。统一时区策略待确认。

`@Version` 是 JPA Optimistic Locking：Equipment 当前 version=3，两用户同时修改，先成功提交的更新使 version 增加，后者仍以旧 version 更新时检测冲突。Service/Exception Handler 后续转换为 HTTP 409。不要手动修改 version。只更新 MaintenanceRecord 而未修改 Equipment 时，不一定触发 Equipment 版本检查，因此它不能单独保证跨表维修唯一性。并发策略仍需设计与测试。

## 10. Validation、DTO、JSON 与分层

| Layer | 职责 | 示例 |
|---|---|---|
| Entity Mapping | 对象与表映射 | @Column、@JoinColumn、@Enumerated |
| Request DTO | 输入格式验证 | @NotBlank、@NotNull、@Size（jakarta.validation.constraints） |
| Controller | 触发输入验证 | @Valid @RequestBody |
| Service / Validator | 业务规则与跨表校验 | 是否允许移动、是否已在维修、actor 权限 |
| Database | 最后一道数据完整性约束 | PK、FK、UNIQUE、CHECK、NOT NULL |
| Response DTO | 控制输出形状 | 只返回所需字段，不返回 password |

Bean Validation 可以用于 Entity，但 V2 主要在 Request DTO 处理 API 输入；JPA Column 不是 Bean Validation。Jackson 的 `@JsonIgnore`、`@JsonManagedReference`、`@JsonBackReference` 控制 JSON，不控制数据库关系。双向 Entity 直接序列化可能递归、意外加载大量数据、暴露密码。V2 使用 DTO，不靠 Entity Jackson Annotation 解决这些问题。DTO 转换涉及 LAZY 字段时要在合适事务/查询边界内完成。

## 11. 老师 Art Gallery、Hospital V1、V2 对比

以下依据实际 ZIP 中 Artist、Artwork、UserProfile、Room、Equipment、UserAccount 源码；V2 依据已确认 Coding Standard。

| 知识点 | Art Gallery | Hospital V1 | V2 |
|---|---|---|---|
| ID | Artist/Artwork int；UserProfile Long | Long | Long + IDENTITY |
| 表名 | Artist/Artwork 隐式；UserProfile 显式 | 显式 | 所有显式 |
| 双向关系 | Artist.artworks mappedBy artist，final List | Room→Department LAZY | Department 两个 LAZY inverse lists，不 final |
| Fetch | Artwork.artist 默认 EAGER | Room.department LAZY | Room.department EAGER，其余 to-one LAZY |
| JSON | Artist/Artwork 使用 Jackson references | 已有 DTO 架构 | DTO 控制输出 |
| Cascade | Artwork→Details ALL | Account→Person 明确不采用 ALL | 不设置 cascade |
| 状态 | 非设备生命周期项目 | 单一 EquipmentStatus | 三维状态 + 独立 History |
| 时间 | UserProfile Timestamp + Hibernate audit annotations | Equipment LocalDate / LocalDateTime | LocalDateTime，维修自动审计 |

不要机械复制老师代码：Artwork/Details 的生命周期与 Account/Person 不同。V2 是基于真实业务与数据库约束选择写法。

## 12. 编译、启动、验证：本次真实排错过程

在 Windows PowerShell，进入含 pom.xml 和 mvnw.cmd 的项目根目录。

```powershell
java -version
.\mvnw.cmd -v
```

`mvn` 是 PATH 上的全局 Maven；`mvnw.cmd` 是项目 Maven Wrapper，`.\` 指当前目录，`.cmd` 是 Windows 脚本。macOS/Linux 常用 `./mvnw`。Wrapper 可自动获取项目指定 Maven，但仍需要 JDK。

本次错误：`release version 21 not supported`。原因：Terminal 的 Java 17 无法编译 pom 指定的 release 21；IntelliJ Project SDK/Maven Runner 与 Terminal JAVA_HOME 可以不同。

```powershell
$env:JAVA_HOME = "C:\Users\tq\.jdks\ms-21.0.12.1"
.\mvnw.cmd -v
.\mvnw.cmd clean compile
```

路径为本机示例，换电脑需替换。此设置只影响当前 PowerShell session；`java -version` 仍可能从 PATH 找到旧 Java，判断 Maven 实际 JDK 以 wrapper -v 为准。IDE 可在 Project Structure 和 Maven Importing/Runner 中设置 JDK。

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Database First 使用 validate：检查映射与现有 schema 的兼容性，不主动修改表。`none` 不验证；`update` 尝试改表；`create`/`create-drop` 可能破坏已有数据，不用于当前 seed 数据库。

```powershell
.\mvnw.cmd spring-boot:run
```

本次真实结果：21 个 Java 文件 BUILD SUCCESS；Java 21.0.12.1；Hibernate 6.6.45.Final；MySQL 8.0.46；HikariPool 启动；EntityManagerFactory 初始化；Tomcat 8080；应用 11.064 秒启动。0 repositories 正常（尚未开发）；默认 Security password 正常（尚未自定义认证）。不要将生成密码或真实数据库密码提交到 Git。

注意：日志未直接证明 ddl-auto 实际值，需检查 application.properties 或有效 profile；若确实为 validate，启动成功即表明 Hibernate schema validation 通过。validate 不保证所有 CHECK、UNIQUE、FK、索引、seed 内容或业务规则都正确。实际 CRUD、LAZY 行为、事务和并发仍需测试。若以后遇到 MySQL VARCHAR 与 Hibernate native ENUM 类型不兼容，先检查具体错误与映射，再决定是否显式使用 VARCHAR JDBC 类型，不预先改 schema。

停止运行：Ctrl+C。然后：

```powershell
git status
git add src/main/java
# 仅在有未提交 Entity 修改时执行 commit
git commit -m "feat: complete entity mappings and lifecycle enums"
git push
```

新 branch 首次 push 可能需 `git push -u origin HEAD`。README 放入仓库后单独提交，例如 `docs: document entity layer development workflow`。

## 13. 常见错误与面试自测

| 问题 | 应能解释的答案 |
|---|---|
| 为什么 FK field 是 Department 而非 Long？ | ORM 保存对象关联，数据库存 PK/FK；DTO 可用 ID |
| mappedBy 写 department_id 可以吗？ | 不行，应写目标 Entity 的 Java field 名 department |
| 为什么 List 不在 SQL 表里？ | inverse association 根据对方 FK 加载 |
| EAGER 一定 JOIN 吗？ | 不一定，可能额外 SELECT |
| LAZY 一定不会 N+1 吗？ | 不一定，循环访问关联可触发额外查询 |
| optional=true 会自动解除 FK 吗？ | 不会，只表示关系可空 |
| Cascade ALL 会删除 Person 吗？ | remove 可能传播，因此 Account 不使用 ALL |
| @Column(nullable=false) 等于 @NotNull 吗？ | 不等于，映射/数据库约束与 Bean Validation 不同 |
| 为什么 Enum 不用 ORDINAL？ | 避免序号随 Enum 调整而改变语义 |
| 为什么不直接返回 Entity？ | API 耦合、递归、LAZY 查询和敏感字段风险 |
| @Version 能防止所有并发问题吗？ | 不能，只保护参与版本检查的实体更新等操作 |
| BUILD SUCCESS 是否说明 DB 正确？ | 不能，还需启动映射验证与真实数据测试 |
| 为什么不在 Entity 写全部业务规则？ | 跨表、权限、历史、事务需要 Service 协调；DB 保证可表达的约束 |

## 14. 下一次项目可复制的 Checklist

- [ ] Business Rules 和 ERD 已确认，明确 pending decisions。
- [ ] 每个表 PK/FK/nullable/unique/check/type 已确认。
- [ ] Seed 已导入并 COMMIT，跨连接验证。
- [ ] 确定 package、ID 类型、命名、构造器、getter/setter 规范。
- [ ] 先写被依赖 Entity，再写引用它的 Entity；Enum 与 DB 对照。
- [ ] 每个 FK 对应正确的 @JoinColumn，确认 owning side。
- [ ] 每个 relationship 明确 direction、fetch、optional、cascade。
- [ ] 双向关系仅在需要时使用，mappedBy 对应 Java field。
- [ ] 时间类型、audit timestamps、version 策略已明确。
- [ ] DTO 与 Entity 分离，密码不序列化。
- [ ] JDK 与 pom release 匹配，clean compile 成功。
- [ ] 确认 ddl-auto=validate，应用启动成功。
- [ ] Git commit/push，记录尚未验证的内容。
- [ ] Repository 阶段测试真实读取、写入、查询及关联行为。

## 15. 本项目阶段交接与来源

已完成：九个 Entity、十一个 Enum、编译、MySQL 连接、应用启动。实际 CSV 共 1263 行（Department 12、Room 100、Person 60、Account 20、Equipment 200、StatusHistory 456、MovementHistory 188、AssignmentHistory 147、MaintenanceRecord 80）。下一步 Repository：先比较老师和 V1 写法，再确定 V2 查询需求。后续测试全部合法/非法状态组合、历史原子性、移动、分配、维修、并发、认证。

依据：`01_schema.sql`、九份 V2 CSV、`Hospital_Equipment_Tracking_V2_Master_Design_Updated.md`、`Hospital-be.zip`、老师 Art Gallery ZIP 中相关 Entity 源码、2026-11-26 启动日志及已确认的 Entity Coding Standard。Master 文件中的 Schema 尚未实施、旧日期字段和默认关系建议已被后续决定覆盖。本 README 记录后续决定，不修改 Master 或 Java 源文件。当前完整 V2 Java 源码未作为独立 ZIP 提供，故示例是已确认写法的教学整理，不是对整个当前仓库的重新审计。实际 validate 配置及 Git push 结果仍需用户确认。
