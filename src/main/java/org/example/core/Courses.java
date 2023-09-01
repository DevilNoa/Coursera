package org.example.core;

public class Courses {
    private int id_courses;
    private String course_name;
    private int id_instructor;
    private String time_created;
    private short total_time;
    private short credit;

    public Courses() {
    }

    public Courses(int id_courses, String course_name, int id_instructor, String time_created, short total_time, short credit) {
        this.id_courses = id_courses;
        this.course_name = course_name;
        this.id_instructor = id_instructor;
        this.time_created = time_created;
        this.total_time = total_time;
        this.credit = credit;
    }

    public int getId_courses() {
        return id_courses;
    }

    public String getCourse_name() {
        return course_name;
    }

    public int getId_instructor() {
        return id_instructor;
    }

    public String getTime_created() {
        return time_created;
    }

    public short getTotal_time() {
        return total_time;
    }

    public short getCredit() {
        return credit;
    }

    @Override
    public String toString() {
        return String.format("Course[id_courses=%d, course_name='%s', time_created='%s', id_instructor=%d, total_time=%d, credit=%d]", id_courses, course_name, time_created, id_instructor, total_time, credit);
    }
}

