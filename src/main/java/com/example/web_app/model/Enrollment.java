package com.example.web_app.model;

import java.io.Serializable;

public class Enrollment implements Serializable {
    private int id;
    private int userId;
    private int courseId;
    private float mark;

    public Enrollment(int id, int studentId, int courseId, float mark) {
        this.id = id;
        this.userId = studentId;
        this.courseId = courseId;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
}
