package com.example.web_app.dao;



import com.example.web_app.model.Course;
import com.example.web_app.model.Marks;
import database.DatabaseConfig;

import java.sql.*;
import java.util.*;

public class CourseDAO {

    private final String CREATE_QUERY = "INSERT INTO courses(courseName, instructor, description) VALUES(?, ?, ?)";
    private final String READ_QUERY = "SELECT * FROM courses WHERE id=?";
    private final String UPDATE_QUERY = "UPDATE courses SET courseName=?, instructor=?, description=? WHERE id=?";
    private final String DELETE_QUERY = "DELETE FROM courses WHERE id=?";
    private final String GET_ALL_QUERY = "SELECT * FROM courses";
    private final String GET_LAST_INSERT = "SELECT * FROM courses WHERE id = LAST_INSERT_ID()";
    private DatabaseConfig databaseConfig;

    public CourseDAO(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    public Course create(Course course) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getInstructor());
            statement.setString(3, course.getDescription());
            statement.execute();
            PreparedStatement selectStmt  = connection.prepareStatement(GET_LAST_INSERT);
            ResultSet resultSet = selectStmt.executeQuery();
            resultSet.next();
            return new Course(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Course read(int id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String courseName = resultSet.getString("courseName");
                String instructor = resultSet.getString("instructor");
                String description = resultSet.getString("description");
                return new Course(id, courseName, instructor, description);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Course update(Course course) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getInstructor());
            statement.setString(3, course.getDescription());
            statement.setInt(4, course.getId());
            statement.executeUpdate();
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String courseName = resultSet.getString("courseName");
                String instructor = resultSet.getString("instructor");
                String description = resultSet.getString("description");
                courses.add(new Course(id, courseName, instructor, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    public Marks showMarks(int id)   {
        try (Connection connection = databaseConfig.getConnection()) {
            double avg = getClassAvg(id,connection);
            double median = getClassMedian(id,connection);
            double max = getClassHighest(id,connection);
            double min = getClassLowest(id,connection);
            return new Marks(avg,max,min,median);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public double getClassAvg(int courseId, Connection connection) throws SQLException {
        String query = "SELECT AVG(mark) FROM enrollment WHERE course_id=" + courseId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getFloat(1);
        } else {
            return -1;
        }
    }

    public double getClassMedian(int courseId, Connection connection) throws SQLException {
        try {
            String query = "SELECT COUNT(*) FROM enrollment WHERE course_id=" + courseId;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count % 2 == 0) {
                    // Even number of marks, need to average middle two
                    query = "SELECT mark FROM enrollment WHERE course_id=" + courseId + " ORDER BY mark LIMIT " + (count/2 - 1) + ", 2";
                    resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        float mark1 = resultSet.getFloat(1);
                        if (resultSet.next()) {
                            float mark2 = resultSet.getFloat(1);
                            return (mark1 + mark2) / 2;
                        } else {
                            return -1;
                        }
                    } else {
                        return -1;
                    }
                } else {
                    // Odd number of marks, need to find middle one
                    query = "SELECT mark FROM enrollment WHERE course_id=" + courseId + " ORDER BY marks_obtained LIMIT " + (count/2) + ", 1";
                    resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        return resultSet.getFloat(1);
                    } else {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        }catch (SQLException e){
            return 0;
        }

    }

    public double getClassHighest(int courseId, Connection connection) throws SQLException {
        String query = "SELECT MAX(mark) FROM enrollment WHERE course_id=" + courseId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getFloat(1);
        } else {
            return -1;
        }
    }

    public double getClassLowest(int courseId, Connection connection) throws SQLException {
        String query = "SELECT MIN(mark) FROM enrollment WHERE course_id=" + courseId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getFloat(1);
        } else {
            return -1;
        }
    }
}
