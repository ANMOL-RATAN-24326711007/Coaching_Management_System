package com.coaching.model;

import java.sql.Date;

public class ClassSession {
    private int sessionId;
    private int routineId;
    private Date sessionDate;
    private String topic;
    private String status; // UPCOMING, TAKEN, CANCELLED

    // joined display fields
    private String courseName;
    private String subjectName;
    private String facultyName;
    private java.sql.Time startTime;
    private java.sql.Time endTime;
    private String room;

    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }
    public int getRoutineId() { return routineId; }
    public void setRoutineId(int routineId) { this.routineId = routineId; }
    public Date getSessionDate() { return sessionDate; }
    public void setSessionDate(Date sessionDate) { this.sessionDate = sessionDate; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }
    public java.sql.Time getStartTime() { return startTime; }
    public void setStartTime(java.sql.Time startTime) { this.startTime = startTime; }
    public java.sql.Time getEndTime() { return endTime; }
    public void setEndTime(java.sql.Time endTime) { this.endTime = endTime; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
}
