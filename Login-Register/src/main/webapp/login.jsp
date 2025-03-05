<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script>
        function togglePassword() {
            var passwordField = document.getElementById("password");
            passwordField.type = (passwordField.type === "password") ? "text" : "password";
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Login</h1>
        <form action="LoginServlet" method="post">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <input type="checkbox" onclick="togglePassword()"> Show Password<br>

            <button type="submit">Login</button>
            <a href="forgot_password.jsp">Forgot Password?</a>
            
        </form>

        <%
            String error = request.getParameter("error");
            if (error != null) {
                if ("1".equals(error)) { 
        %>
            <p style="color: red;">Invalid email or password. Please try again.</p>
        <% 
                } else if ("blocked".equals(error)) { 
        %>
            <p style="color: red;">Your account is temporarily blocked due to multiple failed attempts. Try again after 1 minute.</p>
        <% 
                } 
            } 
        %>
        
        <%
            String success = request.getParameter("success");
            if ("1".equals(success)) { 
        %>
            <p style="color: green;"> Login successful! Welcome. </p>
        <% } %>
    </div>
</body>
</html>