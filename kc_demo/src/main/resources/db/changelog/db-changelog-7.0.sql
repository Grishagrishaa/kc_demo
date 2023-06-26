--liquibase formatted sql

--changeset grisha:1
INSERT INTO employees.employee (id, name, lastname, age, salary, created_date, updated_date, created_by, modified_by, address_id, contact_info_id, department_id)
VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Grisha', 'Mitskevich', 30, 50000, '2023-06-25 19:11:39.570973 +00:00', '2023-06-25 19:11:39.570973 +00:00', 'bbc51cc5-457d-43b6-936d-d1bffbf2ed5d', 'bbc51cc5-457d-43b6-936d-d1bffbf2ed5d', 1, 1, 1),
       ('9f9e5bc8-55f6-4174-9b7a-d349a1563ea9', 'Egor', 'Avsei', 50, 30000, '2023-06-25 19:11:39.570973 +00:00', '2023-06-25 19:11:39.570973 +00:00', 'bbc51cc5-457d-43b6-936d-d1bffbf2ed5d', 'bbc51cc5-457d-43b6-936d-d1bffbf2ed5d', 2, 2, 2);


