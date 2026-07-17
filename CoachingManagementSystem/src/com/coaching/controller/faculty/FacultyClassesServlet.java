package com.coaching.controller.faculty;

import com.coaching.model.ClassRoutine;
import com.coaching.service.*;
import com.coaching.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/faculty/classes")
public class FacultyClassesServlet extends HttpServlet {
    private final AttendanceService attendanceService = new AttendanceService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int facultyId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        try {
            // Auto-generate upcoming sessions for the next 4 weeks based on routines
            autoGenerateUpcomingSessions(facultyId);

            req.setAttribute("allClasses", attendanceService.getSessionsByFaculty(facultyId));
            req.setAttribute("upcomingClasses", attendanceService.getUpcomingByFaculty(facultyId));
            req.setAttribute("takenClasses", attendanceService.getTakenByFaculty(facultyId));
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/faculty/classes.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    /**
     * For each class routine of this faculty, create session records for the
     * next 4 weeks (if they don't already exist). This ensures the faculty
     * always sees upcoming classes without manual seeding.
     */
    private void autoGenerateUpcomingSessions(int facultyId) throws Exception {
        List<ClassRoutine> routines = attendanceService.getRoutinesForFaculty(facultyId);
        LocalDate today = LocalDate.now();
        for (ClassRoutine routine : routines) {
            String day = routine.getDayOfWeek(); // e.g. "MONDAY"
            DayOfWeek dow = DayOfWeek.valueOf(day);
            // Generate sessions for today and 4 weeks ahead
            for (int w = 0; w <= 4; w++) {
                LocalDate sessionDate = nextOrSameDayOfWeek(today.plusWeeks(w), dow);
                if (!sessionDate.isBefore(today)) {
                    attendanceService.getOrCreateSession(routine.getRoutineId(),
                            Date.valueOf(sessionDate));
                }
            }
        }
    }

    private LocalDate nextOrSameDayOfWeek(LocalDate from, DayOfWeek dow) {
        int daysDiff = (dow.getValue() - from.getDayOfWeek().getValue() + 7) % 7;
        return from.plusDays(daysDiff);
    }
}
