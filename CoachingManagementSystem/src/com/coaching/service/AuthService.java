package com.coaching.service;

import com.coaching.dao.UserDAO;
import com.coaching.model.AppUser;
import com.coaching.util.PasswordUtil;
import java.sql.SQLException;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public AppUser login(String username, String password) throws SQLException {
        AppUser user = userDAO.findByUsername(username);
        if (user == null || !"ACTIVE".equals(user.getStatus())) return null;
        if (PasswordUtil.verify(password, user.getSalt(), user.getPasswordHash())) return user;
        return null;
    }

    public void changePassword(int userId, String newPassword) throws SQLException {
        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hash(newPassword, salt);
        userDAO.updatePassword(userId, hash, salt);
    }

    public boolean createHeadAccount(String username, String password) throws SQLException {
        if (userDAO.usernameExists(username)) return false;
        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hash(password, salt);
        AppUser u = new AppUser();
        u.setUsername(username); u.setPasswordHash(hash); u.setSalt(salt);
        u.setRole("HEAD"); u.setLinkedId(0); u.setStatus("ACTIVE");
        userDAO.createUser(u);
        return true;
    }

    public boolean createStudentAccount(String username, String password, int studentId) throws SQLException {
        if (userDAO.usernameExists(username)) return false;
        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hash(password, salt);
        AppUser u = new AppUser();
        u.setUsername(username); u.setPasswordHash(hash); u.setSalt(salt);
        u.setRole("STUDENT"); u.setLinkedId(studentId); u.setStatus("ACTIVE");
        userDAO.createUser(u);
        return true;
    }

    public boolean createFacultyAccount(String username, String password, int facultyId) throws SQLException {
        if (userDAO.usernameExists(username)) return false;
        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hash(password, salt);
        AppUser u = new AppUser();
        u.setUsername(username); u.setPasswordHash(hash); u.setSalt(salt);
        u.setRole("FACULTY"); u.setLinkedId(facultyId); u.setStatus("ACTIVE");
        userDAO.createUser(u);
        return true;
    }
}
