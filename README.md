# Warehouse Management ISI

Aplicatie web Java pentru gestionarea unui depozit de materiale. Proiectul acopera fluxuri simple de administrare pentru angajati, clienti, furnizori, materiale, transportatori, comenzi, evaluari si jurnalizarea unor actiuni importante.

Aplicatia este construita ca proiect Maven de tip WAR si ruleaza pe Tomcat cu Jakarta Servlet/JSP. Datele sunt persistate intr-o baza Oracle, iar scriptul de initializare este inclus in repository.

## Functionalitati

- autentificare pe baza de utilizatori si roluri (`admin`, `user`)
- CRUD pentru angajati, clienti, furnizori, materiale, transportatori, comenzi si evaluari
- filtrare in paginile principale
- export CSV/PDF pentru mai multe entitati
- import angajati din fisiere CSV/XLSX
- grafice JSON pentru angajati, comenzi si evaluari
- jurnalizare actiuni pentru operatii importante

## Tehnologii

- Java 17
- Maven
- Jakarta Servlet/JSP
- Apache Tomcat 10.1+ sau Tomcat 11
- Oracle Database / Oracle SQL Developer
- Oracle JDBC Driver
- iText PDF, Gson, Apache POI

## Structura proiectului

```text
.
├── ProiectIsi/                  # proiectul Maven importabil in IDE
│   ├── pom.xml
│   └── src/main/
│       ├── java/
│       │   ├── database/        # conexiunea la Oracle
│       │   ├── filter/          # filtrari SQL parametrizate
│       │   ├── grafic/          # endpoint-uri JSON pentru grafice
│       │   ├── service/         # servicii auxiliare, inclusiv logging
│       │   └── servlet/         # servlet-uri CRUD/export/import/login
│       └── webapp/              # pagini JSP si resurse statice
├── sql/schema.sql               # schema Oracle + date demo
├── imagini/                     # imagini folosite in documentatie/proiect
├── Proiect ISI.docx             # documentatia proiectului
└── diagrama entitate relatie.pptx
```

## Configurare baza de date

1. Deschide Oracle SQL Developer.
2. Conecteaza-te la utilizatorul Oracle pe care vrei sa ruleze aplicatia. In varianta locala folosita la dezvoltare, utilizatorul este `system`.
3. Deschide fisierul `sql/schema.sql`.
4. Ruleaza tot scriptul. Scriptul recreeaza tabelele, secventele, trigger-ele si insereaza date demo.

Scriptul creeaza inclusiv utilizatori pentru login in aplicatie:

```text
admin / admin123
user  / user123
```

## Configurare conexiune Oracle

Aplicatia citeste conexiunea la baza de date din variabile de mediu. Valorile implicite sunt:

```text
WAREHOUSE_DB_URL=jdbc:oracle:thin:@localhost:1521:orcl
WAREHOUSE_DB_USER=system
WAREHOUSE_DB_PASSWORD=
```

Pe Windows PowerShell, seteaza parola Oracle local inainte de rulare:

```powershell
$env:WAREHOUSE_DB_URL="jdbc:oracle:thin:@localhost:1521:orcl"
$env:WAREHOUSE_DB_USER="system"
$env:WAREHOUSE_DB_PASSWORD="<parola-ta-oracle>"
```

Pentru configuratia locala a autorului, parola indicata se seteaza in `WAREHOUSE_DB_PASSWORD`. Nu este recomandat ca parolele reale sa fie salvate direct in cod sau commit-uite pe GitHub.

Alternativ, poti pasa valorile ca proprietati JVM:

```text
-Dwarehouse.db.url=jdbc:oracle:thin:@localhost:1521:orcl
-Dwarehouse.db.user=system
-Dwarehouse.db.password=<parola-ta-oracle>
```

## Import in IDE

### IntelliJ IDEA

1. `File` -> `Open`.
2. Selecteaza folderul `ProiectIsi` sau direct fisierul `ProiectIsi/pom.xml`.
3. Alege import ca proiect Maven.
4. Configureaza SDK-ul pe Java 17.
5. Configureaza un server Tomcat 10.1+ sau Tomcat 11.
6. Adauga artifact-ul WAR generat de Maven sau ruleaza configuratia Tomcat pe modulul `ProiectIsi`.
7. Seteaza variabilele de mediu din sectiunea de conexiune Oracle in configuratia de run.

### Eclipse

1. `File` -> `Import`.
2. `Maven` -> `Existing Maven Projects`.
3. La `Root Directory`, selecteaza folderul `ProiectIsi`.
4. Finalizeaza importul si seteaza Java 17.
5. Adauga proiectul pe un server Tomcat compatibil Jakarta.

## Build local

Din folderul `ProiectIsi`:

```powershell
mvn clean package
```

WAR-ul rezultat va fi:

```text
ProiectIsi/target/warehouse-management-isi.war
```

Acest fisier poate fi deployat in Tomcat.

## Rulare in Tomcat

1. Porneste Oracle Database si verifica listener-ul.
2. Ruleaza `sql/schema.sql` daca baza nu este initializata.
3. Seteaza variabilele de mediu pentru conexiune.
4. Ruleaza `mvn clean package` in `ProiectIsi`.
5. Copiaza `ProiectIsi/target/warehouse-management-isi.war` in folderul `webapps` al Tomcat sau deployeaza-l din IDE.
6. Acceseaza aplicatia la:

```text
http://localhost:8080/warehouse-management-isi/
```

## Ce face fiecare modul

- `database.DatabaseConnection` centralizeaza conexiunea la Oracle si permite configurare prin environment/JVM properties.
- clasele din `filter` construiesc interogari parametrizate pentru paginile de listare si filtrare.
- servlet-urile din `servlet` gestioneaza operatiile CRUD, exportul CSV/PDF, importul de angajati si autentificarea.
- servlet-urile din `grafic` expun date agregate in JSON pentru graficele din interfata.
- `service.LogService` scrie actiuni in tabela `Logs`.
- paginile JSP din `webapp` reprezinta interfata pentru utilizator.

## Observatii

- Proiectul foloseste parole simple pentru conturile demo; acestea sunt potrivite doar pentru prezentare locala.
- Parola Oracle nu este salvata in repository. Seteaz-o local prin `WAREHOUSE_DB_PASSWORD`.
- Fisierul local Smart Tomcat `ProiectIsi.xml` este ignorat, deoarece contine cai absolute specifice calculatorului pe care a fost creat.
