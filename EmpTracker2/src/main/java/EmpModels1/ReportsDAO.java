package EmpModels1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import EmpView1.Task;

public class ReportsDAO {
   
    private static final String SELECT_ALL_TASKS = "SELECT * FROM tasks";
    private static final String COUNT_COMPLETED_TASKS = "SELECT COUNT(*) FROM tasks WHERE task_status = 'Completed'";
    private static final String COUNT_ONGOING_TASKS = "SELECT COUNT(*) FROM tasks WHERE task_status = 'Ongoing'";

    public List<Task> selectAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASKS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                String taskName = rs.getString("task_name");
                String taskDescription = rs.getString("task_description");
                int taskAssignedTo = rs.getInt("task_assigned_to");
                Date taskStartDate = rs.getDate("task_start_date");
                Date taskEndDate = rs.getDate("task_end_date");
                String taskStatus = rs.getString("task_status");
                tasks.add(new Task(taskId, taskName, taskDescription, taskAssignedTo, taskStartDate, taskEndDate, taskStatus));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public int countCompletedTasks() {
        int count = 0;
        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_COMPLETED_TASKS)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countOngoingTasks() {
        int count = 0;
        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ONGOING_TASKS)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
