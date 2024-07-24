<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Task Reports</title>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawCharts);

        function drawCharts() {
            // Pie Chart
            var pieData = google.visualization.arrayToDataTable([
                ['Task Status', 'Count'],
                ['Assigned', ${assigned}],
                ['Completed', ${completed}],
                ['Ongoing', ${ongoing}]
            ]);

            var pieOptions = {
                title: 'Task Distribution for Employee ID: ${param.employeeId}',
                is3D: true,
                colors: ['#ff00ff', '#800080', '#C0C0C0']
            };

            var pieChart = new google.visualization.PieChart(document.getElementById('piechart'));
            pieChart.draw(pieData, pieOptions);

            // Weekly Column Chart
            var weeklyData = google.visualization.arrayToDataTable([
                ['Week', 'Assigned', 'Completed', 'Ongoing'],
                ['Week 1', ${weekly.assigned1}, ${weekly.completed1}, ${weekly.ongoing1}]
            ]);

            var weeklyOptions = {
                title: 'Weekly Task Progress',
                chartArea: {width: '50%'},
                hAxis: {title: 'Total Tasks', minValue: 0},
                vAxis: {title: 'Week'}
            };

            var weeklyChart = new google.visualization.ColumnChart(document.getElementById('weeklychart'));
            weeklyChart.draw(weeklyData, weeklyOptions);

            // Monthly Column Chart
            var monthlyData = google.visualization.arrayToDataTable([
                ['Month', 'Assigned', 'Completed', 'Ongoing'],
                ['Month 1', ${monthly.assigned1}, ${monthly.completed1}, ${monthly.ongoing1}]
            ]);

            var monthlyOptions = {
                title: 'Monthly Task Progress',
                chartArea: {width: '50%'},
                hAxis: {title: 'Total Tasks', minValue: 0},
                vAxis: {title: 'Month'}
            };

            var monthlyChart = new google.visualization.ColumnChart(document.getElementById('monthlychart'));
            monthlyChart.draw(monthlyData, monthlyOptions);
        }
    </script>
    <style>
        body {
            font-family: "Times New Roman", Times, serif;
            background: url('Ep.jpg') no-repeat center center fixed;
            background-size: cover;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .charts-wrapper {
            display: flex;
            justify-content: space-between;
            width: 90%;
            max-width: 1400px;
            height: 80%;
            margin-top: 20px;
        }
        .chart-container {
            text-align: center;
            width: 48%;
            height: 100%;
        }
        .dashboard-link {
            text-align: center;
            margin-top: 20px;
        }
        #piechart {
            height: 100%;
        }
        #weeklychart, #monthlychart {
            height: 48%;
            margin-top: 20px;
        }
        @media (max-width: 768px) {
            .charts-wrapper {
                flex-direction: column;
                width: 100%;
            }
            #piechart, #weeklychart, #monthlychart {
                height: 30%;
            }
        }
    </style>
</head>
<body>
    <div class="charts-wrapper">
        <div class="chart-container">
            <h2>Pie Chart</h2>
            <div id="piechart"></div>
        </div>
        <div class="chart-container">
            <h2>Weekly Progress</h2>
            <div id="weeklychart"></div>
            <h2>Monthly Progress</h2>
            <div id="monthlychart"></div>
        </div>
    </div>
    <div class="dashboard-link">
        <a href="index.jsp">Go to Dashboard</a>
    </div>
</body>
</html>
