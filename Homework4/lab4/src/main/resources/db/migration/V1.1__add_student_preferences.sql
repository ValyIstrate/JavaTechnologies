CREATE TABLE student_course_preferences
(
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT references students(id),
    course_id BIGINT references courses(id),
    course_rank INT not null
);