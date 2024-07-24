<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="EmpView1.Task" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Tasks</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .edit-btn, .update-btn {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 8px 16px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
        }
        .edit-btn {
            background-color: #007bff;
        }
        .status-dropdown {
            display: none;
        }
        .error-message {
            color: red;
            font-size: 12px;
            margin-top: 5px;
        }
        .status-dropdown select {
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .status-dropdown button {
            background-color: #28a745;
        }
        form {
            text-align: center;
        }
        form button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        form button:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        function toggleEdit(taskId) {
            var statusDropdown = document.getElementById("statusDropdown_" + taskId);
            var updateButton = document.getElementById("updateButton_" + taskId);
            var editButton = document.getElementById("editButton_" + taskId);

            if (statusDropdown.style.display === "none") {
                statusDropdown.style.display = "inline-block";
                updateButton.style.display = "inline-block";
                editButton.innerText = "Cancel";
            } else {
                statusDropdown.style.display = "none";
                updateButton.style.display = "none";
                editButton.innerText = "Edit";
            }
        }

        function updateTaskStatus(taskId, employeeId) {
            var selectElement = document.getElementById("status_" + taskId);
            var taskStatus = selectElement.value;
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "UpdateTaskStatusServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    var errorElement = document.getElementById("error_" + taskId);
                    if (xhr.status === 200) {
                        var response = xhr.responseText.trim();
                        if (response === "success") {
                            location.reload();
                        } else {
                            errorElement.innerText = "Failed to update task status.";
                        }
                    } else {
                        errorElement.innerText = "Failed to update task status.";
                    }
                }
            };

            xhr.send("taskId=" + taskId + "&taskStatus=" + taskStatus + "&employeeId=" + employeeId);
        }
    </script>
</head>
<body>
    <div class="container">
        <%
            String employeeId = request.getAttribute("employeeId").toString();
        %>
        <h1>Tasks for Employee ID: <%= employeeId %></h1>
        <div id="taskListContainer">
            <table>
                <thead>
                    <tr>
                        <th>Task ID</th>
                        <th>Task Name</th>
                        <th>Task Description</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                    if (tasks != null) {
                        for (Task task : tasks) {
                %>
                    <tr>
                        <td><%= task.getTaskId() %></td>
                        <td><%= task.getTaskName() %></td>
                        <td><%= task.getTaskDescription() %></td>
                        <td><%= task.getTaskStartDate() %></td>
                        <td><%= task.getTaskEndDate() %></td>
                        <td id="statusCell_<%= task.getTaskId() %>"><%= task.getTaskStatus() %></td>
                        <td>
                            <button id="editButton_<%= task.getTaskId() %>" class="edit-btn" onclick="toggleEdit('<%= task.getTaskId() %>')">Edit</button>
                            <div id="statusDropdown_<%= task.getTaskId() %>" class="status-dropdown">
                                <select id="status_<%= task.getTaskId() %>" name="taskStatus">
                                    <option value="Ongoing" <%= task.getTaskStatus().equals("Ongoing") ? "selected" : "" %>>Ongoing</option>
                                    <option value="Completed" <%= task.getTaskStatus().equals("Completed") ? "selected" : "" %>>Completed</option>
                                    <option value="In progress" <%= task.getTaskStatus().equals("In progress") ? "selected" : "" %>>In Progress</option>
                                    <option value="Pending" <%= task.getTaskStatus().equals("Pending") ? "selected" : "" %>>Pending</option>
                                </select>
                                <button id="updateButton_<%= task.getTaskId() %>" class="update-btn" onclick="updateTaskStatus('<%= task.getTaskId() %>', '<%= employeeId %>')">Update</button>
                            </div>
                            <div id="error_<%= task.getTaskId() %>" class="error-message"></div>
                        </td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="7">No tasks found for this employee.</td>
                    </tr>
                <%
                    }
                %>  
                </tbody>
            </table>
        </div><br>
        <form action="EmployeeDash.jsp" method="get">
            <button type="submit"><h3>Back to Dashboard</h3></button>
        </form>
    </div>
</body>
</html>
