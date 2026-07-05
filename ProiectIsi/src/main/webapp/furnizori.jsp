<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="database.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Supplier Management</title>
<style>
    /* Text-field and input styling pentru dimensiuni similare */
    input[type="text"], input[type="number"], input[type="date"], select {
        width: calc(30% - 10px); /* Adjusts the size based on available space */
        margin: 5px 0;
        padding: 10px; /* Consistent padding for all fields */
        font-size: 14px;
        border: 1px solid #ccc;
        border-radius: 5px;
        box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1);
    }

    /* Butoanele */
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

    /* Export form */
    .filter-input {
        width: calc(30% - 10px);
        padding: 10px;
        margin-bottom: 10px;
    }

    /* Tabel stilizat */
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

</style>

    <script>
        let selectedRow = null;
        let sortOrder = {};

        // Selects a row
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

        // Sort function
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

        // Delete confirmation function
        function confirmDelete() {
            if (confirm("Are you sure you want to delete this supplier?")) {
                document.getElementById("deleteForm").submit();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Supplier Management</h2>

        <!-- Add/Update form -->
        <form action="furnizori" method="post">
            <label for="id">ID:</label>
            <input type="number" name="id" id="id" placeholder="ID (optional)"><br>
            <label for="nume">Name:</label>
            <input type="text" name="nume" id="nume" required><br>
            <label for="adresa">Address:</label>
            <input type="text" name="adresa" id="adresa" required><br>
            <label for="contact">Contact:</label>
            <input type="text" name="contact" id="contact" required><br>
            <input type="submit" value="Add / Update">
        </form>

        <!-- Delete form -->
        <form action="furnizori" method="post" id="deleteForm">
            <input type="hidden" name="delete" id="deleteId">
            <button type="button" onclick="confirmDelete()">Delete</button>
        </form>

        <!-- Export form -->
        <form action="furnizori" method="get">
            <label for="exportFormat">Export data as:</label>
            <select name="export" id="exportFormat">
                <option value="csv">CSV</option>
                <option value="pdf">PDF</option>
            </select>
            <button type="submit">Export</button>
        </form>

<!-- Filter form -->
<form action="furnizori.jsp" method="get" class="filter-form">
    <label for="idFilter" class="filter-label">ID:</label>
    <input type="text" name="id" id="idFilter" class="filter-input">

    <label for="numeFilter" class="filter-label">Name:</label>
    <input type="text" name="nume" id="numeFilter" class="filter-input">

    <label for="adresaFilter" class="filter-label">Address:</label>
    <input type="text" name="adresa" id="adresaFilter" class="filter-input">

    <label for="contactFilter" class="filter-label">Contact:</label>
    <input type="text" name="contact" id="contactFilter" class="filter-input">

    <button type="submit" class="filter-button">Filter</button>
</form>





        <!-- Supplier table -->
        <h2>Supplier List</h2>
        <table>
            <thead>
                <tr>
                    <th onclick="sortTable(0)">ID</th>
                    <th onclick="sortTable(1)">Name</th>
                    <th onclick="sortTable(2)">Address</th>
                    <th onclick="sortTable(3)">Contact</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String idFilter = request.getParameter("id");
                    String numeFilter = request.getParameter("nume");
                    String adresaFilter = request.getParameter("adresa");
                    String contactFilter = request.getParameter("contact");

                    StringBuilder query = new StringBuilder("SELECT * FROM Furnizori WHERE 1=1");
                    if (idFilter != null && !idFilter.isEmpty()) query.append(" AND ID_FURNIZOR = ?");
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
                    <td><%= rs.getInt("ID_FURNIZOR") %></td>
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
                    <td colspan="4" class="error">Error: <%= e.getMessage() %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
