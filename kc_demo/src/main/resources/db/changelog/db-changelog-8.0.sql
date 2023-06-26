--liquibase formatted sql

--changeset grisha:1
INSERT INTO employees.employee_skills (skill_id, employee_id)
VALUES (1, '123e4567-e89b-12d3-a456-426614174000'),
       (2, '9f9e5bc8-55f6-4174-9b7a-d349a1563ea9');




