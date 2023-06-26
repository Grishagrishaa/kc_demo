--liquibase formatted sql

--changeset grisha:1
INSERT INTO employees.contact_info (email, phone, city_id)
VALUES ('grisha@gmail.com.com', '+375299802357', 1),
       ('egor@gmail.com.com', '+375299802355', 2);

