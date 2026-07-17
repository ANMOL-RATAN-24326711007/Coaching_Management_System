package com.coaching.dao;

import com.coaching.model.AttendanceRecord;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    // ---------------- STUDENT ATTENDANCE ----------------

    public void markStudentAttendance(int sessionId, int studentId, String status) throws SQLException {
        String sql = "INSERT INTO attendance_student (session_id, student_id, status) VALUES (?,?,?) " +
                "ON DUPLICATE KEY UPDATE status=?, marked_at=CURRENT_TIMESTAMP";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, sessionId);
            ps.setInt(2, studentId);
            ps.setString(3, status);
            ps.setString(4, status);
            ps.executeUpdate();
        }
    }

    public List<AttendanceRecord> findByStudent(int studentId) throws SQLException {
        List<AttendanceRecord> list = new ArrayList<>();
        String sql = "SELECT a.att_id, a.student_id, cs.session_date, a.status, sub.subject_name " +
                "FROM attendance_student a " +
                "JOIN class_sessions cs ON a.session_id=cs.session_id " +
                "JOIN class_routine r ON cs.routine_id=r.routine_id " +
                "LEFT JOIN subjects sub ON r.subject_id=sub.subject_id " +
                "WHERE a.student_id=? ORDER BY cs.session_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AttendanceRecord r = new AttendanceRecord();
                    r.setId(rs.getInt("att_id"));
                    r.setPersonId(rs.getInt("student_id"));
                    r.setDate(rs.getDate("session_date"));
                    r.setStatus(rs.getString("status"));
                    r.setExtra(rs.getString("subject_name"));
                    list.add(r);
                }
            }
        }
        return list;
    }

    public List<Object[]> studentAttendanceForSession(int sessionId) throws SQLException {
        // returns [student_id, student_name, status-or-null]
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT s.student_id, s.name, a.status FROM students s " +
                "JOIN class_routine r ON r.routine_id = (SELECT routine_id FROM class_sessions WHERE session_id=?) " +
                "AND s.course_id = r.course_id " +
                "LEFT JOIN attendance_student a ON a.session_id=? AND a.student_id=s.student_id " +
                "WHERE s.status='ACTIVE' ORDER BY s.name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, sessionId);
            ps.setInt(2, sessionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3)});
                }
            }
        }
        return list;
    }

    public double studentAttendancePercent(int studentId) throws SQLException {
        String sql = "SELECT " +
                "SUM(CASE WHEN status='PRESENT' THEN 1 ELSE 0 END) AS p, COUNT(*) AS total " +
                "FROM attendance_student WHERE student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("total");
                    if (total == 0) return 0;
                    return (rs.getInt("p") * 100.0) / total;
                }
            }
        }
        return 0;
    }

    // ---------------- FACULTY ATTENDANCE ----------------

    public void markFacultyAttendance(int facultyId, Date date, String status) throws SQLException {
        String sql = "INSERT INTO attendance_faculty (faculty_id, att_date, status) VALUES (?,?,?) " +
                "ON DUPLICATE KEY UPDATE status=?, marked_at=CURRENT_TIMESTAMP";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, facultyId);
            ps.setDate(2, date);
            ps.setString(3, status);
            ps.setString(4, status);
            ps.executeUpdate();
        }
    }

    public List<AttendanceRecord> findByFaculty(int facultyId) throws SQLException {
        List<AttendanceRecord> list = new ArrayList<>();
        String sql = "SELECT att_id, faculty_id, att_date, status FROM attendance_faculty WHERE faculty_id=? ORDER BY att_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, facultyId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AttendanceRecord r = new AttendanceRecord();
                    r.setId(rs.getInt("att_id"));
                    r.setPersonId(rs.getInt("faculty_id"));
                    r.setDate(rs.getDate("att_date"));
                    r.setStatus(rs.getString("status"));
                    list.add(r);
                }
            }
        }
        return list;
    }

    public double facultyAttendancePercent(int facultyId) throws SQLException {
        String sql = "SELECT SUM(CASE WHEN status='PRESENT' THEN 1 ELSE 0 END) AS p, COUNT(*) AS total " +
                "FROM attendance_faculty WHERE faculty_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, facultyId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("total");
                    if (total == 0) return 0;
                    return (rs.getInt("p") * 100.0) / total;
                }
            }
        }
        return 0;
    }
}
