<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="webapp/static/styles.css">

<%
    String role = (String) session.getAttribute("role");
    if (role == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Aplicație Gestionare</title>
   <style>
           body {
               font-family: Arial, sans-serif;
               margin: 0;
               padding: 0;
           }
           nav {
               background-color: #333;
               padding: 10px;
           }
           nav a {
               color: white;
               text-decoration: none;
               margin: 10px;
           }
           h1 {
               text-align: center;
               margin: 20px 0;
               font-size: 2.5em; /* Ajustează dimensiunea fontului dacă e necesar */
           }
           .image-container img {
               display: block;
               width: 100%; /* Asigură lățimea maximă */
               height: auto; /* Păstrează proporțiile imaginii */
           }
       </style>
</head>
<body>
    <nav>
        <a href="angajati.jsp">Angajați</a>
        <a href="clienti.jsp">Clienți</a>
        <a href="comenzi.jsp">Comenzi</a>
        <a href="furnizori.jsp">Furnizori</a>
        <a href="evaluari.jsp">Evaluări</a>
        <a href="materiale.jsp">Materiale</a>
        <a href="transportatori.jsp">Transportatori</a>
        <a href="logout.jsp" style="float: right;">Logout</a>
    </nav>

    <style>
        h1 {
            text-align: center;
            margin: 20px 0;
            font-size: 2.5em; /* Ajustează dimensiunea fontului dacă e necesar */
        }
    </style>

    <h1> Gestionare Depozit de Materiale </h1>
    <%
        if ("admin".equals(role)) {
            out.print("<p>Sunteți autentificat ca administrator.</p>");
        } else if ("user".equals(role)) {
            out.print("<p>Sunteți autentificat ca utilizator.</p>");
        }
    %>
      <!-- Imaginea principală -->
    <div class="image-container">
        <img src="static/warehouse.jpg" alt="Warehouse">
    </div>



</body>
</html>
