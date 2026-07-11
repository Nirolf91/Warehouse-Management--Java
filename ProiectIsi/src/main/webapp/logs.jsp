<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet" %>
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
    <title>Application Logs</title>
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
    <a href="home.jsp"><button type="button">Home</button></a>
    <h2>Application Usage Logs</h2>
    <table>
        <thead>
            <tr>
                <th>ID Log</th>
                <th>User</th>
                <th>Role</th>
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
                <td><%= h(rs.getString("UTILIZATOR")) %></td>
                <td><%= h(rs.getString("ROL")) %></td>
                <td><%= rs.getTimestamp("DATA_ORA") %></td>
                <td><%= h(rs.getString("ACTIUNE_LOGICA")) %></td>
                <td><%= h(rs.getString("COMANDA_SQL")) %></td>
            </tr>
            <%
                    }
                } catch (Exception e) {
                    out.println("<tr><td colspan='6'>Error: " + h(e.getMessage()) + "</td></tr>");
                }
            %>
        </tbody>
    </table>
</body>
</html>
