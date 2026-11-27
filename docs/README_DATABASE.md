# Hospital Equipment Tracking V2 — Database Setup & Review

> Database 阶段复习笔记 | Windows · IntelliJ IDEA · MySQL 8 · Spring Boot 3.5.13 · Java 21 · Maven
>
> 当前状态：已完成数据库创建、连接配置、Schema 和 Seed Data 导入（本人确认）。本文用于复习和在新环境重建，不要在已有数据的数据库中重复执行初始化。

## 1. 整体流程：我是怎么建立数据库的？

```text
Business Rules → ERD → Relational Schema → MySQL Database & User
→ Spring Boot Connection → CREATE TABLE → CSV Seed Data
→ LOAD DATA LOCAL INFILE → Verification → COMMIT → Git
→ 下一阶段：JPA Entity / Repository
```

V2 使用独立数据库 `hospital_equipment_tracking_v2`，保留 V1 数据库。SQL 管理 Schema，Spring Boot 负责后续业务读写，而不是自动生成数据库结构。

## 2. 创建 Database 和专用 User

通过 MySQL Workbench 或 MySQL CLI 使用管理员账号（例如 root）连接本机 MySQL Server。以下为可复现的初始化命令；密码使用自己的值，不要复制占位符作为真实密码。

```sql
CREATE DATABASE hospital_equipment_tracking_v2
    CHARACTER SET utf8mb4;

CREATE USER 'hospital_v2_user'@'localhost'
    IDENTIFIED BY 'YOUR_LOCAL_PASSWORD';

GRANT ALL PRIVILEGES ON hospital_equipment_tracking_v2.*
    TO 'hospital_v2_user'@'localhost';

SHOW GRANTS FOR 'hospital_v2_user'@'localhost';
SHOW DATABASES;
```

- `CREATE DATABASE`：创建独立的数据库容器，此时还没有 Table。
- `CREATE USER`：创建 MySQL 登录身份，不是应用中的 UserAccount。
- `@localhost`：限定从本机连接的账号身份。
- `GRANT`：授权该用户访问 V2 数据库，不授予整个服务器的管理员权限。这里给出本地开发用的数据库级授权示例；生产环境应进一步限制权限。
- `utf8mb4`：支持完整 Unicode 字符。
- `SHOW GRANTS`：检查授权；通过 CREATE USER/GRANT 修改权限不需要额外 FLUSH PRIVILEGES。

在 Workbench 或 IntelliJ Database 面板新建 MySQL Data Source：Host `localhost`，Port `3306`，User `hospital_v2_user`，Database `hospital_equipment_tracking_v2`，输入本地密码并 Test Connection。IDE 连接成功不等于 Spring Boot 已经连接成功，两者需要分别测试。

## 3. Spring Boot 连接数据库

项目使用 Spring Boot、Spring Data JPA、MySQL Connector/J；本次项目 Java 21、Maven，已有 Spring Security 和 DevTools。根目录（与 pom.xml 同级）创建 `.env`：

```properties
DB_HOST=localhost
DB_PORT=3306
DB_NAME=hospital_equipment_tracking_v2
DB_USER=hospital_v2_user
DB_PASS=YOUR_LOCAL_PASSWORD
```

在 `.gitignore` 添加 `.env`，避免真实密码进入 Git。可另外提交不含真实密码的 `.env.example`（建议，并非本次已确认完成的操作）。

`src/main/resources/application.properties`：

