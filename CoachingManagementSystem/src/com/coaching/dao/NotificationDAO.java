package com.coaching.dao;

import com.coaching.model.Notification;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public int create(Notification n) throws SQLException {
        String sql = "INSERT INTO notifications (student_id, title, message) VALUES (?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (n.getStudentId() != null) ps.setInt(1, n.getStudentId()); else ps.setNull(1, Types.INTEGER);
            ps.setString(2, n.getTitle());
            ps.setString(3, n.getMessage());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    /** Returns broadcast notifications (student_id IS NULL) plus ones addressed to this student. */
    public List<Notification> findForStudent(int studentId) throws SQLException {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE student_id IS NULL OR student_id=? ORDER BY created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public List<Notification> findAll() throws SQLException {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications ORDER BY created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    private Notification map(ResultSet rs) throws SQLException {
        Notification n = new Notification();
        n.setNotifId(rs.getInt("notif_id"));
        int sid = rs.getInt("student_id");
        n.setStudentId(rs.wasNull() ? null : sid);
        n.setTitle(rs.getString("title"));
        n.setMessage(rs.getString("message"));
        n.setCreatedAt(rs.getTimestamp("created_at"));
        return n;
    }
}
