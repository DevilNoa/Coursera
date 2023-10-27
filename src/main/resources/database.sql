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
---------------------
-----------------------------------------------------------------------
--this work only if the id_students and ompletion_date have data in  --
-----------------------------------------------------------------------
WITH student_report AS (
    SELECT
        s.id_students AS student_id,
        s.first_name || ' ' || s.last_name AS student_name,
        SUM(c.credits) AS total_credits,
        array_agg(c.course_name) AS course_names,
        array_agg(c.total_time) AS total_times,
        array_agg(c.credits) AS course_credits,
        array_agg(i.first_name || ' ' || i.last_name) AS instructor_names,
        scx.completion_date
    FROM
        students s
    INNER JOIN
        student_courses_xref scx ON s.id_students = scx.id_student
    INNER JOIN
        courses c ON scx.id_course = c.id_courses
    INNER JOIN
        instructors i ON c.id_instructor = i.id_instructors
    GROUP BY
        s.id_students, scx.completion_date
    HAVING
        (s.id_students = ANY(array['F1', 'F2', 'F4', 'F12', 'F3']) OR array['F1', 'F2', 'F4', 'F12', 'F3'] IS NULL)
        AND (SUM(c.credits) >= 6 OR 6 IS NULL)
        AND (scx.completion_date BETWEEN '2023-10-01' AND '2023-10-10' OR ('2023-10-01' IS NULL AND '2023-10-10' IS NULL))
)
SELECT
    student_name,
    total_credits,
    unnest(course_names) AS course_name,
    unnest(total_times) AS total_time,
    unnest(course_credits) AS course_credit,
    unnest(instructor_names) AS instructor_name,
    completion_date
FROM
    student_report;

    --i need to see how to modify it to accept both of the parameters
    WITH student_report AS (
        SELECT
            s.id_students AS student_id,
            s.first_name || ' ' || s.last_name AS student_name,
            SUM(c.credits) AS total_credits,
            array_agg(c.course_name) AS course_names,
            array_agg(c.total_time) AS total_times,
            array_agg(c.credits) AS course_credits,
            array_agg(i.first_name || ' ' || i.last_name) AS instructor_names,
            scx.completion_date
        FROM
            students s
        INNER JOIN
            student_courses_xref scx ON s.id_students = scx.id_student
        INNER JOIN
            courses c ON scx.id_course = c.id_courses
        INNER JOIN
            instructors i ON c.id_instructor = i.id_instructors
        GROUP BY
            s.id_students, scx.completion_date
        HAVING
            (:student_ids IS NULL OR s.id_students = ANY(:student_ids))
            AND (SUM(c.credits) >= 6)
            AND (scx.completion_date BETWEEN '2023-10-01' AND '2023-10-10')
    )
    SELECT
        student_name,
        total_credits,
        unnest(course_names) AS course_name,
        unnest(total_times) AS total_time,
        unnest(course_credits) AS course_credit,
        unnest(instructor_names) AS instructor_name,
        completion_date
    FROM
        student_report;