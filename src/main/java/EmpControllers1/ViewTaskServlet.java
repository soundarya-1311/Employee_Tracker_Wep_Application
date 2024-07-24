package EmpControllers1;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import EmpModels1.ViewTaskDAO;
import EmpView1.Task;

@WebServlet("/ViewTasksServlet")
public class ViewTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ViewTaskDAO taskDao;

    public void init() {
        taskDao = new ViewTaskDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdStr = request.getParameter("employeeId");
        int employeeId = Integer.parseInt(employeeIdStr);

        List<Task> tasks = taskDao.getTasksByEmployeeId(employeeId);

        // Debug statement
        System.out.println("Tasks retrieved: " + tasks);

        request.setAttribute("tasks", tasks);
        request.setAttribute("employeeId", employeeId); // Pass employeeId to JSP
        request.getRequestDispatcher("ViewTask.jsp").forward(request, response);
    }
}
