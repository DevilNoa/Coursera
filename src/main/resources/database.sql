CREATE TABLE instructors (
    id_instructors INT  PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
------------
CREATE TABLE courses (
    id_courses INTEGER PRIMARY KEY,
    course_name VARCHAR(150),
    id_instructor INTEGER,
    total_time SMALLINT,
    credits SMALLINT,
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
------------
CREATE TABLE student_courses_xref (
    id_student NCHAR(10),
    id_course INTEGER,
    completion_date DATE,
    FOREIGN KEY (id_student) REFERENCES students(id_students),
    FOREIGN KEY (id_course) REFERENCES courses(id_courses)
);
------------
CREATE TABLE students (
    id_students NCHAR(10) PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DO $$
DECLARE
    i integer;
BEGIN
    FOR i IN 1..50 LOOP
        INSERT INTO students (id_students, first_name, last_name)
        VALUES (
            'F' || i::text,
            'Go6o' || i::text,
            'lo6o' || i::text
        );
    END LOOP;
END $$;

drop table students;

select * from students;
------------

CREATE TABLE users (
    username VARCHAR(30) PRIMARY KEY,
    password_hash VARCHAR(100) NOT NULL,
    salt VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_role VARCHAR NOT NULL
);
