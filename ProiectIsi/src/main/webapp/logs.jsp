<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet" %>
<%@ page import="database.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Loguri</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <%
        // Ensure only admin users can access this page
        String role = (String) session.getAttribute("role");
        if (role == null || !"admin".equalsIgnoreCase(role)) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    <h2>Application Usage Logs</h2>
    <table>
        <thead>
            <tr>
                <th>ID Log</th>
                <th>Utilizator</th>
                <th>Rol</th>
                <th>Date and Time</th>
                <th>Logical Action</th>
                <th>SQL Command</th>
            </tr>
        </thead>
        <tbody>
            <%
                String query = "SELECT * FROM Logs ORDER BY DATA_ORA DESC";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement stmt = connection.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
            %>
            <tr>
                <td><%= rs.getInt("ID_LOG") %></td>
                <td><%= rs.getString("UTILIZATOR") %></td>
                <td><%= rs.getString("ROL") %></td>
                <td><%= rs.getTimestamp("DATA_ORA") %></td>
                <td><%= rs.getString("ACTIUNE_LOGICA") %></td>
                <td><%= rs.getString("COMANDA_SQL") %></td>
            </tr>
            <%
                    }
                } catch (Exception e) {
                    out.println("<tr><td colspan='6'>Error: " + e.getMessage() + "</td></tr>");
                }
            %>
        </tbody>
    </table>
</body>
</html>
