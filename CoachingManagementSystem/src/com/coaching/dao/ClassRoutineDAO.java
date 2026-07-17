package com.coaching.dao;

import com.coaching.model.ClassRoutine;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassRoutineDAO {

    private static final String BASE_SELECT =
            "SELECT r.*, c.course_name, sub.subject_name, f.name AS faculty_name " +
            "FROM class_routine r " +
            "LEFT JOIN courses c ON r.course_id=c.course_id " +
            "LEFT JOIN subjects sub ON r.subject_id=sub.subject_id " +
            "LEFT JOIN faculty f ON r.faculty_id=f.faculty_id ";

    public List<ClassRoutine> findAll() throws SQLException {
        List<ClassRoutine> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(BASE_SELECT + "ORDER BY r.day_of_week, r.start_time");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<ClassRoutine> findByFaculty(int facultyId) throws SQLException {
        List<ClassRoutine> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(BASE_SELECT + "WHERE r.faculty_id=? ORDER BY r.day_of_week, r.start_time")) {
            ps.setInt(1, facultyId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public List<ClassRoutine> findByCourse(int courseId) throws SQLException {
        List<ClassRoutine> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(BASE_SELECT + "WHERE r.course_id=? ORDER BY r.day_of_week, r.start_time")) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public ClassRoutine findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(BASE_SELECT + "WHERE r.routine_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public int create(ClassRoutine r) throws SQLException {
        String sql = "INSERT INTO class_routine (course_id, subject_id, faculty_id, day_of_week, start_time, end_time, room) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getCourseId());
            ps.setInt(2, r.getSubjectId());
            ps.setInt(3, r.getFacultyId());
            ps.setString(4, r.getDayOfWeek());
            ps.setTime(5, r.getStartTime());
            ps.setTime(6, r.getEndTime());
            ps.setString(7, r.getRoom());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM class_routine WHERE routine_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private ClassRoutine map(ResultSet rs) throws SQLException {
        ClassRoutine r = new ClassRoutine();
        r.setRoutineId(rs.getInt("routine_id"));
        r.setCourseId(rs.getInt("course_id"));
        r.setCourseName(rs.getString("course_name"));
        r.setSubjectId(rs.getInt("subject_id"));
        r.setSubjectName(rs.getString("subject_name"));
        r.setFacultyId(rs.getInt("faculty_id"));
        r.setFacultyName(rs.getString("faculty_name"));
        r.setDayOfWeek(rs.getString("day_of_week"));
        r.setStartTime(rs.getTime("start_time"));
        r.setEndTime(rs.getTime("end_time"));
        r.setRoom(rs.getString("room"));
        return r;
    }
}
