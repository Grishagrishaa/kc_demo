--liquibase formatted sql

--changeset grisha:1
INSERT INTO employees.skills (name)
VALUES ('Java'),
       ('SMM');




