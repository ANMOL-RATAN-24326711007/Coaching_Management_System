package com.coaching.service;

import com.coaching.dao.*;
import com.coaching.model.*;
import java.sql.*;
import java.util.List;

public class QuizService {
    private final QuizDAO quizDAO = new QuizDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();
    private final MarksDAO marksDAO = new MarksDAO();

    public int startAttempt(int examId, int studentId) throws SQLException {
        return quizDAO.startAttempt(examId, studentId);
    }

    public boolean hasSubmitted(int examId, int studentId) throws SQLException {
        return quizDAO.hasSubmittedAttempt(examId, studentId);
    }

    /** Saves one answer; determines correctness inline. */
    public void saveAnswer(int attemptId, int questionId, String selectedOption) throws SQLException {
        Question q = questionDAO.findById(questionId);
        boolean correct = q != null && q.getCorrectOption().equalsIgnoreCase(selectedOption);
        quizDAO.saveResponse(attemptId, questionId, selectedOption, correct);
    }

    /** Submits attempt, scores it, and auto-records in student_marks. */
    public double submitAttempt(int attemptId, int examId, int studentId) throws SQLException {
        double score = quizDAO.submitAttempt(attemptId);
        // Record result in student_marks (upsert – faculty_id 0 means system-graded)
        marksDAO.upsertMark(examId, studentId, score, "Auto-graded quiz", 0);
        return score;
    }

    public QuizAttempt getAttempt(int attemptId) throws SQLException { return quizDAO.findById(attemptId); }
    public List<QuizAttempt> getAttemptsByStudent(int studentId) throws SQLException { return quizDAO.findByStudent(studentId); }
    public List<QuizResponse> getResponses(int attemptId) throws SQLException { return quizDAO.findResponsesForAttempt(attemptId); }
}
