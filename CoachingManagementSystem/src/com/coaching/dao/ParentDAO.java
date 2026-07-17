package com.coaching.dao;

import com.coaching.model.Parent;
import com.coaching.util.DBConnection;

import java.sql.*;

public class ParentDAO {

    public int create(Parent p) throws SQLException {
        String sql = "INSERT INTO parents (name, phone, email, occupation, address) VALUES (?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getPhone());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getOccupation());
            ps.setString(5, p.getAddress());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public Parent findById(int id) throws SQLException {
        String sql = "SELECT * FROM parents WHERE parent_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Parent p = new Parent();
                    p.setParentId(rs.getInt("parent_id"));
                    p.setName(rs.getString("name"));
                    p.setPhone(rs.getString("phone"));
                    p.setEmail(rs.getString("email"));
                    p.setOccupation(rs.getString("occupation"));
                    p.setAddress(rs.getString("address"));
                    return p;
                }
            }
        }
        return null;
    }
}
