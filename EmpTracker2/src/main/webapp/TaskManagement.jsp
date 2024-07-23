<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="EmpView1.Task" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            font-family: "Times New Roman", Times, serif;
            background: url('Ep.jpg') no-repeat center center fixed;
        }
        .container {
            padding: 20px;
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
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .form-container {
            max-width: 600px;
            margin: 0 auto;
        }
        .form-container input[type="text"],
        .form-container input[type="date"],
        .form-container select {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .form-container button {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            background-color: #191970;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-container button:hover {
            background-color: #B0C4DE;
        }
        .action-buttons button {
            width: 100%;
            padding: 5px;
            margin: 2px 0;
            cursor: pointer;
        }
        .action-buttons .edit {
            background-color: #2196F3;
            color: white;
        }
        .action-buttons .delete {
            background-color: #f44336;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Task Management</h1>
        <table>
            <thead>
                <tr>
                    <th>Task ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Assigned To</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Status</th>
                    <th>Actions</th>
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
                    <td><%= task.getTaskAssignedTo() %></td>
                    <td><%= task.getTaskStartDate() %></td>
                    <td><%= task.getTaskEndDate() %></td>
                    <td><%= task.getTaskStatus() %></td>
                    <td>
                        <form action="TaskManagementServlet" method="post" style="display: inline-block;">
                            <input type="hidden" name="taskId" value="<%= task.getTaskId() %>">
                            <input type="hidden" name="action" value="edit">
                            <button type="submit" class="edit">Edit</button>
                        </form>
                        <form action="TaskManagementServlet" method="post" style="display: inline-block;">
                            <input type="hidden" name="taskId" value="<%= task.getTaskId() %>">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit" class="delete">Delete</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>

        <div class="form-container">
            <h2><%= request.getAttribute("task") != null ? "Update Task" : "Add New Task" %></h2>
            <form action="TaskManagementServlet" method="post">
                <%
                    Task taskToEdit = (Task) request.getAttribute("task");
                    if (taskToEdit != null) {
                %>
                    <input type="hidden" name="taskId" value="<%= taskToEdit.getTaskId() %>">
                    <input type="hidden" name="action" value="update">
                <%
                    } else {
                %>
                    <input type="hidden" name="action" value="add">
                <%
                    }
                %>
                <input type="text" name="taskName" placeholder="Task Name" value="<%= taskToEdit != null ? taskToEdit.getTaskName() : "" %>" required>
                <input type="text" name="taskDescription" placeholder="Task Description" value="<%= taskToEdit != null ? taskToEdit.getTaskDescription() : "" %>" required>
                <input type="text" name="assignedTo" placeholder="Assigned To (Employee ID)" value="<%= taskToEdit != null ? taskToEdit.getTaskAssignedTo() : "" %>" required>
                <input type="date" name="startDate" placeholder="Start Date" value="<%= taskToEdit != null ? taskToEdit.getTaskStartDate() : "" %>" required>
                <input type="date" name="endDate" placeholder="End Date" value="<%= taskToEdit != null ? taskToEdit.getTaskEndDate() : "" %>" required>
                <select name="status" required>
                    
                    <option value="Ongoing" <%= taskToEdit != null && taskToEdit.getTaskStatus().equals("Ongoing") ? "selected" : "" %>>Ongoing</option>
                    <option value="Completed" <%= taskToEdit != null && taskToEdit.getTaskStatus().equals("Completed") ? "selected" : "" %>>Completed</option>
                </select>
                <button type="submit"><h2><%= taskToEdit != null ? "Update Task" : "Add Task" %></h2></button>
            </form>
            <form action="adminDash.jsp" method="get">
            <button type="submit"><h2>Back to Dashboard</h2></button>
        </form>
        </div>
    </div>
</body>
</html>
