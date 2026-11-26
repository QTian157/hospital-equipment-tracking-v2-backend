-- Hospital Equipment Tracking V2 | CSV imports
-- Run from the database directory, with empty tables only.
-- mysql -u hospital_v2_user -p --local-infile=1
-- If local_infile is OFF, an administrator must run SET GLOBAL local_infile = 1.
-- Copy and execute each LOAD statement and its checks separately.
-- Stop if warnings occur or the row count differs from the expected count.
USE hospital_equipment_tracking_v2;
SHOW GLOBAL VARIABLES LIKE 'local_infile';
START TRANSACTION;

-- Import departments (12 records)
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
SELECT COUNT(*)
FROM departments;

-- Import rooms (100 records)
LOAD DATA LOCAL INFILE 'data/rooms.csv'
    INTO TABLE rooms
    CHARACTER SET utf8mb4
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY ''
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (id, name, department_id);
SHOW WARNINGS;
SELECT COUNT(*)
FROM rooms;

-- Import people (60 records)
LOAD DATA LOCAL INFILE 'data/people.csv'
    INTO TABLE people
    CHARACTER SET utf8mb4
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY ''
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (id, first_name, last_name, @employee_id, active, @department_id)
    SET employee_id = NULLIF(@employee_id, '__NULL__'),
        department_id = NULLIF(@department_id, '__NULL__');
SHOW WARNINGS;
SELECT COUNT(*)
FROM people;

-- Import user_accounts (20 records)
LOAD DATA LOCAL INFILE 'data/user_accounts.csv'
    INTO TABLE user_accounts
    CHARACTER SET utf8mb4
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY ''
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (id, username, password, user_role, enabled, @person_id)
    SET person_id = NULLIF(@person_id, '__NULL__');
SHOW WARNINGS;
SELECT COUNT(*)
FROM user_accounts;

-- Import equipments (200 records)
LOAD DATA LOCAL INFILE 'data/equipments.csv'
    INTO TABLE equipments
    CHARACTER SET utf8mb4
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY ''
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (id, name, asset_tag, serial_number, type, category, mobile, requires_cleaning, usage_status, cleaning_status,
     repair_status, @current_room_id, department_id, @assigned_person_id, @assigned_at, version, @last_maintenance_at)
    SET current_room_id = NULLIF(@current_room_id, '__NULL__'),
        assigned_person_id = NULLIF(@assigned_person_id, '__NULL__'),
        assigned_at = NULLIF(@assigned_at, '__NULL__'),
        last_maintenance_at = NULLIF(@last_maintenance_at, '__NULL__');
SHOW WARNINGS;
SELECT COUNT(*)
FROM equipments;

-- Import equipment_status_histories (456 records)
LOAD DATA LOCAL INFILE 'data/equipment_status_histories.csv'
    INTO TABLE equipment_status_histories
    CHARACTER SET utf8mb4
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY ''
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (id, equipment_id, action, @from_usage_status, @to_usage_status, @from_cleaning_status, @to_cleaning_status,
     @from_repair_status, @to_repair_status, changed_by_user_id, changed_at, @notes)
    SET from_usage_status = NULLIF(@from_usage_status, '__NULL__'),
        to_usage_status = NULLIF(@to_usage_status, '__NULL__'),
        from_cleaning_status = NULLIF(@from_cleaning_status, '__NULL__'),
        to_cleaning_status = NULLIF(@to_cleaning_status, '__NULL__'),
        from_repair_status = NULLIF(@from_repair_status, '__NULL__'),
        to_repair_status = NULLIF(@to_repair_status, '__NULL__'),
        notes = NULLIF(@notes, '__NULL__');
SHOW WARNINGS;
SELECT COUNT(*)
FROM equipment_status_histories;

-- Import equipment_movement_histories (188 records)
LOAD DATA LOCAL INFILE 'data/equipment_movement_histories.csv'
    INTO TABLE equipment_movement_histories
    CHARACTER SET utf8mb4
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY ''
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (id, equipment_id, @from_room_id, to_room_id, moved_by_user_id, moved_at, @notes)
    SET notes = NULLIF(@notes, '__NULL__'),
        from_room_id = NULLIF(@from_room_id, '__NULL__');
SHOW WARNINGS;
SELECT COUNT(*)
FROM equipment_movement_histories;

-- Import equipment_assignment_histories (147 records)
LOAD DATA LOCAL INFILE 'data/equipment_assignment_histories.csv'
    INTO TABLE equipment_assignment_histories
    CHARACTER SET utf8mb4
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY ''
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (id, equipment_id, @from_person_id, @to_person_id, action, changed_by_user_id, changed_at, @notes)
    SET from_person_id = NULLIF(@from_person_id, '__NULL__'),
        to_person_id = NULLIF(@to_person_id, '__NULL__'),
        notes = NULLIF(@notes, '__NULL__');
SHOW WARNINGS;
SELECT COUNT(*)
FROM equipment_assignment_histories;

-- Import maintenance_records (80 records)
LOAD DATA LOCAL INFILE 'data/maintenance_records.csv'
    INTO TABLE maintenance_records
    CHARACTER SET utf8mb4
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
    ESCAPED BY ''
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (id, equipment_id, maintenance_type, maintenance_status, @scheduled_at, @started_at, @completed_at, @canceled_at,
     requested_by_person_id, @performed_by, description, @notes, created_at, updated_at)
    SET scheduled_at = NULLIF(@scheduled_at, '__NULL__'),
        started_at = NULLIF(@started_at, '__NULL__'),
        completed_at = NULLIF(@completed_at, '__NULL__'),
        canceled_at = NULLIF(@canceled_at, '__NULL__'),
        performed_by = NULLIF(@performed_by, '__NULL__'),
        notes = NULLIF(@notes, '__NULL__');
SHOW WARNINGS;
SELECT COUNT(*)
FROM maintenance_records;

-- Run 03_verify_data.sql in this same session before committing.
-- If all checks pass, execute COMMIT; otherwise execute ROLLBACK;
-- COMMIT;
-- ROLLBACK;
