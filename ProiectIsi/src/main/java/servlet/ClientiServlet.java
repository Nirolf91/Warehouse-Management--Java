package servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DatabaseConnection;
import filter.ClientiFilter;
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

@WebServlet("/clienti")
public class ClientiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exportFormat = request.getParameter("export");

        // Export date
        if (exportFormat != null) {
            exportData(exportFormat, response);
            return;
        }

        // Read filter parameters
        String idClient = request.getParameter("id");
        String nume = request.getParameter("nume");
        String adresa = request.getParameter("adresa");
        String contact = request.getParameter("contact");

        try {
            // Create ClientiFilter for filtering
            ClientiFilter filter = new ClientiFilter();
            ResultSet rs = filter.filterClienti(idClient, nume, adresa, contact);

            // Forward filtered results to JSP
            request.setAttribute("resultSet", rs);
            request.getRequestDispatcher("clienti.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("clienti.jsp?status=error&message=SQL error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String deleteId = request.getParameter("delete");
        String nume = request.getParameter("nume");
        String adresa = request.getParameter("adresa");
        String contact = request.getParameter("contact");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (deleteId != null && !deleteId.isEmpty()) {
                // Deleterea unui client
                String deleteQuery = "DELETE FROM Clienti WHERE ID_CLIENT = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
                    pstmt.setInt(1, Integer.parseInt(deleteId));
                    int rowsDeleted = pstmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        response.sendRedirect("clienti.jsp?status=success&message=Client deleted successfully!");
                    } else {
                        response.sendRedirect("clienti.jsp?status=error&message=No client was found for deletion.");
                    }
                }
            } else if (nume != null && adresa != null && contact != null) {
                if (idParam != null && !idParam.isEmpty()) {
                    // Actualizare client
                    String updateQuery = "UPDATE Clienti SET NUME = ?, ADRESA = ?, CONTACT = ? WHERE ID_CLIENT = ?";
                    try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
                        pstmt.setString(1, nume);
                        pstmt.setString(2, adresa);
                        pstmt.setString(3, contact);
                        pstmt.setInt(4, Integer.parseInt(idParam));
                        pstmt.executeUpdate();
                        response.sendRedirect("clienti.jsp?status=success&message=Client updated successfully!");
                    }
                } else {
                    // Add a new client
                    String insertQuery = "INSERT INTO Clienti (NUME, ADRESA, CONTACT) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                        pstmt.setString(1, nume);
                        pstmt.setString(2, adresa);
                        pstmt.setString(3, contact);
                        pstmt.executeUpdate();
                        response.sendRedirect("clienti.jsp?status=success&message=Client added successfully!");
                    }
                }
            } else {
                response.sendRedirect("clienti.jsp?status=error&message=The submitted data is incomplete!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("clienti.jsp?status=error&message=SQL error: " + e.getMessage());
        }
    }

    private void exportData(String format, HttpServletResponse response) throws IOException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Clienti");
             ResultSet rs = stmt.executeQuery()) {

            if ("csv".equalsIgnoreCase(format)) {
                exportToCSV(rs, response);
            } else if ("pdf".equalsIgnoreCase(format)) {
                exportToPDF(rs, response);
            } else {
                response.sendRedirect("clienti.jsp?status=error&message=Invalid export format!");
            }
        } catch (Exception e) {
            response.sendRedirect("clienti.jsp?status=error&message=Export error: " + e.getMessage());
        }
    }

    private void exportToCSV(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=clienti.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID_CLIENT,NUME,ADRESA,CONTACT");

        while (rs.next()) {
            writer.println(rs.getInt("ID_CLIENT") + "," +
                    rs.getString("NUME") + "," +
                    rs.getString("ADRESA") + "," +
                    rs.getString("CONTACT"));
        }
        writer.flush();
    }

    private void exportToPDF(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=clienti.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("Client List\n\n"));

        PdfPTable table = new PdfPTable(4);
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Address");
        table.addCell("Contact");

        while (rs.next()) {
            table.addCell(String.valueOf(rs.getInt("ID_CLIENT")));
            table.addCell(rs.getString("NUME"));
            table.addCell(rs.getString("ADRESA"));
            table.addCell(rs.getString("CONTACT"));
        }
        document.add(table);
        document.close();
    }
}
