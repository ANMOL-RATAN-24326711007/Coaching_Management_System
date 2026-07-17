package com.coaching.dao;

import com.coaching.model.Faculty;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {

    public List<Faculty> findAll() throws SQLException {
        List<Faculty> list = new ArrayList<>();
        String sql = "SELECT f.*, sub.subject_name FROM faculty f " +
                "LEFT JOIN subjects sub ON f.subject_id=sub.subject_id ORDER BY f.name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Faculty findById(int id) throws SQLException {
        String sql = "SELECT f.*, sub.subject_name FROM faculty f " +
                "LEFT JOIN subjects sub ON f.subject_id=sub.subject_id WHERE f.faculty_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public int create(Faculty f) throws SQLException {
        String sql = "INSERT INTO faculty (name, dob, gender, address, phone, email, photo_path, qualification, joining_date, subject_id, basic_pay, status) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, f.getName());
            ps.setDate(2, f.getDob());
            ps.setString(3, f.getGender());
            ps.setString(4, f.getAddress());
            ps.setString(5, f.getPhone());
            ps.setString(6, f.getEmail());
            ps.setString(7, f.getPhotoPath());
            ps.setString(8, f.getQualification());
            ps.setDate(9, f.getJoiningDate() != null ? f.getJoiningDate() : new Date(System.currentTimeMillis()));
            ps.setInt(10, f.getSubjectId());
            ps.setDouble(11, f.getBasicPay());
            ps.setString(12, f.getStatus() == null ? "ACTIVE" : f.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public void update(Faculty f) throws SQLException {
        String sql = "UPDATE faculty SET name=?, dob=?, gender=?, address=?, phone=?, email=?, qualification=?, subject_id=?, basic_pay=?, status=? WHERE faculty_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getName());
            ps.setDate(2, f.getDob());
            ps.setString(3, f.getGender());
            ps.setString(4, f.getAddress());
            ps.setString(5, f.getPhone());
            ps.setString(6, f.getEmail());
            ps.setString(7, f.getQualification());
            ps.setInt(8, f.getSubjectId());
            ps.setDouble(9, f.getBasicPay());
            ps.setString(10, f.getStatus());
            ps.setInt(11, f.getFacultyId());
            ps.executeUpdate();
        }
    }

    public void updatePhoto(int facultyId, String photoPath) throws SQLException {
        String sql = "UPDATE faculty SET photo_path=? WHERE faculty_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, photoPath);
            ps.setInt(2, facultyId);
            ps.executeUpdate();
        }
    }

    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM faculty WHERE status='ACTIVE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM faculty WHERE faculty_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Faculty map(ResultSet rs) throws SQLException {
        Faculty f = new Faculty();
        f.setFacultyId(rs.getInt("faculty_id"));
        f.setName(rs.getString("name"));
        f.setDob(rs.getDate("dob"));
        f.setGender(rs.getString("gender"));
        f.setAddress(rs.getString("address"));
        f.setPhone(rs.getString("phone"));
        f.setEmail(rs.getString("email"));
        f.setPhotoPath(rs.getString("photo_path"));
        f.setQualification(rs.getString("qualification"));
        f.setJoiningDate(rs.getDate("joining_date"));
        f.setSubjectId(rs.getInt("subject_id"));
        f.setSubjectName(rs.getString("subject_name"));
        f.setBasicPay(rs.getDouble("basic_pay"));
        f.setStatus(rs.getString("status"));
        return f;
    }
}
