package com.example.web_app;

import database.DatabaseConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class Startup implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        String url = "jdbc:mysql://localhost/webApp";
        String username = "root";
        String password = "123456";
        DatabaseConfig databaseConfig = new DatabaseConfig(url, username, password);
        try {
            sce.getServletContext().setAttribute("databaseConfig", databaseConfig);
            Class.forName("com.mysql.cj.jdbc.Driver");
            sce.getServletContext().setAttribute("databaseConfig", databaseConfig);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
