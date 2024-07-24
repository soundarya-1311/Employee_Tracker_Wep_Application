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
    private static final String COUNT_INPROGRESS_TASKS = "SELECT COUNT(*) FROM tasks WHERE task_status = 'In Progress'";
    private static final String COUNT_PENDING_TASKS = "SELECT COUNT(*) FROM tasks WHERE task_status = 'Pending'";

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
                float workhours = rs.getFloat("work_hours");
                Date workdate = rs.getDate("work_date");
                tasks.add(new Task(taskId, taskName, taskDescription, taskAssignedTo, taskStartDate, taskEndDate, taskStatus,workhours,workdate));
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
    
    public int countInprogressTasks() {
        int count = 0;
        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_INPROGRESS_TASKS)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int countPendingTasks() {
        int count = 0;
        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_PENDING_TASKS)) {
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
