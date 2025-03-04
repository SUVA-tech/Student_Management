<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String error = request.getParameter("error");
    String message = request.getParameter("message");
%>

<html>
<head><title>Change Password</title></head>
<body>

<h2>Change Password</h2>

<% if ("wrong_old_password".equals(error)) { %>
    <p style="color: red;">❌ Old password is incorrect!</p>
<% } else if ("user_not_found".equals(error)) { %>
    <p style="color: red;">❌ User not found!</p>
<% } else if ("update_failed".equals(error)) { %>
    <p style="color: red;">❌ Failed to update password. Try again!</p>
<% } else if ("server_error".equals(error)) { %>
    <p style="color: red;">❌ Server error. Please try later!</p>
<% } else if ("session_expired".equals(error)) { %>
    <p style="color: red;">⚠️ Session expired. Please log in again.</p>
<% } else if ("password_updated".equals(message)) { %>
    <p style="color: green;">✅ Password changed successfully!</p>
<% } %>

<form action="ChangePasswordServlet" method="post">
    <input type="password" name="oldPassword" placeholder="Old Password" required><br>
    <input type="password" name="newPassword" placeholder="New Password" required><br>
    <input type="submit" value="Update">
</form>

<a href="student_dashboard.jsp">Back to Dashboard</a>

</body>
</html>
