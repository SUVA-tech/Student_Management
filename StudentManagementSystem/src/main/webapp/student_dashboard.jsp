<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.sms.util.DBConnection" %>

<%
    HttpSession sessionObj = request.getSession(false);
    
    if (sessionObj == null || sessionObj.getAttribute("username") == null) {
        response.sendRedirect("studentLogin.jsp?error=session_expired");
        return;
    }

    String username = (String) sessionObj.getAttribute("username");

    // Fetch the department of the logged-in student
    String department = null;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        ps = conn.prepareStatement("SELECT department FROM students WHERE username = ?");
        ps.setString(1, username);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            department = rs.getString("department");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <style>
        .peers-container {
            margin-top: 20px;
            padding: 10px;
            border: 1px solid #ccc;
            display: none; /* Initially hidden */
        }
    </style>
    <script>
        function fetchPeers(department) {
            fetch("PeersServlet?department=" + department)
                .then(response => response.text())
                .then(data => {
                    document.getElementById("peersList").innerHTML = data;
                    document.getElementById("peersContainer").style.display = "block";
                });
        }
    </script>
</head>
<body>
    <h2>Welcome, <%= username %>!</h2>

    <a href="editProfile.jsp">Edit Profile</a> |
    <a href="changePassword.jsp">Change Password</a> |
    <a href="LogoutServlet">Logout</a>

    <hr>

    <!-- Peers Button -->
    <% if (department != null) { %>
        <button onclick="fetchPeers('<%= department %>')">View Peers</button>
    <% } %>

    <!-- Peers List -->
    <div id="peersContainer" class="peers-container">
        <h3>Peers in <%= department %> Department</h3>
        <ul id="peersList"></ul>
    </div>
</body>
</html>
