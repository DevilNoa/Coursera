CREATE TABLE instructors (
    id_instructors INT  PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE courses (
    id_courses INTEGER PRIMARY KEY,
    course_name VARCHAR(150),
    id_instructor INTEGER,
    total_time SMALLINT,
    credits SMALLINT,
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE student_courses_xref (
    id_student NCHAR(10),
    id_course INTEGER,
    completion_date DATE,
    FOREIGN KEY (id_student) REFERENCES students(id_students),
    FOREIGN KEY (id_course) REFERENCES courses(id_courses)
);

CREATE TABLE students (
    id_students NCHAR(10) PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users (
    username VARCHAR(30) PRIMARY KEY,
    password_hash VARCHAR(100) NOT NULL,
    salt VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP table users;



SELECT
    CONCAT(s.first_name, ' ', s.last_name) AS "Student Name",
    tc.total_credits AS "Total Credit",
    c.course_name AS "Course Name",
    c.total_time AS "Total Time",
    CASE WHEN scx.completion_date IS NOT NULL THEN c.credits END AS "Credit (if completed)",
    CONCAT(i.first_name, ' ', i.last_name) AS "Instructor Name"
FROM
    students s
JOIN
    student_courses_xref scx ON s.id_students = scx.id_student
JOIN
    courses c ON scx.id_course = c.id_courses
JOIN
    instructors i ON c.id_instructor = i.id_instructors
LEFT JOIN (
    SELECT
        s.id_students,
        SUM(CASE WHEN scx.completion_date IS NOT NULL THEN c.credits ELSE 0 END) AS total_credits
    FROM
        students s
    JOIN
        student_courses_xref scx ON s.id_students = scx.id_student
    JOIN
        courses c ON scx.id_course = c.id_courses
    GROUP BY
        s.id_students
) tc ON s.id_students = tc.id_students
ORDER BY
    s.id_students, c.course_name;


SELECT
    CONCAT(s.first_name, ' ', s.last_name) AS "Student Name",
    tc.total_credits AS "Total Credit",
    c.course_name AS "Course Name",
    c.total_time AS "Total Time",
    CASE WHEN scx.completion_date IS NOT NULL THEN c.credits END AS "Credit (if completed)",
    CONCAT(i.first_name, ' ', i.last_name) AS "Instructor Name"
FROM
    students s
JOIN
    student_courses_xref scx ON s.id_students = scx.id_student
JOIN
    courses c ON scx.id_course = c.id_courses
JOIN
    instructors i ON c.id_instructor = i.id_instructors
LEFT JOIN (
    SELECT
        s.id_students,
        c.credits AS total_credits
    FROM
        students s
    JOIN
        student_courses_xref scx ON s.id_students = scx.id_student
    JOIN
        courses c ON scx.id_course = c.id_courses
    WHERE
        scx.completion_date IS NOT NULL
) tc ON s.id_students = tc.id_students
ORDER BY
    s.id_students, c.course_name;


alter table users
    add user_role varchar;
SELECT * from courses;
