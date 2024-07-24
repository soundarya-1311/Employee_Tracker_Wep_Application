package EmpControllers1;

import EmpView1.MonthlyReport;
import EmpView1.TaskReport;
import EmpView1.WeeklyReport;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import EmpModels1.ViewReportsDAO;

@WebServlet("/ViewReportsServlet")
public class ViewReportsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        
        ViewReportsDAO taskDAO = new ViewReportsDAO();
        TaskReport taskReport = taskDAO.getTaskReportByEmployeeId(employeeId);
        WeeklyReport weeklyReport = taskDAO.getWeeklyTaskReport(employeeId);
        MonthlyReport monthlyReport = taskDAO.getMonthlyTaskReport(employeeId);
        
        request.setAttribute("assigned", taskReport.getAssigned());
        request.setAttribute("completed", taskReport.getCompleted());
        request.setAttribute("ongoing", taskReport.getOngoing());
        
        request.setAttribute("weekly", weeklyReport);
        request.setAttribute("monthly", monthlyReport);
        
        request.getRequestDispatcher("Reports&Charts.jsp").forward(request, response);
    }
}
