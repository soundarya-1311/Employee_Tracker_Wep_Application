package EmpControllers1;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import EmpModels1.ViewTaskDAO;

@WebServlet("/UpdateTaskStatusServlet")
public class UpdateTaskStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskId = request.getParameter("taskId");
        String taskStatus = request.getParameter("taskStatus");

        ViewTaskDAO taskDao = new ViewTaskDAO();
        boolean success = taskDao.updateTaskStatus(taskId, taskStatus);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (success) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("failure");
        }
    }
}
