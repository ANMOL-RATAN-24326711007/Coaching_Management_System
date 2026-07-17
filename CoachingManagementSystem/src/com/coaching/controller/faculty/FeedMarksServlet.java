package com.coaching.controller.faculty;

import com.coaching.service.*;
import com.coaching.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/faculty/feedMarks")
public class FeedMarksServlet extends HttpServlet {
    private final ExamService examService = new ExamService();
    private final MarksService marksService = new MarksService();
    private final StudentService studentService = new StudentService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int facultyId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        try {
            String examIdParam = req.getParameter("examId");
            req.setAttribute("exams", examService.getByFaculty(facultyId));
            if (examIdParam != null && !examIdParam.isEmpty()) {
                int examId = Integer.parseInt(examIdParam);
                req.setAttribute("selectedExam", examService.getById(examId));
                req.setAttribute("students", studentService.getByCourse(examService.getById(examId).getCourseId()));
                req.setAttribute("existingMarks", marksService.getByExam(examId));
            }
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/faculty/feedMarks.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int facultyId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        int examId = Integer.parseInt(req.getParameter("examId"));
        String[] studentIds = req.getParameterValues("studentId");
        try {
            if (studentIds != null) {
                for (String sid : studentIds) {
                    String marksStr = req.getParameter("marks_" + sid);
                    String remarks = req.getParameter("remarks_" + sid);
                    if (marksStr != null && !marksStr.isEmpty()) {
                        marksService.feedMark(examId, Integer.parseInt(sid), Double.parseDouble(marksStr), remarks, facultyId);
                    }
                }
            }
            resp.sendRedirect(req.getContextPath() + "/faculty/feedMarks?examId=" + examId + "&msg=saved");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
