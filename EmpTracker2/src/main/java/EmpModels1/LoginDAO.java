package EmpModels1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public boolean validateAdmin(String username, String password) {
        boolean isValid = false;

        try (Connection conn = TrackerDAO.getConnection()) {
            String sql = "SELECT * FROM admin_credentials WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }

    public boolean validateCustomer(String emp_name, String emp_password) {
        boolean isValid = false;

        try (Connection conn = TrackerDAO.getConnection()) {
            String sql = "SELECT * FROM employees WHERE emp_name = ? AND emp_password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emp_name);
            stmt.setString(2, emp_password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }
    
    public boolean resetPassword(String username, String empId, String newPassword) {
        boolean isUpdated = false;

        try (Connection conn = TrackerDAO.getConnection()) {
            String sql = "UPDATE employees SET emp_password = ? WHERE emp_name = ? AND emp_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.setString(3, empId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
    }
}

