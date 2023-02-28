package com.example.web_app.dao;


import com.example.web_app.model.User;
import database.DatabaseConfig;
import com.example.web_app.model.Student;

import java.sql.*;
import java.util.*;


public class StudentDAO {

    private final String CREATE_QUERY = "INSERT INTO users(username, password, role) VALUES(?, ?, 'STUDENT')";
    private final String READ_QUERY = "SELECT * FROM users WHERE id=? AND role = 'STUDENT'";
    private final String UPDATE_QUERY = "UPDATE users SET username=?, password=? WHERE id=? AND role = 'STUDENT'";
    private final String DELETE_QUERY = "DELETE FROM users WHERE id=? AND role = 'STUDENT'";
    private final String GET_ALL_QUERY = "SELECT * FROM users WHERE role = 'STUDENT'";
    private DatabaseConfig databaseConfig;
    public StudentDAO(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    public Student create(Student student)  {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, student.getUsername());
            statement.setString(2, student.getPassword());
            if(statement.execute())
                return student;
            return null;
        } catch (SQLException  e) {
            e.printStackTrace();
            return null;
        }
    }

    public Student read(int id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                return new Student(new User(id,username,password,"STUDENT"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(Student student) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, student.getUsername());
            statement.setString(2, student.getPassword());
            statement.setInt(3, student.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                students.add(new Student(new User(id,username,password,"STUDENT")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
