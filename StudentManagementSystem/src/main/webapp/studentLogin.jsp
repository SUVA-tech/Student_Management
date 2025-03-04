<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj != null && sessionObj.getAttribute("username") != null) {
        response.sendRedirect("student_dashboard.jsp"); // Redirect only if user is logged in
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Student Login</title>
</head>
<body>

    <h2>Student Login</h2>

    <% String error = request.getParameter("error"); %>
    <% if ("invalid".equals(error)) { %>
        <p style="color: red;">❌ Invalid username or password.</p>
    <% } else if ("session_expired".equals(error)) { %>
        <p style="color: red;">⚠️ Your session has expired. Please log in again.</p>
    <% } %>

    <form action="StudentLoginServlet" method="post">
        <label>Username:</label>
        <input type="text" name="username" required><br>

        <label>Password:</label>
        <input type="password" name="password" required><br>

        <input type="submit" value="Login">
    </form>

    <a href="student_register.jsp">No Account? Click here to register</a>

</body>
</html>
