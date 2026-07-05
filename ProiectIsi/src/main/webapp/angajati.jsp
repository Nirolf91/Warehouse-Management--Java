<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="database.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestionare Angajați</title>
   <style>
       /* Stil general pentru butoane */
       input[type="submit"], button, select {
           margin-top: 10px;
           padding: 10px 20px;
           font-size: 14px;
           font-weight: bold;
           color: white;
           background-color: #4CAF50;
           border: none;
           border-radius: 5px;
           cursor: pointer;
           transition: background-color 0.3s ease, transform 0.2s ease;
           box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
       }

       input[type="submit"]:hover, button:hover {
           background-color: #45a049;
           transform: scale(1.05);
       }

       button.delete {
           background-color: #d9534f;
       }

       button.delete:hover {
           background-color: #c9302c;
       }

       /* Stilizare pentru tabel */
       table {
           width: 100%;
           border-collapse: collapse;
           margin-top: 20px;
           background-color: #f9f9f9;
           border-radius: 8px;
           overflow: hidden;
           box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
       }

       th {
           background-color: #4CAF50;
           color: white;
           font-weight: bold;
           padding: 12px 8px;
       }

       td {
           padding: 10px 8px;
           text-align: center;
           color: #333;
       }

       tr:nth-child(even) {
           background-color: #f2f2f2;
       }

       tr:hover {
           background-color: #e1f5e1;
       }

       th:hover {
           cursor: pointer;
           background-color: #45a049;
       }

       tr.selected {
           background-color: #d1ecf1;
           color: #333; /* Asigură lizibilitate */

       }
   </style>

    <script>
        let selectedRow = null;
        let sortOrder = {};

        function selectRow(row) {
            if (selectedRow) selectedRow.classList.remove('selected');
            selectedRow = row;
            row.classList.add('selected');

            document.getElementById("id").value = row.cells[0].innerText;
            document.getElementById("nume").value = row.cells[1].innerText;
            document.getElementById("functie").value = row.cells[2].innerText;
            document.getElementById("contact").value = row.cells[3].innerText;
            document.getElementById("deleteId").value = row.cells[0].innerText;
        }

        function sortTable(columnIndex) {
            const table = document.querySelector("table tbody");
            const rows = Array.from(table.rows);

            if (!sortOrder[columnIndex]) sortOrder[columnIndex] = 'asc';

            rows.sort((rowA, rowB) => {
                const cellA = rowA.cells[columnIndex].innerText.toLowerCase();
                const cellB = rowB.cells[columnIndex].innerText.toLowerCase();

                return sortOrder[columnIndex] === 'asc'
                    ? cellA.localeCompare(cellB, undefined, {numeric: true})
                    : cellB.localeCompare(cellA, undefined, {numeric: true});
            });

            sortOrder[columnIndex] = sortOrder[columnIndex] === 'asc' ? 'desc' : 'asc';

            table.innerHTML = "";
            rows.forEach(row => table.appendChild(row));
        }

        function confirmDelete() {
            if (confirm("Sigur doriți să ștergeți acest angajat?")) {
                document.getElementById("deleteForm").submit();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Gestionare Angajați</h2>

        <!-- Formular pentru Adăugare/Actualizare -->
        <form action="angajati" method="post">
            <label for="id">ID:</label>
            <input type="number" name="id" id="id" placeholder="ID (opțional)"><br>
            <label for="nume">Nume:</label>
            <input type="text" name="nume" id="nume" required><br>
            <label for="functie">Funcție:</label>
            <input type="text" name="functie" id="functie" required><br>
            <label for="contact">Date de Contact:</label>
            <input type="text" name="contact" id="contact" required><br>
            <input type="submit" value="Adaugă / Actualizează">
        </form>

        <!-- Formular pentru Ștergere -->
        <form action="angajati" method="post" id="deleteForm">
            <input type="hidden" name="delete" id="deleteId">
            <button type="button" onclick="confirmDelete()">Șterge</button>
        </form>

        <!-- Formular pentru Export -->
        <form action="angajati" method="post">
            <label for="exportFormat">Exportă datele în format:</label>
            <select name="export" id="exportFormat">
                <option value="csv">CSV</option>
                <option value="pdf">PDF</option>
            </select>
            <button type="submit">Export</button>
        </form>

  <!-- Import -->
    <form action="importAngajati" method="post" enctype="multipart/form-data" style="display: inline;">
        <label for="file">Importă datele:</label>
        <input type="file" name="file" id="file" accept=".csv, .xlsx, .xls" required>
        <button type="submit">Importă</button>
    </form>

        <!-- Formular pentru Filtrare -->
        <form action="angajati.jsp" method="get">
            <label for="idFilter">ID:</label>
            <input type="text" name="id" id="idFilter">
            <label for="numeFilter">Nume:</label>
            <input type="text" name="nume" id="numeFilter">
            <label for="functieFilter">Funcție:</label>
            <input type="text" name="functie" id="functieFilter">
            <label for="contactFilter">Date de Contact:</label>
            <input type="text" name="contact" id="contactFilter">
            <button type="submit">Filtrează</button>
        </form>

        <!-- Tabel pentru afișarea angajaților -->
        <h2>Lista Angajați</h2>
        <table>
            <thead>
                <tr>
                    <th onclick="sortTable(0)">ID</th>
                    <th onclick="sortTable(1)">Nume</th>
                    <th onclick="sortTable(2)">Funcție</th>
                    <th onclick="sortTable(3)">Date de Contact</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String id = request.getParameter("id");
                    String nume = request.getParameter("nume");
                    String functie = request.getParameter("functie");
                    String contact = request.getParameter("contact");

                    StringBuilder query = new StringBuilder("SELECT * FROM Angajati WHERE 1=1");
                    if (id != null && !id.isEmpty()) query.append(" AND ID_ANGAJAT = ?");
                    if (nume != null && !nume.isEmpty()) query.append(" AND NUME LIKE ?");
                    if (functie != null && !functie.isEmpty()) query.append(" AND FUNCTIE LIKE ?");
                    if (contact != null && !contact.isEmpty()) query.append(" AND DATE_DE_CONTACT LIKE ?");

                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(query.toString())) {
                        int index = 1;
                        if (id != null && !id.isEmpty()) stmt.setInt(index++, Integer.parseInt(id));
                        if (nume != null && !nume.isEmpty()) stmt.setString(index++, "%" + nume + "%");
                        if (functie != null && !functie.isEmpty()) stmt.setString(index++, "%" + functie + "%");
                        if (contact != null && !contact.isEmpty()) stmt.setString(index, "%" + contact + "%");

                        try (ResultSet rs = stmt.executeQuery()) {
                            while (rs.next()) {
                %>
                <tr onclick="selectRow(this)">
                    <td><%= rs.getInt("ID_ANGAJAT") %></td>
                    <td><%= rs.getString("NUME") %></td>
                    <td><%= rs.getString("FUNCTIE") %></td>
                    <td><%= rs.getString("DATE_DE_CONTACT") %></td>
                </tr>
                <%
                            }
                        }
                    } catch (SQLException e) {
                %>
                <tr>
                    <td colspan="4" class="error">Eroare: <%= e.getMessage() %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

  <!-- Include Chart.js și plugin-ul Data Labels -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>

  <!-- Grafic pentru distribuția funcțiilor angajaților -->
  <h2>Distribuția Funcțiilor Angajaților</h2>
  <div style="width: 60%; margin: auto;">
      <canvas id="graficAngajati"></canvas>
  </div>

  <script>
      // Funcție pentru a încărca datele și a desena graficul
      function incarcaGraficAngajati() {
          fetch('graficAngajati') // Apelează servlet-ul pentru datele graficului
              .then(response => response.json())
              .then(data => {
                  // Transformă datele în format pentru Chart.js
                  const functii = data.map(item => item.functie);
                  const numere = data.map(item => item.numar);

                  // Creează graficul
                  const ctx = document.getElementById('graficAngajati').getContext('2d');
                  new Chart(ctx, {
                      type: 'pie',
                      data: {
                          labels: functii,
                          datasets: [{
                              label: 'Distribuția Funcțiilor',
                              data: numere,
                              backgroundColor: [
                                  'rgba(255, 99, 132, 0.6)',
                                  'rgba(54, 162, 235, 0.6)',
                                  'rgba(255, 206, 86, 0.6)',
                                  'rgba(75, 192, 192, 0.6)',
                                  'rgba(153, 102, 255, 0.6)',
                                  'rgba(255, 159, 64, 0.6)'
                              ]
                          }]
                      },
                      options: {
                          responsive: true,
                          plugins: {
                              legend: {
                                  position: 'bottom',
                              },
                              title: {
                                  display: true,
                                  text: 'Distribuția Funcțiilor Angajaților'
                              },
                              datalabels: {
                                  formatter: (value, ctx) => {
                                      let sum = ctx.dataset.data.reduce((a, b) => a + b, 0);
                                      let percentage = (value * 100 / sum).toFixed(2) + "%";
                                      return percentage;
                                  },
                                  color: '#fff',
                                  font: {
                                      weight: 'bold',
                                      size: 12
                                  }
                              }
                          }
                      },
                      plugins: [ChartDataLabels]
                  });
              })
              .catch(error => console.error('Eroare la încărcarea datelor pentru grafic:', error));
      }

      // Încarcă graficul când pagina este complet încărcată
      window.onload = incarcaGraficAngajati;
  </script>


    </div>
</body>
</html>
