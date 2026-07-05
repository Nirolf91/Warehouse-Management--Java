<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="database.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestionare Comenzi</title>
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
            if (confirm("Sigur doriți să ștergeți această comandă?")) {
                document.getElementById("deleteForm").submit();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Gestionare Comenzi</h2>

        <!-- Form for Add/Update -->
        <form action="comenzi" method="post">
            <input type="hidden" name="action" value="addOrUpdate">
            <label for="id">ID Comandă:</label>
            <input type="number" name="id" id="id" placeholder="ID (opțional pentru Adăugare)"><br>
            <label for="data_comenzii">Data Comenzii:</label>
            <input type="date" name="data_comenzii" id="data_comenzii" required><br>
            <label for="id_client">ID Client:</label>
            <input type="number" name="id_client" id="id_client" required><br>
            <label for="id_furnizor">ID Furnizor:</label>
            <input type="number" name="id_furnizor" id="id_furnizor" required><br>
            <label for="id_angajat">ID Angajat:</label>
            <input type="number" name="id_angajat" id="id_angajat" required><br>
            <label for="id_material">ID Material:</label>
            <input type="number" name="id_material" id="id_material" required><br>
            <label for="total_comanda">Total Comandă:</label>
            <input type="text" name="total_comanda" id="total_comanda" required><br>
            <label for="statut_comanda">Statut Comandă:</label>
            <input type="text" name="statut_comanda" id="statut_comanda" required><br>
            <label for="tip_comanda">Tip Comandă:</label>
            <input type="text" name="tip_comanda" id="tip_comanda" required><br>
            <label for="cantitate">Cantitate:</label>
            <input type="number" name="cantitate" id="cantitate" required><br>
            <label for="pret_total">Preț Total:</label>
            <input type="text" name="pret_total" id="pret_total" required><br>
            <label for="id_transportator">ID Transportator:</label>
            <input type="number" name="id_transportator" id="id_transportator" required><br>
            <input type="submit" value="Adaugă / Actualizează">
        </form>

        <!-- Form for Delete -->
        <form action="comenzi" method="post" id="deleteForm">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" id="deleteId">
            <button type="button" onclick="confirmDelete()">Șterge</button>
        </form>

        <!-- Filter Form -->
        <form action="comenzi.jsp" method="get">
            <label>ID Comandă:</label><input type="text" name="id">
            <label>Data Comenzii:</label><input type="date" name="data_comenzii">
            <label>ID Client:</label><input type="text" name="id_client">
            <label>Statut:</label><input type="text" name="statut_comanda">
            <button type="submit">Filtrează</button>
        </form>

        <!-- Export Options -->
        <form action="comenzi" method="get">
            <label for="exportFormat">Export date:</label>
            <select name="export" id="exportFormat">
                <option value="csv">CSV</option>
                <option value="pdf">PDF</option>
            </select>
            <button type="submit">Export</button>
        </form>

     <!-- Orders Table -->
            <h2>Lista Comenzi</h2>
        <table>
            <thead>
                <tr>
                    <th onclick="sortTable(0)">ID</th>
                    <th onclick="sortTable(1)">Data Comenzii</th>
                    <th onclick="sortTable(2)">ID Client</th>
                    <th onclick="sortTable(3)">ID Furnizor</th>
                    <th onclick="sortTable(4)">ID Angajat</th>
                    <th onclick="sortTable(5)">ID Material</th>
                    <th onclick="sortTable(6)">Total</th>
                    <th onclick="sortTable(7)">Statut</th>
                    <th onclick="sortTable(8)">Tip</th>
                    <th onclick="sortTable(9)">Cantitate</th>
                    <th onclick="sortTable(10)">Preț Total</th>
                    <th onclick="sortTable(11)">ID Transportator</th>
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
                    <td><%= rs.getString("STATUT_COMANDA") %></td>
                    <td><%= rs.getString("TIP_COMANDA") %></td>
                    <td><%= rs.getInt("CANTITATE") %></td>
                    <td><%= rs.getDouble("PRET_TOTAL") %></td>
                    <td><%= rs.getInt("ID_TRANSPORTATOR") %></td>
                </tr>
                <%
                        }
                    } catch (SQLException e) {
                %>
                <tr>
                    <td colspan="12" class="error">Eroare SQL: <%= e.getMessage() %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

<!-- Grafic pentru distribuția comenzilor -->
<div id="graficComenzi" class="tab" style="width: 600px; height: 400px; margin: auto;">
    <h2>Grafic Comenzi</h2>
    <canvas id="chartComenzi"></canvas>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    function afiseazaGraficComenzi() {
        fetch('graficComenzi') // Apelează servlet-ul pentru datele graficului
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
                            label: 'Număr de Comenzi',
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
            .catch(error => console.error('Eroare la încărcarea datelor pentru grafic:', error));
    }

    // Afișează graficul când se încarcă pagina
    window.onload = afiseazaGraficComenzi;
</script>




    </div>
</body>
</html>