```properties
spring.application.name=hospital-equipment-tracking
spring.config.import=optional:file:.env[.properties]
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.open-in-view=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

| 配置 | 作用 |
|---|---|
| spring.config.import | 将根目录 .env 作为 properties 文件加载；optional 表示文件缺失时不因导入本身报错，但缺少必要变量仍可能导致启动失败 |
| datasource.url | JDBC 连接地址 |
| datasource.username/password | 数据库登录凭证 |
| ddl-auto=validate | Hibernate 验证已映射的 Entity 与现有 Schema，不创建或修改表 |
| open-in-view=false | 不让 JPA Session 延续到整个 Web 请求，后续在 Service Transaction 内处理需要的数据 |
| show-sql / format_sql | 开发时输出并格式化 Hibernate SQL |

从项目根目录运行 Spring Boot（IDE Run 或 `./mvnw spring-boot:run`；Windows PowerShell 可用 `.\mvnw.cmd spring-boot:run`，前提是项目包含 Maven Wrapper）。本次启动日志已确认 Hikari 连接 MySQL、Tomcat 8080 启动。尚未建立 Entity 时，启动成功不代表所有表的 JPA Mapping 已验证。Spring Security 默认密码也不是 MySQL 密码。

## 4. 项目文件结构

```text
hospital-equipment-tracking_be/
├── .env                         # 本地凭证，不提交
├── .gitignore
├── pom.xml
├── src/main/resources/application.properties
└── database/
    ├── 01_schema.sql             # DDL：创建表及约束
    ├── 02_import_data.sql        # DML：批量导入 CSV
    ├── 03_verify_data.sql        # 查询并验证数据
    ├── README_DATABASE.md        # 本复习笔记（建议存放位置）
    └── data/                     # 九份 CSV，每张表一份
