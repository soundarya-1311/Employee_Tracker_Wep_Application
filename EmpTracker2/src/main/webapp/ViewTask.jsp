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
        .container {
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 800px;
            text-align: center;
            
        }
        .container h1 {
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .update-btn, .edit-btn {
            background-color: #f0f0f0;
            border: 1px solid #ccc;
            padding: 5px 10px;
            cursor: pointer;
        }
        .update-btn.clicked {
            background-color: #191970;
            color: white;
        }
        .error-message {
            color: red;
        }
        .status-dropdown {
            display: none;
        }
        .status-dropdown.show {
            display: inline-block;
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
                            // Refresh the entire page
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
            String employeeId = request.getParameter("employeeId");
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
