package com.example.web_app.controller;


import com.example.web_app.dao.UserDAO;
import com.example.web_app.model.User;
import database.DatabaseConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "Users", value = "/users")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init(){
        DatabaseConfig databaseConfig = (DatabaseConfig) getServletContext().getAttribute("databaseConfig");
        userDAO = new UserDAO(databaseConfig);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false).getAttribute("role") == null){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        String action = request.getParameter("action");
        if (action == null) {
            listUsers(request, response);
            return;
        }
        switch (action) {
            case "add":
                showNewUserForm(request, response);
                break;
            case "edit":
                showEditUserForm(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession(false).getAttribute("role") == null){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/users");
            return;
        }

        switch (action) {
            case "add":
                addUser(request, response);
                break;
            case "edit":
                updateUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users;
        if(request.getSession(false).getAttribute("role").equals("STUDENT")){
            users = new ArrayList<>();
            users.add(userDAO.read(Integer.parseInt(request.getSession(false).getAttribute("userId").toString())));
        }
        else {
            users = userDAO.getAll();
        }
        request.setAttribute("users", users);
        request.getRequestDispatcher("WEB-INF/views/users.jsp").forward(request, response);
    }

    private void showNewUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!request.getSession(false).getAttribute("role").equals("ADMIN")){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        request.getRequestDispatcher("WEB-INF/views/add_user.jsp").forward(request, response);
    }

    private void showEditUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getSession(false).getAttribute("role").toString();
        String userId = request.getParameter("id");
        String currentUserId = request.getSession(false).getAttribute("userId").toString();
        if(role.equals("STUDENT") && !currentUserId.equals(userId)){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        int id = Integer.parseInt(userId);
        User user = userDAO.read(id);
        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("WEB-INF/views/edit_user.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(!request.getSession(false).getAttribute("role").equals("ADMIN")){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        User newStudent = new User(0,username,password,role);
        User user = userDAO.create(newStudent);
        if(user != null)
            response.sendRedirect(request.getContextPath() + "/users");
        else
            showErrorPage(request, response, "Error adding user");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String role = request.getSession(false).getAttribute("role").toString();
        String username = request.getParameter("username");
        int currentUserId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
        String password = request.getParameter("password");
        User user = userDAO.read(id);
        if(role.equals("STUDENT") && currentUserId != id){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        if(role.equals("STUDENT")) {
            String oldPassword = request.getParameter("oldPassword");
            if(!user.getPassword().equals(oldPassword)){
                showErrorPage(request, response, "Wrong password");
                return;
            }
        }
        User newUser = new User(id,username,password,user.getRole());
        if(role.equals("ADMIN")){
            String newRole = request.getParameter("role");
            user.setRole(newRole);
        }
        if (userDAO.update(newUser)!=null) {
            if(id == currentUserId) {
                response.sendRedirect(request.getContextPath() + "/logout");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/users");
        }
        else
            showErrorPage(request, response, "Error adding user");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int currentUserId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
        userDAO.delete(id);
        if(currentUserId == id){
            response.sendRedirect(request.getContextPath() + "/logout");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/users");
    }
    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
    }
}
