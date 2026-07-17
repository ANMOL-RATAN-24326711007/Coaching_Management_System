package com.coaching.service;

import com.coaching.dao.*;
import com.coaching.model.*;
import java.sql.*;
import java.util.List;

public class ExamService {
    private final ExamDAO examDAO = new ExamDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();

    public int createExam(Exam e) throws SQLException { return examDAO.create(e); }
    public Exam getById(int id) throws SQLException { return examDAO.findById(id); }
    public List<Exam> getAll() throws SQLException { return examDAO.findAll(); }
    public List<Exam> getByFaculty(int facultyId) throws SQLException { return examDAO.findByFaculty(facultyId); }
    public List<Exam> getByCourse(int courseId) throws SQLException { return examDAO.findByCourse(courseId); }
    public List<Exam> getOnlineQuizzesForCourse(int courseId) throws SQLException { return examDAO.findOnlineQuizzesForCourse(courseId); }

    public int addQuestion(Question q) throws SQLException { return questionDAO.create(q); }
    public List<Question> getQuestions(int examId) throws SQLException { return questionDAO.findByExam(examId); }
    public Question getQuestion(int questionId) throws SQLException { return questionDAO.findById(questionId); }
    public int countQuestions(int examId) throws SQLException { return questionDAO.countByExam(examId); }
}
