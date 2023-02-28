package com.example.web_app.model;

import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String courseName;
    private String instructor;
    private String description;

    public Course(int id, String courseName, String instructor, String description) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
