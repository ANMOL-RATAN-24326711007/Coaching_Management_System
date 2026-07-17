package com.coaching.dao;

import com.coaching.model.InstituteConfig;
import com.coaching.util.DBConnection;

import java.sql.*;

public class InstituteConfigDAO {

    public InstituteConfig getConfig() throws SQLException {
        String sql = "SELECT * FROM institute_config ORDER BY id LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                InstituteConfig c = new InstituteConfig();
                c.setId(rs.getInt("id"));
                c.setInstituteName(rs.getString("institute_name"));
                c.setTagline(rs.getString("tagline"));
                c.setAddress(rs.getString("address"));
                c.setContactPhone(rs.getString("contact_phone"));
                c.setContactEmail(rs.getString("contact_email"));
                c.setLogoPath(rs.getString("logo_path"));
                return c;
            }
        }
        // fallback default if table empty
        InstituteConfig c = new InstituteConfig();
        c.setInstituteName("My Coaching Institute");
        return c;
    }

    public void updateConfig(InstituteConfig c) throws SQLException {
        String sql = "UPDATE institute_config SET institute_name=?, tagline=?, address=?, contact_phone=?, contact_email=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getInstituteName());
            ps.setString(2, c.getTagline());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getContactPhone());
            ps.setString(5, c.getContactEmail());
            ps.setInt(6, c.getId());
            ps.executeUpdate();
        }
    }

    public void updateLogo(int id, String logoPath) throws SQLException {
        String sql = "UPDATE institute_config SET logo_path=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, logoPath);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}
