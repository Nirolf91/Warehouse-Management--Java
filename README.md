# Warehouse Management ISI

Warehouse Management ISI is a Java web application for managing a construction-material warehouse. It covers the main operational flows of a small warehouse: employees, clients, suppliers, materials, carriers, orders, reviews, authentication and activity logging.

The application is packaged as a Maven WAR project and runs on Tomcat with Jakarta Servlet/JSP. Data is stored in Oracle Database, and the repository includes a SQL bootstrap script so the project can be imported and run on another machine.

## Features

- username/password authentication with `admin` and `user` roles
- CRUD flows for employees, clients, suppliers, materials, carriers, orders and reviews
- filtering on the main listing pages
- CSV/PDF export for multiple entities
- employee import from CSV/XLSX files
- JSON endpoints for employee, order and review charts
- action logging for important operations

## Technologies

- Java 17
- Maven
- Jakarta Servlet/JSP
- Apache Tomcat 10.1+ or Tomcat 11
- Oracle Database / Oracle SQL Developer
- Oracle JDBC Driver
- iText PDF, Gson, Apache POI

## Project Structure

```text
.
|-- ProiectIsi/                  # Maven project imported by the IDE
|   |-- pom.xml
|   `-- src/main/
|       |-- java/
|       |   |-- database/        # Oracle connection helper
|       |   |-- filter/          # parameterized SQL filters
|       |   |-- grafic/          # JSON chart endpoints
|       |   |-- service/         # helper services, including logging
|       |   `-- servlet/         # CRUD/export/import/login servlets
|       `-- webapp/              # JSP pages and static assets
|-- sql/schema.sql               # Oracle schema and demo data
|-- imagini/                     # project/documentation images
|-- Proiect ISI.docx             # original project documentation
`-- diagrama entitate relatie.pptx
```

## Database Setup

1. Open Oracle SQL Developer.
2. Connect to the Oracle user/schema used by the application. The local development setup uses the `system` user.
3. Open `sql/schema.sql`.
4. Run the full script. It recreates the tables, sequences, triggers and demo data.

The script also creates demo login accounts:

```text
admin / admin123
user  / user123
```

## Oracle Connection Configuration

The application reads the database connection from environment variables. Defaults are:

```text
WAREHOUSE_DB_URL=jdbc:oracle:thin:@localhost:1521:orcl
WAREHOUSE_DB_USER=system
WAREHOUSE_DB_PASSWORD=
```

On Windows PowerShell, set the local Oracle password before running the app:

```powershell
$env:WAREHOUSE_DB_URL="jdbc:oracle:thin:@localhost:1521:orcl"
$env:WAREHOUSE_DB_USER="system"
$env:WAREHOUSE_DB_PASSWORD="<your-oracle-password>"
```

For the author's local setup, the provided SQL Developer password should be set as the value of `WAREHOUSE_DB_PASSWORD`. Real passwords should not be hardcoded or committed to GitHub.

Alternatively, pass the values as JVM properties:

```text
-Dwarehouse.db.url=jdbc:oracle:thin:@localhost:1521:orcl
-Dwarehouse.db.user=system
-Dwarehouse.db.password=<your-oracle-password>
```

## IDE Import

### IntelliJ IDEA

1. Go to `File` -> `Open`.
2. Select the `ProiectIsi` folder or the `ProiectIsi/pom.xml` file.
3. Import it as a Maven project.
4. Set the project SDK to Java 17.
5. Configure Tomcat 10.1+ or Tomcat 11.
6. Add the generated WAR artifact or run the Tomcat configuration against the `ProiectIsi` module.
7. Add the Oracle environment variables in the run configuration.

### Eclipse

1. Go to `File` -> `Import`.
2. Select `Maven` -> `Existing Maven Projects`.
3. For `Root Directory`, select `ProiectIsi`.
4. Finish the import and set Java 17.
5. Add the project to a Jakarta-compatible Tomcat server.

## Local Build

From the `ProiectIsi` folder:

```powershell
mvn clean package
```

The generated WAR is:

```text
ProiectIsi/target/warehouse-management-isi.war
```

Deploy this file to Tomcat.

## Running on Tomcat

1. Start Oracle Database and make sure the listener is available.
2. Run `sql/schema.sql` if the schema is not initialized.
3. Set the Oracle connection environment variables.
4. Run `mvn clean package` inside `ProiectIsi`.
5. Copy `ProiectIsi/target/warehouse-management-isi.war` to Tomcat's `webapps` folder or deploy it from the IDE.
6. Open:

```text
http://localhost:8080/warehouse-management-isi/
```

## Module Overview

- `database.DatabaseConnection` centralizes the Oracle connection and supports environment/JVM configuration.
- `filter` classes build parameterized queries for listing and filtering pages.
- `servlet` classes handle CRUD operations, CSV/PDF export, employee import and authentication.
- `grafic` servlets expose aggregated JSON data for charts.
- `service.LogService` writes important actions to the `Logs` table.
- JSP files in `webapp` provide the user interface.

## Notes

- Demo accounts use simple passwords and are intended only for local presentation.
- The Oracle password is not stored in the repository. Set it locally through `WAREHOUSE_DB_PASSWORD`.
- The local Smart Tomcat file `ProiectIsi.xml` is ignored because it contains absolute paths specific to one machine.
