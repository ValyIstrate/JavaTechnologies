CREATE TABLE instructors (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(200) NOT NULL,
                             email VARCHAR(200) UNIQUE NOT NULL
);

CREATE TABLE packs (
                       id BIGSERIAL PRIMARY KEY,
                       year INTEGER NOT NULL,
                       semester INTEGER NOT NULL,
                       name VARCHAR(200) NOT NULL
);

CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         type VARCHAR(20) NOT NULL,
                         code VARCHAR(50) NOT NULL UNIQUE,
                         abbr VARCHAR(50),
                         name VARCHAR(255) NOT NULL,
                         instructor_id BIGINT REFERENCES instructors(id),
                         pack_id BIGINT REFERENCES packs(id),
                         group_count INTEGER,
                         description TEXT
);

CREATE TABLE students (
                          id BIGSERIAL PRIMARY KEY,
                          code VARCHAR(50) UNIQUE NOT NULL,
                          name VARCHAR(200) NOT NULL,
                          email VARCHAR(200) UNIQUE NOT NULL,
                          year INTEGER NOT NULL
);

CREATE TABLE student_course (
                                          id BIGSERIAL PRIMARY KEY,
                                          student_id BIGINT REFERENCES students(id) ON DELETE CASCADE,
                                          pack_id BIGINT REFERENCES packs(id) ON DELETE CASCADE,
                                          course_id BIGINT REFERENCES courses(id)
);

CREATE INDEX idx_course_pack ON courses(pack_id);
CREATE INDEX idx_course_instructor ON courses(instructor_id);
