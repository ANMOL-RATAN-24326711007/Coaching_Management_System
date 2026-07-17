package com.coaching.controller.faculty;

import com.coaching.model.Exam;
import com.coaching.service.*;
import com.coaching.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/faculty/createExam")
public class CreateExamServlet extends HttpServlet {
    private final ExamService examService = new ExamService();
    private final CourseService courseService = new CourseService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int facultyId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        try {
            req.setAttribute("courses", courseService.getAllCourses());
            req.setAttribute("myExams", examService.getByFaculty(facultyId));
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/faculty/createExam.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int facultyId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        try {
            Exam e = new Exam();
            e.setExamName(req.getParameter("examName"));
            e.setCourseId(Integer.parseInt(req.getParameter("courseId")));
            e.setSubjectId(Integer.parseInt(req.getParameter("subjectId")));
            e.setFacultyId(facultyId);
            String dateStr = req.getParameter("examDate");
            e.setExamDate(dateStr != null && !dateStr.isEmpty() ? Date.valueOf(dateStr) : null);
            e.setTotalMarks(Integer.parseInt(req.getParameter("totalMarks")));
            e.setDurationMinutes(Integer.parseInt(req.getParameter("durationMinutes")));
            e.setQuizOnline("1".equals(req.getParameter("isQuizOnline")));
            int examId = examService.createExam(e);
            resp.sendRedirect(req.getContextPath() + "/faculty/addQuestion?examId=" + examId + "&msg=exam_created");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
