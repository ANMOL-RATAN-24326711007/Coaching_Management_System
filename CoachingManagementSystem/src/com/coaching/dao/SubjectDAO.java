package com.coaching.dao;

import com.coaching.model.Subject;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    public List<Subject> findAll() throws SQLException {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT s.*, c.course_name FROM subjects s LEFT JOIN courses c ON s.course_id=c.course_id ORDER BY s.subject_name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Subject> findByCourse(int courseId) throws SQLException {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT s.*, c.course_name FROM subjects s LEFT JOIN courses c ON s.course_id=c.course_id WHERE s.course_id=? ORDER BY s.subject_name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public Subject findById(int id) throws SQLException {
        String sql = "SELECT s.*, c.course_name FROM subjects s LEFT JOIN courses c ON s.course_id=c.course_id WHERE s.subject_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public int create(Subject s) throws SQLException {
        String sql = "INSERT INTO subjects (subject_name, course_id) VALUES (?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getSubjectName());
            ps.setInt(2, s.getCourseId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    private Subject map(ResultSet rs) throws SQLException {
        Subject s = new Subject();
        s.setSubjectId(rs.getInt("subject_id"));
        s.setSubjectName(rs.getString("subject_name"));
        s.setCourseId(rs.getInt("course_id"));
        s.setCourseName(rs.getString("course_name"));
        return s;
    }
}
