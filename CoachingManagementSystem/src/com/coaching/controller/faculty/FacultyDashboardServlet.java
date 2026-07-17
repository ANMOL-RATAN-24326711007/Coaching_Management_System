package com.coaching.controller.faculty;

import com.coaching.service.*;
import com.coaching.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/faculty/dashboard")
public class FacultyDashboardServlet extends HttpServlet {
    private final FacultyService facultyService = new FacultyService();
    private final AttendanceService attendanceService = new AttendanceService();
    private final SalaryService salaryService = new SalaryService();
    private final ExamService examService = new ExamService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int facultyId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        try {
            req.setAttribute("faculty", facultyService.getById(facultyId));
            req.setAttribute("attendancePct", String.format("%.1f", attendanceService.getFacultyAttendancePercent(facultyId)));
            req.setAttribute("upcomingClasses", attendanceService.getUpcomingByFaculty(facultyId));
            req.setAttribute("takenClasses", attendanceService.getTakenByFaculty(facultyId));
            req.setAttribute("salaryHistory", salaryService.getByFaculty(facultyId));
            req.setAttribute("exams", examService.getByFaculty(facultyId));
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/faculty/dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
