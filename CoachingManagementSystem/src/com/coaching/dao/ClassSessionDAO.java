package com.coaching.dao;

import com.coaching.model.ClassSession;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassSessionDAO {

    private static final String BASE_SELECT =
            "SELECT cs.*, c.course_name, sub.subject_name, f.name AS faculty_name, " +
            "r.start_time, r.end_time, r.room, r.faculty_id, r.course_id, r.subject_id " +
            "FROM class_sessions cs " +
            "JOIN class_routine r ON cs.routine_id=r.routine_id " +
            "LEFT JOIN courses c ON r.course_id=c.course_id " +
            "LEFT JOIN subjects sub ON r.subject_id=sub.subject_id " +
            "LEFT JOIN faculty f ON r.faculty_id=f.faculty_id ";

    /** Finds an existing session for the routine+date, or creates one with status UPCOMING. */
    public int findOrCreateSession(int routineId, Date sessionDate) throws SQLException {
        String find = "SELECT session_id FROM class_sessions WHERE routine_id=? AND session_date=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(find)) {
            ps.setInt(1, routineId);
            ps.setDate(2, sessionDate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        String ins = "INSERT INTO class_sessions (routine_id, session_date, status) VALUES (?,?,'UPCOMING')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, routineId);
            ps.setDate(2, sessionDate);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public ClassSession findById(int sessionId) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(BASE_SELECT + "WHERE cs.session_id=?")) {
            ps.setInt(1, sessionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<ClassSession> findByFacultyAndStatus(int facultyId, String status) throws SQLException {
        List<ClassSession> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE r.faculty_id=? AND cs.status=? ORDER BY cs.session_date";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, facultyId);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public List<ClassSession> findByFaculty(int facultyId) throws SQLException {
        List<ClassSession> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE r.faculty_id=? ORDER BY cs.session_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, facultyId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public void markTaken(int sessionId, String topic) throws SQLException {
        String sql = "UPDATE class_sessions SET status='TAKEN', topic=? WHERE session_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, topic);
            ps.setInt(2, sessionId);
            ps.executeUpdate();
        }
    }

    private ClassSession map(ResultSet rs) throws SQLException {
        ClassSession cs = new ClassSession();
        cs.setSessionId(rs.getInt("session_id"));
        cs.setRoutineId(rs.getInt("routine_id"));
        cs.setSessionDate(rs.getDate("session_date"));
        cs.setTopic(rs.getString("topic"));
        cs.setStatus(rs.getString("status"));
        cs.setCourseName(rs.getString("course_name"));
        cs.setSubjectName(rs.getString("subject_name"));
        cs.setFacultyName(rs.getString("faculty_name"));
        cs.setStartTime(rs.getTime("start_time"));
        cs.setEndTime(rs.getTime("end_time"));
        cs.setRoom(rs.getString("room"));
        return cs;
    }
}
