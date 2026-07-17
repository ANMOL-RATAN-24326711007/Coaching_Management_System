package com.coaching.service;

import com.coaching.dao.FeeDAO;
import com.coaching.model.*;
import java.sql.*;
import java.util.List;

public class FeeService {
    private final FeeDAO feeDAO = new FeeDAO();

    public FeeStructure getStructureByStudent(int studentId) throws SQLException {
        return feeDAO.findStructureByStudent(studentId);
    }

    public void setFeeStructure(int studentId, double totalAmount, Date dueDate) throws SQLException {
        feeDAO.upsertStructure(studentId, totalAmount, dueDate);
    }

    public int recordPayment(FeeTransaction t) throws SQLException {
        return feeDAO.addTransaction(t);
    }

    public List<FeeTransaction> getTransactionsByStudent(int studentId) throws SQLException {
        return feeDAO.findByStudent(studentId);
    }

    public List<FeeTransaction> getAllTransactions(Date from, Date to) throws SQLException {
        return feeDAO.findAll(from, to);
    }

    public double getTotalCollected(Date from, Date to) throws SQLException {
        return feeDAO.totalCollectedBetween(from, to);
    }

    public List<Object[]> getMonthlyTrend(int months) throws SQLException {
        return feeDAO.monthlyCollectionTrend(months);
    }
}
