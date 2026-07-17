package com.coaching.service;

import com.coaching.dao.*;
import com.coaching.model.*;
import java.sql.*;
import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAO();
    private final ParentDAO parentDAO = new ParentDAO();

    public List<Student> getAllStudents() throws SQLException { return studentDAO.findAll(); }
    public Student getById(int id) throws SQLException { return studentDAO.findById(id); }
    public List<Student> getByCourse(int courseId) throws SQLException { return studentDAO.findByCourse(courseId); }

    public int enroll(Student s, Parent p) throws SQLException {
        int parentId = parentDAO.create(p);
        s.setParentId(parentId);
        return studentDAO.create(s);
    }

    public void update(Student s) throws SQLException { studentDAO.update(s); }
    public void updatePhoto(int id, String path) throws SQLException { studentDAO.updatePhoto(id, path); }
    public void delete(int id) throws SQLException { studentDAO.delete(id); }
    public int countAll() throws SQLException { return studentDAO.countAll(); }
    public List<Object[]> countByCourse() throws SQLException { return studentDAO.countByCourse(); }
}
