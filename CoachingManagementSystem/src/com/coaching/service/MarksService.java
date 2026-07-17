package com.coaching.service;

import com.coaching.dao.MarksDAO;
import com.coaching.model.StudentMark;
import java.sql.*;
import java.util.List;

public class MarksService {
    private final MarksDAO marksDAO = new MarksDAO();

    public void feedMark(int examId, int studentId, double marks, String remarks, int facultyId) throws SQLException {
        marksDAO.upsertMark(examId, studentId, marks, remarks, facultyId);
    }

    public List<StudentMark> getByExam(int examId) throws SQLException { return marksDAO.findByExam(examId); }
    public List<StudentMark> getByStudent(int studentId) throws SQLException { return marksDAO.findByStudent(studentId); }
    public double getAvgPercentForStudent(int studentId) throws SQLException { return marksDAO.averagePercentForStudent(studentId); }
}
