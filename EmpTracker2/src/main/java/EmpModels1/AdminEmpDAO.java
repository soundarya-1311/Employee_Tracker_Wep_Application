package EmpModels1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import EmpView1.Emp;
import EmpModels1.TrackerDAO;

public class AdminEmpDAO {

    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO employees (emp_name, emp_email, emp_password, emp_role, emp_phone, emp_address, emp_department) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employees WHERE emp_id = ?";

    public boolean registerEmployee(Emp employee) {
        boolean rowInserted = false;

        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
             
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getPassword());
            preparedStatement.setString(4, employee.getRole());
            preparedStatement.setString(5, employee.getPhone());
            preparedStatement.setString(6, employee.getAddress());
            preparedStatement.setString(7, employee.getDepartment());

            rowInserted = preparedStatement.executeUpdate() > 0;

            if (rowInserted) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }

    public boolean deleteEmployee(int empId) {
        boolean rowDeleted = false;

        try (Connection connection = TrackerDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_SQL)) {
             
            preparedStatement.setInt(1, empId);
            rowDeleted = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
