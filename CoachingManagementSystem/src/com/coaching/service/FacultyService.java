package com.coaching.service;

import com.coaching.dao.FacultyDAO;
import com.coaching.model.Faculty;
import java.sql.*;
import java.util.List;

public class FacultyService {
    private final FacultyDAO facultyDAO = new FacultyDAO();

    public List<Faculty> getAll() throws SQLException { return facultyDAO.findAll(); }
    public Faculty getById(int id) throws SQLException { return facultyDAO.findById(id); }
    public int add(Faculty f) throws SQLException { return facultyDAO.create(f); }
    public void update(Faculty f) throws SQLException { facultyDAO.update(f); }
    public void updatePhoto(int id, String path) throws SQLException { facultyDAO.updatePhoto(id, path); }
    public void delete(int id) throws SQLException { facultyDAO.delete(id); }
    public int countAll() throws SQLException { return facultyDAO.countAll(); }
}
