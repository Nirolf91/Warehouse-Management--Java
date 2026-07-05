package servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DatabaseConnection;

import filter.TransportatoriFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/transportatori")
public class TransportatoriServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exportFormat = request.getParameter("export");

        // Check whether export was requested
        if (exportFormat != null) {
            exportData(exportFormat, response);
            return;
        }

        // Read filter parameters
        String idTransportator = request.getParameter("id");
        String nume = request.getParameter("nume");
        String contact = request.getParameter("contact");
        String pretPeKg = request.getParameter("pret_pe_kg");

        try {
            // Filter data with TransportatoriFilter
            TransportatoriFilter filter = new TransportatoriFilter();
            ResultSet rs = filter.filterTransportatori(idTransportator, nume, contact, pretPeKg);

            // Store results in the request
            request.setAttribute("resultSet", rs);
            request.getRequestDispatcher("transportatori.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("transportatori.jsp?status=error&message=Filter error: " + e.getMessage());
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String deleteId = request.getParameter("delete");
        String nume = request.getParameter("nume");
        String contact = request.getParameter("contact");
        String pretKgParam = request.getParameter("pret_pe_kg");

        double pretKg = 0.0;

        try {
            if (pretKgParam != null) {
                pretKg = Double.parseDouble(pretKgParam);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("transportatori.jsp?status=error&message=Format invalid pentru price per kg.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (deleteId != null && !deleteId.isEmpty()) {
                // Deletere transportator
                String deleteQuery = "DELETE FROM Transportatori WHERE ID_TRANSPORTATOR = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
                    pstmt.setInt(1, Integer.parseInt(deleteId));
                    pstmt.executeUpdate();
                    response.sendRedirect("transportatori.jsp?status=success&message=Carrier deleted successfully!");
                }
            } else if (nume != null && contact != null && pretKgParam != null) {
                if (idParam != null && !idParam.isEmpty()) {
                    // Actualizare transportator
                    String updateQuery = "UPDATE Transportatori SET NUME = ?, CONTACT = ?, PRET_PE_KG = ? WHERE ID_TRANSPORTATOR = ?";
                    try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
                        pstmt.setString(1, nume);
                        pstmt.setString(2, contact);
                        pstmt.setDouble(3, pretKg);
                        pstmt.setInt(4, Integer.parseInt(idParam));
                        pstmt.executeUpdate();
                        response.sendRedirect("transportatori.jsp?status=success&message=Carrier updated successfully!");
                    }
                } else {
                    // Add a new carrier
                    String insertQuery = "INSERT INTO Transportatori (NUME, CONTACT, PRET_PE_KG) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                        pstmt.setString(1, nume);
                        pstmt.setString(2, contact);
                        pstmt.setDouble(3, pretKg);
                        pstmt.executeUpdate();
                        response.sendRedirect("transportatori.jsp?status=success&message=Carrier added successfully!");
                    }
                }
            } else {
                response.sendRedirect("transportatori.jsp?status=error&message=The submitted data is incomplete!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("transportatori.jsp?status=error&message=SQL error: " + e.getMessage());
        }
    }

    private void exportData(String format, HttpServletResponse response) throws IOException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Transportatori");
             ResultSet rs = stmt.executeQuery()) {

            if ("csv".equalsIgnoreCase(format)) {
                exportToCSV(rs, response);
            } else if ("pdf".equalsIgnoreCase(format)) {
                exportToPDF(rs, response);
            }
        } catch (Exception e) {
            response.sendRedirect("transportatori.jsp?status=error&message=Export error: " + e.getMessage());
        }
    }

    private void exportToCSV(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=transportatori.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID_TRANSPORTATOR,NUME,CONTACT,PRET_PE_KG");

        while (rs.next()) {
            writer.println(rs.getInt("ID_TRANSPORTATOR") + "," +
                    rs.getString("NUME") + "," +
                    rs.getString("CONTACT") + "," +
                    rs.getDouble("PRET_PE_KG"));
        }
        writer.flush();
    }

    private void exportToPDF(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transportatori.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("Carrier List\n\n"));

        PdfPTable table = new PdfPTable(4);
        table.addCell("ID Transportator");
        table.addCell("Name");
        table.addCell("Contact");
        table.addCell("Price per Kg");

        while (rs.next()) {
            table.addCell(String.valueOf(rs.getInt("ID_TRANSPORTATOR")));
            table.addCell(rs.getString("NUME"));
            table.addCell(rs.getString("CONTACT"));
            table.addCell(String.valueOf(rs.getDouble("PRET_PE_KG")));
        }
        document.add(table);
        document.close();
    }
}
