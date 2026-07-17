package com.coaching.dao;

import com.coaching.model.Question;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public int create(Question q) throws SQLException {
        String sql = "INSERT INTO questions (exam_id, question_text, image_path, option_a, option_b, option_c, option_d, correct_option, marks) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, q.getExamId());
            ps.setString(2, q.getQuestionText());
            ps.setString(3, q.getImagePath());
            ps.setString(4, q.getOptionA());
            ps.setString(5, q.getOptionB());
            ps.setString(6, q.getOptionC());
            ps.setString(7, q.getOptionD());
            ps.setString(8, q.getCorrectOption());
            ps.setInt(9, q.getMarks());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public List<Question> findByExam(int examId) throws SQLException {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE exam_id=? ORDER BY question_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public Question findById(int id) throws SQLException {
        String sql = "SELECT * FROM questions WHERE question_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public int countByExam(int examId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM questions WHERE exam_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    private Question map(ResultSet rs) throws SQLException {
        Question q = new Question();
        q.setQuestionId(rs.getInt("question_id"));
        q.setExamId(rs.getInt("exam_id"));
        q.setQuestionText(rs.getString("question_text"));
        q.setImagePath(rs.getString("image_path"));
        q.setOptionA(rs.getString("option_a"));
        q.setOptionB(rs.getString("option_b"));
        q.setOptionC(rs.getString("option_c"));
        q.setOptionD(rs.getString("option_d"));
        q.setCorrectOption(rs.getString("correct_option"));
        q.setMarks(rs.getInt("marks"));
        return q;
    }
}
