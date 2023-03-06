package com.example.web_app.dao;

import com.example.web_app.model.Course;
import com.example.web_app.model.User;
import database.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final String CREATE_QUERY = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";
    private final String READ_QUERY = "SELECT * FROM users WHERE id=?";
    private final String UPDATE_QUERY = "UPDATE users SET username=?, password=? ,role=? WHERE id=?";
    private final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
    private final String GET_ALL_QUERY = "SELECT * FROM users";
    private final String GET_LAST_INSERT = "SELECT * FROM users WHERE id = LAST_INSERT_ID()";
    private DatabaseConfig databaseConfig;
    public UserDAO(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    public User create(User user)  {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.execute();
            PreparedStatement selectStmt  = connection.prepareStatement(GET_LAST_INSERT);
            ResultSet resultSet = selectStmt.executeQuery();
            resultSet.next();
            return new User(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                return new User(id,username,password,role);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean update(User user) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setInt(4, user.getId());
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

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                users.add(new User(id,username,password,role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
