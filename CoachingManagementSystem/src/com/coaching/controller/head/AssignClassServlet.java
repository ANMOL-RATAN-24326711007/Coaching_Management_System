package com.coaching.controller.head;

import com.coaching.model.ClassRoutine;
import com.coaching.service.*;
import com.coaching.util.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.Time;

@WebServlet("/head/assignClass")
public class AssignClassServlet extends HttpServlet {
    private final CourseService courseService = new CourseService();
    private final FacultyService facultyService = new FacultyService();
    private final AttendanceService attendanceService = new AttendanceService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("courses", courseService.getAllCourses());
            req.setAttribute("subjects", courseService.getAllSubjects());
            req.setAttribute("faculties", facultyService.getAll());
            req.setAttribute("routines", new com.coaching.dao.ClassRoutineDAO().findAll());
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/head/assignClass.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle delete action
        if ("delete".equals(req.getParameter("action"))) {
            try {
                int routineId = Integer.parseInt(req.getParameter("routineId"));
                new com.coaching.dao.ClassRoutineDAO().delete(routineId);
                resp.sendRedirect(req.getContextPath() + "/head/assignClass?msg=deleted");
                return;
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
                return;
            }
        }

        try {
            String courseIdStr = req.getParameter("courseId");
            String subjectIdStr = req.getParameter("subjectId");
            String facultyIdStr = req.getParameter("facultyId");
            String startTimeStr = req.getParameter("startTime");
            String endTimeStr = req.getParameter("endTime");

            if (courseIdStr == null || courseIdStr.isEmpty() ||
                subjectIdStr == null || subjectIdStr.isEmpty() ||
                facultyIdStr == null || facultyIdStr.isEmpty() ||
                startTimeStr == null || startTimeStr.isEmpty() ||
                endTimeStr == null || endTimeStr.isEmpty()) {
                req.setAttribute("error", "All required fields must be filled.");
                doGet(req, resp);
                return;
            }

            ClassRoutine r = new ClassRoutine();
            r.setCourseId(Integer.parseInt(courseIdStr));
            r.setSubjectId(Integer.parseInt(subjectIdStr));
            r.setFacultyId(Integer.parseInt(facultyIdStr));
            r.setDayOfWeek(req.getParameter("dayOfWeek"));
            r.setStartTime(Time.valueOf(startTimeStr + ":00"));
            r.setEndTime(Time.valueOf(endTimeStr + ":00"));
            r.setRoom(req.getParameter("room"));
            new com.coaching.dao.ClassRoutineDAO().create(r);
            resp.sendRedirect(req.getContextPath() + "/head/assignClass?msg=assigned");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
