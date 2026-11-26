-- Run from database directory using mysql --local-infile=1. Empty tables only.
USE hospital_equipment_tracking_v2;
START TRANSACTION;
LOAD DATA LOCAL INFILE 'data/departments.csv' INTO TABLE departments
CHARACTER SET utf8mb4 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY ''
LINES TERMINATED BY '\n' IGNORE 1 LINES
(@id, @name, @department_type)
SET id=NULLIF(@id, '__NULL__'), name=NULLIF(@name, '__NULL__'), department_type=NULLIF(@department_type, '__NULL__');
SHOW WARNINGS;
LOAD DATA LOCAL INFILE 'data/rooms.csv' INTO TABLE rooms
CHARACTER SET utf8mb4 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY ''
LINES TERMINATED BY '\n' IGNORE 1 LINES
(@id, @name, @department_id)
SET id=NULLIF(@id, '__NULL__'), name=NULLIF(@name, '__NULL__'), department_id=NULLIF(@department_id, '__NULL__');
SHOW WARNINGS;
LOAD DATA LOCAL INFILE 'data/people.csv' INTO TABLE people
CHARACTER SET utf8mb4 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY ''
LINES TERMINATED BY '\n' IGNORE 1 LINES
(@id, @first_name, @last_name, @employee_id, @active, @department_id)
SET id=NULLIF(@id, '__NULL__'), first_name=NULLIF(@first_name, '__NULL__'), last_name=NULLIF(@last_name, '__NULL__'), employee_id=NULLIF(@employee_id, '__NULL__'), active=NULLIF(@active, '__NULL__'), department_id=NULLIF(@department_id, '__NULL__');
SHOW WARNINGS;
LOAD DATA LOCAL INFILE 'data/user_accounts.csv' INTO TABLE user_accounts
CHARACTER SET utf8mb4 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY ''
LINES TERMINATED BY '\n' IGNORE 1 LINES
(@id, @username, @password, @user_role, @enabled, @person_id)
SET id=NULLIF(@id, '__NULL__'), username=NULLIF(@username, '__NULL__'), password=NULLIF(@password, '__NULL__'), user_role=NULLIF(@user_role, '__NULL__'), enabled=NULLIF(@enabled, '__NULL__'), person_id=NULLIF(@person_id, '__NULL__');
SHOW WARNINGS;
LOAD DATA LOCAL INFILE 'data/equipments.csv' INTO TABLE equipments
CHARACTER SET utf8mb4 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY ''
LINES TERMINATED BY '\n' IGNORE 1 LINES
(@id, @name, @asset_tag, @serial_number, @type, @category, @mobile, @requires_cleaning, @usage_status, @cleaning_status, @repair_status, @current_room_id, @department_id, @assigned_person_id, @assigned_at, @version, @last_maintenance_at)
SET id=NULLIF(@id, '__NULL__'), name=NULLIF(@name, '__NULL__'), asset_tag=NULLIF(@asset_tag, '__NULL__'), serial_number=NULLIF(@serial_number, '__NULL__'), type=NULLIF(@type, '__NULL__'), category=NULLIF(@category, '__NULL__'), mobile=NULLIF(@mobile, '__NULL__'), requires_cleaning=NULLIF(@requires_cleaning, '__NULL__'), usage_status=NULLIF(@usage_status, '__NULL__'), cleaning_status=NULLIF(@cleaning_status, '__NULL__'), repair_status=NULLIF(@repair_status, '__NULL__'), current_room_id=NULLIF(@current_room_id, '__NULL__'), department_id=NULLIF(@department_id, '__NULL__'), assigned_person_id=NULLIF(@assigned_person_id, '__NULL__'), assigned_at=NULLIF(@assigned_at, '__NULL__'), version=NULLIF(@version, '__NULL__'), last_maintenance_at=NULLIF(@last_maintenance_at, '__NULL__');
SHOW WARNINGS;
LOAD DATA LOCAL INFILE 'data/equipment_status_histories.csv' INTO TABLE equipment_status_histories
CHARACTER SET utf8mb4 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY ''
LINES TERMINATED BY '\n' IGNORE 1 LINES
(@id, @equipment_id, @action, @from_usage_status, @to_usage_status, @from_cleaning_status, @to_cleaning_status, @from_repair_status, @to_repair_status, @changed_by_user_id, @changed_at, @notes)
SET id=NULLIF(@id, '__NULL__'), equipment_id=NULLIF(@equipment_id, '__NULL__'), action=NULLIF(@action, '__NULL__'), from_usage_status=NULLIF(@from_usage_status, '__NULL__'), to_usage_status=NULLIF(@to_usage_status, '__NULL__'), from_cleaning_status=NULLIF(@from_cleaning_status, '__NULL__'), to_cleaning_status=NULLIF(@to_cleaning_status, '__NULL__'), from_repair_status=NULLIF(@from_repair_status, '__NULL__'), to_repair_status=NULLIF(@to_repair_status, '__NULL__'), changed_by_user_id=NULLIF(@changed_by_user_id, '__NULL__'), changed_at=NULLIF(@changed_at, '__NULL__'), notes=NULLIF(@notes, '__NULL__');
SHOW WARNINGS;
LOAD DATA LOCAL INFILE 'data/equipment_movement_histories.csv' INTO TABLE equipment_movement_histories
CHARACTER SET utf8mb4 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY ''
LINES TERMINATED BY '\n' IGNORE 1 LINES
(@id, @equipment_id, @from_room_id, @to_room_id, @moved_by_user_id, @moved_at, @notes)
SET id=NULLIF(@id, '__NULL__'), equipment_id=NULLIF(@equipment_id, '__NULL__'), from_room_id=NULLIF(@from_room_id, '__NULL__'), to_room_id=NULLIF(@to_room_id, '__NULL__'), moved_by_user_id=NULLIF(@moved_by_user_id, '__NULL__'), moved_at=NULLIF(@moved_at, '__NULL__'), notes=NULLIF(@notes, '__NULL__');
SHOW WARNINGS;
LOAD DATA LOCAL INFILE 'data/equipment_assignment_histories.csv' INTO TABLE equipment_assignment_histories
CHARACTER SET utf8mb4 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY ''
LINES TERMINATED BY '\n' IGNORE 1 LINES
(@id, @equipment_id, @from_person_id, @to_person_id, @action, @changed_by_user_id, @changed_at, @notes)
SET id=NULLIF(@id, '__NULL__'), equipment_id=NULLIF(@equipment_id, '__NULL__'), from_person_id=NULLIF(@from_person_id, '__NULL__'), to_person_id=NULLIF(@to_person_id, '__NULL__'), action=NULLIF(@action, '__NULL__'), changed_by_user_id=NULLIF(@changed_by_user_id, '__NULL__'), changed_at=NULLIF(@changed_at, '__NULL__'), notes=NULLIF(@notes, '__NULL__');
SHOW WARNINGS;
LOAD DATA LOCAL INFILE 'data/maintenance_records.csv' INTO TABLE maintenance_records
CHARACTER SET utf8mb4 FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY ''
LINES TERMINATED BY '\n' IGNORE 1 LINES
(@id, @equipment_id, @maintenance_type, @maintenance_status, @scheduled_at, @started_at, @completed_at, @canceled_at, @requested_by_person_id, @performed_by, @description, @notes, @created_at, @updated_at)
SET id=NULLIF(@id, '__NULL__'), equipment_id=NULLIF(@equipment_id, '__NULL__'), maintenance_type=NULLIF(@maintenance_type, '__NULL__'), maintenance_status=NULLIF(@maintenance_status, '__NULL__'), scheduled_at=NULLIF(@scheduled_at, '__NULL__'), started_at=NULLIF(@started_at, '__NULL__'), completed_at=NULLIF(@completed_at, '__NULL__'), canceled_at=NULLIF(@canceled_at, '__NULL__'), requested_by_person_id=NULLIF(@requested_by_person_id, '__NULL__'), performed_by=NULLIF(@performed_by, '__NULL__'), description=NULLIF(@description, '__NULL__'), notes=NULLIF(@notes, '__NULL__'), created_at=NULLIF(@created_at, '__NULL__'), updated_at=NULLIF(@updated_at, '__NULL__');
SHOW WARNINGS;
COMMIT;
