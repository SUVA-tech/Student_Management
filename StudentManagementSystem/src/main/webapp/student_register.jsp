<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 50px;
            text-align: center;
        }
        form {
            display: inline-block;
            text-align: left;
            background: #f4f4f4;
            padding: 20px;
            border-radius: 10px;
        }
        input {
            margin-bottom: 10px;
            padding: 8px;
            width: 100%;
        }
        button {
            background-color: #28a745;
            color: white;
            padding: 10px;
            border: none;
            width: 100%;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
        a {
            display: block;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<h2>Student Registration</h2>
    
<form action="StudentRegisterServlet" method="post">
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

<a href="studentLogin.jsp">Already have an account? Login here</a>

</body>
</html>
