package servlet;

import database.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Validate user credentials from database
    private String validateUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT ROL FROM Utilizatori WHERE UTILIZATOR = ? AND PAROLA = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("ROL");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if validation fails
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve credentials
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate credentials
        String role = validateUser(username, password);

        if (role != null) {
            // Set session attributes
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", role);

            // Redirect to home page
            response.sendRedirect("home.jsp");
        } else {
            // If login fails
            request.setAttribute("errorMessage", "Nume utilizator sau parolă incorectă.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

