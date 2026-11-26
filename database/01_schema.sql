-- Hospital Equipment Tracking V2 | MySQL 8.0.16+
-- Run once against the empty V2 database. No DROP statements.
USE
    hospital_equipment_tracking_v2;

CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_type VARCHAR(50) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_id BIGINT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE people (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    employee_id VARCHAR(50) UNIQUE,
    active BOOLEAN NOT NULL,
    department_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES departments (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE user_accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_role VARCHAR(30) NOT NULL,
    enabled BOOLEAN NOT NULL,
    person_id BIGINT UNIQUE,
    FOREIGN KEY (person_id) REFERENCES people (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE equipments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    asset_tag VARCHAR(100) NOT NULL UNIQUE,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    mobile BOOLEAN NOT NULL,
    requires_cleaning BOOLEAN NOT NULL,
    usage_status VARCHAR(30) COLLATE utf8mb4_bin NOT NULL,
    cleaning_status VARCHAR(30) COLLATE utf8mb4_bin NOT NULL,
    repair_status VARCHAR(30) COLLATE utf8mb4_bin NOT NULL,
    current_room_id BIGINT,
    department_id BIGINT NOT NULL,
    assigned_person_id BIGINT,
    assigned_at DATETIME,
    version BIGINT NOT NULL DEFAULT 0,
    last_maintenance_at DATETIME,
    FOREIGN KEY (current_room_id) REFERENCES rooms (id),
    FOREIGN KEY (department_id) REFERENCES departments (id),
    FOREIGN KEY (assigned_person_id) REFERENCES people (id),
    CONSTRAINT ck_equipment_usage CHECK (usage_status IN ('IDLE', 'IN_USE')),
    CONSTRAINT ck_equipment_cleaning CHECK (cleaning_status IN ('NOT_REQUIRED', 'CLEAN', 'DIRTY', 'IN_CLEANING')),
    CONSTRAINT ck_equipment_repair CHECK (repair_status IN ('ACTIVE', 'IN_REPAIR')),
    CONSTRAINT ck_equipment_requires_cleaning CHECK (
        (requires_cleaning = 0 AND cleaning_status = 'NOT_REQUIRED') OR
        (requires_cleaning = 1 AND cleaning_status <> 'NOT_REQUIRED')),
    CONSTRAINT ck_equipment_in_use CHECK (usage_status <> 'IN_USE' OR
                                          (repair_status = 'ACTIVE' AND cleaning_status IN ('CLEAN', 'NOT_REQUIRED'))),
    CONSTRAINT ck_equipment_in_repair CHECK (repair_status <> 'IN_REPAIR' OR
                                             (usage_status = 'IDLE' AND cleaning_status <> 'IN_CLEANING')),
    CONSTRAINT ck_equipment_assignment CHECK (
        (assigned_person_id IS NULL AND assigned_at IS NULL) OR
        (assigned_person_id IS NOT NULL AND assigned_at IS NOT NULL)),
    CONSTRAINT ck_equipment_mobile CHECK (mobile IN (0, 1)),
    INDEX ix_equipment_type (type),
    INDEX ix_equipment_category (category)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE equipment_status_histories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,
    from_usage_status VARCHAR(30) COLLATE utf8mb4_bin,
    to_usage_status VARCHAR(30) COLLATE utf8mb4_bin,
    from_cleaning_status VARCHAR(30) COLLATE utf8mb4_bin,
    to_cleaning_status VARCHAR(30) COLLATE utf8mb4_bin,
    from_repair_status VARCHAR(30) COLLATE utf8mb4_bin,
    to_repair_status VARCHAR(30) COLLATE utf8mb4_bin,
    changed_by_user_id BIGINT NOT NULL,
    changed_at DATETIME NOT NULL,
    notes VARCHAR(500),
    FOREIGN KEY (equipment_id) REFERENCES equipments (id),
    FOREIGN KEY (changed_by_user_id) REFERENCES user_accounts (id),
    CONSTRAINT ck_history_usage CHECK (
        (from_usage_status IS NULL AND to_usage_status IS NULL) OR
        (from_usage_status IS NOT NULL AND to_usage_status IS NOT NULL AND
         from_usage_status IN ('IDLE', 'IN_USE') AND to_usage_status IN ('IDLE', 'IN_USE') AND
         from_usage_status <> to_usage_status)),
    CONSTRAINT ck_history_cleaning CHECK (
        (from_cleaning_status IS NULL AND to_cleaning_status IS NULL) OR
        (from_cleaning_status IS NOT NULL AND to_cleaning_status IS NOT NULL AND
         from_cleaning_status IN ('NOT_REQUIRED', 'CLEAN', 'DIRTY', 'IN_CLEANING') AND
         to_cleaning_status IN ('NOT_REQUIRED', 'CLEAN', 'DIRTY', 'IN_CLEANING') AND
         from_cleaning_status <> to_cleaning_status)),
    CONSTRAINT ck_history_repair CHECK (
        (from_repair_status IS NULL AND to_repair_status IS NULL) OR
        (from_repair_status IS NOT NULL AND to_repair_status IS NOT NULL AND
         from_repair_status IN ('ACTIVE', 'IN_REPAIR') AND to_repair_status IN ('ACTIVE', 'IN_REPAIR') AND
         from_repair_status <> to_repair_status)),
    CONSTRAINT ck_history_change CHECK (to_usage_status IS NOT NULL OR
                                        to_cleaning_status IS NOT NULL OR to_repair_status IS NOT NULL),
    INDEX ix_status_history_time (equipment_id, changed_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE equipment_movement_histories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    from_room_id BIGINT,
    to_room_id BIGINT NOT NULL,
    moved_by_user_id BIGINT NOT NULL,
    moved_at DATETIME NOT NULL,
    notes VARCHAR(500),
    FOREIGN KEY (equipment_id) REFERENCES equipments (id),
    FOREIGN KEY (from_room_id) REFERENCES rooms (id),
    FOREIGN KEY (to_room_id) REFERENCES rooms (id),
    FOREIGN KEY (moved_by_user_id) REFERENCES user_accounts (id),
    CONSTRAINT ck_movement_rooms CHECK (from_room_id IS NULL OR from_room_id <> to_room_id),
    INDEX ix_movement_history_time (equipment_id, moved_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE equipment_assignment_histories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    from_person_id BIGINT,
    to_person_id BIGINT,
    action VARCHAR(30) COLLATE utf8mb4_bin NOT NULL,
    changed_by_user_id BIGINT NOT NULL,
    changed_at DATETIME NOT NULL,
    notes VARCHAR(500),
    FOREIGN KEY (equipment_id) REFERENCES equipments (id),
    FOREIGN KEY (from_person_id) REFERENCES people (id),
    FOREIGN KEY (to_person_id) REFERENCES people (id),
    FOREIGN KEY (changed_by_user_id) REFERENCES user_accounts (id),
    CONSTRAINT ck_assignment_action CHECK (
        (action = 'ASSIGN' AND from_person_id IS NULL AND to_person_id IS NOT NULL) OR
        (action = 'UNASSIGN' AND from_person_id IS NOT NULL AND to_person_id IS NULL) OR
        (action = 'REASSIGN' AND from_person_id IS NOT NULL AND to_person_id IS NOT NULL AND
         from_person_id <> to_person_id)),
    INDEX ix_assignment_history_time (equipment_id, changed_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE maintenance_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    maintenance_type VARCHAR(50) NOT NULL,
    maintenance_status VARCHAR(30) COLLATE utf8mb4_bin NOT NULL,
    scheduled_at DATETIME,
    started_at DATETIME,
    completed_at DATETIME,
    canceled_at DATETIME,
    requested_by_person_id BIGINT NOT NULL,
    performed_by VARCHAR(150),
    description TEXT,
    notes TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (equipment_id) REFERENCES equipments (id),
    FOREIGN KEY (requested_by_person_id) REFERENCES people (id),
    CONSTRAINT ck_maintenance_status CHECK (maintenance_status IN
                                            ('SCHEDULED', 'IN_PROGRESS', 'COMPLETED', 'CANCELED')),
    CONSTRAINT ck_maintenance_dates CHECK (
        (maintenance_status = 'SCHEDULED' AND scheduled_at IS NOT NULL AND started_at IS NULL AND
         completed_at IS NULL AND canceled_at IS NULL) OR
        (maintenance_status = 'IN_PROGRESS' AND started_at IS NOT NULL AND completed_at IS NULL AND
         canceled_at IS NULL) OR
        (maintenance_status = 'COMPLETED' AND started_at IS NOT NULL AND completed_at IS NOT NULL AND
         canceled_at IS NULL) OR
        (maintenance_status = 'CANCELED' AND canceled_at IS NOT NULL AND completed_at IS NULL)),
    CONSTRAINT ck_maintenance_date_order CHECK (
        updated_at >= created_at AND
        (completed_at IS NULL OR completed_at >= started_at) AND
        (canceled_at IS NULL OR started_at IS NULL OR canceled_at >= started_at)),
    INDEX ix_maintenance_equipment_status (equipment_id, maintenance_status)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Cross-table invariants and transition rules belong in transactional services.
-- Cancellation of in-progress maintenance remains a pending business decision.
SHOW
    TABLES;
