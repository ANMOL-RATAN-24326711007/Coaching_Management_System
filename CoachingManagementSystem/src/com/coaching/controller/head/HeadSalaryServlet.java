package com.coaching.controller.head;

import com.coaching.model.*;
import com.coaching.service.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/head/salary")
public class HeadSalaryServlet extends HttpServlet {
    private final SalaryService salaryService = new SalaryService();
    private final FacultyService facultyService = new FacultyService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int month = LocalDate.now().getMonthValue();
            int year = LocalDate.now().getYear();
            String mStr = req.getParameter("month");
            String yStr = req.getParameter("year");
            if (mStr != null && !mStr.isEmpty()) month = Integer.parseInt(mStr);
            if (yStr != null && !yStr.isEmpty()) year = Integer.parseInt(yStr);
            req.setAttribute("transactions", salaryService.getAll(month, year));
            req.setAttribute("faculties", facultyService.getAll());
            req.setAttribute("selectedMonth", month);
            req.setAttribute("selectedYear", year);
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/head/salary.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            SalaryTransaction t = new SalaryTransaction();
            t.setFacultyId(Integer.parseInt(req.getParameter("facultyId")));
            t.setPayMonth(Integer.parseInt(req.getParameter("payMonth")));
            t.setPayYear(Integer.parseInt(req.getParameter("payYear")));
            t.setBasicPay(Double.parseDouble(req.getParameter("basicPay")));
            t.setArrears(Double.parseDouble(req.getParameter("arrears")));
            t.setDeductions(Double.parseDouble(req.getParameter("deductions")));
            t.setNetPay(t.getBasicPay() + t.getArrears() - t.getDeductions());
            t.setRemarks(req.getParameter("remarks"));
            t.setPaidDate(new Date(System.currentTimeMillis()));
            salaryService.payFaculty(t);
            resp.sendRedirect(req.getContextPath() + "/head/salary?msg=paid");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
