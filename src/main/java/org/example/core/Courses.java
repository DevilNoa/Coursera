package org.example.core;

import java.util.Set;

public class Courses {
    private int id_courses;
    private String course_name;
    private int id_instructor;
    private String time_created;
    private short total_time;
    private short credit;
    private Set<User> users;

    public Courses(int id_courses, String course_name, int id_instructor, String time_created, short total_time, short credit) {
    }

    public Courses(int id_courses, String course_name, int id_instructor, String time_created, short total_time, short credit, Set<User> users) {
        this.id_courses = id_courses;
        this.course_name = course_name;
        this.id_instructor = id_instructor;
        this.time_created = time_created;
        this.total_time = total_time;
        this.credit = credit;
        this.users = users;
    }

    public int getId_courses() {
        return id_courses;
    }

    public void setId_courses(int id_courses) {
        this.id_courses = id_courses;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getId_instructor() {
        return id_instructor;
    }

    public void setId_instructor(int id_instructor) {
        this.id_instructor = id_instructor;
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }

    public short getTotal_time() {
        return total_time;
    }

    public void setTotal_time(short total_time) {
        this.total_time = total_time;
    }

    public short getCredit() {
        return credit;
    }

    public void setCredit(short credit) {
        this.credit = credit;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return String.format("Course[id_courses=%d, course_name='%s', time_created='%s', id_instructor=%d, total_time=%d, credit=%d]", id_courses, course_name, time_created, id_instructor, total_time, credit);
    }
}

