create table app_user
(
    id bigserial primary key,
    role varchar(20) not null,
    entity_id bigint not null,
    email varchar(200) unique not null,
    is_locked boolean default false,
    is_activated boolean default false,
    password varchar(25) default null
);

create table one_time_password
(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    otp_code VARCHAR(255) NOT NULL,
    expiration_date TIMESTAMP NOT NULL
);

INSERT INTO app_user (role, entity_id, email)
SELECT 'STUDENT', s.id, s.email
FROM students s;

INSERT INTO app_user (role, entity_id, email)
SELECT 'INSTRUCTOR', i.id, i.email
FROM instructors i;

INSERT INTO app_user (role, entity_id, email)
values ('ADMIN', 1, 'info_admin@uaic.ro');