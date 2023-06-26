--liquibase formatted sql

--changeset grisha:1
INSERT INTO employees.department (name, city_id)
VALUES ('Development', 1),
       ('Sales', 2);



