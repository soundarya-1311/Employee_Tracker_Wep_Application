package EmpControllers1;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import EmpModels1.LoginDAO;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String empId = request.getParameter("empId");
        String newPassword = request.getParameter("newPassword");

        LoginDAO loginDAO = new LoginDAO();
        boolean result = loginDAO.resetPassword(username, empId, newPassword);

        if (result) {
            response.sendRedirect("index.jsp?message=Password reset successful");
        } else {
            response.sendRedirect("forgotpassword.jsp?error=Invalid username or employee ID");
        }
    }
}
