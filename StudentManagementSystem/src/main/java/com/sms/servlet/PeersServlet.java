package com.sms.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sms.util.DBConnection;


public class PeersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String loggedInUsername = (session != null) ? (String) session.getAttribute("username") : null;

        if (loggedInUsername == null) {
            response.sendRedirect("studentLogin.jsp?error=session_expired");
            return;
        }

        String department = request.getParameter("department");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement("SELECT name FROM students WHERE department = ? AND username <> ?");
            ps.setString(1, department);
            ps.setString(2, loggedInUsername);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                out.println("<li>" + rs.getString("name") + "</li>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<li>Error fetching peers.</li>");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
