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

@WebServlet("/graficComenzi")
public class ComenziGraficServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT TIP_COMANDA, COUNT(*) AS NUMAR_COMENZI FROM Comenzi GROUP BY TIP_COMANDA"
             );
             ResultSet rs = stmt.executeQuery()) {

            JsonArray jsonArray = new JsonArray();

            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("tip_comanda", rs.getString("TIP_COMANDA"));
                jsonObject.addProperty("numar_comenzi", rs.getInt("NUMAR_COMENZI"));
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
