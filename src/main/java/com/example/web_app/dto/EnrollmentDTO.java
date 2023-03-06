package com.example.web_app.dto;

public class EnrollmentDTO {
    private int id;
    private int userId;
    private String username;
    private int courseId;
    private String courseName;
    private float mark;

    public EnrollmentDTO(int id, int userId, String username, int courseId, String courseName, float mark) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.courseId = courseId;
        this.courseName = courseName;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
}
