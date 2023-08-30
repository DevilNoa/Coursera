package org.example.core;

public class Courses {
    private final int id_course;
    private final int in_instructor;
    private final String course_name;
    private final String time_created;
    private final short total_time;
    private final short credit;

    public Courses(int id_course, String course_name, String time_created, int in_instructor, short total_time, short credit) {
        this.id_course = id_course;
        this.course_name = course_name;
        this.time_created = time_created;
        this.in_instructor = in_instructor;
        this.total_time = total_time;
        this.credit = credit;
    }

    public int getId_course() {
        return id_course;
    }

    public int getId_instructor() {
        return in_instructor;
    }

    public String getCourse_name() {
        return course_name;
    }

    public short getTotal_time() {
        return total_time;
    }

    public short getCredit() {
        return credit;
    }

    @Override
    public String toString() {
        return String.format("Course[id_course=%d, course_name='%s', time_created='%s', in_instructor=%d, total_time=%d, credit=%d]",
                id_course, course_name, time_created, in_instructor, total_time, credit);
    }
}
