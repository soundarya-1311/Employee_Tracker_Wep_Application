package EmpModels1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import EmpView1.WeeklyReport;
import EmpView1.MonthlyReport;
import EmpView1.TaskReport;

public class ViewReportsDAO {

    public TaskReport getTaskReportByEmployeeId(int employeeId) {
        TaskReport taskReport = new TaskReport();
        try (Connection connection = DBTrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT task_status, COUNT(*) AS status_count FROM tasks WHERE task_assigned_to = ? GROUP BY task_status"
             )) {
            preparedStatement.setInt(1, employeeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String status = resultSet.getString("task_status");
                    int count = resultSet.getInt("status_count");
                    switch (status) {
                        case "Assigned":
                            taskReport.setAssigned(count);
                            break;
                        case "Completed":
                            taskReport.setCompleted(count);
                            break;
                        case "Ongoing":
                            taskReport.setOngoing(count);
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskReport;
    }
    
    public WeeklyReport getWeeklyTaskReport(int employeeId) {
        WeeklyReport weeklyReport = new WeeklyReport();
        try (Connection connection = DBTrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT WEEK(task_start_date) AS week, task_status, COUNT(*) AS status_count " +
                 "FROM tasks WHERE task_assigned_to = ? GROUP BY WEEK(task_start_date), task_status"
             )) {
            preparedStatement.setInt(1, employeeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int week = resultSet.getInt("week");
                    String status = resultSet.getString("task_status");
                    int count = resultSet.getInt("status_count");
                    if (week == 1) {
                        if (status.equals("Assigned")) weeklyReport.setAssigned1(count);
                        if (status.equals("Completed")) weeklyReport.setCompleted1(count);
                        if (status.equals("Ongoing")) weeklyReport.setOngoing1(count);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weeklyReport;
    }
    
    public MonthlyReport getMonthlyTaskReport(int employeeId) {
        MonthlyReport monthlyReport = new MonthlyReport();
        try (Connection connection = DBTrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT MONTH(task_start_date) AS month, task_status, COUNT(*) AS status_count " +
                 "FROM tasks WHERE task_assigned_to = ? GROUP BY MONTH(task_start_date), task_status"
             )) {
            preparedStatement.setInt(1, employeeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int month = resultSet.getInt("month");
                    String status = resultSet.getString("task_status");
                    int count = resultSet.getInt("status_count");
                    if (month == 1) {
                        if (status.equals("Assigned")) monthlyReport.setAssigned1(count);
                        if (status.equals("Completed")) monthlyReport.setCompleted1(count);
                        if (status.equals("Ongoing")) monthlyReport.setOngoing1(count);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyReport;
    }
}
