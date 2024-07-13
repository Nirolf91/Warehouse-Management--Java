package main.java.com.proiectdb.warehouse.service;

import main.java.com.proiectdb.warehouse.dao.MaterialDAO;
import main.java.com.proiectdb.warehouse.model.Material;
import main.java.com.proiectdb.warehouse.model.Transportator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MaterialService {
    private final MaterialDAO materialDAO;

    public MaterialService() {
        this.materialDAO = new MaterialDAO();
    }

    public List<Material> getAllMaterials() {
        try {
            return materialDAO.getAllMaterials();
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null sau o listă goală
            return null;
        }
    }

    public void createMaterial(Material material) {
        try {
            materialDAO.createMaterial(material);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void updateMaterial(Material material) {
        try {
            materialDAO.updateMaterial(material);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void deleteMaterial(int idMaterial) {
        try {
            materialDAO.deleteMaterial(idMaterial);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public Material readMaterial(int idMaterial) {
        try {
            return materialDAO.readMaterial(idMaterial);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null
            return null;
        }
    }

    public List<String> getNumeColoane() {
        return materialDAO.getNumeColoane();
    }
    public void saveMaterial(Material material) {
        try {
            if (material.getIdMaterial() <= 0) {
                // ID-ul este 0 sau negativ, deci inserăm un nou transportator
                materialDAO.createMaterial(material);
            } else {
                // ID-ul este pozitiv, deci actualizăm transportatorul existent
                materialDAO.updateMaterial(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }
    // Metoda din clasa MaterialService
    public List<Material> filterMateriale(String id, String nume, String descriere, String cantitateInStoc, String pretUnitar, String idFurnizor) {
        List<Material> filteredMateriale = new ArrayList<>();
        try {
            List<Material> allMateriale = materialDAO.getAllMaterials();

            for (Material material : allMateriale) {
                boolean matches = true;

                if (id != null && !id.trim().isEmpty() && material.getIdMaterial() != Integer.parseInt(id.trim())) {
                    matches = false;
                }
                if (nume != null && !nume.trim().isEmpty() && !material.getNume().toLowerCase().contains(nume.trim().toLowerCase())) {
                    matches = false;
                }
                if (descriere != null && !descriere.trim().isEmpty() && !material.getDescriere().toLowerCase().contains(descriere.trim().toLowerCase())) {
                    matches = false;
                }
                if (cantitateInStoc != null && !cantitateInStoc.trim().isEmpty() && material.getCantitateInStoc() != Integer.parseInt(cantitateInStoc.trim())) {
                    matches = false;
                }
                if (pretUnitar != null && !pretUnitar.trim().isEmpty() && material.getPretUnitar() != Double.parseDouble(pretUnitar.trim())) {
                    matches = false;
                }
                if (idFurnizor != null && !idFurnizor.trim().isEmpty() && material.getIdFurnizor() != Integer.parseInt(idFurnizor.trim())) {
                    matches = false;
                }

                if (matches) {
                    filteredMateriale.add(material);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția, de exemplu, arată un mesaj de eroare
        }
        return filteredMateriale;
    }

    public Map<String, Integer> getCantitatePeCategorie() {
        List<Material> materiale = getAllMaterials();
        if (materiale == null) return new HashMap<>();

        return materiale.stream()
                .collect(Collectors.groupingBy(Material::getDescriere,
                        Collectors.summingInt(Material::getCantitateInStoc)));
    }
}