```

## 5. 从 ERD 到 Schema

ERD 表达 Entity、Relationship 和 Cardinality；Schema 将它们落实为 MySQL Table、Column、Primary Key、Foreign Key 和 Constraints。

在 Workbench/IntelliJ Query Console 对空 V2 数据库执行 `01_schema.sql`。脚本包含 `USE hospital_equipment_tracking_v2;`，不包含 DROP，不用于反复重建已有表。

例如：

```sql
CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_type VARCHAR(50) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_id BIGINT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
```

| SQL 概念 | 含义 |
|---|---|
| BIGINT AUTO_INCREMENT PRIMARY KEY | 自增唯一主键，Java 通常对应 Long |
| VARCHAR(n) / TEXT | 有长度限制的字符串 / 长文本 |
| BOOLEAN / DATETIME | 布尔值 / 日期时间 |
| NOT NULL / UNIQUE | 必填 / 唯一性；MySQL UNIQUE nullable 字段允许多个 NULL |
| FOREIGN KEY | 保证引用的父表记录存在 |
| CHECK | 限制合法字段值及同一行的合法组合（MySQL 8.0.16+ 执行检查） |
| INDEX | 加速查询；不是业务规则 |
| InnoDB | 支持事务和外键 |

### 九张表及依赖顺序

| Table | 职责 | 主要父表 | Seed 数量 |
|---|---|---|---:|
| departments | 固定归属科室 | — | 12 |
| rooms | 物理位置 | departments | 100 |
| people | 人员 | departments（可空） | 60 |
| user_accounts | 登录账号 | people（可空且唯一） | 20 |
| equipments | 设备当前状态 | departments, rooms, people | 200 |
| equipment_status_histories | 生命周期状态变化 | equipments, user_accounts | 456 |
| equipment_movement_histories | 房间移动历史 | equipments, rooms, user_accounts | 188 |
| equipment_assignment_histories | 责任人分配历史 | equipments, people, user_accounts | 147 |
| maintenance_records | 维护维修记录 | equipments, people | 80 |
| **Total** | | | **1263** |

### V2 设计要点

- Department 是固定 owning department，不因移动而改变；current_room_id 是当前位置；assigned_person_id 是责任人，不等于使用者。
- UsageStatus：IDLE / IN_USE；CleaningStatus：NOT_REQUIRED / CLEAN / DIRTY / IN_CLEANING；RepairStatus：ACTIVE / IN_REPAIR。
- IN_USE 必须 ACTIVE 且 CLEAN 或 NOT_REQUIRED；IN_REPAIR 必须 IDLE 且不在 IN_CLEANING。
- requires_cleaning 与 NOT_REQUIRED 必须一致；assigned_person_id 与 assigned_at 同时为空或同时有值。
- 每次生命周期状态变更写一条 EquipmentStatusHistory，未变化维度的 from/to 均为 NULL；不引入 operationId。
- Movement 只允许 IDLE 且 mobile 的设备；数据库检查 from/to 不相同，Service 负责操作时的状态检查。
- Assignment 的 ASSIGN / REASSIGN / UNASSIGN 对应不同 from/to person 组合。
- MaintenanceStatus：SCHEDULED / IN_PROGRESS / COMPLETED / CANCELED；时间字段统一 scheduled_at、started_at、completed_at、canceled_at。
- version BIGINT DEFAULT 0 为后续 JPA @Version 预留；仅有数据库字段并不等于已实现乐观锁。
- 跨表一致性、历史写入、状态转换由后续 Transactional Service 保证；取消进行中维护的业务规则仍待确认。

检查实际数据库：

```sql
USE hospital_equipment_tracking_v2;
SHOW TABLES;
DESCRIBE maintenance_records;
SHOW CREATE TABLE equipments;
```

注意：修改本地 01_schema.sql 不会自动修改已经创建的 Table。实际结构需要通过 DESCRIBE / SHOW CREATE TABLE 确认；已有数据库变更需要明确的 ALTER/migration，而不是重新执行 CREATE TABLE。

## 6. 准备 Seed Data

Seed Data 是开发和演示用的模拟初始数据，不是真实患者数据。此次使用 AI 辅助生成九份 CSV，再通过 Schema Constraints 和验证 SQL 检查。CSV 第一行是 Header，数据使用逗号分隔、UTF-8、LF 换行；`__NULL__` 是约定的空值占位字符串。

示意（非实际记录）：

```csv
id,first_name,last_name,employee_id,active,department_id
1,Jane,Doe,EMP001,1,2
2,John,Doe,__NULL__,1,__NULL__
```

CSV 存在项目中不代表数据已进入 MySQL。必须执行 INSERT 或 LOAD DATA。这里采用老师课程的 MySQL CLI + LOAD DATA LOCAL INFILE 方法，适合批量导入。

Seed ZIP 的原始说明记录了 BCrypt demo password `HospitalDemo123!`，账号 admin、staff01–staff18、guest，staff18 disabled。仅供本地演示；应用端登录功能尚待实现和测试，不是数据库登录密码。

## 7. Import Script 逐行理解

以 departments 为例：

```sql
LOAD DATA LOCAL INFILE 'data/departments.csv'
    INTO TABLE departments
    CHARACTER SET utf8mb4
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY ''
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (id, name, department_type);
SHOW WARNINGS;
SELECT COUNT(*) FROM departments;
```

| 语句 | 含义 |
|---|---|
| LOAD DATA LOCAL INFILE | 从 MySQL Client 所在电脑读取文件；相对路径基于 CLI 启动目录 |
| INTO TABLE | 指定目标表 |
| CHARACTER SET utf8mb4 | 指定输入字符编码 |
| FIELDS TERMINATED BY ',' | 逗号分隔字段 |
| OPTIONALLY ENCLOSED BY '"' | 识别可选双引号，例如 `"Storage, Clean"` 是一个字段 |
| ESCAPED BY '' | 禁用默认反斜杠转义，配合本次 CSV 格式 |
| LINES TERMINATED BY '\n' | LF 换行分隔记录 |
| IGNORE 1 ROWS | 跳过 CSV Header |
| (id, name, department_type) | 按位置映射 CSV 字段到表列，不按 Header 自动匹配 |
| ; | 结束整条 SQL Statement |
| SHOW WARNINGS | 立即检查上一个导入语句的警告 |
| SELECT COUNT(*) | 检查目标表记录数 |

### Nullable Fields：@variable + NULLIF

```text
(id, first_name, last_name, @employee_id, active, @department_id)
SET employee_id = NULLIF(@employee_id, '__NULL__'),
    department_id = NULLIF(@department_id, '__NULL__');
