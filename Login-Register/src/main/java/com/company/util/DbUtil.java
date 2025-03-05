package com.company.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = "root"; 
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER); 
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!"); 
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("Error: JDBC Driver not found!");
            e.printStackTrace();
            throw new SQLException("Database Driver not found!", e);
        } catch (SQLException e) {
            System.err.println("Database connection failed! Check MySQL credentials and DB.");
            e.printStackTrace();
            throw e;
        }
    }
}