<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
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
            background: url('Ep.jpg') no-repeat center center fixed;
            background-size: cover;
        }
        .container {
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 1200px;
            margin: auto;
        }
        h1 {
            text-align: center;
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
            margin-top: 20px;
        }
        .form-container form {
            display: flex;
            flex-direction: column;
        }
        .form-container label, .form-container input, .form-container select, .form-container textarea {
            margin-bottom: 10px;
            padding: 10px;
        }
        .form-container input, .form-container select, .form-container textarea {
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .form-container button {
            padding: 10px;
            background-color: #191970;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-container button:hover {
            background-color: #B0C4DE;
        }
        .action-button {
            margin-right: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Task Management</h1>
        
        <!-- Task List Table -->
        <table>
            <thead>
                <tr>
                    <th>Task ID</th>
                    <th>Task Name</th>
                    <th>Description</th>
                    <th>Assigned To</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Status</th>
                    <th>Work Hours</th>
                    <th>Work Date</th>
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
                    <td><%= task.getWorkHours() %></td>
                    <td><%= task.getWorkDate() %></td>
                    <td>
                        <form action="TaskManagementServlet" method="post" style="display:inline;">
                            <input type="hidden" name="taskId" value="<%= task.getTaskId() %>">
                            <input type="hidden" name="action" value="edit">
                            <button type="submit" class="action-button">Edit</button>
                        </form>
                        <form action="TaskManagementServlet" method="post" style="display:inline;">
                            <input type="hidden" name="taskId" value="<%= task.getTaskId() %>">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit" class="action-button">Delete</button>
                        </form>
                    </td>
                </tr>
                <% 
                        }
                    }
                %>
            </tbody>
        </table>
        
        <!-- Add/Edit Task Form -->
        <div class="form-container">
            <form action="TaskManagementServlet" method="post">
                <input type="hidden" name="action" value="<%= (request.getAttribute("task") != null) ? "update" : "add" %>">
                <input type="hidden" name="taskId" value="<%= (request.getAttribute("task") != null) ? ((Task)request.getAttribute("task")).getTaskId() : "" %>">
                <label for="taskName">Task Name</label>
                <input type="text" id="taskName" name="taskName" value="<%= (request.getAttribute("task") != null) ? ((Task)request.getAttribute("task")).getTaskName() : "" %>" required>
                
                <label for="taskDescription">Task Description</label>
                <textarea id="taskDescription" name="taskDescription" required><%= (request.getAttribute("task") != null) ? ((Task)request.getAttribute("task")).getTaskDescription() : "" %></textarea>
                
                <label for="assignedTo">Assigned To</label>
                <input type="number" id="assignedTo" name="assignedTo" value="<%= (request.getAttribute("task") != null) ? ((Task)request.getAttribute("task")).getTaskAssignedTo() : "" %>" required>
                
                <label for="startDate">Start Date</label>
                <%
                    // Create SimpleDateFormat instance for formatting the date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                    // Retrieve and format the start date
                    Date startDateObj = (request.getAttribute("task") != null) ? ((Task)request.getAttribute("task")).getTaskStartDate() : null;
                    String startDateFormatted = (startDateObj != null) ? dateFormat.format(startDateObj) : "";
                %>
                <input type="datetime-local" id="startDate" name="startDate" value="<%= startDateFormatted %>" required>
                
                <label for="endDate">End Date</label>
                <%
                    // Retrieve and format the end date
                    Date endDateObj = (request.getAttribute("task") != null) ? ((Task)request.getAttribute("task")).getTaskEndDate() : null;
                    String endDateFormatted = (endDateObj != null) ? dateFormat.format(endDateObj) : "";
                %>
                <input type="datetime-local" id="endDate" name="endDate" value="<%= endDateFormatted %>" required>
                
                <label for="status">Status</label>
                <select id="status" name="status" required>
                    <option value="Ongoing" <%= (request.getAttribute("task") != null && ((Task)request.getAttribute("task")).getTaskStatus().equals("Ongoing")) ? "selected" : "" %>>Ongoing</option>
                    <option value="Completed" <%= (request.getAttribute("task") != null && ((Task)request.getAttribute("task")).getTaskStatus().equals("Completed")) ? "selected" : "" %>>Completed</option>
                    <option value="Pending" <%= (request.getAttribute("task") != null && ((Task)request.getAttribute("task")).getTaskStatus().equals("Pending")) ? "selected" : "" %>>Pending</option>
                    <option value="In Progress" <%= (request.getAttribute("task") != null && ((Task)request.getAttribute("task")).getTaskStatus().equals("In Progress")) ? "selected" : "" %>>In Progress</option>
                    <!-- Add more status options as needed -->
                </select>
                
                <label for="workHours">Work Hours</label>
                <input type="number" step="0.1" id="workHours" name="workHours" value="<%= (request.getAttribute("task") != null) ? ((Task)request.getAttribute("task")).getWorkHours() : "" %>">
                
                <label for="workDate">Work Date</label>
                <input type="date" id="workDate" name="workDate" value="<%= (request.getAttribute("task") != null) ? ((Task)request.getAttribute("task")).getWorkDate() : "" %>">
                
                <button type="submit"><%= (request.getAttribute("task") != null) ? "Update Task" : "Add Task" %></button>
            </form><br>
            <form action="adminDash.jsp" method="get">
                <button type="submit">Back to Admin Dashboard</button>
            </form>
        </div>
    </div>
</body>
</html>
