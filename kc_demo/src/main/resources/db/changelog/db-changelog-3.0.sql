--liquibase formatted sql

--changeset grisha:1
INSERT INTO employees.address (street, city_id)
VALUES ('123 Main Street', 1),
       ('Yanki Luchiny 3', 2);
