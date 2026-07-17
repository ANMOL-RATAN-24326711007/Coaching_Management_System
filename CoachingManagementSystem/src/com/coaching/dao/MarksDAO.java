package com.coaching.dao;

import com.coaching.model.StudentMark;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarksDAO {

    public void upsertMark(int examId, int studentId, double marks, String remarks, int enteredBy) throws SQLException {
        String sql = "INSERT INTO student_marks (exam_id, student_id, marks_obtained, remarks, entered_by) VALUES (?,?,?,?,?) " +
                "ON DUPLICATE KEY UPDATE marks_obtained=?, remarks=?, entered_by=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            ps.setInt(2, studentId);
            ps.setDouble(3, marks);
            ps.setString(4, remarks);
            ps.setInt(5, enteredBy);
            ps.setDouble(6, marks);
            ps.setString(7, remarks);
            ps.setInt(8, enteredBy);
            ps.executeUpdate();
        }
    }

    public List<StudentMark> findByExam(int examId) throws SQLException {
        List<StudentMark> list = new ArrayList<>();
        String sql = "SELECT m.*, s.name AS student_name, e.exam_name, e.total_marks FROM student_marks m " +
                "JOIN students s ON m.student_id=s.student_id " +
                "JOIN exams e ON m.exam_id=e.exam_id WHERE m.exam_id=? ORDER BY s.name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public List<StudentMark> findByStudent(int studentId) throws SQLException {
        List<StudentMark> list = new ArrayList<>();
        String sql = "SELECT m.*, s.name AS student_name, e.exam_name, e.total_marks FROM student_marks m " +
                "JOIN students s ON m.student_id=s.student_id " +
                "JOIN exams e ON m.exam_id=e.exam_id WHERE m.student_id=? ORDER BY e.exam_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public double averagePercentForStudent(int studentId) throws SQLException {
        String sql = "SELECT AVG(m.marks_obtained * 100.0 / e.total_marks) FROM student_marks m " +
                "JOIN exams e ON m.exam_id=e.exam_id WHERE m.student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getObject(1) != null) return rs.getDouble(1);
            }
        }
        return 0;
    }

    private StudentMark map(ResultSet rs) throws SQLException {
        StudentMark m = new StudentMark();
        m.setMarkId(rs.getInt("mark_id"));
        m.setExamId(rs.getInt("exam_id"));
        m.setExamName(rs.getString("exam_name"));
        m.setStudentId(rs.getInt("student_id"));
        m.setStudentName(rs.getString("student_name"));
        m.setMarksObtained(rs.getDouble("marks_obtained"));
        m.setTotalMarks(rs.getInt("total_marks"));
        m.setRemarks(rs.getString("remarks"));
        return m;
    }
}
