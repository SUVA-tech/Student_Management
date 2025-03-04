package com.sms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sms.util.DBConnection;

public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Ensure user is logged in
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("studentLogin.jsp?error=session_expired");
            return;
        }

        String username = (String) session.getAttribute("username");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            
            // 1️⃣ Check if old password is correct
            ps = conn.prepareStatement("SELECT password FROM students WHERE usn = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                if (!oldPassword.equals(storedPassword)) {
                    response.sendRedirect("change_password.jsp?error=wrong_old_password");
                    return;
                }
            } else {
                response.sendRedirect("change_password.jsp?error=user_not_found");
                return;
            }

            rs.close();
            ps.close();

            // 2️⃣ Update to new password
            ps = conn.prepareStatement("UPDATE students SET password = ? WHERE usn = ?");
            ps.setString(1, newPassword);
            ps.setString(2, username);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                response.sendRedirect("student_dashboard.jsp?message=password_updated");
            } else {
                response.sendRedirect("change_password.jsp?error=update_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("change_password.jsp?error=server_error");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
