package com.example.web_app.dao;



import com.example.web_app.model.Course;
import database.DatabaseConfig;

import java.sql.*;
import java.util.*;

public class CourseDAO {

    private final String CREATE_QUERY = "INSERT INTO courses(id, course_name, instructor, description) VALUES(?, ?, ?, ?)";
    private final String READ_QUERY = "SELECT * FROM courses WHERE id=?";
    private final String UPDATE_QUERY = "UPDATE courses SET course_name=?, instructor=?, description=? WHERE id=?";
    private final String DELETE_QUERY = "DELETE FROM courses WHERE id=?";
    private final String GET_ALL_QUERY = "SELECT * FROM courses";
    private DatabaseConfig databaseConfig;

    public CourseDAO(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    public Course create(Course course) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setInt(1, course.getId());
            statement.setString(2, course.getCourseName());
            statement.setString(3, course.getInstructor());
            statement.setString(4, course.getDescription());
            statement.executeUpdate();
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Course read(int id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                String instructor = resultSet.getString("instructor");
                String description = resultSet.getString("description");
                return new Course(id, courseName, instructor, description);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Course update(Course course) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getInstructor());
            statement.setString(3, course.getDescription());
            statement.setInt(4, course.getId());
            statement.executeUpdate();
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(int id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String courseName = resultSet.getString("course_name");
                String instructor = resultSet.getString("instructor");
                String description = resultSet.getString("description");
                courses.add(new Course(id, courseName, instructor, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
