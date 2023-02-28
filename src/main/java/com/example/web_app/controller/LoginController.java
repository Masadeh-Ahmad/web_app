package com.example.web_app.controller;

import com.example.web_app.dao.LoginDAO;
import com.example.web_app.dao.StudentDAO;
import com.example.web_app.model.LoginForm;
import com.example.web_app.model.Student;
import com.example.web_app.model.User;
import database.DatabaseConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    LoginDAO loginDAO;

    public void init(){
        DatabaseConfig databaseConfig = (DatabaseConfig) getServletContext().getAttribute("databaseConfig");
        loginDAO = new LoginDAO(databaseConfig);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if(request.getSession().getAttribute("role") != null){
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        else {
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the login form data from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = loginDAO.read(username,password);

        if (user != null) {
            request.getSession().setAttribute("userId",user.getId());
            request.getSession().setAttribute("username",user.getUsername());
            request.getSession().setAttribute("role",user.getRole());
        } else {
            request.setAttribute("error", "Invalid username or password");
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}