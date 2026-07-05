<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="database.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Review Management</title>
    <style>
   /* Text-field and input styling */
   input[type="text"], input[type="number"], input[type="date"], select {
       width: calc(30% - 10px); /* Adjusts the size based on available space */
       margin: 5px 0;
       padding: 10px; /* Consistent padding for all fields */
       font-size: 14px;
       border: 1px solid #ccc;
       border-radius: 5px;
       box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1);
   }

   /* Stilizare pentru butoane */
   input[type="submit"], button, select {
       margin: 5px 0;
       padding: 10px 20px;
       font-size: 14px;
       font-weight: bold;
       color: white;
       background-color: #4CAF50;
       border: none;
       border-radius: 5px;
       cursor: pointer;
       transition: background-color 0.3s, transform 0.2s;
       box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
   }

   input[type="submit"]:hover, button:hover {
       background-color: #45a049;
       transform: scale(1.05);
   }

   button.delete {
       background-color: #d9534f;
   }

   button.delete:hover {
       background-color: #c9302c;
   }

   /* Stilizare pentru tabel */
   table {
       width: 100%;
       border-collapse: collapse;
       margin-top: 20px;
       background-color: #f9f9f9;
       border-radius: 8px;
       overflow: hidden;
       box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
   }

   th {
       background-color: #4CAF50;
       color: white;
       font-weight: bold;
       padding: 12px;
       text-align: center; /* Centrare text pentru titlurile coloanelor */
       vertical-align: middle; /* Ensures vertical centering */
   }

   td {
       padding: 10px;
       text-align: center; /* Centrare text pentru celule */
       color: #333;
   }

   tr:nth-child(even) {
       background-color: #f2f2f2;
   }

   tr:hover {
       background-color: #e1f5e1;
   }

   th:hover {
       cursor: pointer;
       background-color: #45a049;
   }

   tr.selected {
       background-color: #d1ecf1;
   }

    </style>
    <script>
        let selectedRow = null;
        let sortOrder = {};

        function selectRow(row) {
            if (selectedRow) selectedRow.classList.remove('selected');
            selectedRow = row;
            row.classList.add('selected');

            document.getElementById("id").value = row.cells[0].innerText;
            document.getElementById("id_client").value = row.cells[1].innerText;
            document.getElementById("scor").value = row.cells[2].innerText;
            document.getElementById("feedback").value = row.cells[3].innerText;
            document.getElementById("data").value = row.cells[4].innerText;
            document.getElementById("deleteId").value = row.cells[0].innerText;
        }

        function sortTable(columnIndex) {
            const table = document.querySelector("table tbody");
            const rows = Array.from(table.rows);

            if (!sortOrder[columnIndex]) sortOrder[columnIndex] = 'asc';

            rows.sort((rowA, rowB) => {
                const cellA = rowA.cells[columnIndex].innerText.toLowerCase();
                const cellB = rowB.cells[columnIndex].innerText.toLowerCase();

                return sortOrder[columnIndex] === 'asc'
                    ? cellA.localeCompare(cellB, undefined, {numeric: true})
                    : cellB.localeCompare(cellA, undefined, {numeric: true});
            });

            sortOrder[columnIndex] = sortOrder[columnIndex] === 'asc' ? 'desc' : 'asc';
            table.innerHTML = "";
            rows.forEach(row => table.appendChild(row));
        }

        function confirmDelete() {
            if (confirm("Are you sure you want to delete this review?")) {
                document.getElementById("deleteForm").submit();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Review Management</h2>

        <!-- Add/Update form -->
        <form action="evaluari" method="post">
            <label for="id">Review ID:</label>
            <input type="number" name="id" id="id" placeholder="ID (optional)"><br>
            <label for="id_client">ID Client:</label>
            <input type="number" name="id_client" id="id_client" required><br>
            <label for="scor">Score:</label>
            <input type="number" name="scor" id="scor" required><br>
            <label for="feedback">Feedback:</label>
            <input type="text" name="feedback" id="feedback" required><br>
            <label for="data">Review Date:</label>
            <input type="date" name="data_evaluarii" id="data" required><br>
            <input type="submit" value="Add / Update">
        </form>

        <!-- Delete form -->
        <form action="evaluari" method="post" id="deleteForm">
            <input type="hidden" name="delete" id="deleteId">
            <button type="button" onclick="confirmDelete()">Delete</button>
        </form>

        <!-- Export form -->
        <form action="evaluari" method="get">
            <label for="exportFormat">Export data as:</label>
            <select name="export" id="exportFormat">
                <option value="csv">CSV</option>
                <option value="pdf">PDF</option>
            </select>
            <button type="submit">Export</button>
        </form>

        <!-- Filter form -->
        <form action="evaluari.jsp" method="get">
            <label for="idFilter">Review ID:</label>
            <input type="text" name="id" id="idFilter">
            <label for="idClientFilter">ID Client:</label>
            <input type="text" name="id_client" id="idClientFilter">
            <label for="scorFilter">Score:</label>
            <input type="text" name="scor" id="scorFilter">
            <label for="feedbackFilter">Feedback:</label>
            <input type="text" name="feedback" id="feedbackFilter">
            <label for="dataFilter">Review Date:</label>
            <input type="date" name="data_evaluarii" id="dataFilter">
            <button type="submit">Filter</button>
        </form>

        <!-- Mesaje de eroare/succes -->
        <%
            String status = request.getParameter("status");
            String message = request.getParameter("message");
            if ("success".equals(status)) {
        %>
            <div class="message"><%= message %></div>
        <% } else if ("error".equals(status)) { %>
            <div class="error"><%= message %></div>
        <% } %>

        <!-- Review table -->
        <h2>Review List</h2>
        <table>
            <thead>
                <tr>
                    <th onclick="sortTable(0)">ID Evaluare</th>
                    <th onclick="sortTable(1)">ID Client</th>
                    <th onclick="sortTable(2)">Scor</th>
                    <th onclick="sortTable(3)">Feedback</th>
                    <th onclick="sortTable(4)">Review Date</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String id = request.getParameter("id");
                    String idClient = request.getParameter("id_client");
                    String scor = request.getParameter("scor");
                    String feedback = request.getParameter("feedback");
                    String data = request.getParameter("data_evaluarii");

                    StringBuilder query = new StringBuilder("SELECT * FROM Evaluari WHERE 1=1");
                    if (id != null && !id.isEmpty()) query.append(" AND ID_EVALUARE = ?");
                    if (idClient != null && !idClient.isEmpty()) query.append(" AND ID_CLIENT = ?");
                    if (scor != null && !scor.isEmpty()) query.append(" AND SCOR = ?");
                    if (feedback != null && !feedback.isEmpty()) query.append(" AND FEEDBACK LIKE ?");
                    if (data != null && !data.isEmpty()) query.append(" AND DATA_EVALUARII = ?");

                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(query.toString())) {
                        int index = 1;

                        if (id != null && !id.isEmpty()) stmt.setInt(index++, Integer.parseInt(id));
                        if (idClient != null && !idClient.isEmpty()) stmt.setInt(index++, Integer.parseInt(idClient));
                        if (scor != null && !scor.isEmpty()) stmt.setInt(index++, Integer.parseInt(scor));
                        if (feedback != null && !feedback.isEmpty()) stmt.setString(index++, "%" + feedback + "%");
                        if (data != null && !data.isEmpty()) stmt.setDate(index, java.sql.Date.valueOf(data));

                        try (ResultSet rs = stmt.executeQuery()) {
                            while (rs.next()) {
                %>
                <tr onclick="selectRow(this)">
                    <td><%= rs.getInt("ID_EVALUARE") %></td>
                    <td><%= rs.getInt("ID_CLIENT") %></td>
                    <td><%= rs.getInt("SCOR") %></td>
                    <td><%= rs.getString("FEEDBACK") %></td>
                    <td><%= rs.getDate("DATA_EVALUARII") %></td>
                </tr>
                <%
                            }
                        }
                    } catch (SQLException e) {
                %>
                <tr>
                    <td colspan="5" class="error">Error: <%= e.getMessage() %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>


<!-- Chart section -->
<div id="graficEvaluari" style="width: 800px; margin: auto;">
    <h2>Review Chart</h2>
    <canvas id="chartEvaluari" width="800" height="400"></canvas>
</div>

<!-- Include biblioteca Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    function afiseazaGraficEvaluari() {
        // Calls the chart data servlet
        fetch('graficEvaluari')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error retrieving chart data.');
                }
                return response.json();
            })
            .then(data => {
                console.log(data); // Debugging: prints retrieved data to the console
                const idEvaluare = data.map(item => item.id_evaluare);
                const scor = data.map(item => item.scor);

                // Configurarea graficului folosind Chart.js
                const ctx = document.getElementById('chartEvaluari').getContext('2d');
                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: idEvaluare,
                        datasets: [{
                            label: 'Scor Evaluare',
                            data: scor,
                            backgroundColor: 'rgba(54, 162, 235, 0.6)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            title: {
                                display: true,
                                text: 'Review Score Trend'
                            }
                        },
                        scales: {
                            x: {
                                title: {
                                    display: true,
                                    text: 'ID Evaluare'
                                }
                            },
                            y: {
                                title: {
                                    display: true,
                                    text: 'Scor'
                                },
                                beginAtZero: true
                            }
                        }
                    }
                });
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while displaying the chart.');
            });
    }

    // Calls the chart display function when the page loads
    window.onload = afiseazaGraficEvaluari;
</script>
        });
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while displaying the chart.');
            });
    }

    // Call the function to render the chart
    window.onload = afiseazaGraficEvaluari;
</script>


    </div>
</body>
</html>
