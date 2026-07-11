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
    <title>Management Application</title>
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
               font-size: 2.5em; /* Adjusts the font size if needed */
           }
           .image-container img {
               display: block;
               width: 100%; /* Ensures maximum width */
               height: auto; /* Preserves image proportions */
           }
       </style>
</head>
<body>
    <nav>
        <a href="angajati.jsp">Employees</a>
        <a href="clienti.jsp">Clients</a>
        <a href="comenzi.jsp">Orders</a>
        <a href="furnizori.jsp">Suppliers</a>
        <a href="evaluari.jsp">Reviews</a>
        <a href="materiale.jsp">Materials</a>
        <a href="transportatori.jsp">Carriers</a>
        <a href="logout.jsp" style="float: right;">Logout</a>
    </nav>

    <style>
        h1 {
            text-align: center;
            margin: 20px 0;
            font-size: 2.5em; /* Adjusts the font size if needed */
        }
    </style>

    <h1> Material Warehouse Management </h1>
    <%
        if ("admin".equals(role)) {
            out.print("<p>You are logged in as an administrator.</p>");
        } else if ("user".equals(role)) {
            out.print("<p>You are logged in as a standard user.</p>");
        }
    %>
      <!-- Main image -->
    <div class="image-container">
        <img src="static/warehouse.jpg" alt="Warehouse">
    </div>



</body>
</html>
