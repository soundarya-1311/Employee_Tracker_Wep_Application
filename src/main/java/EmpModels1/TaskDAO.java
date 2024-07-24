package EmpModels1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import EmpView1.Task;

public class TaskDAO {

    public static List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = TrackerDAO.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tasks");

            while (resultSet.next()) {
                int taskId = resultSet.getInt("task_id");
                String taskName = resultSet.getString("task_name");
                String taskDescription = resultSet.getString("task_description");
                int assignedTo = resultSet.getInt("task_assigned_to");
                Date startDate = resultSet.getDate("task_start_date");
                Date endDate = resultSet.getDate("task_end_date");
                String taskStatus = resultSet.getString("task_status");
                float workHours = resultSet.getFloat("work_hours");
                Date workDate = resultSet.getDate("work_date");

                Task task = new Task(taskId, taskName, taskDescription, assignedTo, startDate, endDate, taskStatus, workHours, workDate);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TrackerDAO.close(resultSet, statement, connection);
        }

        return tasks;
    }

    public static void addTask(Task task) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = TrackerDAO.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO tasks (task_name, task_description, task_assigned_to, task_start_date, task_end_date, task_status, work_hours, work_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setString(2, task.getTaskDescription());
            preparedStatement.setInt(3, task.getTaskAssignedTo());
            preparedStatement.setDate(4, task.getTaskStartDate());
            preparedStatement.setDate(5, task.getTaskEndDate());
            preparedStatement.setString(6, task.getTaskStatus());
            preparedStatement.setFloat(7, task.getWorkHours());
            preparedStatement.setDate(8, task.getWorkDate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TrackerDAO.close(null, preparedStatement, connection);
        }
    }

    public static Task getTaskById(int taskId) {
        Task task = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = TrackerDAO.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tasks WHERE task_id = ?");
            preparedStatement.setInt(1, taskId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String taskName = resultSet.getString("task_name");
                String taskDescription = resultSet.getString("task_description");
                int assignedTo = resultSet.getInt("task_assigned_to");
                Date startDate = resultSet.getDate("task_start_date");
                Date endDate = resultSet.getDate("task_end_date");
                String taskStatus = resultSet.getString("task_status");
                float workHours = resultSet.getFloat("work_hours");
                Date workDate = resultSet.getDate("work_date");

                task = new Task(taskId, taskName, taskDescription, assignedTo, startDate, endDate, taskStatus, workHours, workDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TrackerDAO.close(resultSet, preparedStatement, connection);
        }

        return task;
    }

    public static void updateTask(Task task) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = TrackerDAO.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE tasks SET task_name = ?, task_description = ?, task_assigned_to = ?, task_start_date = ?, task_end_date = ?, task_status = ?, work_hours = ?, work_date = ? WHERE task_id = ?");
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setString(2, task.getTaskDescription());
            preparedStatement.setInt(3, task.getTaskAssignedTo());
            preparedStatement.setDate(4, task.getTaskStartDate());
            preparedStatement.setDate(5, task.getTaskEndDate());
            preparedStatement.setString(6, task.getTaskStatus());
            preparedStatement.setFloat(7, task.getWorkHours());
            preparedStatement.setDate(8, task.getWorkDate());
            preparedStatement.setInt(9, task.getTaskId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TrackerDAO.close(null, preparedStatement, connection);
        }
    }

    public static void deleteTask(int taskId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = TrackerDAO.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM tasks WHERE task_id = ?");
            preparedStatement.setInt(1, taskId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TrackerDAO.close(null, preparedStatement, connection);
        }
    }
}
