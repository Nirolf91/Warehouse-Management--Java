<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="database.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carrier Management</title>
    <style>
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
            text-align: center; /* Centrare text pentru titluri */
        }

        td {
            padding: 10px;
            text-align: center; /* Centers content text */
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

        /* Error and success messages */
        .error {
            color: red;
            text-align: center;
            margin-top: 10px;
        }

        .message {
            color: green;
            text-align: center;
            margin-top: 10px;
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
            document.getElementById("nume").value = row.cells[1].innerText;
            document.getElementById("contact").value = row.cells[2].innerText;
            document.getElementById("pret").value = row.cells[3].innerText;
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
            if (confirm("Are you sure you want to delete this carrier?")) {
                document.getElementById("deleteForm").submit();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Carrier Management</h2>

        <!-- Add/Update form -->
        <form action="transportatori" method="post">
            <label for="id">ID:</label>
            <input type="number" name="id" id="id" placeholder="ID (optional)"><br>
            <label for="nume">Name:</label>
            <input type="text" name="nume" id="nume" required><br>
            <label for="contact">Contact:</label>
            <input type="text" name="contact" id="contact" required><br>
            <label for="pret">Price per KG:</label>
            <input type="number" step="0.01" name="pret_pe_kg" id="pret" required><br>
            <input type="submit" value="Add / Update">
        </form>

        <!-- Delete form -->
        <form action="transportatori" method="post" id="deleteForm">
            <input type="hidden" name="delete" id="deleteId">
            <button type="button" onclick="confirmDelete()">Delete</button>
        </form>

        <!-- Filter form -->
        <form action="transportatori.jsp" method="get">
            <label for="idFilter">ID:</label>
            <input type="text" name="id" id="idFilter" class="filter-input">
            <label for="numeFilter">Name:</label>
            <input type="text" name="nume" id="numeFilter" class="filter-input">
            <label for="contactFilter">Contact:</label>
            <input type="text" name="contact" id="contactFilter" class="filter-input">
            <label for="pretFilter">Price per KG:</label>
            <input type="text" name="pret_pe_kg" id="pretFilter" class="filter-input">
            <button type="submit">Filter</button>
        </form>

        <!-- Export form -->
<form action="transportatori" method="get">
    <label for="exportFormat">Export date:</label>
    <select name="export" id="exportFormat">
        <option value="csv">CSV</option>
        <option value="pdf">PDF</option>
    </select>
    <button type="submit">Export</button>
</form>



        <!-- Carrier table -->
        <h2>Carrier List</h2>
        <table>
            <thead>
                <tr>
                    <th onclick="sortTable(0)">ID</th>
                    <th onclick="sortTable(1)">Name</th>
                    <th onclick="sortTable(2)">Contact</th>
                    <th onclick="sortTable(3)">Price per KG</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String idFilter = request.getParameter("id");
                    String numeFilter = request.getParameter("nume");
                    String contactFilter = request.getParameter("contact");
                    String pretFilter = request.getParameter("pret_pe_kg");

                    StringBuilder query = new StringBuilder("SELECT * FROM Transportatori WHERE 1=1");
                    if (idFilter != null && !idFilter.isEmpty()) query.append(" AND ID_TRANSPORTATOR = ? ");
                    if (numeFilter != null && !numeFilter.isEmpty()) query.append(" AND NUME LIKE ? ");
                    if (contactFilter != null && !contactFilter.isEmpty()) query.append(" AND CONTACT LIKE ? ");
                    if (pretFilter != null && !pretFilter.isEmpty()) query.append(" AND PRET_PE_KG = ? ");

                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(query.toString())) {

                        int index = 1;
                        if (idFilter != null && !idFilter.isEmpty()) stmt.setInt(index++, Integer.parseInt(idFilter));
                        if (numeFilter != null && !numeFilter.isEmpty()) stmt.setString(index++, "%" + numeFilter + "%");
                        if (contactFilter != null && !contactFilter.isEmpty()) stmt.setString(index++, "%" + contactFilter + "%");
                        if (pretFilter != null && !pretFilter.isEmpty()) stmt.setDouble(index, Double.parseDouble(pretFilter));

                        try (ResultSet rs = stmt.executeQuery()) {
                            while (rs.next()) {
                %>
                <tr onclick="selectRow(this)">
                    <td><%= rs.getInt("ID_TRANSPORTATOR") %></td>
                    <td><%= rs.getString("NUME") %></td>
                    <td><%= rs.getString("CONTACT") %></td>
                    <td><%= rs.getDouble("PRET_PE_KG") %></td>
                </tr>
                <%
                            }
                        }
                    } catch (SQLException e) {
                %>
                <tr>
                    <td colspan="4" class="error">Error loading data: <%= e.getMessage() %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
