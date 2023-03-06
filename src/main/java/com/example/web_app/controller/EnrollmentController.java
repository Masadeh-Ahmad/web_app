package com.example.web_app.controller;



import com.example.web_app.dao.UserDAO;
import com.example.web_app.dto.EnrollmentDTO;
import com.example.web_app.dao.CourseDAO;
import com.example.web_app.dao.EnrollmentDAO;
import com.example.web_app.model.*;
import database.DatabaseConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Enrollment", value = "/enrollment")
public class EnrollmentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EnrollmentDAO enrollmentDAO;
    private UserDAO userDAO;
    private CourseDAO courseDAO;

    public void init(){
        DatabaseConfig databaseConfig = (DatabaseConfig) getServletContext().getAttribute("databaseConfig");
        enrollmentDAO = new EnrollmentDAO(databaseConfig);
        userDAO = new UserDAO(databaseConfig);
        courseDAO = new CourseDAO(databaseConfig);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession(false).getAttribute("role") == null){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        String action = request.getParameter("action");
        if (action == null) {
            listEnrollments(request, response);
            return;
        }

        switch (action) {
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default:
                showErrorPage(request, response, "Invalid Action");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false).getAttribute("role") == null && !request.getSession(false).getAttribute("role").equals("ADMIN")){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/enrollment");
            return;
        }
        switch (action) {
            case "add":
                addEnrollment(request, response);
                break;
            case "edit":
                updateEnrollment(request, response);
                break;
            case "delete":
                deleteEnrollment(request, response);
                break;
            default:
                showErrorPage(request, response, "Invalid Action");
                break;
        }
    }

    private void listEnrollments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Enrollment> enrollments;
        if(request.getSession(false).getAttribute("role").equals("STUDENT")){
            enrollments = enrollmentDAO.getAllForUser(Integer.parseInt(request.getSession().getAttribute("userId").toString()));
        }
        else
            enrollments = enrollmentDAO.getAll();
        List<Course> courses =  courseDAO.getAll();
        List<User> students = userDAO.getAll();
        List<EnrollmentDTO> enrollmentDTOS = new ArrayList<>();
        for(Enrollment enrollment : enrollments){
            User user = null;
            for (User student : students){
                if(enrollment.getUserId() == student.getId()){
                    user = student;
                    break;
                }
            }
            Course course = null;
            for (Course course1 : courses){
                if(enrollment.getCourseId() == course1.getId()){
                    course = course1;
                    break;
                }
            }
            if(course != null && user != null)
                enrollmentDTOS.add(new EnrollmentDTO(enrollment.getId(),user.getId(), user.getUsername(),
                        course.getId(),course.getCourseName(),enrollment.getMark()));
        }
        request.setAttribute("enrollments", enrollmentDTOS);
        request.getRequestDispatcher("WEB-INF/views/enrollment.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false).getAttribute("role").equals("STUDENT")){
            showErrorPage(request, response, "UNAUTHORIZED");
        }
        request.getRequestDispatcher("WEB-INF/views/add-enrollment.jsp").forward(request, response);
    }

    private void addEnrollment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("userId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        float mark = Float.parseFloat(request.getParameter("mark"));
        Enrollment newEnrollment = new Enrollment(0,studentId, courseId, mark);
        Enrollment enrollment = enrollmentDAO.create(newEnrollment);
        if (enrollment != null)
            response.sendRedirect(request.getContextPath() + "/enrollment");
        else
            showErrorPage(request, response, "Error adding enrollment");

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession(false).getAttribute("role").equals("STUDENT")){
            showErrorPage(request, response, "UNAUTHORIZED");
        }
        int id = Integer.parseInt(request.getParameter("id"));
        Enrollment enrollment = enrollmentDAO.read(id);
        if(enrollment != null) {
            request.setAttribute("enrollment", enrollment);
            request.getRequestDispatcher("WEB-INF/views/edit-enrollment.jsp").forward(request, response);
        }
        else
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void updateEnrollment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int courseId = Integer.parseInt(request.getParameter("course_id"));
        float mark = Float.parseFloat(request.getParameter("mark"));

        Enrollment newEnrollment = new Enrollment(id, userId, courseId, mark);
        Enrollment enrollment = enrollmentDAO.update(newEnrollment);
        if (enrollment != null)
            response.sendRedirect(request.getContextPath() + "/enrollment");
         else
            showErrorPage(request, response, "Error updating enrollment");

    }
    private void deleteEnrollment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int enrollmentId = Integer.parseInt(request.getParameter("id"));
        boolean success = enrollmentDAO.delete(enrollmentId);
        if (success) {
            response.sendRedirect(request.getContextPath() + "/enrollment");
        } else {
            showErrorPage(request, response, "Error deleting enrollment");
        }
    }
    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
    }

}
