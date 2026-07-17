package com.coaching.dao;

import com.coaching.model.FeeStructure;
import com.coaching.model.FeeTransaction;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeeDAO {

    public FeeStructure findStructureByStudent(int studentId) throws SQLException {
        String sql = "SELECT * FROM fee_structure WHERE student_id=? ORDER BY fee_id DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    FeeStructure f = new FeeStructure();
                    f.setFeeId(rs.getInt("fee_id"));
                    f.setStudentId(rs.getInt("student_id"));
                    f.setTotalAmount(rs.getDouble("total_amount"));
                    f.setDueDate(rs.getDate("due_date"));
                    f.setPaidAmount(totalPaid(studentId));
                    f.setBalanceAmount(f.getTotalAmount() - f.getPaidAmount());
                    return f;
                }
            }
        }
        return null;
    }

    public int upsertStructure(int studentId, double totalAmount, Date dueDate) throws SQLException {
        String find = "SELECT fee_id FROM fee_structure WHERE student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(find)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int feeId = rs.getInt(1);
                    String upd = "UPDATE fee_structure SET total_amount=?, due_date=? WHERE fee_id=?";
                    try (PreparedStatement ps2 = con.prepareStatement(upd)) {
                        ps2.setDouble(1, totalAmount);
                        ps2.setDate(2, dueDate);
                        ps2.setInt(3, feeId);
                        ps2.executeUpdate();
                    }
                    return feeId;
                }
            }
        }
        String ins = "INSERT INTO fee_structure (student_id, total_amount, due_date) VALUES (?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, studentId);
            ps.setDouble(2, totalAmount);
            ps.setDate(3, dueDate);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public double totalPaid(int studentId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(amount),0) FROM fee_transactions WHERE student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        }
        return 0;
    }

    public int addTransaction(FeeTransaction t) throws SQLException {
        String sql = "INSERT INTO fee_transactions (student_id, amount, txn_date, mode, remarks, receipt_no) VALUES (?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getStudentId());
            ps.setDouble(2, t.getAmount());
            ps.setDate(3, t.getTxnDate());
            ps.setString(4, t.getMode());
            ps.setString(5, t.getRemarks());
            ps.setString(6, t.getReceiptNo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public List<FeeTransaction> findByStudent(int studentId) throws SQLException {
        List<FeeTransaction> list = new ArrayList<>();
        String sql = "SELECT * FROM fee_transactions WHERE student_id=? ORDER BY txn_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public List<FeeTransaction> findAll(Date from, Date to) throws SQLException {
        List<FeeTransaction> list = new ArrayList<>();
        String sql = "SELECT t.*, s.name AS student_name FROM fee_transactions t " +
                "JOIN students s ON t.student_id=s.student_id " +
                "WHERE t.txn_date BETWEEN ? AND ? ORDER BY t.txn_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, from);
            ps.setDate(2, to);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FeeTransaction t = map(rs);
                    t.setStudentName(rs.getString("student_name"));
                    list.add(t);
                }
            }
        }
        return list;
    }

    public double totalCollectedBetween(Date from, Date to) throws SQLException {
        String sql = "SELECT COALESCE(SUM(amount),0) FROM fee_transactions WHERE txn_date BETWEEN ? AND ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, from);
            ps.setDate(2, to);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        }
        return 0;
    }

    public List<Object[]> monthlyCollectionTrend(int months) throws SQLException {
        // returns [YYYY-MM, total]
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT DATE_FORMAT(txn_date, '%Y-%m') ym, SUM(amount) total FROM fee_transactions " +
                "WHERE txn_date >= DATE_SUB(CURDATE(), INTERVAL ? MONTH) GROUP BY ym ORDER BY ym";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, months);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(new Object[]{rs.getString(1), rs.getDouble(2)});
            }
        }
        return list;
    }

    private FeeTransaction map(ResultSet rs) throws SQLException {
        FeeTransaction t = new FeeTransaction();
        t.setTxnId(rs.getInt("txn_id"));
        t.setStudentId(rs.getInt("student_id"));
        t.setAmount(rs.getDouble("amount"));
        t.setTxnDate(rs.getDate("txn_date"));
        t.setMode(rs.getString("mode"));
        t.setRemarks(rs.getString("remarks"));
        t.setReceiptNo(rs.getString("receipt_no"));
        return t;
    }
}
