package EmpModels1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import EmpView1.Task;

public class ViewTaskDAO {
    private static final String GET_TASKS_BY_EMPLOYEE_ID = "SELECT * FROM tasks WHERE task_assigned_to = ?";
    private static final String UPDATE_TASK_STATUS = "UPDATE tasks SET task_status = ? WHERE task_id = ?";

    // Method to get tasks for a specific employee
    public List<Task> getTasksByEmployeeId(int employeeId) {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_TASKS_BY_EMPLOYEE_ID)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setTaskId(resultSet.getInt("task_id")); // Changed to int
                task.setTaskName(resultSet.getString("task_name"));
                task.setTaskDescription(resultSet.getString("task_description"));
                task.setTaskStartDate(resultSet.getDate("task_start_date"));
                task.setTaskEndDate(resultSet.getDate("task_end_date"));
                task.setTaskStatus(resultSet.getString("task_status"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // Method to update task status
    public boolean updateTaskStatus(int taskId, String taskStatus) {
        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TASK_STATUS)) {
            statement.setString(1, taskStatus);
            statement.setInt(2, taskId); // Changed to int
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
