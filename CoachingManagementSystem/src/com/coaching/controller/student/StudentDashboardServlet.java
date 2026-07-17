package com.coaching.controller.student;

import com.coaching.service.*;
import com.coaching.util.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final AttendanceService attendanceService = new AttendanceService();
    private final MarksService marksService = new MarksService();
    private final FeeService feeService = new FeeService();
    private final NotificationService notifService = new NotificationService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int studentId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        try {
            req.setAttribute("student", studentService.getById(studentId));
            req.setAttribute("attendancePct", String.format("%.1f", attendanceService.getStudentAttendancePercent(studentId)));
            req.setAttribute("avgMarksPct", String.format("%.1f", marksService.getAvgPercentForStudent(studentId)));
            req.setAttribute("feeStructure", feeService.getStructureByStudent(studentId));
            req.setAttribute("notifications", notifService.getForStudent(studentId));
            req.setAttribute("marks", marksService.getByStudent(studentId));
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/student/dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
