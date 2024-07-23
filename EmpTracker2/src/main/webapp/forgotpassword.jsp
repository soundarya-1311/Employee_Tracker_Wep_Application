<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
    <style>
        body {
            font-family: "Times New Roman", Times, serif;
            background: url('Ep.jpg') no-repeat center center fixed;
            background-size: cover;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .reset-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
            font-family: "Times New Roman", Times, serif;
        }
        .reset-container h1 {
            margin-bottom: 20px;
            text-align: center;
        }
        .reset-container label {
            display: block;
            margin-bottom: 5px;
        }
        .reset-container input[type="text"], .reset-container input[type="password"] {
            width: 90%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .reset-container button {
            width: 95%;
            padding: 10px;
            background-color: #191970;
            border: none;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }
        .reset-container button:hover {
            background-color: #B0C4DE;
        }
    </style>
</head>
<body>

    <div class="reset-container">
        <h1>Reset Password</h1>
        <form action="ForgotPasswordServlet" method="post">
            <label for="username"><h2>Username:</h2></label>
            <input type="text" id="username" name="username" required>

            <label for="empId"><h2>Employee ID:</h2></label>
            <input type="text" id="empId" name="empId" required>

            <label for="newPassword"><h2>New Password:</h2></label>
            <input type="password" id="newPassword" name="newPassword" required>

            <button type="submit"><h2>Reset Password</h2></button>
        </form>
        <%
            String message = request.getParameter("message");
            String error = request.getParameter("error");
            if (message != null) {
                out.println("<div class='message'>" + message + "</div>");
            }
            if (error != null) {
                out.println("<div class='error'>" + error + "</div>");
            }
        %>
        <br>
        <form action="index.jsp" method="get">
            <button type="submit">Back to Login</button>
        </form>
    </div>

</body>
</html>
