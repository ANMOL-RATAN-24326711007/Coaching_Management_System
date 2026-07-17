package com.coaching.controller.head;

import com.coaching.model.FeeTransaction;
import com.coaching.service.*;
import com.coaching.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/head/fees")
public class HeadFeesServlet extends HttpServlet {
    private final FeeService feeService = new FeeService();
    private final StudentService studentService = new StudentService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String from = req.getParameter("from");
            String to = req.getParameter("to");
            Date fromDate = (from != null && !from.isEmpty()) ? Date.valueOf(from) : Date.valueOf("2000-01-01");
            Date toDate = (to != null && !to.isEmpty()) ? Date.valueOf(to) : new Date(System.currentTimeMillis());
            req.setAttribute("transactions", feeService.getAllTransactions(fromDate, toDate));
            req.setAttribute("totalCollected", feeService.getTotalCollected(fromDate, toDate));
            req.setAttribute("students", studentService.getAllStudents());
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/head/fees.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            FeeTransaction t = new FeeTransaction();
            t.setStudentId(Integer.parseInt(req.getParameter("studentId")));
            t.setAmount(Double.parseDouble(req.getParameter("amount")));
            t.setMode(req.getParameter("mode"));
            t.setRemarks(req.getParameter("remarks"));
            t.setReceiptNo(req.getParameter("receiptNo"));
            t.setTxnDate(new Date(System.currentTimeMillis()));
            // Set fee structure if not set
            feeService.setFeeStructure(t.getStudentId(),
                Double.parseDouble(req.getParameter("totalFee")),
                null);
            feeService.recordPayment(t);
            resp.sendRedirect(req.getContextPath() + "/head/fees?msg=paid");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
