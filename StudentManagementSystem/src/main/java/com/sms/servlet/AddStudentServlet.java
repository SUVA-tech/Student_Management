package com.sms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sms.util.DBConnection;

public class AddStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usn = request.getParameter("usn");
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String department = request.getParameter("department");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            // Check if USN already exists
            ps = conn.prepareStatement("SELECT usn FROM students WHERE usn = ?");
            ps.setString(1, usn);
            rs = ps.executeQuery();
            if (rs.next()) {
                response.getWriter().println("Error: USN already exists. Please try again.");
                return;
            }
            rs.close();
            ps.close();

            // Hash the password before storing
            String hashedPassword = hashPassword(password);

            // Insert new student record
            ps = conn.prepareStatement("INSERT INTO students (usn, name, age, department, password) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, usn);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, department);
            ps.setString(5, password);

            int result = ps.executeUpdate();
            if (result > 0) {
                response.sendRedirect("admin_dashboard.jsp"); // Successful registration, redirect to login
            } else {
                response.getWriter().println("Registration Failed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
