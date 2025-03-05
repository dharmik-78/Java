<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reset Password</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
            position: relative;
        }

        h2 {
            margin-bottom: 15px;
            color: #333;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            text-align: left;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px;
            width: 100%;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #0056b3;
        }

        .notification {
            position: fixed;
            bottom: 20px;
            left: 50%;
            transform: translateX(-50%);
            background: rgba(255, 0, 0, 0.9);
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            display: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Reset Password</h2>
        
        <form action="ForgotPasswordServlet" method="post">
            <label for="email">Enter Your Email:</label>
            <input type="email" name="email" required>

            <label for="newPassword">Enter New Password:</label>
            <input type="password" name="newPassword" required>

            <button type="submit">Reset Password</button>
        </form>
    </div>

    <% String errorMessage = request.getParameter("error"); %>
    <% if (errorMessage != null) { %>
        <div class="notification" id="errorNotification"><%= errorMessage %></div>
        <script>
            document.getElementById("errorNotification").style.display = "block";
            setTimeout(function() {
                document.getElementById("errorNotification").style.display = "none";
            }, 5000);
        </script>
    <% } %>
</body>
</html>