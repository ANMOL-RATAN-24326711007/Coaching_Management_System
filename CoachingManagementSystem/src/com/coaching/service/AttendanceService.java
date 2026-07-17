package com.coaching.service;

import com.coaching.dao.*;
import com.coaching.model.*;
import java.sql.*;
import java.util.List;

public class AttendanceService {
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final ClassSessionDAO sessionDAO = new ClassSessionDAO();
    private final ClassRoutineDAO routineDAO = new ClassRoutineDAO();

    public List<ClassRoutine> getRoutinesForFaculty(int facultyId) throws SQLException {
        return routineDAO.findByFaculty(facultyId);
    }

    public int getOrCreateSession(int routineId, java.sql.Date date) throws SQLException {
        return sessionDAO.findOrCreateSession(routineId, date);
    }

    public ClassSession getSessionById(int sessionId) throws SQLException {
        return sessionDAO.findById(sessionId);
    }

    public List<ClassSession> getSessionsByFaculty(int facultyId) throws SQLException {
        return sessionDAO.findByFaculty(facultyId);
    }

    public List<ClassSession> getUpcomingByFaculty(int facultyId) throws SQLException {
        return sessionDAO.findByFacultyAndStatus(facultyId, "UPCOMING");
    }

    public List<ClassSession> getTakenByFaculty(int facultyId) throws SQLException {
        return sessionDAO.findByFacultyAndStatus(facultyId, "TAKEN");
    }

    public void markSessionTaken(int sessionId, String topic) throws SQLException {
        sessionDAO.markTaken(sessionId, topic);
    }

    public List<Object[]> getStudentsForSession(int sessionId) throws SQLException {
        return attendanceDAO.studentAttendanceForSession(sessionId);
    }

    public void markStudentAttendance(int sessionId, int studentId, String status) throws SQLException {
        attendanceDAO.markStudentAttendance(sessionId, studentId, status);
    }

    public List<AttendanceRecord> getStudentAttendance(int studentId) throws SQLException {
        return attendanceDAO.findByStudent(studentId);
    }

    public double getStudentAttendancePercent(int studentId) throws SQLException {
        return attendanceDAO.studentAttendancePercent(studentId);
    }

    public void markFacultyAttendance(int facultyId, java.sql.Date date, String status) throws SQLException {
        attendanceDAO.markFacultyAttendance(facultyId, date, status);
    }

    public List<AttendanceRecord> getFacultyAttendance(int facultyId) throws SQLException {
        return attendanceDAO.findByFaculty(facultyId);
    }

    public double getFacultyAttendancePercent(int facultyId) throws SQLException {
        return attendanceDAO.facultyAttendancePercent(facultyId);
    }
}
