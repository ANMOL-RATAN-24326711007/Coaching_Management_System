package com.coaching.controller.faculty;

import com.coaching.service.*;
import com.coaching.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/faculty/takeAttendance")
public class TakeAttendanceServlet extends HttpServlet {
    private final AttendanceService attendanceService = new AttendanceService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionIdStr = req.getParameter("sessionId");
        if (sessionIdStr == null || sessionIdStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/faculty/classes");
            return;
        }
        try {
            int sessionId = Integer.parseInt(sessionIdStr);
            req.setAttribute("classSession", attendanceService.getSessionById(sessionId));
            req.setAttribute("students", attendanceService.getStudentsForSession(sessionId));
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/faculty/takeAttendance.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/faculty/classes");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionIdStr = req.getParameter("sessionId");
        if (sessionIdStr == null || sessionIdStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/faculty/classes");
            return;
        }
        try {
            int sessionId = Integer.parseInt(sessionIdStr);
            String topic = req.getParameter("topic");
            String[] studentIds = req.getParameterValues("studentId");

            attendanceService.markSessionTaken(sessionId, topic);

            if (studentIds != null) {
                for (String sidStr : studentIds) {
                    int sid = Integer.parseInt(sidStr);
                    // Each student's status comes from a field named status_<studentId>
                    String status = req.getParameter("status_" + sid);
                    if (status == null || status.isEmpty()) {
                        status = "ABSENT";
                    }
                    attendanceService.markStudentAttendance(sessionId, sid, status);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/faculty/classes?msg=attendance_saved");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
