<%@ page import="java.sql.*" %>
<%@ page import="com.sms.util.DBConnection" %>
<%@ page session="true" %>
<%
    HttpSession sessionObj = request.getSession(false);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Manage Students</title>
    <style>
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid black; text-align: left; }
        th { background-color: #f2f2f2; }
        form { display: inline; }
        .nav {
            background-color: #333;
            padding: 10px;
            margin-bottom: 20px;
        }
        .nav ul {
            list-style-type: none;
            padding: 0;
        }
        .nav ul li {
            display: inline;
            margin-right: 20px;
        }
        .nav ul li a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>
<body>

<!-- Navigation Menu -->
<div class="nav">
    <ul>
        <li><a href="admin_dashboard.jsp" class="active">Dashboard</a></li>
        <li><a href="StudentListServlet">Manage Students</a></li>
        <li><a href="add_student.jsp">
            <button style="background-color: blue; color: white; padding: 10px; border: none; cursor: pointer;">Add Student</button>
        </a></li>
        <li>
            <form action="AdminLogoutServlet" method="post" style="display:inline;">
                <button type="submit" style="background-color: red; color: white; padding: 10px; border: none; cursor: pointer;">Logout</button>
            </form>
        </li>
    </ul>
</div>

<h2>Admin Dashboard - Manage Students</h2>

<%
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        ps = conn.prepareStatement("SELECT * FROM students");
        rs = ps.executeQuery();
%>

<table>
    <tr>
        <th>USN</th>
        <th>Name</th>
        <th>Age</th>
        <th>Department</th>
        <th>Actions</th>
    </tr>
    <%
        while (rs.next()) {
    %>
    <tr>
        <form action="UpdateStudentServlet" method="post">
            <td><input type="text" name="usn" value="<%= rs.getString("usn") %>" readonly></td>
            <td><input type="text" name="name" value="<%= rs.getString("name") %>"></td>
            <td><input type="number" name="age" value="<%= rs.getInt("age") %>"></td>
            <td><input type="text" name="department" value="<%= rs.getString("department") %>"></td>
            <td>
                <button type="submit">Update</button>
        </form>
        <form action="DeleteStudentServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this student?');">
            <input type="hidden" name="usn" value="<%= rs.getString("usn") %>">
            <button type="submit" style="background-color: red; color: white;">Delete</button>
        </form>
        </td>
    </tr>
    <%
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
%>
</table>

<!-- Success/Failure Messages -->
<% if (request.getParameter("delete") != null) { %>
    <% if (request.getParameter("delete").equals("success")) { %>
        <p style="color: green;">Student deleted successfully!</p>
    <% } else if (request.getParameter("delete").equals("failure")) { %>
        <p style="color: red;">Failed to delete student. Try again!</p>
    <% } else if (request.getParameter("delete").equals("error")) { %>
        <p style="color: red;">An error occurred while deleting the student.</p>
    <% } %>
<% } %>

</body>
</html>
