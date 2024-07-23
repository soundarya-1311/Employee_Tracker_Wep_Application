package EmpModels1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import EmpView1.Task;

public class ViewTaskDAO {
   

    private static final String SELECT_TASKS_BY_EMPLOYEE_ID = "SELECT * FROM tasks WHERE task_assigned_to = ?";
    private static final String UPDATE_TASK_STATUS = "UPDATE tasks SET task_status = ? WHERE task_id = ?";


    public List<Task> getTasksByEmployeeId(int employeeId) {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASKS_BY_EMPLOYEE_ID)) {
            preparedStatement.setInt(1, employeeId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                String taskName = rs.getString("task_name");
                String taskDescription = rs.getString("task_description");
                int taskAssignedTo = rs.getInt("task_assigned_to");
                Date taskStartDate = rs.getDate("task_start_date");
                Date taskEndDate = rs.getDate("task_end_date");
                String taskStatus = rs.getString("task_status");

                Task task = new Task(taskId, taskName, taskDescription, taskAssignedTo, taskStartDate, taskEndDate, taskStatus);
                tasks.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Tasks retrieved from database: " + tasks); // Debug statement
        return tasks;
    }

    public boolean updateTaskStatus(String taskId, String taskStatus) {
        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TASK_STATUS)) {
            statement.setString(1, taskStatus);
            statement.setString(2, taskId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Object[]> getWeeklyProgress(int employeeId) {
        List<Object[]> weeklyProgress = new ArrayList<>();
        String query = "SELECT WEEK(task_start_date) AS week, COUNT(*) AS task_count FROM tasks WHERE task_assigned_to = ? GROUP BY WEEK(task_start_date)";

        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, employeeId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int week = rs.getInt("week");
                int count = rs.getInt("task_count");
                weeklyProgress.add(new Object[]{String.valueOf(week), count});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weeklyProgress;
    }

    public List<Object[]> getMonthlyProgress(int employeeId) {
        List<Object[]> monthlyProgress = new ArrayList<>();
        String query = "SELECT MONTH(task_start_date) AS month, COUNT(*) AS task_count FROM tasks WHERE task_assigned_to = ? GROUP BY MONTH(task_start_date)";

        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, employeeId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("month");
                int count = rs.getInt("task_count");
                monthlyProgress.add(new Object[]{String.valueOf(month), count});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyProgress;
    }

}
