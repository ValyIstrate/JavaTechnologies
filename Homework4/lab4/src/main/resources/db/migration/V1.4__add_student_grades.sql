create table student_grades
(
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT references students(id) not null,
    course_id BIGINT references courses(id) not null,
    grade double precision not null
);