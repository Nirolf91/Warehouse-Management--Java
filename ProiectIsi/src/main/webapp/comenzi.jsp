<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="database.DatabaseConnection" %>
<%!
    private String h(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        h2 {
            text-align: center;
            margin-top: 20px;
        }
        .container {
            width: 90%;
            margin: auto;
        }
        /* Styled buttons */
        input[type="submit"], button, select {
            margin: 5px 0;
            margin-right: 6px;
            padding: 8px 16px;
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

        form {
            margin: 8px 0;
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
        /* Styled table */
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
            text-align: center;
        }
        td {
            padding: 10px;
            text-align: center;
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
        .message {
            color: green;
            text-align: center;
            margin-top: 10px;
        }
        .error {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
    <script>
        let selectedRow = null;
        let sortOrder = {};

        function selectRow(row) {
            if (selectedRow) selectedRow.classList.remove('selected');
            selectedRow = row;
            row.classList.add('selected');
            document.getElementById("id").value = row.cells[0].innerText;
            document.getElementById("data_comenzii").value = row.cells[1].innerText;
            document.getElementById("id_client").value = row.cells[2].innerText;
            document.getElementById("id_furnizor").value = row.cells[3].innerText;
            document.getElementById("id_angajat").value = row.cells[4].innerText;
            document.getElementById("id_material").value = row.cells[5].innerText;
            document.getElementById("total_comanda").value = row.cells[6].innerText;
            document.getElementById("statut_comanda").value = row.cells[7].innerText;
            document.getElementById("tip_comanda").value = row.cells[8].innerText;
            document.getElementById("cantitate").value = row.cells[9].innerText;
            document.getElementById("pret_total").value = row.cells[10].innerText;
            document.getElementById("id_transportator").value = row.cells[11].innerText;
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
            if (confirm("Are you sure you want to delete this order?")) {
                document.getElementById("deleteForm").submit();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <a href="home.jsp"><button type="button">Home</button></a>
        <h2>Order Management</h2>

        <%
            String status = request.getParameter("status");
            String message = request.getParameter("message");
            if ("success".equals(status)) {
        %>
            <div class="message"><%= h(message) %></div>
        <% } else if ("error".equals(status)) { %>
            <div class="error"><%= h(message) %></div>
        <% } %>

        <!-- Form for Add/Update -->
        <form action="comenzi" method="post">
            <input type="hidden" name="action" value="addOrUpdate">
            <label for="id">Order ID:</label>
            <input type="number" name="id" id="id" placeholder="ID (optional for add)"><br>
            <label for="data_comenzii">Order Date:</label>
            <input type="date" name="data_comenzii" id="data_comenzii" required><br>
            <label for="id_client">ID Client:</label>
            <input type="number" name="id_client" id="id_client" required><br>
            <label for="id_furnizor">Supplier ID:</label>
            <input type="number" name="id_furnizor" id="id_furnizor" required><br>
            <label for="id_angajat">Employee ID:</label>
            <input type="number" name="id_angajat" id="id_angajat" required><br>
            <label for="id_material">Material ID:</label>
            <input type="number" name="id_material" id="id_material" required><br>
            <label for="total_comanda">Order Total:</label>
            <input type="text" name="total_comanda" id="total_comanda" required><br>
            <label for="statut_comanda">Order Status:</label>
            <input type="text" name="statut_comanda" id="statut_comanda" required><br>
            <label for="tip_comanda">Order Type:</label>
            <input type="text" name="tip_comanda" id="tip_comanda" required><br>
            <label for="cantitate">Quantity:</label>
            <input type="number" name="cantitate" id="cantitate" required><br>
            <label for="pret_total">Total Price:</label>
            <input type="text" name="pret_total" id="pret_total" required><br>
            <label for="id_transportator">Carrier ID:</label>
            <input type="number" name="id_transportator" id="id_transportator" required><br>
            <input type="submit" value="Add / Update">
        </form>

        <!-- Form for Delete -->
        <form action="comenzi" method="post" id="deleteForm">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="delete" id="deleteId">
            <button type="button" onclick="confirmDelete()">Delete</button>
        </form>

        <!-- Filter Form -->
        <form action="comenzi.jsp" method="get">
            <label>Order ID:</label><input type="text" name="id">
            <label>Order Date:</label><input type="date" name="data_comenzii">
            <label>ID Client:</label><input type="text" name="id_client">
            <label>Status:</label><input type="text" name="statut_comanda">
            <button type="submit">Filter</button>
        </form>

        <!-- Export Options -->
        <form action="comenzi" method="get">
            <label for="exportFormat">Export data as:</label>
            <select name="export" id="exportFormat">
                <option value="csv">CSV</option>
                <option value="pdf">PDF</option>
            </select>
            <button type="submit">Export</button>
        </form>

     <!-- Orders Table -->
            <h2>Order List</h2>
        <table>
            <thead>
                <tr>
                    <th onclick="sortTable(0)">ID</th>
                    <th onclick="sortTable(1)">Order Date</th>
                    <th onclick="sortTable(2)">ID Client</th>
                    <th onclick="sortTable(3)">Supplier ID</th>
                    <th onclick="sortTable(4)">Employee ID</th>
                    <th onclick="sortTable(5)">Material ID</th>
                    <th onclick="sortTable(6)">Total</th>
                    <th onclick="sortTable(7)">Status</th>
                    <th onclick="sortTable(8)">Type</th>
                    <th onclick="sortTable(9)">Quantity</th>
                    <th onclick="sortTable(10)">Total Price</th>
                    <th onclick="sortTable(11)">Carrier ID</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String id = request.getParameter("id");
                    String data = request.getParameter("data_comenzii");
                    String client = request.getParameter("id_client");
                    String statut = request.getParameter("statut_comanda");

                    StringBuilder query = new StringBuilder("SELECT * FROM Comenzi WHERE 1=1");
                    if (id != null && !id.isEmpty()) query.append(" AND ID_COMANDA = ").append(id);
                    if (data != null && !data.isEmpty()) query.append(" AND DATA_COMENZII = '").append(data).append("'");
                    if (client != null && !client.isEmpty()) query.append(" AND ID_CLIENT = ").append(client);
                    if (statut != null && !statut.isEmpty()) query.append(" AND STATUT_COMANDA LIKE '%").append(statut).append("%'");

                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(query.toString());
                         ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                %>
                <tr onclick="selectRow(this)">
                    <td><%= rs.getInt("ID_COMANDA") %></td>
                    <td><%= rs.getDate("DATA_COMENZII") %></td>
                    <td><%= rs.getInt("ID_CLIENT") %></td>
                    <td><%= rs.getInt("ID_FURNIZOR") %></td>
                    <td><%= rs.getInt("ID_ANGAJAT") %></td>
                    <td><%= rs.getInt("ID_MATERIAL") %></td>
                    <td><%= rs.getDouble("TOTAL_COMANDA") %></td>
                    <td><%= h(rs.getString("STATUT_COMANDA")) %></td>
                    <td><%= h(rs.getString("TIP_COMANDA")) %></td>
                    <td><%= rs.getInt("CANTITATE") %></td>
                    <td><%= rs.getDouble("PRET_TOTAL") %></td>
                    <td><%= rs.getInt("ID_TRANSPORTATOR") %></td>
                </tr>
                <%
                        }
                    } catch (SQLException e) {
                %>
                <tr>
                    <td colspan="12" class="error">SQL error: <%= h(e.getMessage()) %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

<!-- Order distribution chart -->
<div id="graficComenzi" class="tab" style="width: 600px; height: 400px; margin: auto;">
    <h2>Order Chart</h2>
    <canvas id="chartComenzi"></canvas>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    function afiseazaGraficComenzi() {
        fetch('graficComenzi') // Calls the chart data servlet
            .then(response => response.json())
            .then(data => {
                const tipuriComenzi = data.map(item => item.tip_comanda);
                const numarComenzi = data.map(item => item.numar_comenzi);

                const ctx = document.getElementById('chartComenzi').getContext('2d');
                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: tipuriComenzi,
                        datasets: [{
                            label: 'Number of Orders',
                            data: numarComenzi,
                            backgroundColor: 'rgba(54, 162, 235, 0.6)'
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });
            })
            .catch(error => console.error('Error loading chart data:', error));
    }

    // Display the chart when the page loads
    window.onload = afiseazaGraficComenzi;
</script>




    </div>
</body>
</html>
