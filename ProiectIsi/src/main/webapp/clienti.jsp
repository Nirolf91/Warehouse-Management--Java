<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="database.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestionare Clienți</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            font-size: 2em;
            color: #333;
            margin-bottom: 20px;
            text-transform: uppercase;
        }

        .container {
            width: 80%;
            margin: auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            font-size: 14px;
            text-align: center;
        }

        td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
            font-size: 14px;
            color: #555;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ddd;
        }

        button,
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 15px;
            font-size: 14px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover,
        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .error {
            color: red;
            text-align: center;
            margin: 10px 0;
        }

        .message {
            color: green;
            text-align: center;
            margin: 10px 0;
        }

        form label {
            font-weight: bold;
            color: #333;
        }

        .button-container {
            display: flex;
            gap: 15px; /* Spațiu între butoane */
            margin-top: 10px;
        }

        .button-container button,
        .button-container input[type="submit"] {
            flex: 1; /* Opțional: pentru a face butoanele de dimensiuni egale */
        }

        tr.selected {
            background-color: #d1ecf1; /* Evidențiază rândul selectat */
            color: #333; /* Asigură lizibilitate */
        }

    </style>
</head>

    <script>
        let selectedRow = null;
        let sortOrder = {}; // Obiect pentru a urmări ordinea de sortare pe coloane

        function selectRow(row) {
            if (selectedRow) selectedRow.classList.remove('selected');
            selectedRow = row;
            row.classList.add('selected');

            document.getElementById("id").value = row.cells[0].innerText;
            document.getElementById("nume").value = row.cells[1].innerText;
            document.getElementById("adresa").value = row.cells[2].innerText;
            document.getElementById("contact").value = row.cells[3].innerText;
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
            if (confirm("Sigur doriți să ștergeți acest client?")) {
                document.getElementById("deleteForm").submit();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Gestionare Clienți</h2>

        <!-- Formular pentru Adăugare/Actualizare -->
        <form action="clienti" method="post">
            <label for="id">ID:</label>
            <input type="number" name="id" id="id" placeholder="ID (opțional)"><br>
            <label for="nume">Nume:</label>
            <input type="text" name="nume" id="nume" required><br>
            <label for="adresa">Adresă:</label>
            <input type="text" name="adresa" id="adresa" required><br>
            <label for="contact">Contact:</label>
            <input type="text" name="contact" id="contact" required><br>
            <input type="submit" value="Adaugă / Actualizează">
        </form>

        <!-- Formular pentru Ștergere -->
        <form action="clienti" method="post" id="deleteForm">
            <input type="hidden" name="delete" id="deleteId">
            <button type="button" onclick="confirmDelete()">Șterge</button>
        </form>

        <!-- Formular pentru Export -->
        <form action="clienti" method="get">
            <label for="exportFormat">Exportă datele în format:</label>
            <select name="export" id="exportFormat">
                <option value="csv">CSV</option>
                <option value="pdf">PDF</option>
            </select>
            <button type="submit">Export</button>
        </form>

        <!-- Formular pentru Filtrare -->
        <form action="clienti.jsp" method="get">
            <label for="idFilter">ID:</label>
            <input type="text" name="id" id="idFilter">
            <label for="numeFilter">Nume:</label>
            <input type="text" name="nume" id="numeFilter">
            <label for="adresaFilter">Adresă:</label>
            <input type="text" name="adresa" id="adresaFilter">
            <label for="contactFilter">Contact:</label>
            <input type="text" name="contact" id="contactFilter">
            <button type="submit">Filtrează</button>
        </form>

        <!-- Tabel pentru afișarea clienților -->
        <h2>Lista Clienți</h2>
        <table>
            <thead>
                <tr>
                    <th onclick="sortTable(0)">ID</th>
                    <th onclick="sortTable(1)">Nume</th>
                    <th onclick="sortTable(2)">Adresă</th>
                    <th onclick="sortTable(3)">Contact</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String idFilter = request.getParameter("id");
                    String numeFilter = request.getParameter("nume");
                    String adresaFilter = request.getParameter("adresa");
                    String contactFilter = request.getParameter("contact");

                    StringBuilder query = new StringBuilder("SELECT * FROM Clienti WHERE 1=1");
                    if (idFilter != null && !idFilter.isEmpty()) query.append(" AND ID_CLIENT = ?");
                    if (numeFilter != null && !numeFilter.isEmpty()) query.append(" AND NUME LIKE ?");
                    if (adresaFilter != null && !adresaFilter.isEmpty()) query.append(" AND ADRESA LIKE ?");
                    if (contactFilter != null && !contactFilter.isEmpty()) query.append(" AND CONTACT LIKE ?");

                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(query.toString())) {
                        int index = 1;

                        if (idFilter != null && !idFilter.isEmpty()) stmt.setInt(index++, Integer.parseInt(idFilter));
                        if (numeFilter != null && !numeFilter.isEmpty()) stmt.setString(index++, "%" + numeFilter + "%");
                        if (adresaFilter != null && !adresaFilter.isEmpty()) stmt.setString(index++, "%" + adresaFilter + "%");
                        if (contactFilter != null && !contactFilter.isEmpty()) stmt.setString(index++, "%" + contactFilter + "%");

                        try (ResultSet rs = stmt.executeQuery()) {
                            while (rs.next()) {
                %>
                <tr onclick="selectRow(this)">
                    <td><%= rs.getInt("ID_CLIENT") %></td>
                    <td><%= rs.getString("NUME") %></td>
                    <td><%= rs.getString("ADRESA") %></td>
                    <td><%= rs.getString("CONTACT") %></td>
                </tr>
                <%
                            }
                        }
                    } catch (SQLException e) {
                %>
                <tr>
                    <td colspan="4" class="error">Eroare: <%= e.getMessage() %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
