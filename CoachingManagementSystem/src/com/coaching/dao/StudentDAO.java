package com.coaching.dao;

import com.coaching.model.Student;
import com.coaching.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> findAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT st.*, p.name AS parent_name, c.course_name FROM students st " +
                "LEFT JOIN parents p ON st.parent_id=p.parent_id " +
                "LEFT JOIN courses c ON st.course_id=c.course_id ORDER BY st.name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Student findById(int id) throws SQLException {
        String sql = "SELECT st.*, p.name AS parent_name, c.course_name FROM students st " +
                "LEFT JOIN parents p ON st.parent_id=p.parent_id " +
                "LEFT JOIN courses c ON st.course_id=c.course_id WHERE st.student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Student> findByCourse(int courseId) throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT st.*, p.name AS parent_name, c.course_name FROM students st " +
                "LEFT JOIN parents p ON st.parent_id=p.parent_id " +
                "LEFT JOIN courses c ON st.course_id=c.course_id WHERE st.course_id=? ORDER BY st.name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public int create(Student s) throws SQLException {
        String sql = "INSERT INTO students (name, dob, gender, address, phone, email, photo_path, parent_id, course_id, admission_date, status) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getName());
            ps.setDate(2, s.getDob());
            ps.setString(3, s.getGender());
            ps.setString(4, s.getAddress());
            ps.setString(5, s.getPhone());
            ps.setString(6, s.getEmail());
            ps.setString(7, s.getPhotoPath());
            if (s.getParentId() > 0) ps.setInt(8, s.getParentId()); else ps.setNull(8, Types.INTEGER);
            ps.setInt(9, s.getCourseId());
            ps.setDate(10, s.getAdmissionDate() != null ? s.getAdmissionDate() : new Date(System.currentTimeMillis()));
            ps.setString(11, s.getStatus() == null ? "ACTIVE" : s.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public void update(Student s) throws SQLException {
        String sql = "UPDATE students SET name=?, dob=?, gender=?, address=?, phone=?, email=?, course_id=?, status=? WHERE student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setDate(2, s.getDob());
            ps.setString(3, s.getGender());
            ps.setString(4, s.getAddress());
            ps.setString(5, s.getPhone());
            ps.setString(6, s.getEmail());
            ps.setInt(7, s.getCourseId());
            ps.setString(8, s.getStatus());
            ps.setInt(9, s.getStudentId());
            ps.executeUpdate();
        }
    }

    public void updatePhoto(int studentId, String photoPath) throws SQLException {
        String sql = "UPDATE students SET photo_path=? WHERE student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, photoPath);
            ps.setInt(2, studentId);
            ps.executeUpdate();
        }
    }

    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM students WHERE status='ACTIVE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public List<Object[]> countByCourse() throws SQLException {
        // returns [course_name, count]
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT c.course_name, COUNT(st.student_id) cnt FROM courses c " +
                "LEFT JOIN students st ON st.course_id=c.course_id AND st.status='ACTIVE' " +
                "GROUP BY c.course_id, c.course_name ORDER BY c.course_name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new Object[]{rs.getString(1), rs.getInt(2)});
        }
        return list;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Student map(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setStudentId(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setDob(rs.getDate("dob"));
        s.setGender(rs.getString("gender"));
        s.setAddress(rs.getString("address"));
        s.setPhone(rs.getString("phone"));
        s.setEmail(rs.getString("email"));
        s.setPhotoPath(rs.getString("photo_path"));
        s.setParentId(rs.getInt("parent_id"));
        s.setParentName(rs.getString("parent_name"));
        s.setCourseId(rs.getInt("course_id"));
        s.setCourseName(rs.getString("course_name"));
        s.setAdmissionDate(rs.getDate("admission_date"));
        s.setStatus(rs.getString("status"));
        return s;
    }
}
