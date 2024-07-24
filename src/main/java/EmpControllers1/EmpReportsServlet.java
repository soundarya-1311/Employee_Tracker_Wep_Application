package EmpControllers1;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import EmpModels1.ViewTaskDAO;
import EmpView1.Task;

import java.util.List;

@WebServlet("/EmpReportsServlet")
public class EmpReportsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ViewTaskDAO taskDao;

    public void init() {
        taskDao = new ViewTaskDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdStr = request.getParameter("employeeId");
        int employeeId = Integer.parseInt(employeeIdStr);

        // Fetch the task data
        List<Task> tasks = taskDao.getTasksByEmployeeId(employeeId);

        // Prepare data for the pie chart
        int assigned = 0, completed = 0, ongoing = 0;
        for (Task task : tasks) {
            switch (task.getTaskStatus()) {
                case "Assigned":
                    assigned++;
                    break;
                case "Completed":
                    completed++;
                    break;
                case "Ongoing":
                    ongoing++;
                    break;
            }
        }

        // Prepare data for the bar charts
        List<Object[]> weeklyData = taskDao.getWeeklyProgress(employeeId);
        List<Object[]> monthlyData = taskDao.getMonthlyProgress(employeeId);

        // Send HTML response
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Reports & Charts</title>");
        out.println("<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>");
        out.println("<script type='text/javascript'>");
        out.println("google.charts.load('current', {'packages':['corechart', 'bar']});");
        out.println("google.charts.setOnLoadCallback(drawCharts);");

        out.println("function drawCharts() {");
        out.println("  // Pie Chart");
        out.println("  var pieData = google.visualization.arrayToDataTable([");
        out.println("    ['Status', 'Count'],");
        out.println("    ['Assigned', " + assigned + "],");
        out.println("    ['Completed', " + completed + "],");
        out.println("    ['Ongoing', " + ongoing + "]");
        out.println("  ]);");
        out.println("  var pieOptions = { title: 'Task Status Distribution' };");
        out.println("  var pieChart = new google.visualization.PieChart(document.getElementById('pie_chart'));");
        out.println("  pieChart.draw(pieData, pieOptions);");

        out.println("  // Weekly Progress Bar Chart");
        out.println("  var weeklyData = google.visualization.arrayToDataTable([");
        out.println("    ['Week', 'Tasks'],");
        for (Object[] row : weeklyData) {
            out.println("    ['" + row[0] + "', " + row[1] + "],");
        }
        out.println("  ]);");
        out.println("  var weeklyOptions = { title: 'Weekly Progress', hAxis: { title: 'Week' }, vAxis: { title: 'Tasks' } };");
        out.println("  var weeklyChart = new google.visualization.BarChart(document.getElementById('weekly_chart'));");
        out.println("  weeklyChart.draw(weeklyData, weeklyOptions);");

        out.println("  // Monthly Progress Bar Chart");
        out.println("  var monthlyData = google.visualization.arrayToDataTable([");
        out.println("    ['Month', 'Tasks'],");
        for (Object[] row : monthlyData) {
            out.println("    ['" + row[0] + "', " + row[1] + "],");
        }
        out.println("  ]);");
        out.println("  var monthlyOptions = { title: 'Monthly Progress', hAxis: { title: 'Month' }, vAxis: { title: 'Tasks' } };");
        out.println("  var monthlyChart = new google.visualization.BarChart(document.getElementById('monthly_chart'));");
        out.println("  monthlyChart.draw(monthlyData, monthlyOptions);");
        out.println("}");
        out.println("</script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Reports & Charts</h1>");
        out.println("<div id='pie_chart' style='width: 900px; height: 500px;'></div>");
        out.println("<div id='weekly_chart' style='width: 900px; height: 500px;'></div>");
        out.println("<div id='monthly_chart' style='width: 900px; height: 500px;'></div>");
        out.println("</body>");
        out.println("</html>");

        out.flush();
    }
}
