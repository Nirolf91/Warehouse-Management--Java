package grafic;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.DatabaseConnection;
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

@WebServlet("/graficAngajati")
public class AngajatiGraficServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT FUNCTIE, COUNT(*) AS NUMAR_ANGAJATI FROM ANGAJATI GROUP BY FUNCTIE");
             ResultSet rs = stmt.executeQuery()) {

            JsonArray jsonArray = new JsonArray();

            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("functie", rs.getString("FUNCTIE"));
                jsonObject.addProperty("numar", rs.getInt("NUMAR_ANGAJATI"));
                jsonArray.add(jsonObject);
            }

            PrintWriter out = response.getWriter();
            out.print(jsonArray);
            out.flush();

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

