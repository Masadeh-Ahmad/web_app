package com.example.web_app.dao;

import com.example.web_app.model.User;
import database.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    private final String GET_USER_QUERY = "INSERT INTO users(username, password, role) VALUES(?, ?, 'STUDENT')";
    private final String READ_QUERY = "SELECT * FROM users WHERE username=? AND password=? ";
    private DatabaseConfig databaseConfig;
    public LoginDAO(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    public User read(String username, String password) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
