package com.coaching.dao;

import com.coaching.model.QuizAttempt;
import com.coaching.model.QuizResponse;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the online quiz attempt lifecycle: starting an attempt, recording
 * answers, submitting/scoring, and retrieving attempt history & review data.
 */
public class QuizDAO {

    /** Starts a new attempt (or returns existing IN_PROGRESS attempt id if one exists). */
    public int startAttempt(int examId, int studentId) throws SQLException {
        String findSql = "SELECT attempt_id FROM quiz_attempts WHERE exam_id=? AND student_id=? AND status='IN_PROGRESS'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(findSql)) {
            ps.setInt(1, examId);
            ps.setInt(2, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("attempt_id");
                }
            }
        }
        String insertSql = "INSERT INTO quiz_attempts (exam_id, student_id, status) VALUES (?,?,'IN_PROGRESS')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, examId);
            ps.setInt(2, studentId);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        throw new SQLException("Could not start quiz attempt");
    }

    /** Returns true if the student already has a SUBMITTED attempt for this exam. */
    public boolean hasSubmittedAttempt(int examId, int studentId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM quiz_attempts WHERE exam_id=? AND student_id=? AND status='SUBMITTED'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            ps.setInt(2, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /** Saves (or updates) the selected option for one question within an attempt. */
    public void saveResponse(int attemptId, int questionId, String selectedOption, boolean correct) throws SQLException {
        String sql = "INSERT INTO quiz_responses (attempt_id, question_id, selected_option, is_correct) " +
                "VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE selected_option=VALUES(selected_option), is_correct=VALUES(is_correct)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, attemptId);
            ps.setInt(2, questionId);
            ps.setString(3, selectedOption);
            ps.setBoolean(4, correct);
            ps.executeUpdate();
        }
    }

    /** Finalizes an attempt: computes total score from saved responses and marks SUBMITTED. */
    public double submitAttempt(int attemptId) throws SQLException {
        String scoreSql = "SELECT COALESCE(SUM(q.marks),0) AS total_score FROM quiz_responses r " +
                "JOIN questions q ON r.question_id = q.question_id " +
                "WHERE r.attempt_id=? AND r.is_correct=1";
        double score = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(scoreSql)) {
            ps.setInt(1, attemptId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    score = rs.getDouble("total_score");
                }
            }
        }
        String updateSql = "UPDATE quiz_attempts SET score=?, end_time=CURRENT_TIMESTAMP, status='SUBMITTED' WHERE attempt_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateSql)) {
            ps.setDouble(1, score);
            ps.setInt(2, attemptId);
            ps.executeUpdate();
        }
        return score;
    }

    public QuizAttempt findById(int attemptId) throws SQLException {
        String sql = "SELECT a.*, e.exam_name, e.total_marks FROM quiz_attempts a " +
                "JOIN exams e ON a.exam_id = e.exam_id WHERE a.attempt_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, attemptId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<QuizAttempt> findByStudent(int studentId) throws SQLException {
        List<QuizAttempt> list = new ArrayList<>();
        String sql = "SELECT a.*, e.exam_name, e.total_marks FROM quiz_attempts a " +
                "JOIN exams e ON a.exam_id = e.exam_id WHERE a.student_id=? ORDER BY a.start_time DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    /** Returns the questions of an exam joined with this attempt's saved responses, for review/results screens. */
    public List<QuizResponse> findResponsesForAttempt(int attemptId) throws SQLException {
        List<QuizResponse> list = new ArrayList<>();
        String sql = "SELECT q.question_id, q.question_text, q.image_path, q.option_a, q.option_b, q.option_c, " +
                "q.option_d, q.correct_option, q.marks, r.response_id, r.selected_option, r.is_correct " +
                "FROM questions q " +
                "LEFT JOIN quiz_responses r ON r.question_id = q.question_id AND r.attempt_id = ? " +
                "WHERE q.exam_id = (SELECT exam_id FROM quiz_attempts WHERE attempt_id = ?) " +
                "ORDER BY q.question_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, attemptId);
            ps.setInt(2, attemptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    QuizResponse r = new QuizResponse();
                    r.setResponseId(rs.getInt("response_id"));
                    r.setAttemptId(attemptId);
                    r.setQuestionId(rs.getInt("question_id"));
                    r.setSelectedOption(rs.getString("selected_option"));
                    r.setCorrect(rs.getBoolean("is_correct"));
                    r.setQuestionText(rs.getString("question_text"));
                    r.setImagePath(rs.getString("image_path"));
                    r.setOptionA(rs.getString("option_a"));
                    r.setOptionB(rs.getString("option_b"));
                    r.setOptionC(rs.getString("option_c"));
                    r.setOptionD(rs.getString("option_d"));
                    r.setCorrectOption(rs.getString("correct_option"));
                    r.setMarks(rs.getInt("marks"));
                    list.add(r);
                }
            }
        }
        return list;
    }

    private QuizAttempt map(ResultSet rs) throws SQLException {
        QuizAttempt a = new QuizAttempt();
        a.setAttemptId(rs.getInt("attempt_id"));
        a.setExamId(rs.getInt("exam_id"));
        a.setExamName(rs.getString("exam_name"));
        a.setStudentId(rs.getInt("student_id"));
        a.setStartTime(rs.getTimestamp("start_time"));
        a.setEndTime(rs.getTimestamp("end_time"));
        a.setScore(rs.getDouble("score"));
        a.setTotalMarks(rs.getInt("total_marks"));
        a.setStatus(rs.getString("status"));
        return a;
    }
}
