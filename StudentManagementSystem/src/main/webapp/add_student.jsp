<%@ page import="java.sql.*" %>
<%@ page import="com.sms.util.DBConnection" %>
<% if (request.getParameter("status") != null) { %>
    <% if (request.getParameter("status").equals("success")) { %>
        <p style="color: green; text-align: center;"><%= request.getParameter("message") %></p>
    <% } else { %>
        <p style="color: red; text-align: center;"><%= request.getParameter("message") %></p>
    <% } %>
<% } %>

<!DOCTYPE html>
<html>
<head>
    <title>Add New Student</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { max-width: 400px; margin: auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        input, select { width: 100%; padding: 10px; margin: 5px 0; }
        button { background-color: #4CAF50; color: white; padding: 10px; width: 100%; border: none; cursor: pointer; }
    </style>
</head>
<body>

<h2 style="text-align: center;">Add New Student</h2>

<% if (request.getParameter("status") != null) { %>
    <% if (request.getParameter("status").equals("success")) { %>
        <p style="color: green; text-align: center;">Student added successfully!</p>
    <% } else { %>
        <p style="color: red; text-align: center;">Failed to add student. Try again.</p>
    <% } %>
<% } %>

    
<form action="AddStudentServlet" method="post">
    <label for="usn">USN:</label>
    <input type="text" id="usn" name="usn" required placeholder="Enter USN">
    
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required placeholder="Enter Full Name">
    
    <label for="age">Age:</label>
    <input type="number" id="age" name="age" min="1" required placeholder="Enter Age">
    
    <label for="department">Department:</label>
    <input type="text" id="department" name="department" required placeholder="Enter Department">
    
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required placeholder="Create Password">
    
    <button type="submit">Register</button>
</form>

<br>
<p style="text-align: center;"><a href="admin_dashboard.jsp">Back to Dashboard</a></p>

</body>
</html>
