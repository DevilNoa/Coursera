package org.example.core;


public class Courses {
    private int id_courses;
    private String course_name;
    private int id_instructor;
    private String time_created;
    private short total_time;
    private short credit;

    public Courses(int id_courses, String course_name, int id_instructor, String time_created, short total_time, short credit) {
        this.id_courses = id_courses;
        this.course_name = course_name;
        this.id_instructor = id_instructor;
        this.time_created = time_created;
        this.total_time = total_time;
        this.credit = credit;
    }

    public Courses() {
    }

    public int getId_courses() {
//        System.out.println(id_courses+":inside getID_course");
        return id_courses;
    }


    public String getCourse_name() {
        return course_name;
    }


    public int getId_instructor() {
//        System.out.println(id_instructor+":inside get_idInstructor");
        return id_instructor;
    }


    public String getTime_created() {
        return time_created;
    }


    public short getTotal_time() {
//        System.out.println(total_time+":inside total time ");
        return total_time;
    }


    public short getCredit() {
        return credit;
    }


}
