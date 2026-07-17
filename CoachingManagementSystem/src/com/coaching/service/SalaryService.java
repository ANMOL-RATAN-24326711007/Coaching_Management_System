package com.coaching.service;

import com.coaching.dao.SalaryDAO;
import com.coaching.model.SalaryTransaction;
import java.sql.*;
import java.util.List;

public class SalaryService {
    private final SalaryDAO salaryDAO = new SalaryDAO();

    public int payFaculty(SalaryTransaction t) throws SQLException {
        return salaryDAO.addTransaction(t);
    }

    public List<SalaryTransaction> getByFaculty(int facultyId) throws SQLException {
        return salaryDAO.findByFaculty(facultyId);
    }

    public List<SalaryTransaction> getAll(int month, int year) throws SQLException {
        return salaryDAO.findAll(month, year);
    }

    public double getTotalPaid(int fromMonth, int fromYear, int toMonth, int toYear) throws SQLException {
        return salaryDAO.totalPaidBetween(fromMonth, fromYear, toMonth, toYear);
    }
}
