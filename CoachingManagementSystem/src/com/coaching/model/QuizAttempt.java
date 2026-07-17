package com.coaching.model;

import java.sql.Timestamp;

public class QuizAttempt {
    private int attemptId;
    private int examId;
    private String examName;
    private int studentId;
    private Timestamp startTime;
    private Timestamp endTime;
    private double score;
    private int totalMarks;
    private String status;

    public int getAttemptId() { return attemptId; }
    public void setAttemptId(int attemptId) { this.attemptId = attemptId; }
    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }
    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public Timestamp getStartTime() { return startTime; }
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public Timestamp getEndTime() { return endTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
