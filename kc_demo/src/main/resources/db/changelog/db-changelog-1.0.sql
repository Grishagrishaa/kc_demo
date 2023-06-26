--liquibase formatted sql

--changeset grisha:1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


--changeset grisha:2
create schema if not exists employees;

--changeset grisha:3
CREATE TABLE employees.city
(
    id         BIGSERIAL PRIMARY KEY,
    population BIGINT,
    name       VARCHAR(255) UNIQUE
);

--changeset grisha:4
CREATE TABLE employees.address
(
    id      BIGSERIAL PRIMARY KEY,
    street  VARCHAR(255) UNIQUE,
    city_id BIGINT,
    CONSTRAINT fkpo044ng5x4gynb291cv24vtea FOREIGN KEY (city_id) REFERENCES employees.city
);

--changeset grisha:5
CREATE TABLE employees.contact_info (
    id      BIGSERIAL PRIMARY KEY,
    email   VARCHAR(255),
    phone   VARCHAR(255),
    city_id BIGINT,
    CONSTRAINT fkr5lf42r4r2ibwljqf05u0o4tn FOREIGN KEY (city_id) REFERENCES employees.city
);

--changeset grisha:6
CREATE TABLE employees.department (
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(255) UNIQUE,
    city_id BIGINT,
    CONSTRAINT fknavmwckikadl3isnfkrwv1rt9 FOREIGN KEY (city_id) REFERENCES employees.city
);

--changeset grisha:7
CREATE TABLE employees.skills (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

--changeset grisha:8
CREATE TABLE employees.employee (
    id              UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    name            VARCHAR(255),
    lastname        VARCHAR(255),
    age             INTEGER,
    salary          BIGINT,
    created_date    TIMESTAMP(6) WITH TIME ZONE,
    updated_date    TIMESTAMP(6) WITH TIME ZONE,
    created_by      VARCHAR(255),
    modified_by     VARCHAR(255),
    address_id      BIGINT UNIQUE,
    contact_info_id BIGINT UNIQUE,
    department_id   BIGINT,
    CONSTRAINT fkga73hdtpb67twlr9c1i337tyt FOREIGN KEY (address_id) REFERENCES employees.address,
    CONSTRAINT fk97agd27kwohowhjirrpyuc1u6 FOREIGN KEY (contact_info_id) REFERENCES employees.contact_info,
    CONSTRAINT fkbejtwvg9bxus2mffsm3swj3u9 FOREIGN KEY (department_id) REFERENCES employees.department
);

--changeset grisha:9
CREATE TABLE employees.employee_skills (
    skill_id    bigint NOT NULL,
    employee_id uuid   NOT NULL,
    CONSTRAINT fk8anwsnenk9d8nirjuov0ywinb FOREIGN KEY (skill_id) REFERENCES employees.skills,
    CONSTRAINT fknepe51hewn4dd673e3qk1v2qx FOREIGN KEY (employee_id) REFERENCES employees.employee,
    PRIMARY KEY (skill_id, employee_id)
);








