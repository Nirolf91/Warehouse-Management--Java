package servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DatabaseConnection;
import filter.ComenziFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/comenzi")
public class ComenziServlet extends HttpServlet {
    private String page(HttpServletRequest request, String status, String message) {
        return request.getContextPath() + "/comenzi.jsp?status=" + encode(status)
                + "&message=" + encode(message);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exportFormat = request.getParameter("export");

        // Export date
        if (exportFormat != null) {
            exportData(exportFormat, response);
            return;
        }

        // Filter orders
        String idComanda = request.getParameter("id");
        String dataComenzii = request.getParameter("data_comenzii");
        String idClient = request.getParameter("id_client");
        String statutComanda = request.getParameter("statut_comanda");

        try {
            // Use ComenziFilter to filter orders
            ComenziFilter filter = new ComenziFilter();
            ResultSet rs = filter.filterComenzi(idComanda, dataComenzii, idClient, null, null, null, null, statutComanda, null, null, null, null);

            // Forward results to JSP
            request.setAttribute("resultSet", rs);
            request.getRequestDispatcher("comenzi.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(page(request, "error", "SQL error: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            response.sendRedirect(page(request, "error", "Invalid order data: " + e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String deleteId = request.getParameter("delete");
        String dataComenzii = request.getParameter("data_comenzii");
        String idClient = request.getParameter("id_client");
        String idFurnizor = request.getParameter("id_furnizor");
        String idAngajat = request.getParameter("id_angajat");
        String idMaterial = request.getParameter("id_material");
        String totalComanda = request.getParameter("total_comanda");
        String statutComanda = request.getParameter("statut_comanda");
        String tipComanda = request.getParameter("tip_comanda");
        String cantitate = request.getParameter("cantitate");
        String pretTotal = request.getParameter("pret_total");
        String idTransportator = request.getParameter("id_transportator");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (deleteId != null && !deleteId.isEmpty()) {
                // Delete an order
                deleteComanda(request, connection, deleteId, response);
            } else if (dataComenzii != null && idClient != null && idFurnizor != null && idAngajat != null
                    && idMaterial != null && totalComanda != null && statutComanda != null && tipComanda != null
                    && cantitate != null && pretTotal != null && idTransportator != null) {

                if (idParam != null && !idParam.isEmpty()) {
                    // Update order
                    updateComanda(request, connection, idParam, dataComenzii, idClient, idFurnizor, idAngajat,
                            idMaterial, totalComanda, statutComanda, tipComanda, cantitate, pretTotal, idTransportator, response);
                } else {
                    // Add a new order
                    addComanda(request, connection, dataComenzii, idClient, idFurnizor, idAngajat, idMaterial,
                            totalComanda, statutComanda, tipComanda, cantitate, pretTotal, idTransportator, response);
                }
            } else {
                response.sendRedirect(page(request, "error", "The submitted data is incomplete!"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(page(request, "error", "SQL error: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            response.sendRedirect(page(request, "error", "Invalid order data: " + e.getMessage()));
        }
    }

    private void deleteComanda(HttpServletRequest request, Connection connection, String deleteId, HttpServletResponse response) throws IOException, SQLException {
        String deleteQuery = "DELETE FROM Comenzi WHERE ID_COMANDA = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, Integer.parseInt(deleteId));
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                response.sendRedirect(page(request, "success", "Order deleted successfully!"));
            } else {
                response.sendRedirect(page(request, "error", "No order was found for deletion."));
            }
        }
    }

    private void updateComanda(HttpServletRequest request, Connection connection, String idParam, String dataComenzii, String idClient, String idFurnizor,
                               String idAngajat, String idMaterial, String totalComanda, String statutComanda,
                               String tipComanda, String cantitate, String pretTotal, String idTransportator,
                               HttpServletResponse response) throws IOException, SQLException {
        String updateQuery = "UPDATE Comenzi SET DATA_COMENZII = ?, ID_CLIENT = ?, ID_FURNIZOR = ?, " +
                "ID_ANGAJAT = ?, ID_MATERIAL = ?, TOTAL_COMANDA = ?, STATUT_COMANDA = ?, TIP_COMANDA = ?, " +
                "CANTITATE = ?, PRET_TOTAL = ?, ID_TRANSPORTATOR = ? WHERE ID_COMANDA = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setDate(1, Date.valueOf(dataComenzii));
            pstmt.setInt(2, Integer.parseInt(idClient));
            pstmt.setInt(3, Integer.parseInt(idFurnizor));
            pstmt.setInt(4, Integer.parseInt(idAngajat));
            pstmt.setInt(5, Integer.parseInt(idMaterial));
            pstmt.setDouble(6, Double.parseDouble(totalComanda));
            pstmt.setString(7, statutComanda);
            pstmt.setString(8, tipComanda);
            pstmt.setInt(9, Integer.parseInt(cantitate));
            pstmt.setDouble(10, Double.parseDouble(pretTotal));
            pstmt.setInt(11, Integer.parseInt(idTransportator));
            pstmt.setInt(12, Integer.parseInt(idParam));
            pstmt.executeUpdate();
            response.sendRedirect(page(request, "success", "Order updated successfully!"));
        }
    }

    private void addComanda(HttpServletRequest request, Connection connection, String dataComenzii, String idClient, String idFurnizor,
                            String idAngajat, String idMaterial, String totalComanda, String statutComanda,
                            String tipComanda, String cantitate, String pretTotal, String idTransportator,
                            HttpServletResponse response) throws IOException, SQLException {
        String insertQuery = "INSERT INTO Comenzi (ID_COMANDA, DATA_COMENZII, ID_CLIENT, ID_FURNIZOR, ID_ANGAJAT, " +
                "ID_MATERIAL, TOTAL_COMANDA, STATUT_COMANDA, TIP_COMANDA, CANTITATE, PRET_TOTAL, ID_TRANSPORTATOR) " +
                "VALUES (comanda_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setDate(1, Date.valueOf(dataComenzii));
            pstmt.setInt(2, Integer.parseInt(idClient));
            pstmt.setInt(3, Integer.parseInt(idFurnizor));
            pstmt.setInt(4, Integer.parseInt(idAngajat));
            pstmt.setInt(5, Integer.parseInt(idMaterial));
            pstmt.setDouble(6, Double.parseDouble(totalComanda));
            pstmt.setString(7, statutComanda);
            pstmt.setString(8, tipComanda);
            pstmt.setInt(9, Integer.parseInt(cantitate));
            pstmt.setDouble(10, Double.parseDouble(pretTotal));
            pstmt.setInt(11, Integer.parseInt(idTransportator));
            pstmt.executeUpdate();
            response.sendRedirect(page(request, "success", "Order added successfully!"));
        }
    }

    private void exportData(String format, HttpServletResponse response) throws IOException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Comenzi");
             ResultSet rs = stmt.executeQuery()) {

            if ("csv".equalsIgnoreCase(format)) {
                exportToCSV(rs, response);
            } else if ("pdf".equalsIgnoreCase(format)) {
                exportToPDF(rs, response);
            }
        } catch (Exception e) {
            response.sendRedirect("comenzi.jsp?status=error&message=Export error: " + e.getMessage());
        }
    }

    private void exportToCSV(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=orders.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID_COMANDA,DATA_COMENZII,ID_CLIENT,ID_FURNIZOR,ID_ANGAJAT,ID_MATERIAL,TOTAL_COMANDA," +
                "STATUT_COMANDA,TIP_COMANDA,CANTITATE,PRET_TOTAL,ID_TRANSPORTATOR");

        while (rs.next()) {
            writer.println(rs.getInt("ID_COMANDA") + "," +
                    rs.getString("DATA_COMENZII") + "," +
                    rs.getInt("ID_CLIENT") + "," +
                    rs.getInt("ID_FURNIZOR") + "," +
                    rs.getInt("ID_ANGAJAT") + "," +
                    rs.getInt("ID_MATERIAL") + "," +
                    rs.getDouble("TOTAL_COMANDA") + "," +
                    rs.getString("STATUT_COMANDA") + "," +
                    rs.getString("TIP_COMANDA") + "," +
                    rs.getInt("CANTITATE") + "," +
                    rs.getDouble("PRET_TOTAL") + "," +
                    rs.getInt("ID_TRANSPORTATOR"));
        }
        writer.flush();
    }

    private void exportToPDF(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=orders.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("Order List\n\n"));

        PdfPTable table = new PdfPTable(12);
        table.addCell("ID");
        table.addCell("Date");
        table.addCell("ID Client");
        table.addCell("Supplier ID");
        table.addCell("Employee ID");
        table.addCell("Material ID");
        table.addCell("Total");
        table.addCell("Status");
        table.addCell("Type");
        table.addCell("Quantity");
        table.addCell("Total Price");
        table.addCell("Carrier ID");

        while (rs.next()) {
            table.addCell(String.valueOf(rs.getInt("ID_COMANDA")));
            table.addCell(rs.getString("DATA_COMENZII"));
            table.addCell(String.valueOf(rs.getInt("ID_CLIENT")));
            table.addCell(String.valueOf(rs.getInt("ID_FURNIZOR")));
            table.addCell(String.valueOf(rs.getInt("ID_ANGAJAT")));
            table.addCell(String.valueOf(rs.getInt("ID_MATERIAL")));
            table.addCell(String.valueOf(rs.getDouble("TOTAL_COMANDA")));
            table.addCell(rs.getString("STATUT_COMANDA"));
            table.addCell(rs.getString("TIP_COMANDA"));
            table.addCell(String.valueOf(rs.getInt("CANTITATE")));
            table.addCell(String.valueOf(rs.getDouble("PRET_TOTAL")));
            table.addCell(String.valueOf(rs.getInt("ID_TRANSPORTATOR")));
        }
        document.add(table);
        document.close();
    }
}
