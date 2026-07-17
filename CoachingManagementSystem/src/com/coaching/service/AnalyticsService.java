package com.coaching.service;

import com.coaching.dao.*;
import com.coaching.util.JsonUtil;
import java.sql.*;
import java.util.*;

/** Aggregates data for dashboards and analytics screens. */
public class AnalyticsService {
    private final StudentDAO studentDAO = new StudentDAO();
    private final FacultyDAO facultyDAO = new FacultyDAO();
    private final FeeDAO feeDAO = new FeeDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final MarksDAO marksDAO = new MarksDAO();

    public int totalStudents() throws SQLException { return studentDAO.countAll(); }
    public int totalFaculty() throws SQLException { return facultyDAO.countAll(); }

    /** Returns JSON array strings {labels, data} for enrolment-by-course chart. */
    public String[] enrolmentChartData() throws SQLException {
        List<Object[]> rows = studentDAO.countByCourse();
        List<String> labels = new ArrayList<>();
        List<Number> data = new ArrayList<>();
        for (Object[] r : rows) {
            labels.add((String) r[0]);
            data.add((Number) r[1]);
        }
        return new String[]{JsonUtil.toJsonArray(labels), JsonUtil.toJsonNumberArray(data)};
    }

    /** Monthly fee collection for last N months — returns {labels, data}. */
    public String[] feeCollectionChartData(int months) throws SQLException {
        List<Object[]> rows = feeDAO.monthlyCollectionTrend(months);
        List<String> labels = new ArrayList<>();
        List<Number> data = new ArrayList<>();
        for (Object[] r : rows) {
            labels.add((String) r[0]);
            data.add((Number) r[1]);
        }
        return new String[]{JsonUtil.toJsonArray(labels), JsonUtil.toJsonNumberArray(data)};
    }

    public double studentAttendancePercent(int studentId) throws SQLException {
        return attendanceDAO.studentAttendancePercent(studentId);
    }

    public double facultyAttendancePercent(int facultyId) throws SQLException {
        return attendanceDAO.facultyAttendancePercent(facultyId);
    }

    public double studentAvgPercent(int studentId) throws SQLException {
        return marksDAO.averagePercentForStudent(studentId);
    }
}
