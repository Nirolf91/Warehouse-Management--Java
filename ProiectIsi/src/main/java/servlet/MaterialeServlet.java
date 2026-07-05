package servlet;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DatabaseConnection;

import filter.MaterialeFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.LogService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/materiale")
public class MaterialeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exportFormat = request.getParameter("export");
        if (exportFormat != null) {
            exportData(exportFormat, request, response);
            return;
        }

        // Filter materials with MaterialeFilter
        String idParam = request.getParameter("id");
        String nume = request.getParameter("nume");
        String descriere = request.getParameter("descriere");
        String cantitate = request.getParameter("cantitate");
        String pret = request.getParameter("pret");
        String idFurnizor = request.getParameter("id_furnizor");

        try {
            MaterialeFilter filter = new MaterialeFilter();
            ResultSet rs = filter.filterMateriale(idParam, nume, descriere, cantitate, pret, idFurnizor);

            // Forward data to JSP
            request.setAttribute("resultSet", rs);
            request.getRequestDispatcher("materiale.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("materiale.jsp?status=error&message=SQL error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String utilizator = (String) request.getSession().getAttribute("username");
        String rol = (String) request.getSession().getAttribute("role");

        // Preluare parametri din cererea HTTP
        String idMaterial = request.getParameter("id");
        String deleteId = request.getParameter("delete");
        String nume = request.getParameter("nume");
        String descriere = request.getParameter("descriere");
        String cantitate = request.getParameter("cantitate");
        String pret = request.getParameter("pret");
        String idFurnizor = request.getParameter("idFurnizor");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (deleteId != null && !deleteId.isEmpty()) {
                // Delete material
                String deleteQuery = "DELETE FROM Materiale WHERE ID_MATERIAL = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
                    pstmt.setInt(1, Integer.parseInt(deleteId));
                    int rowsDeleted = pstmt.executeUpdate();

                    LogService.logAction(utilizator, rol, "Delete material", deleteQuery);

                    response.sendRedirect(rowsDeleted > 0 ?
                            "materiale.jsp?status=success&message=Material deleted successfully!" :
                            "materiale.jsp?status=error&message=No material was found for deletion.");
                    return;
                }
            }

            // Validate fields
            if (nume == null || nume.trim().isEmpty() ||
                    descriere == null || descriere.trim().isEmpty() ||
                    cantitate == null || cantitate.trim().isEmpty() ||
                    pret == null || pret.trim().isEmpty() ||
                    idFurnizor == null || idFurnizor.trim().isEmpty()) {
                response.sendRedirect("materiale.jsp?status=error&message=The submitted data is incomplete or invalid!");
                return;
            }


            // INSERT or UPDATE operation
            if (idMaterial != null && !idMaterial.isEmpty()) {
                // Update material
                String updateQuery = "UPDATE Materiale SET NUME = ?, DESCRIERE = ?, CANTITATE_IN_STOC = ?, PRET_UNITAR = ?, ID_FURNIZOR = ? WHERE ID_MATERIAL = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
                    pstmt.setString(1, nume);
                    pstmt.setString(2, descriere);
                    pstmt.setInt(3, Integer.parseInt(cantitate));
                    pstmt.setDouble(4, Double.parseDouble(pret));
                    pstmt.setInt(5, Integer.parseInt(idFurnizor));
                    pstmt.setInt(6, Integer.parseInt(idMaterial));
                    pstmt.executeUpdate();

                    LogService.logAction(utilizator, rol, "Update material", updateQuery);

                    response.sendRedirect("materiale.jsp?status=success&message=Material updated successfully!");
                    return;
                }
            } else {
                // Inserare material nou
                String insertQuery = "INSERT INTO Materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR) "
                        + "VALUES (materiale_seq.NEXTVAL, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                    pstmt.setString(1, nume);
                    pstmt.setString(2, descriere);
                    pstmt.setInt(3, Integer.parseInt(cantitate));
                    pstmt.setDouble(4, Double.parseDouble(pret));
                    pstmt.setInt(5, Integer.parseInt(idFurnizor));
                    pstmt.executeUpdate();
                    response.sendRedirect("materiale.jsp?status=success&message=Material added successfully!");
                    return;
                }

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("materiale.jsp?status=error&message=Invalid numeric format!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("materiale.jsp?status=error&message=SQL error: " + e.getMessage());
        }
    }



    private void exportData(String format, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        String nume = request.getParameter("nume");
        String descriere = request.getParameter("descriere");
        String cantitate = request.getParameter("cantitate");
        String pret = request.getParameter("pret");
        String idFurnizor = request.getParameter("id_furnizor");

        try {
            MaterialeFilter filter = new MaterialeFilter();
            ResultSet rs = filter.filterMateriale(idParam, nume, descriere, cantitate, pret, idFurnizor);

            if ("csv".equalsIgnoreCase(format)) {
                exportToCSV(rs, response);
            } else if ("pdf".equalsIgnoreCase(format)) {
                exportToPDF(rs, response);
            }
        } catch (Exception e) {
            response.sendRedirect("materiale.jsp?status=error&message=Export error: " + e.getMessage());
        }
    }

    private void exportToCSV(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=materiale.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID_MATERIAL,NUME,DESCRIERE,CANTITATE_IN_STOC,PRET_UNITAR,ID_FURNIZOR");

        while (rs.next()) {
            writer.println(rs.getInt("ID_MATERIAL") + "," +
                    rs.getString("NUME") + "," +
                    rs.getString("DESCRIERE") + "," +
                    rs.getInt("CANTITATE_IN_STOC") + "," +
                    rs.getDouble("PRET_UNITAR") + "," +
                    rs.getInt("ID_FURNIZOR"));
        }
        writer.flush();
    }

    private void exportToPDF(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=materiale.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("Material List\n\n"));

        PdfPTable table = new PdfPTable(6);
        table.addCell("ID Material");
        table.addCell("Name");
        table.addCell("Descriere");
        table.addCell("Stock Quantity");
        table.addCell("Unit Price");
        table.addCell("ID Furnizor");

        while (rs.next()) {
            table.addCell(String.valueOf(rs.getInt("ID_MATERIAL")));
            table.addCell(rs.getString("NUME"));
            table.addCell(rs.getString("DESCRIERE"));
            table.addCell(String.valueOf(rs.getInt("CANTITATE_IN_STOC")));
            table.addCell(String.valueOf(rs.getDouble("PRET_UNITAR")));
            table.addCell(String.valueOf(rs.getInt("ID_FURNIZOR")));
        }
        document.add(table);
        document.close();
    }
}
