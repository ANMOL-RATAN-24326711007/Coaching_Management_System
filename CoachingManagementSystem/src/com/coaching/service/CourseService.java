package com.coaching.service;

import com.coaching.dao.*;
import com.coaching.model.*;
import java.sql.*;
import java.util.List;

public class CourseService {
    private final CourseDAO courseDAO = new CourseDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();

    public List<Course> getAllCourses() throws SQLException { return courseDAO.findAll(); }
    public Course getCourseById(int id) throws SQLException { return courseDAO.findById(id); }
    public int createCourse(Course c) throws SQLException { return courseDAO.create(c); }
    public void updateCourse(Course c) throws SQLException { courseDAO.update(c); }
    public void deleteCourse(int id) throws SQLException { courseDAO.delete(id); }

    public List<Subject> getAllSubjects() throws SQLException { return subjectDAO.findAll(); }
    public List<Subject> getSubjectsByCourse(int courseId) throws SQLException { return subjectDAO.findByCourse(courseId); }
    public Subject getSubjectById(int id) throws SQLException { return subjectDAO.findById(id); }
    public int createSubject(Subject s) throws SQLException { return subjectDAO.create(s); }
}
