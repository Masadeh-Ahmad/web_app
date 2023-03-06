package com.example.web_app.dao;


import com.example.web_app.model.Enrollment;
import database.DatabaseConfig;

import java.sql.*;
import java.util.*;

public class EnrollmentDAO {

    private final String CREATE_QUERY = "INSERT INTO enrollment(user_id, course_id, mark) VALUES(?, ?, ?)";
    private final String GET_LAST_INSERT = "SELECT * FROM enrollment WHERE id = LAST_INSERT_ID()";
    private final String READ_QUERY = "SELECT * FROM enrollment WHERE id=?";
    private final String CHECK_QUERY = "SELECT * FROM enrollment WHERE user_id=? AND course_id=?";
    private final String UPDATE_QUERY = "UPDATE enrollment SET user_id=?, course_id=?, mark=? WHERE id=?";
    private final String DELETE_QUERY = "DELETE FROM enrollment WHERE id=?";
    private final String GET_ALL_QUERY = "SELECT * FROM enrollment";
    private final String GET_USER_ALL_QUERY = "SELECT * FROM enrollment  WHERE user_id=?";

    private DatabaseConfig databaseConfig;
    public EnrollmentDAO(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }
    public Enrollment create(Enrollment enrollment) {
        if(check(enrollment.getUserId(), enrollment.getCourseId())){
            try (Connection connection = databaseConfig.getConnection();
                 PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
                statement.setInt(1, enrollment.getUserId());
                statement.setInt(2, enrollment.getCourseId());
                statement.setFloat(3, enrollment.getMark());
                statement.execute();
                PreparedStatement selectStmt  = connection.prepareStatement(GET_LAST_INSERT);
                ResultSet resultSet = selectStmt.executeQuery();
                resultSet.next();
                return new Enrollment(resultSet.getInt(1),enrollment.getUserId(), enrollment.getCourseId(), enrollment.getMark());
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
       return null;
    }
    public Boolean check(int userId, int courseId){
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_QUERY)) {
            statement.setInt(1,userId);
            statement.setInt(2,courseId);
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public Enrollment read(int id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int enrollmentId = resultSet.getInt("id");
                int studentId = resultSet.getInt("user_id");
                int courseId = resultSet.getInt("course_id");
                float mark = resultSet.getFloat("mark");
                return new Enrollment(enrollmentId, studentId, courseId, mark);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Enrollment update(Enrollment enrollment) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, enrollment.getUserId());
            statement.setInt(2, enrollment.getCourseId());
            statement.setFloat(3, enrollment.getMark());
            statement.setInt(4, enrollment.getId());
            statement.executeUpdate();
            return enrollment;
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

    public List<Enrollment> getAll() {
        List<Enrollment> enrollments = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int enrollmentId = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int courseId = resultSet.getInt("course_id");
                float mark = resultSet.getFloat("mark");
                enrollments.add(new Enrollment(enrollmentId, userId, courseId, mark));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }
    public List<Enrollment> getAllForUser(int id) {
        List<Enrollment> enrollments = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_ALL_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int enrollmentId = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int courseId = resultSet.getInt("course_id");
                float mark = resultSet.getFloat("mark");
                enrollments.add(new Enrollment(enrollmentId, userId, courseId, mark));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }
}


