package com.example.web_app.controller;



import com.example.web_app.dao.CourseDAO;
import com.example.web_app.dao.EnrollmentDAO;
import com.example.web_app.model.Course;
import com.example.web_app.model.Enrollment;
import com.example.web_app.model.Marks;
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
        if(request.getSession(false).getAttribute("role") == null){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        String action = request.getParameter("action");
        if (action == null){
            listCourses(request, response);
            return;
        }
        switch (action) {
            case "add":
                showNewCourseForm(request, response);
                break;
            case "edit":
                showEditCourseForm(request, response);
                break;
            case "enroll":
                enrollCourse (request, response);
                break;
            case "marks":
                showMarks(request,response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession(false).getAttribute("role") == null && !request.getSession(false).getAttribute("role").equals("ADMIN")){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/courses");
            return;
        }
        switch (action) {
            case "add":
                addCourse(request, response);
                break;
            case "edit":
                updateCourse(request, response);
                break;
            case "delete":
                deleteCourse(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    private void enrollCourse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if(!request.getSession().getAttribute("role").equals("STUDENT"))
            showErrorPage(request, response, "UNAUTHORIZED");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int userId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
        Enrollment enrollment = enrollmentDAO.create(new Enrollment(0,userId,courseId,0));
        if (enrollment != null)
            response.sendRedirect(request.getContextPath() + "/enrollment");
        else
            showErrorPage(request, response, "Error enrolling to this course ");
    }

    private void listCourses(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Course> courses = courseDAO.getAll();
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("WEB-INF/views/courses.jsp").forward(request, response);
    }

    private void showNewCourseForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(!request.getSession(false).getAttribute("role").equals("ADMIN")){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        request.getRequestDispatcher("WEB-INF/views/add-course.jsp").forward(request, response);
    }

    private void showEditCourseForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(!request.getSession(false).getAttribute("role").equals("ADMIN")){
            showErrorPage(request, response, "UNAUTHORIZED");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = courseDAO.read(id);
        if (course != null) {
            request.setAttribute("course", course);
            request.getRequestDispatcher("WEB-INF/views/edit-course.jsp").forward(request, response);
        } else
            response.sendError(HttpServletResponse.SC_NOT_FOUND);

    }

    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseName = request.getParameter("courseName");
        String instructor = request.getParameter("instructor");
        String description = request.getParameter("description");
        Course course = new Course(0,courseName, instructor, description);
        Course newCourse = courseDAO.create(course);
        if(newCourse != null)
            response.sendRedirect(request.getContextPath() + "/courses");
        else
            showErrorPage(request, response, "Error adding course");
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String courseName = request.getParameter("courseName");
        String instructor = request.getParameter("instructor");
        String description = request.getParameter("description");
        Course course = new Course(id, courseName, instructor, description);
        Course newCourse = courseDAO.update(course);
        if(newCourse != null)
            response.sendRedirect(request.getContextPath() + "/courses");
        else
            showErrorPage(request, response, "Error updating course");
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        courseDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/courses");
    }
    private void showMarks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = courseDAO.read(id);
        Marks marks = courseDAO.showMarks(id);
        if(marks != null){
            request.setAttribute("marks",marks);
            request.setAttribute("course",course);
            request.getRequestDispatcher("WEB-INF/views/marks.jsp").forward(request, response);
        }else showErrorPage(request,response,"Error showing marks");
    }
    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
    }
}
