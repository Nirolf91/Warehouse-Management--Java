package servlet;

import database.DatabaseConnection;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/importAngajati")
@MultipartConfig
public class ImportAngajatiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();

        // Verificăm extensia fișierului
        if (fileName.endsWith(".csv")) {
            importFromCSV(filePart.getInputStream(), response);
        } else if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            importFromExcel(filePart.getInputStream(), response);
        } else {
            response.sendRedirect("angajati.jsp?status=error&message=Formatul fișierului nu este suportat!");
        }
    }

    private void importFromCSV(InputStream inputStream, HttpServletResponse response) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             Connection connection = DatabaseConnection.getConnection()) {
            String line;
            String insertQuery = "INSERT INTO Angajati (NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length >= 3) { // Presupunem că fișierul CSV are 3 coloane
                        pstmt.setString(1, values[0].trim()); // NUME
                        pstmt.setString(2, values[1].trim()); // FUNCTIE
                        pstmt.setString(3, values[2].trim()); // DATE_DE_CONTACT
                        pstmt.addBatch();
                    }
                }
                pstmt.executeBatch();
            }
            response.sendRedirect("angajati.jsp?status=success&message=Importul din CSV a fost realizat cu succes!");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("angajati.jsp?status=error&message=Eroare la importul din CSV: " + e.getMessage());
        }
    }

    private void importFromExcel(InputStream inputStream, HttpServletResponse response) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(inputStream);
             Connection connection = DatabaseConnection.getConnection()) {

            Sheet sheet = workbook.getSheetAt(0); // Prima foaie din Excel
            String insertQuery = "INSERT INTO Angajati (NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue; // Ignorăm rândul de antet

                    Cell numeCell = row.getCell(0);
                    Cell functieCell = row.getCell(1);
                    Cell contactCell = row.getCell(2);

                    pstmt.setString(1, numeCell.getStringCellValue().trim());
                    pstmt.setString(2, functieCell.getStringCellValue().trim());
                    pstmt.setString(3, contactCell.getStringCellValue().trim());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            response.sendRedirect("angajati.jsp?status=success&message=Importul din Excel a fost realizat cu succes!");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("angajati.jsp?status=error&message=Eroare la importul din Excel: " + e.getMessage());
        }
    }
}
