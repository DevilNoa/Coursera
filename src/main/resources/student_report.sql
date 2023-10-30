-- student_report.sql
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
        (? IS NULL OR s.id_students = ANY(?))
        AND (SUM(c.credits) >= ?)
        AND (scx.completion_date BETWEEN ? AND ?)
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