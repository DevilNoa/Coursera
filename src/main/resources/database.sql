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
                                      FOREIGN KEY (id_course) REFERENCES courses(id_courses),
                                      first_name VARCHAR(50),
                                      last_name VARCHAR(50)
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
    SUM(CASE WHEN scx.completion_date IS NOT NULL THEN c.credits ELSE 0 END) AS "Total Credits",
    c.course_name AS "Course Name",
    c.total_time AS "Total Time",
    CASE WHEN scx.completion_date IS NOT NULL THEN c.credits END AS "Credits (if completed)",
    CONCAT(i.first_name, ' ', i.last_name) AS "Instructor Name"
FROM
    students s
        JOIN
    student_courses_xref scx ON s.id_students = scx.id_student
        JOIN
    courses c ON scx.id_course = c.id_courses
        JOIN
    instructors i ON c.id_instructor = i.id_instructors
WHERE
    (s.id_students IN (1, 2) OR 1 IS NULL OR 2 IS NULL)
  AND (c.credits >= 3 OR 3 IS NULL)
  AND (scx.completion_date BETWEEN '2023-09-04' AND '2023-10-04' OR '2023-09-04' IS NULL OR '2023-10-04' IS NULL)
GROUP BY
    s.id_students, c.course_name
ORDER BY
    s.id_students, c.course_name;




alter table users
    add user_role varchar;
SELECT * from courses;


CREATE OR REPLACE FUNCTION update_student_name()
RETURNS TRIGGER AS $$
BEGIN
    -- Update the first_name and last_name columns in student_courses_xref
UPDATE student_courses_xref AS scx
SET
    first_name = s.first_name,
    last_name = s.last_name
    FROM students AS s
WHERE
    scx.id_student = s.id_students
  AND NEW.id_student = s.id_students;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER update_student_name_trigger
    AFTER INSERT ON student_courses_xref
    FOR EACH ROW
    EXECUTE FUNCTION update_student_name();


SET search_path TO dropwizard_task, public;


--------------------------------------------
-- Define input parameters
DO $$
DECLARE
StudentList TEXT = '1,2'; -- Replace with the list of student PINs (e.g., 'PIN1,PIN2,PIN3')
    MinimumCredit INT = 50;
    StartDate DATE = '2023-01-01'; -- Replace with the desired start date
    EndDate DATE = '2023-12-31'; -- Replace with the desired end date
BEGIN
    -- Calculate student reports
WITH StudentReport AS (
    SELECT
        CONCAT(s.first_name, ' ', s.last_name) AS "Student Name",
        SUM(CASE WHEN scx.completion_date BETWEEN StartDate AND EndDate THEN c.credits ELSE 0 END) AS "Total Credit"
    FROM
        student_courses_xref scx
            LEFT JOIN
        students s ON scx.id_student = s.id_students
            LEFT JOIN
        courses c ON scx.id_course = c.id_courses
    WHERE
            s.id_students IN (SELECT unnest(string_to_array(StudentList, ','))) OR StudentList = ''
    GROUP BY
        s.id_students, s.first_name, s.last_name
    HAVING
            SUM(CASE WHEN scx.completion_date BETWEEN StartDate AND EndDate THEN c.credits ELSE 0 END) >= MinimumCredit
)
-- Retrieve course details for each selected student
SELECT
    "Student Name",
    c.course_name AS "Course Name",
    CONCAT(i.first_name, ' ', i.last_name) AS "Instructor Name",
    c.total_time AS "Total Time",
    c.credits AS "Credit"
FROM
    StudentReport sr
        LEFT JOIN
    student_courses_xref scx ON sr."Student Name" = CONCAT(scx.first_name, ' ', scx.last_name)
        LEFT JOIN
    courses c ON scx.id_course = c.id_courses
        LEFT JOIN
    instructors i ON c.id_instructor = i.id_instructors
WHERE
    scx.completion_date BETWEEN StartDate AND EndDate
ORDER BY
    "Student Name", "Course Name";
END $$;

