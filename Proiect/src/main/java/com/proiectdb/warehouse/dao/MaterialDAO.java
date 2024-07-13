package main.java.com.proiectdb.warehouse.dao;
import main.java.com.proiectdb.warehouse.model.Material;
import main.java.com.proiectdb.warehouse.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    private final Connection connection;

    public MaterialDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }


    // Metoda pentru a citi toate materialele
    public List<Material> getAllMaterials() throws SQLException {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT * FROM Materiale";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Material material = new Material();
                material.setIdMaterial(resultSet.getInt("ID_MATERIAL"));
                material.setNume(resultSet.getString("NUME"));
                material.setDescriere(resultSet.getString("DESCRIERE"));
                material.setCantitateInStoc(resultSet.getInt("CANTITATE_IN_STOC"));
                material.setPretUnitar(resultSet.getDouble("PRET_UNITAR"));
                material.setIdFurnizor(resultSet.getInt("ID_FURNIZOR"));

                materials.add(material);
            }
        }
        return materials;
    }

    public void updateMaterial(Material material) throws SQLException {
        String query = "UPDATE Materiale SET NUME = ?, DESCRIERE = ?, CANTITATE_IN_STOC = ?, PRET_UNITAR = ?, ID_FURNIZOR = ? WHERE ID_MATERIAL = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, material.getNume());
            preparedStatement.setString(2, material.getDescriere());
            preparedStatement.setInt(3, material.getCantitateInStoc());
            preparedStatement.setDouble(4, material.getPretUnitar());
            preparedStatement.setInt(5, material.getIdFurnizor());
            preparedStatement.setInt(6, material.getIdMaterial());

            preparedStatement.executeUpdate();
        }
    }

    public void deleteMaterial(int idMaterial) throws SQLException {
        String query = "DELETE FROM Materiale WHERE ID_MATERIAL = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idMaterial);

            preparedStatement.executeUpdate();
        }
    }

    // Alte metode CRUD (update, delete) pot fi adăugate aici
    public void createMaterial(Material material) throws SQLException {
        String query = "INSERT INTO Materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, material.getIdMaterial());
            preparedStatement.setString(2, material.getNume());
            preparedStatement.setString(3, material.getDescriere());
            preparedStatement.setInt(4, material.getCantitateInStoc());
            preparedStatement.setDouble(5, material.getPretUnitar());
            preparedStatement.setInt(6, material.getIdFurnizor());

            preparedStatement.executeUpdate();
        }
    }
    public Material readMaterial(int idMaterial) throws SQLException {
        String query = "SELECT * FROM Materiale WHERE ID_MATERIAL = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idMaterial);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Material material = new Material();
                    material.setIdMaterial(resultSet.getInt("ID_MATERIAL"));
                    material.setNume(resultSet.getString("NUME"));
                    material.setDescriere(resultSet.getString("DESCRIERE"));
                    material.setCantitateInStoc(resultSet.getInt("CANTITATE_IN_STOC"));
                    material.setPretUnitar(resultSet.getDouble("PRET_UNITAR"));
                    material.setIdFurnizor(resultSet.getInt("ID_FURNIZOR"));

                    return material;
                }
            }
        }
        return null; // sau arunca o exceptie daca materialul nu este gasit
    }

    public List<String> getNumeColoane() {
        List<String> coloane = new ArrayList<>();
        String query = "SELECT * FROM Materiale WHERE 1=0"; // Query care nu returnează rânduri

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numarColoane = metaData.getColumnCount();

            for (int i = 1; i <= numarColoane; i++) {
                coloane.add(metaData.getColumnName(i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează sau propagă excepția după caz
        }

        return coloane;
    }

}