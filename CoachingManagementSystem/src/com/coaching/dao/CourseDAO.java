package com.coaching.dao;

import com.coaching.model.Course;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<Course> findAll() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY course_name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Course findById(int id) throws SQLException {
        String sql = "SELECT * FROM courses WHERE course_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public int create(Course c) throws SQLException {
        String sql = "INSERT INTO courses (course_name, duration_months, fee_amount, description) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getCourseName());
            ps.setInt(2, c.getDurationMonths());
            ps.setDouble(3, c.getFeeAmount());
            ps.setString(4, c.getDescription());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public void update(Course c) throws SQLException {
        String sql = "UPDATE courses SET course_name=?, duration_months=?, fee_amount=?, description=? WHERE course_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCourseName());
            ps.setInt(2, c.getDurationMonths());
            ps.setDouble(3, c.getFeeAmount());
            ps.setString(4, c.getDescription());
            ps.setInt(5, c.getCourseId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM courses WHERE course_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Course map(ResultSet rs) throws SQLException {
        Course c = new Course();
        c.setCourseId(rs.getInt("course_id"));
        c.setCourseName(rs.getString("course_name"));
        c.setDurationMonths(rs.getInt("duration_months"));
        c.setFeeAmount(rs.getDouble("fee_amount"));
        c.setDescription(rs.getString("description"));
        return c;
    }
}
