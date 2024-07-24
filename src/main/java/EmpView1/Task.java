package EmpView1;

import java.sql.Date;

public class Task {
    private int taskId;  // Changed from String to int
    private String taskName;
    private String taskDescription;
    private int taskAssignedTo;
    private Date taskStartDate;
    private Date taskEndDate;
    private String taskStatus;
    private float workHours;
    private Date workDate;

    // Constructors
    public Task(int taskId, String taskName, String taskDescription, int taskAssignedTo, Date taskStartDate, Date taskEndDate, String taskStatus, float workHours, Date workDate) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskAssignedTo = taskAssignedTo;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskStatus = taskStatus;
        this.workHours = workHours;
        this.workDate = workDate;
    }

    public Task(String taskName, String taskDescription, int taskAssignedTo, Date taskStartDate, Date taskEndDate, String taskStatus, float workHours, Date workDate) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskAssignedTo = taskAssignedTo;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskStatus = taskStatus;
        this.workHours = workHours;
        this.workDate = workDate;
    }

    public Task(int taskId, String taskName, String taskDescription, int taskAssignedTo, Date taskStartDate, Date taskEndDate, String taskStatus) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskAssignedTo = taskAssignedTo;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskStatus = taskStatus;
    }

    public Task() {
    }

    // Getters and Setters
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskAssignedTo() {
        return taskAssignedTo;
    }

    public void setTaskAssignedTo(int taskAssignedTo) {
        this.taskAssignedTo = taskAssignedTo;
    }

    public Date getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public Date getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(Date taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public float getWorkHours() {
        return workHours;
    }

    public void setWorkHours(float workHours) {
        this.workHours = workHours;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }
}
