package com.example.web_app.controller;



import com.example.web_app.dao.CourseDAO;
import com.example.web_app.dao.EnrollmentDAO;
import com.example.web_app.model.Course;
import com.example.web_app.model.Enrollment;
import database.DatabaseConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "Courses", value = "/courses")
public class CourseController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;
    private EnrollmentDAO enrollmentDAO;

    public void init(){
        DatabaseConfig databaseConfig = (DatabaseConfig) getServletContext().getAttribute("databaseConfig");
        courseDAO = new CourseDAO(databaseConfig);
        enrollmentDAO = new EnrollmentDAO(databaseConfig);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null)
            action = "list";
        switch (action) {
            case "list":
                listCourses(request, response);
                break;
            case "new":
                showNewCourseForm(request, response);
                break;
            case "edit":
                showEditCourseForm(request, response);
                break;
            case "delete":
                deleteCourse(request, response);
                break;
            case "enroll":
                enrollCourse (request, response);
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
                    addCourse(request, response);
                    break;
                case "edit":
                    updateCourse(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    private void enrollCourse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if(!request.getSession().getAttribute("role").equals("STUDENT"))
            request.getRequestDispatcher("courses").forward(request, response);
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        enrollmentDAO.create(new Enrollment(0,userId,courseId,0));
        request.getRequestDispatcher("/enrollment").forward(request, response);
    }

    private void listCourses(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Course> courses = courseDAO.getAll();
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("views/courses.jsp").forward(request, response);
    }

    private void showNewCourseForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("new_course.jsp").forward(request, response);
    }

    private void showEditCourseForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = courseDAO.read(id);
        if (course != null) {
            request.setAttribute("course", course);
            request.getRequestDispatcher("edit_course.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseName = request.getParameter("courseName");
        String instructor = request.getParameter("instructor");
        String description = request.getParameter("description");
        Course course = new Course(0,courseName, instructor, description);
        courseDAO.create(course);
        response.sendRedirect(request.getContextPath() + "/course?action=list");
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String courseName = request.getParameter("courseName");
        String instructor = request.getParameter("instructor");
        String description = request.getParameter("description");
        Course course = new Course(id, courseName, instructor, description);
        courseDAO.update(course);
        response.sendRedirect(request.getContextPath() + "/course?action=list");
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        courseDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/course?action=list");
    }
}
