package servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DatabaseConnection;
import filter.EvaluariFilter;

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

@WebServlet("/evaluari")
public class EvaluariServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exportFormat = request.getParameter("export");

        if (exportFormat != null) {
            exportData(exportFormat, response);
            return;
        }

        // Filtrare Evaluări folosind EvaluariFilter
        String idEvaluare = request.getParameter("idEvaluare");
        String idClient = request.getParameter("idClient");
        String scor = request.getParameter("scor");
        String feedback = request.getParameter("feedback");
        String dataEvaluare = request.getParameter("dataEvaluare");

        try {
            EvaluariFilter filter = new EvaluariFilter();
            ResultSet rs = filter.filterEvaluari(idEvaluare, idClient, scor, feedback, dataEvaluare);

            request.setAttribute("resultSet", rs);
            request.getRequestDispatcher("evaluari.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("evaluari.jsp?status=error&message=Eroare SQL: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String deleteId = request.getParameter("delete");
        String idClient = request.getParameter("id_client");
        String scor = request.getParameter("scor");
        String feedback = request.getParameter("feedback");
        String dataEvaluarii = request.getParameter("data_evaluarii");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (deleteId != null && !deleteId.isEmpty()) {
                // Ștergerea unei evaluări
                String deleteQuery = "DELETE FROM Evaluari WHERE ID_EVALUARE = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
                    pstmt.setInt(1, Integer.parseInt(deleteId));
                    int rowsDeleted = pstmt.executeUpdate();
                    response.sendRedirect(rowsDeleted > 0
                            ? "evaluari.jsp?status=success&message=Evaluare ștearsă cu succes!"
                            : "evaluari.jsp?status=error&message=Nu s-a găsit evaluarea pentru ștergere.");
                }
            } else if (idClient != null && scor != null && feedback != null && dataEvaluarii != null) {
                if (idParam != null && !idParam.isEmpty()) {
                    // Actualizare evaluare
                    String updateQuery = "UPDATE Evaluari SET ID_CLIENT = ?, SCOR = ?, FEEDBACK = ?, DATA_EVALUARII = ? WHERE ID_EVALUARE = ?";
                    try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
                        pstmt.setInt(1, Integer.parseInt(idClient));
                        pstmt.setInt(2, Integer.parseInt(scor));
                        pstmt.setString(3, feedback);
                        pstmt.setDate(4, java.sql.Date.valueOf(dataEvaluarii));
                        pstmt.setInt(5, Integer.parseInt(idParam));
                        pstmt.executeUpdate();
                        response.sendRedirect("evaluari.jsp?status=success&message=Evaluare actualizată cu succes!");
                    }
                } else {
                    // Adăugare evaluare nouă
                    String insertQuery = "INSERT INTO Evaluari (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII) " +
                            "VALUES (evaluare_seq.NEXTVAL, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                        pstmt.setInt(1, Integer.parseInt(idClient));
                        pstmt.setInt(2, Integer.parseInt(scor));
                        pstmt.setString(3, feedback);
                        pstmt.setDate(4, java.sql.Date.valueOf(dataEvaluarii));
                        pstmt.executeUpdate();
                        response.sendRedirect("evaluari.jsp?status=success&message=Evaluare adăugată cu succes!");
                    }

                }
            } else {
                response.sendRedirect("evaluari.jsp?status=error&message=Datele nu sunt complete!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("evaluari.jsp?status=error&message=Eroare SQL: " + e.getMessage());
        }
    }

    private void exportData(String format, HttpServletResponse response) throws IOException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Evaluari");
             ResultSet rs = stmt.executeQuery()) {

            if ("csv".equalsIgnoreCase(format)) {
                exportToCSV(rs, response);
            } else if ("pdf".equalsIgnoreCase(format)) {
                exportToPDF(rs, response);
            }
        } catch (Exception e) {
            response.sendRedirect("evaluari.jsp?status=error&message=Eroare la export: " + e.getMessage());
        }
    }

    private void exportToCSV(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=evaluari.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID_EVALUARE,ID_CLIENT,SCOR,FEEDBACK,DATA_EVALUARII");

        while (rs.next()) {
            writer.println(rs.getInt("ID_EVALUARE") + "," +
                    rs.getInt("ID_CLIENT") + "," +
                    rs.getInt("SCOR") + "," +
                    rs.getString("FEEDBACK") + "," +
                    rs.getDate("DATA_EVALUARII"));
        }
        writer.flush();
    }

    private void exportToPDF(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=evaluari.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("Lista Evaluări\n\n"));

        PdfPTable table = new PdfPTable(5);
        table.addCell("ID Evaluare");
        table.addCell("ID Client");
        table.addCell("Scor");
        table.addCell("Feedback");
        table.addCell("Data Evaluării");

        while (rs.next()) {
            table.addCell(String.valueOf(rs.getInt("ID_EVALUARE")));
            table.addCell(String.valueOf(rs.getInt("ID_CLIENT")));
            table.addCell(String.valueOf(rs.getInt("SCOR")));
            table.addCell(rs.getString("FEEDBACK"));
            table.addCell(String.valueOf(rs.getDate("DATA_EVALUARII")));
        }
        document.add(table);
        document.close();
    }
}
