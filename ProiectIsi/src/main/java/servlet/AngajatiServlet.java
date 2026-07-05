package servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DatabaseConnection;

import filter.AngajatiFilter;
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

@WebServlet("/angajati")
public class AngajatiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exportFormat = request.getParameter("export"); // Verificăm dacă exportăm datele
        if (exportFormat != null) {
            exportData(exportFormat, request, response);
            return;
        }

        // Filtrare angajați cu AngajatiFilter
        String idParam = request.getParameter("id");
        String nume = request.getParameter("nume");
        String functie = request.getParameter("functie");
        String contact = request.getParameter("contact");

        try {
            AngajatiFilter filter = new AngajatiFilter();
            ResultSet rs = filter.filterAngajati(idParam, nume, functie, contact);

            // Pasăm datele către JSP
            request.setAttribute("resultSet", rs);
            request.getRequestDispatcher("angajati.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("angajati.jsp?status=error&message=Eroare SQL: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve user and role from session
        String utilizator = (String) request.getSession().getAttribute("username");
        String rol = (String) request.getSession().getAttribute("role");

        String exportFormat = request.getParameter("export");
        if (exportFormat != null) {
            exportData(exportFormat, request, response);
            return;
        }

        String idParam = request.getParameter("id");
        String deleteId = request.getParameter("delete");
        String nume = request.getParameter("nume");
        String functie = request.getParameter("functie");
        String contact = request.getParameter("contact");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (deleteId != null && !deleteId.isEmpty()) {
                // Delete action
                String deleteQuery = "DELETE FROM Angajati WHERE ID_ANGAJAT = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
                    pstmt.setInt(1, Integer.parseInt(deleteId));
                    int rowsDeleted = pstmt.executeUpdate();

                    // Log the delete action
                    LogService.logAction(utilizator, rol, "Ștergere angajat", deleteQuery);

                    response.sendRedirect(rowsDeleted > 0 ?
                            "angajati.jsp?status=success&message=Angajat șters cu succes!" :
                            "angajati.jsp?status=error&message=Nu s-a găsit angajatul pentru ștergere.");
                }
            } else if (nume != null && functie != null && contact != null) {
                if (idParam != null && !idParam.isEmpty()) {
                    // Update action
                    String updateQuery = "UPDATE Angajati SET NUME = ?, FUNCTIE = ?, DATE_DE_CONTACT = ? WHERE ID_ANGAJAT = ?";
                    try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
                        pstmt.setString(1, nume);
                        pstmt.setString(2, functie);
                        pstmt.setString(3, contact);
                        pstmt.setInt(4, Integer.parseInt(idParam));
                        pstmt.executeUpdate();

                        // Log the update action
                        LogService.logAction(utilizator, rol, "Actualizare angajat", updateQuery);

                        response.sendRedirect("angajati.jsp?status=success&message=Angajat actualizat cu succes!");
                    }
                } else {
                    // Insert action
                    String insertQuery = "INSERT INTO Angajati (NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                        pstmt.setString(1, nume);
                        pstmt.setString(2, functie);
                        pstmt.setString(3, contact);
                        pstmt.executeUpdate();

                        // Log the insert action
                        LogService.logAction(utilizator, rol, "Adăugare angajat", insertQuery);

                        response.sendRedirect("angajati.jsp?status=success&message=Angajat adăugat cu succes!");
                    }
                }
            } else {
                response.sendRedirect("angajati.jsp?status=error&message=Datele nu sunt complete!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("angajati.jsp?status=error&message=Eroare SQL: " + e.getMessage());
        }
    }


    private void exportData(String format, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        String nume = request.getParameter("nume");
        String functie = request.getParameter("functie");
        String contact = request.getParameter("contact");

        try {
            AngajatiFilter filter = new AngajatiFilter();
            ResultSet rs = filter.filterAngajati(idParam, nume, functie, contact);

            if ("csv".equalsIgnoreCase(format)) {
                exportToCSV(rs, response);
            } else if ("pdf".equalsIgnoreCase(format)) {
                exportToPDF(rs, response);
            }
        } catch (Exception e) {
            response.sendRedirect("angajati.jsp?status=error&message=Eroare la export: " + e.getMessage());
        }
    }

    private void exportToCSV(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=angajati.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID_ANGAJAT,NUME,FUNCTIE,DATE_DE_CONTACT");

        while (rs.next()) {
            writer.println(rs.getInt("ID_ANGAJAT") + "," +
                    rs.getString("NUME") + "," +
                    rs.getString("FUNCTIE") + "," +
                    rs.getString("DATE_DE_CONTACT"));
        }
        writer.flush();
    }

    private void exportToPDF(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=angajati.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("Lista Angajați\n\n"));

        PdfPTable table = new PdfPTable(4);
        table.addCell("ID");
        table.addCell("Nume");
        table.addCell("Funcție");
        table.addCell("Date de Contact");

        while (rs.next()) {
            table.addCell(String.valueOf(rs.getInt("ID_ANGAJAT")));
            table.addCell(rs.getString("NUME"));
            table.addCell(rs.getString("FUNCTIE"));
            table.addCell(rs.getString("DATE_DE_CONTACT"));
        }
        document.add(table);
        document.close();
    }
}
