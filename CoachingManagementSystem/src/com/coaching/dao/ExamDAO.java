package com.coaching.dao;

import com.coaching.model.Exam;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {

    private static final String BASE_SELECT =
            "SELECT e.*, c.course_name, sub.subject_name, f.name AS faculty_name " +
            "FROM exams e " +
            "LEFT JOIN courses c ON e.course_id=c.course_id " +
            "LEFT JOIN subjects sub ON e.subject_id=sub.subject_id " +
            "LEFT JOIN faculty f ON e.faculty_id=f.faculty_id ";

    public int create(Exam e) throws SQLException {
        String sql = "INSERT INTO exams (exam_name, course_id, subject_id, faculty_id, exam_date, total_marks, duration_minutes, is_quiz_online) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getExamName());
            ps.setInt(2, e.getCourseId());
            ps.setInt(3, e.getSubjectId());
            ps.setInt(4, e.getFacultyId());
            ps.setDate(5, e.getExamDate());
            ps.setInt(6, e.getTotalMarks());
            ps.setInt(7, e.getDurationMinutes());
            ps.setBoolean(8, e.isQuizOnline());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public Exam findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(BASE_SELECT + "WHERE e.exam_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Exam> findAll() throws SQLException {
        List<Exam> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(BASE_SELECT + "ORDER BY e.exam_date DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Exam> findByFaculty(int facultyId) throws SQLException {
        List<Exam> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(BASE_SELECT + "WHERE e.faculty_id=? ORDER BY e.exam_date DESC")) {
            ps.setInt(1, facultyId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    /** Online quizzes available to a student, based on their course. */
    public List<Exam> findOnlineQuizzesForCourse(int courseId) throws SQLException {
        List<Exam> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     BASE_SELECT + "WHERE e.course_id=? AND e.is_quiz_online=1 ORDER BY e.exam_date DESC")) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public List<Exam> findByCourse(int courseId) throws SQLException {
        List<Exam> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(BASE_SELECT + "WHERE e.course_id=? ORDER BY e.exam_date DESC")) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    private Exam map(ResultSet rs) throws SQLException {
        Exam e = new Exam();
        e.setExamId(rs.getInt("exam_id"));
        e.setExamName(rs.getString("exam_name"));
        e.setCourseId(rs.getInt("course_id"));
        e.setCourseName(rs.getString("course_name"));
        e.setSubjectId(rs.getInt("subject_id"));
        e.setSubjectName(rs.getString("subject_name"));
        e.setFacultyId(rs.getInt("faculty_id"));
        e.setFacultyName(rs.getString("faculty_name"));
        e.setExamDate(rs.getDate("exam_date"));
        e.setTotalMarks(rs.getInt("total_marks"));
        e.setDurationMinutes(rs.getInt("duration_minutes"));
        e.setQuizOnline(rs.getBoolean("is_quiz_online"));
        return e;
    }
}
