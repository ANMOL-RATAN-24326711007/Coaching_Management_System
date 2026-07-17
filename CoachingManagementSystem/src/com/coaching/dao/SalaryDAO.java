package com.coaching.dao;

import com.coaching.model.SalaryTransaction;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAO {

    public int addTransaction(SalaryTransaction t) throws SQLException {
        String sql = "INSERT INTO salary_transactions (faculty_id, pay_month, pay_year, basic_pay, arrears, deductions, net_pay, paid_date, remarks) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getFacultyId());
            ps.setInt(2, t.getPayMonth());
            ps.setInt(3, t.getPayYear());
            ps.setDouble(4, t.getBasicPay());
            ps.setDouble(5, t.getArrears());
            ps.setDouble(6, t.getDeductions());
            ps.setDouble(7, t.getNetPay());
            ps.setDate(8, t.getPaidDate());
            ps.setString(9, t.getRemarks());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public List<SalaryTransaction> findByFaculty(int facultyId) throws SQLException {
        List<SalaryTransaction> list = new ArrayList<>();
        String sql = "SELECT * FROM salary_transactions WHERE faculty_id=? ORDER BY pay_year DESC, pay_month DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, facultyId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public List<SalaryTransaction> findAll(int month, int year) throws SQLException {
        List<SalaryTransaction> list = new ArrayList<>();
        String sql = "SELECT t.*, f.name AS faculty_name FROM salary_transactions t " +
                "JOIN faculty f ON t.faculty_id=f.faculty_id WHERE t.pay_month=? AND t.pay_year=? ORDER BY f.name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SalaryTransaction t = map(rs);
                    t.setFacultyName(rs.getString("faculty_name"));
                    list.add(t);
                }
            }
        }
        return list;
    }

    public double totalPaidBetween(int fromMonth, int fromYear, int toMonth, int toYear) throws SQLException {
        String sql = "SELECT COALESCE(SUM(net_pay),0) FROM salary_transactions WHERE (pay_year*12+pay_month) BETWEEN (?*12+?) AND (?*12+?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, fromYear); ps.setInt(2, fromMonth);
            ps.setInt(3, toYear); ps.setInt(4, toMonth);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        }
        return 0;
    }

    private SalaryTransaction map(ResultSet rs) throws SQLException {
        SalaryTransaction t = new SalaryTransaction();
        t.setTxnId(rs.getInt("txn_id"));
        t.setFacultyId(rs.getInt("faculty_id"));
        t.setPayMonth(rs.getInt("pay_month"));
        t.setPayYear(rs.getInt("pay_year"));
        t.setBasicPay(rs.getDouble("basic_pay"));
        t.setArrears(rs.getDouble("arrears"));
        t.setDeductions(rs.getDouble("deductions"));
        t.setNetPay(rs.getDouble("net_pay"));
        t.setPaidDate(rs.getDate("paid_date"));
        t.setRemarks(rs.getString("remarks"));
        return t;
    }
}
