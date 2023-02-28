package com.example.web_app.controller;


import com.example.web_app.dao.StudentDAO;
import com.example.web_app.model.Student;
import com.example.web_app.model.User;
import database.DatabaseConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "Students", value = "/students")
public class StudentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init(){
        DatabaseConfig databaseConfig = (DatabaseConfig) getServletContext().getAttribute("databaseConfig");
        studentDAO = new StudentDAO(databaseConfig);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "list":
                listStudents(request, response);
                break;
            case "new":
                showNewStudentForm(request, response);
                break;
            case "edit":
                showEditStudentForm(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "new":
                    addStudent(request, response);
                    break;
                case "edit":
                    updateStudent(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = studentDAO.getAll();
        request.setAttribute("students", students);
        request.getRequestDispatcher("views/list_students.jsp").forward(request, response);
    }

    private void showNewStudentForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/new_student.jsp").forward(request, response);
    }

    private void showEditStudentForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.read(id);
        if (student != null) {
            request.setAttribute("student", student);
            request.getRequestDispatcher("views/edit_student.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Student newStudent = new Student(new User(0,username,password,"STUDENT"));
        studentDAO.create(newStudent);
        response.sendRedirect(request.getContextPath() + "/students");

    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Student student = new Student(new User(id,username,password,"STUDENT"));
        studentDAO.update(student);
        response.sendRedirect(request.getContextPath() + "/students");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/students");
    }
    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }
}
