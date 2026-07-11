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
    <title>Material Management</title>
    <style>
       input[type="submit"], button, select {
           margin-top: 10px;
           margin-right: 6px;
           margin-bottom: 4px;
           padding: 10px 20px;
           font-size: 14px;
           font-weight: bold;
           color: white;
           background-color: #4CAF50;
           border: none;
           border-radius: 5px;
           cursor: pointer;
           transition: background-color 0.3s ease, transform 0.2s ease;
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
           padding: 12px 8px;
       }

       td {
           padding: 10px 8px;
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
           color: #333;
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
            document.getElementById("descriere").value = row.cells[2].innerText;
            document.getElementById("cantitate").value = row.cells[3].innerText;
            document.getElementById("pret").value = row.cells[4].innerText;
            document.getElementById("idFurnizor").value = row.cells[5].innerText;
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
            if (confirm("Are you sure you want to delete this material?")) {
                document.getElementById("deleteForm").submit();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <a href="home.jsp"><button type="button">Home</button></a>
        <h2>Material Management</h2>

        <!-- Add/Update form -->
        <form action="materiale" method="post">
            <label for="id">ID:</label>
            <input type="number" name="id" id="id" placeholder="ID (optional)"><br>
            <label for="nume">Name:</label>
            <input type="text" name="nume" id="nume" required><br>
            <label for="descriere">Description:</label>
            <input type="text" name="descriere" id="descriere" required><br>
            <label for="cantitate">Stock Quantity:</label>
            <input type="number" name="cantitate" id="cantitate" required><br>
            <label for="pret">Unit Price:</label>
            <input type="number" step="0.01" name="pret" id="pret" required><br>
            <label for="idFurnizor">Supplier ID:</label>
            <input type="number" name="idFurnizor" id="idFurnizor" required><br>
            <input type="submit" value="Add / Update">
        </form>

        <!-- Delete form -->
        <form action="materiale" method="post" id="deleteForm">
            <input type="hidden" name="delete" id="deleteId">
            <button type="button" onclick="confirmDelete()">Delete</button>
        </form>

        <!-- Export form -->
        <form action="materiale" method="post">
            <label for="exportFormat">Export data as:</label>
            <select name="export" id="exportFormat">
                <option value="csv">CSV</option>
                <option value="pdf">PDF</option>
            </select>
            <button type="submit">Export</button>
        </form>

        <!-- Filter form -->
        <form action="materiale.jsp" method="get">
            <label for="idFilter">ID:</label>
            <input type="text" name="id" id="idFilter">
            <label for="numeFilter">Name:</label>
            <input type="text" name="nume" id="numeFilter">
            <label for="descriereFilter">Description:</label>
            <input type="text" name="descriere" id="descriereFilter">
            <label for="cantitateFilter">Quantity:</label>
            <input type="text" name="cantitate" id="cantitateFilter">
            <label for="pretFilter">Price:</label>
            <input type="text" name="pret" id="pretFilter">
            <label for="idFurnizorFilter">Supplier ID:</label>
            <input type="text" name="idFurnizor" id="idFurnizorFilter">
            <button type="submit">Filter</button>
        </form>

        <!-- Material table -->
        <h2>Material List</h2>
        <table>
            <thead>
                <tr>
                    <th onclick="sortTable(0)">ID</th>
                    <th onclick="sortTable(1)">Name</th>
                    <th onclick="sortTable(2)">Description</th>
                    <th onclick="sortTable(3)">Stock Quantity</th>
                    <th onclick="sortTable(4)">Unit Price</th>
                    <th onclick="sortTable(5)">Supplier ID</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String id = request.getParameter("id");
                    String nume = request.getParameter("nume");
                    String descriere = request.getParameter("descriere");
                    String cantitate = request.getParameter("cantitate");
                    String pret = request.getParameter("pret");
                    String idFurnizor = request.getParameter("idFurnizor");

                    StringBuilder query = new StringBuilder("SELECT * FROM Materiale WHERE 1=1");
                    if (id != null && !id.isEmpty()) query.append(" AND ID_MATERIAL = ?");
                    if (nume != null && !nume.isEmpty()) query.append(" AND NUME LIKE ?");
                    if (descriere != null && !descriere.isEmpty()) query.append(" AND DESCRIERE LIKE ?");
                    if (cantitate != null && !cantitate.isEmpty()) query.append(" AND CANTITATE_IN_STOC = ?");
                    if (pret != null && !pret.isEmpty()) query.append(" AND PRET_UNITAR = ?");
                    if (idFurnizor != null && !idFurnizor.isEmpty()) query.append(" AND ID_FURNIZOR = ?");

                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(query.toString())) {
                        int index = 1;
                        if (id != null && !id.isEmpty()) stmt.setInt(index++, Integer.parseInt(id));
                        if (nume != null && !nume.isEmpty()) stmt.setString(index++, "%" + nume + "%");
                        if (descriere != null && !descriere.isEmpty()) stmt.setString(index++, "%" + descriere + "%");
                        if (cantitate != null && !cantitate.isEmpty()) stmt.setInt(index++, Integer.parseInt(cantitate));
                        if (pret != null && !pret.isEmpty()) stmt.setDouble(index++, Double.parseDouble(pret));
                        if (idFurnizor != null && !idFurnizor.isEmpty()) stmt.setInt(index, Integer.parseInt(idFurnizor));

                        try (ResultSet rs = stmt.executeQuery()) {
                            while (rs.next()) {
                %>
                <tr onclick="selectRow(this)">
                    <td><%= rs.getInt("ID_MATERIAL") %></td>
                    <td><%= h(rs.getString("NUME")) %></td>
                    <td><%= h(rs.getString("DESCRIERE")) %></td>
                    <td><%= rs.getInt("CANTITATE_IN_STOC") %></td>
                    <td><%= rs.getDouble("PRET_UNITAR") %></td>
                    <td><%= rs.getInt("ID_FURNIZOR") %></td>
                </tr>
                <%
                            }
                        }
                    } catch (SQLException e) {
                %>
                <tr>
                    <td colspan="6" class="error">Error: <%= h(e.getMessage()) %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