```

`@employee_id` 是 MySQL Session User Variable，先接收 CSV 原始值；`SET` 将处理后的值写入真正的列。`NULLIF(a,b)` 在 a=b 时返回 SQL NULL，否则返回 a。普通字符串 `__NULL__` 不等于 SQL NULL。

本次最后补充的两处转换：

```text
-- equipments 的 Column Mapping 中使用 @current_room_id
SET current_room_id = NULLIF(@current_room_id, '__NULL__'),
    assigned_person_id = NULLIF(@assigned_person_id, '__NULL__'),
    assigned_at = NULLIF(@assigned_at, '__NULL__'),
    last_maintenance_at = NULLIF(@last_maintenance_at, '__NULL__');

-- equipment_movement_histories 的 Mapping 中使用 @from_room_id
SET notes = NULLIF(@notes, '__NULL__'),
    from_room_id = NULLIF(@from_room_id, '__NULL__');
```

上述 SET 是各自 LOAD DATA 的片段，不应单独执行。to_room_id 必填，不需要空值转换。Maintenance 的 scheduled_at / completed_at 与本地最终 Schema 保持一致。未来若 CSV 的 nullable description 也使用 `__NULL__`，需增加对应转换；当前脚本直接映射 description。

## 8. Terminal 导入：完整操作顺序

### 8.1 启用 Server 的 local_infile（管理员）

在有权限的 MySQL Session 中：

```sql
SHOW GLOBAL VARIABLES LIKE 'local_infile';
SET GLOBAL local_infile = ON;
```

仅在 OFF 时需要开启。普通项目用户通常没有修改全局变量的权限。这个设置影响整个服务器，不是某张表的设置。

### 8.2 从 IntelliJ Terminal 进入 database 目录

```powershell
cd 'C:\Hospital Tracking\hospital-equipment-tracking_be\database'
dir
mysql -u hospital_v2_user -p --local-infile=1
```

若已经在项目根目录，只需 `cd database`。`-u` 指定用户，`-p` 交互输入密码（不显示字符），`--local-infile=1` 启用 Client 读取本地文件。Client 和 Server 都要允许 LOCAL INFILE。

登录后出现 `mysql>`，此时输入 SQL，而不是 PowerShell 命令。

### 8.3 选择数据库并开启事务

```sql
USE hospital_equipment_tracking_v2;
SHOW TABLES;
SHOW GLOBAL VARIABLES LIKE 'local_infile';
START TRANSACTION;
```

确认是空表再导入。按照 02_import_data.sql 的顺序逐段复制九个 LOAD DATA 语句及其 SHOW WARNINGS、COUNT 检查。不要重复执行 START TRANSACTION，也不要在已有数据的表中重跑导入。

之所以逐张导入，是因为 LOCAL 可能将某些数据错误降为 Warning；出现 Warning、Error 或数量不一致时应停止并检查，而不是盲目继续。父表必须先导入，才能满足 Foreign Key。

### 8.4 在同一个 mysql> Session 验证

```text
SOURCE 03_verify_data.sql;
```

SOURCE 是 MySQL CLI 命令，读取并执行文件内容；不是通用 SQL。也可以逐条复制验证 SQL。必须在同一连接中验证尚未提交的事务。

### 8.5 提交或撤销

```sql
COMMIT;
```

只有全部检查通过才执行。若导入失败且尚未提交，则执行 `ROLLBACK;` 撤销本次 InnoDB 数据导入。不要两条都执行。CREATE TABLE 等 DDL 通常隐式提交，不属于这里可撤销的 Seed Data 事务。

```text
EXIT;
```

退出 CLI。导入结束后，建议由管理员恢复原来的 local_infile 设置（若原本 OFF，则 `SET GLOBAL local_infile = OFF;`）。

## 9. Verification：不仅检查数量

`03_verify_data.sql` 使用 COUNT(*)、UNION ALL 汇总九张表的 actual_count 与 expected_count；预期总数 1263。还检查：

| 检查 | 预期 |
|---|---|
| IN_REPAIR 与 IN_PROGRESS maintenance 对应，且进行中记录不超过一条 | 0 mismatches |
| 最新 movement 的 to_room_id 与 current_room_id 一致，且设备可移动 | 0 |
| 最新 assignment 的 to_person_id / changed_at 与当前责任人 / assigned_at 一致 | 0 |
| last_maintenance_at 与最近 COMPLETED 的 completed_at 一致 | 0 |
| Usage / Cleaning / Repair 各维度最后一次状态变更与当前状态一致 | 各 0 |
| 按三维状态 GROUP BY 统计 | 用于检查数据分布，不要求为 0 |

`<=>` 是 MySQL NULL-safe equality，两个 NULL 也可以判断为相等。`COALESCE` 用于没有历史时的默认状态。现有验证脚本并不证明所有历史链连续或所有历史操作均符合当时的业务状态；这些仍需 Service 测试补充。

提交后可以在另一个连接中运行 `SELECT COUNT(*) FROM equipments;`（预期 200），确认数据已经持久化。

## 10. 常见问题

| 问题 | 检查方式 |
|---|---|
| Access denied | 用户名、密码、host、SHOW GRANTS |
| LOCAL INFILE disabled | Server local_infile 和 Client --local-infile=1 |
| File not found | Terminal 是否从 database 目录启动；CSV 路径是否正确 |
| Unknown column scheduled_at | DESCRIBE 检查实际表是否仍使用旧的 scheduled_date |
| Incorrect integer/datetime | CSV 字段顺序、NULLIF、数据格式、换行符 |
| Foreign key violation | 父表导入顺序、引用 ID 是否存在 |
| Duplicate entry | 是否重复导入、PK/UNIQUE 是否重复 |
| Spring Boot schema validation error | Entity 映射与真实 Schema 是否一致 |

不要为了导入成功而随意关闭 Foreign Key 或删除 CHECK Constraints。

## 11. Git Milestone

```bash
git status
git check-ignore .env
git ls-files .env
```

确认 .env 被忽略且未被跟踪（最后一条应无输出），检查待提交文件后：

```bash
git add database .gitignore src/main/resources/application.properties
git commit -m "Complete V2 database schema and seed data setup"
git push
```

这是建议的提交方式，不代表已验证远端 Git 状态。如果 .env 曾被提交，仅添加 .gitignore 不会移除历史凭证，需要另行处理并更换密码。

## 12. 下一阶段与复习重点

数据库初始化完成后，Java Entity + Enum → Repository → DTO / Exception → Service → Controller → Security → Tests。JPA Entity 对应已有 Table；@ManyToOne 等关系对应 FK；@Version 对应 version；@Transactional 保证设备更新与 History 写入原子性。

复习时应能独立回答：Database User 与应用 UserAccount 的区别；Schema 与 Seed 的区别；为何父表先导入；LOCAL 的含义；@variable / NULLIF 的用途；为什么 ddl-auto=validate；为什么验证后才 COMMIT；哪些规则由 DB 负责、哪些由 Service 负责。

## 13. 依据与版本说明

依据本次项目讨论、最终贴出的 02_import_data.sql、更新后的 Schema 内容、Master Technical Design、Seed ZIP 中的 CSV/README/03_verify_data.sql。早期上传的 SQL 文件可能仍有旧字段名或缺少两处 NULL 转换；以本地最终修订版为准。本文不修改任何 SQL 或数据库。用户已确认导入完成，但本文编写时没有直接连接本地 MySQL，未独立核实最终 COMMIT、验证输出、实际授权语句或 Git Push；相应命令作为复现步骤记录。
