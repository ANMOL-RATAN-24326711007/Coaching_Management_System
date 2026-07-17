package com.coaching.controller.faculty;

import com.coaching.model.Question;
import com.coaching.service.*;
import com.coaching.util.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/faculty/addQuestion")
@MultipartConfig(maxFileSize = 2097152)
public class AddQuestionServlet extends HttpServlet {
    private final ExamService examService = new ExamService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int examId = Integer.parseInt(req.getParameter("examId"));
        try {
            req.setAttribute("exam", examService.getById(examId));
            req.setAttribute("questions", examService.getQuestions(examId));
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/faculty/addQuestion.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int examId = Integer.parseInt(req.getParameter("examId"));
        try {
            Question q = new Question();
            q.setExamId(examId);
            q.setQuestionText(req.getParameter("questionText"));
            q.setOptionA(req.getParameter("optionA"));
            q.setOptionB(req.getParameter("optionB"));
            q.setOptionC(req.getParameter("optionC"));
            q.setOptionD(req.getParameter("optionD"));
            q.setCorrectOption(req.getParameter("correctOption"));
            q.setMarks(Integer.parseInt(req.getParameter("marks")));
            Part imagePart = req.getPart("questionImage");
            if (imagePart != null && imagePart.getSize() > 0) {
                String uploadDir = getServletContext().getRealPath("/uploads");
                String imgPath = FileUploadUtil.saveFile(imagePart, uploadDir, "questions");
                q.setImagePath(imgPath);
            }
            examService.addQuestion(q);
            resp.sendRedirect(req.getContextPath() + "/faculty/addQuestion?examId=" + examId + "&msg=question_added");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
