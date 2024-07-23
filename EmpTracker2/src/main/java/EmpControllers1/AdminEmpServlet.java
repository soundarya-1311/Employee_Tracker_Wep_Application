package EmpControllers1;

import java.io.IOException;
import java.security.SecureRandom;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import EmpModels1.AdminEmpDAO;
import EmpView1.Emp;

@WebServlet("/AdminEmpServlet")
public class AdminEmpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        AdminEmpDAO employeeDAO = new AdminEmpDAO();

        switch (action) {
            case "register":
                // Handle employee registration
                String name = request.getParameter("employeeName");
                String email = request.getParameter("employeeEmail");
                String role = request.getParameter("employeeRole");
                
                String phone = request.getParameter("employeePhone");
                String address = request.getParameter("employeeAddress");
                String department = request.getParameter("employeeDepartment");

                // Validate email and phone
                if (!isValidEmail(email)) {
                    request.setAttribute("message", "Invalid email format");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }

                if (!isValidPhone(phone)) {
                    request.setAttribute("message", "Invalid phone number format");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }

                // Generate random password
                String password = generateRandomPassword();

                Emp newEmployee = new Emp(name, email, password, role, phone, address, department);
                boolean isRegistered = employeeDAO.registerEmployee(newEmployee);

                if (isRegistered) {
                    // Set attributes to display on success page
                    request.setAttribute("message", "Registration Successful!");
                    request.setAttribute("employeeId", newEmployee.getId());
                    request.setAttribute("employeePassword", password);
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                } else {
                    response.sendRedirect("error.jsp");
                }
                break;
            case "delete":
                // Handle employee deletion
            	try {
                    int empIdToDelete = Integer.parseInt(request.getParameter("employeeId"));
                    boolean isDeleted = employeeDAO.deleteEmployee(empIdToDelete);
                    
                    if (isDeleted) {
                        request.setAttribute("message", "Employee deletion is successful!");
                    } else {
                        request.setAttribute("message", "Employee deletion failed. Please check the ID and try again.");
                    }
                    
                    request.getRequestDispatcher("employee-deletion.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Invalid Employee ID format.");
                    request.getRequestDispatcher("employee-deletion.jsp").forward(request, response);
                }
                break;
            default:
                response.sendRedirect("error.jsp");
                return;
        }
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int tempPassword = 100000 + random.nextInt(900000);
        return String.valueOf(tempPassword);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidPhone(String phone) {
        return phone == null || phone.matches("^\\d{10}$");
    }
}
