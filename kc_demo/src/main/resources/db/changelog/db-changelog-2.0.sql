--liquibase formatted sql

--changeset grisha:1
INSERT INTO employees.city (name, population)
VALUES ('New York', 8500000),
       ('Mogilev', 700000);
