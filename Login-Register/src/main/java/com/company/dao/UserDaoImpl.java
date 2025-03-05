package com.company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.company.model.User;
import com.company.util.DbUtil;

public class UserDaoImpl {

    private static final String SELECT_USER_SQL = "SELECT password, user_type, failed_attempts, is_blocked FROM users WHERE email = ?";
    private static final String INSERT_USER_SQL = "INSERT INTO users (name, email, password, phone, dob, user_type, image_path, failed_attempts, is_blocked) VALUES (?, ?, ?, ?, ?, ?, ?, 0, false)";
    private static final String CHECK_EMAIL_SQL = "SELECT email FROM users WHERE email = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE users SET password = ? WHERE email = ?";

    public boolean isValidUser(String email, String password) {
        boolean isValid = false;

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_USER_SQL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                int userType = rs.getInt("user_type");
                int failedAttempts = rs.getInt("failed_attempts");
                boolean isBlocked = rs.getBoolean("is_blocked");

                // Direct password comparison
                if (!isBlocked && storedPassword.equals(password) && userType == 1) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public boolean registerUser(User user) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_USER_SQL)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword()); // Storing password as plain text
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getDob());
            ps.setInt(6, user.getStatus());
            ps.setString(7, user.getImage());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExists(String email) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CHECK_EMAIL_SQL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updatePassword(String email, String newPassword) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PASSWORD_SQL)) {
            ps.setString(1, newPassword); // Storing plain text password
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
