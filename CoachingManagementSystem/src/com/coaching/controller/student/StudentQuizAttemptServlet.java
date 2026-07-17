package com.coaching.controller.student;

import com.coaching.service.*;
import com.coaching.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/student/quiz/attempt")
public class StudentQuizAttemptServlet extends HttpServlet {
    private final QuizService quizService = new QuizService();
    private final ExamService examService = new ExamService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int examId = Integer.parseInt(req.getParameter("examId"));
        int studentId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        try {
            if (quizService.hasSubmitted(examId, studentId)) {
                resp.sendRedirect(req.getContextPath() + "/student/quizzes?msg=already_submitted");
                return;
            }
            int attemptId = quizService.startAttempt(examId, studentId);
            req.setAttribute("attempt", quizService.getAttempt(attemptId));
            req.setAttribute("exam", examService.getById(examId));
            req.setAttribute("questions", examService.getQuestions(examId));
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/student/quizAttempt.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int attemptId = Integer.parseInt(req.getParameter("attemptId"));
        int examId = Integer.parseInt(req.getParameter("examId"));
        int studentId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        try {
            String[] questionIds = req.getParameterValues("questionId");
            if (questionIds != null) {
                for (String qid : questionIds) {
                    String selected = req.getParameter("answer_" + qid);
                    if (selected != null && !selected.isEmpty()) {
                        quizService.saveAnswer(attemptId, Integer.parseInt(qid), selected);
                    }
                }
            }
            double score = quizService.submitAttempt(attemptId, examId, studentId);
            resp.sendRedirect(req.getContextPath() + "/student/quiz/result?attemptId=" + attemptId + "&score=" + (int)score);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
