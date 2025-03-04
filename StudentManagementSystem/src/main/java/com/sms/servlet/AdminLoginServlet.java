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

public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement("SELECT id, password FROM users WHERE username = ? AND role = 'admin'");
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                int adminId = rs.getInt("id"); // Retrieve admin ID

                if (verifyPassword(password, storedPassword)) {
                    HttpSession session = request.getSession(true); // ✅ Create new session
                    session.setAttribute("adminUsername", username);
                    session.setAttribute("adminId", adminId); // Store admin ID
                    session.setAttribute("role", "admin");

                    session.setMaxInactiveInterval(30 * 60); // ✅ Set session timeout (30 minutes)

                    response.sendRedirect("admin_dashboard.jsp");
                    return;
                }
            }

            response.sendRedirect("adminLogin.jsp?error=invalid");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("adminLogin.jsp?error=server_error");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private boolean verifyPassword(String inputPassword, String storedHash) {
        return inputPassword.equals(storedHash); // Ensure passwords are hashed in the database
    }
}
