USE hospital_equipment_tracking_v2;
SELECT 'departments' AS table_name, COUNT(*) AS actual_count, 12 AS expected_count
FROM departments
UNION ALL
SELECT 'rooms' AS table_name, COUNT(*) AS actual_count, 100 AS expected_count
FROM rooms
UNION ALL
SELECT 'people' AS table_name, COUNT(*) AS actual_count, 60 AS expected_count
FROM people
UNION ALL
SELECT 'user_accounts' AS table_name, COUNT(*) AS actual_count, 20 AS expected_count
FROM user_accounts
UNION ALL
SELECT 'equipments' AS table_name, COUNT(*) AS actual_count, 200 AS expected_count
FROM equipments
UNION ALL
SELECT 'equipment_status_histories' AS table_name, COUNT(*) AS actual_count, 456 AS expected_count
FROM equipment_status_histories
UNION ALL
SELECT 'equipment_movement_histories' AS table_name, COUNT(*) AS actual_count, 188 AS expected_count
FROM equipment_movement_histories
UNION ALL
SELECT 'equipment_assignment_histories' AS table_name, COUNT(*) AS actual_count, 147 AS expected_count
FROM equipment_assignment_histories
UNION ALL
SELECT 'maintenance_records' AS table_name, COUNT(*) AS actual_count, 80 AS expected_count
FROM maintenance_records;
-- All following violation counts should be zero.
SELECT COUNT(*) AS repair_mismatches
FROM equipments e
WHERE (e.repair_status = 'IN_REPAIR') <>
      (SELECT COUNT(*) FROM maintenance_records m WHERE m.equipment_id = e.id AND m.maintenance_status = 'IN_PROGRESS')
   OR
    (SELECT COUNT(*) FROM maintenance_records m WHERE m.equipment_id = e.id AND m.maintenance_status = 'IN_PROGRESS') >
    1;
SELECT COUNT(*) AS movement_mismatches
FROM equipments e
         JOIN equipment_movement_histories h ON h.id = (SELECT id
                                                        FROM equipment_movement_histories
                                                        WHERE equipment_id = e.id
                                                        ORDER BY moved_at DESC, id DESC
                                                        LIMIT 1)
WHERE NOT (e.current_room_id <=> h.to_room_id)
   OR e.mobile = 0;
SELECT COUNT(*) AS assignment_mismatches
FROM equipments e
         JOIN equipment_assignment_histories h ON h.id = (SELECT id
                                                          FROM equipment_assignment_histories
                                                          WHERE equipment_id = e.id
                                                          ORDER BY changed_at DESC, id DESC
                                                          LIMIT 1)
WHERE NOT (e.assigned_person_id <=> h.to_person_id)
   OR NOT (e.assigned_at <=> IF(h.to_person_id IS NULL, NULL, h.changed_at));
SELECT COUNT(*) AS maintenance_date_mismatches
FROM equipments e
WHERE NOT (e.last_maintenance_at <=> (SELECT MAX(completed_at)
                                      FROM maintenance_records m
                                      WHERE m.equipment_id = e.id AND m.maintenance_status = 'COMPLETED'));
SELECT COUNT(*) AS usage_mismatches
FROM equipments e
WHERE e.usage_status <> COALESCE((SELECT to_usage_status
                                  FROM equipment_status_histories h
                                  WHERE h.equipment_id = e.id
                                    AND to_usage_status IS NOT NULL
                                  ORDER BY changed_at DESC, id DESC
                                  LIMIT 1), 'IDLE');
SELECT COUNT(*) AS cleaning_mismatches
FROM equipments e
WHERE e.cleaning_status <> COALESCE((SELECT to_cleaning_status
                                     FROM equipment_status_histories h
                                     WHERE h.equipment_id = e.id
                                       AND to_cleaning_status IS NOT NULL
                                     ORDER BY changed_at DESC, id DESC
                                     LIMIT 1), IF(e.requires_cleaning, 'CLEAN', 'NOT_REQUIRED'));
SELECT COUNT(*) AS repair_mismatches
FROM equipments e
WHERE e.repair_status <> COALESCE((SELECT to_repair_status
                                   FROM equipment_status_histories h
                                   WHERE h.equipment_id = e.id
                                     AND to_repair_status IS NOT NULL
                                   ORDER BY changed_at DESC, id DESC
                                   LIMIT 1), 'ACTIVE');
SELECT usage_status, cleaning_status, repair_status, COUNT(*) AS total
FROM equipments
GROUP BY 1, 2, 3;
