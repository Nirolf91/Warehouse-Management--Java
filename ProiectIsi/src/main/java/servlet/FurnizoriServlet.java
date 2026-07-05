package servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DatabaseConnection;
import filter.FurnizoriFilter;

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

@WebServlet("/furnizori")
public class FurnizoriServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exportFormat = request.getParameter("export");

        if (exportFormat != null) {
            exportData(exportFormat, response);
            return;
        }

        // Preia parametrii de filtrare
        String id = request.getParameter("id");
        String nume = request.getParameter("nume");
        String adresa = request.getParameter("adresa");
        String contact = request.getParameter("contact");

        try {
            // Obține datele filtrate
            FurnizoriFilter filter = new FurnizoriFilter();
            ResultSet rs = filter.filterFurnizori(id, nume, adresa, contact);
            request.setAttribute("resultSet", rs);
            request.getRequestDispatcher("furnizori.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("furnizori.jsp?status=error&message=Eroare SQL: " + e.getMessage());
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
                String deleteQuery = "DELETE FROM Furnizori WHERE ID_FURNIZOR = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
                    pstmt.setInt(1, Integer.parseInt(deleteId));
                    int rowsDeleted = pstmt.executeUpdate();
                    response.sendRedirect(rowsDeleted > 0
                            ? "furnizori.jsp?status=success&message=Furnizor șters cu succes!"
                            : "furnizori.jsp?status=error&message=Nu s-a găsit furnizorul pentru ștergere.");
                }
            } else if (nume != null && adresa != null && contact != null) {
                if (idParam != null && !idParam.isEmpty()) {
                    String updateQuery = "UPDATE Furnizori SET NUME = ?, ADRESA = ?, CONTACT = ? WHERE ID_FURNIZOR = ?";
                    try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
                        pstmt.setString(1, nume);
                        pstmt.setString(2, adresa);
                        pstmt.setString(3, contact);
                        pstmt.setInt(4, Integer.parseInt(idParam));
                        pstmt.executeUpdate();
                        response.sendRedirect("furnizori.jsp?status=success&message=Furnizor actualizat cu succes!");
                    }
                } else {
                    String insertQuery = "INSERT INTO Furnizori (NUME, ADRESA, CONTACT) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                        pstmt.setString(1, nume);
                        pstmt.setString(2, adresa);
                        pstmt.setString(3, contact);
                        pstmt.executeUpdate();
                        response.sendRedirect("furnizori.jsp?status=success&message=Furnizor adăugat cu succes!");
                    }
                }
            } else {
                response.sendRedirect("furnizori.jsp?status=error&message=Datele nu sunt complete!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("furnizori.jsp?status=error&message=Eroare SQL: " + e.getMessage());
        }
    }

    private void exportData(String format, HttpServletResponse response) throws IOException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Furnizori");
             ResultSet rs = stmt.executeQuery()) {

            if ("csv".equalsIgnoreCase(format)) {
                exportToCSV(rs, response);
            } else if ("pdf".equalsIgnoreCase(format)) {
                exportToPDF(rs, response);
            }
        } catch (Exception e) {
            response.sendRedirect("furnizori.jsp?status=error&message=Eroare la export: " + e.getMessage());
        }
    }

    private void exportToCSV(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=furnizori.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID_FURNIZOR,NUME,ADRESA,CONTACT");

        while (rs.next()) {
            writer.println(rs.getInt("ID_FURNIZOR") + "," +
                    rs.getString("NUME") + "," +
                    rs.getString("ADRESA") + "," +
                    rs.getString("CONTACT"));
        }
        writer.flush();
    }

    private void exportToPDF(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=furnizori.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("Lista Furnizori\n\n"));

        PdfPTable table = new PdfPTable(4);
        table.addCell("ID");
        table.addCell("Nume");
        table.addCell("Adresă");
        table.addCell("Contact");

        while (rs.next()) {
            table.addCell(String.valueOf(rs.getInt("ID_FURNIZOR")));
            table.addCell(rs.getString("NUME"));
            table.addCell(rs.getString("ADRESA"));
            table.addCell(rs.getString("CONTACT"));
        }
        document.add(table);
        document.close();
    }
}
