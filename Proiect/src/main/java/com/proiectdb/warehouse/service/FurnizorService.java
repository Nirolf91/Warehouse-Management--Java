package main.java.com.proiectdb.warehouse.service;

import main.java.com.proiectdb.warehouse.dao.FurnizorDAO;
import main.java.com.proiectdb.warehouse.model.Furnizor;
import main.java.com.proiectdb.warehouse.model.Material;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FurnizorService {
    private final FurnizorDAO furnizorDAO;

    public FurnizorService() {
        this.furnizorDAO = new FurnizorDAO();
    }

    public List<Furnizor> getAllFurnizori() {
        try {
            return furnizorDAO.getAllFurnizori();
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null sau o listă goală
            return null;
        }
    }

    public void createFurnizor(Furnizor furnizor) {
        try {
            furnizorDAO.createFurnizor(furnizor);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void updateFurnizor(Furnizor furnizor) {
        try {
            furnizorDAO.updateFurnizor(furnizor);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void deleteFurnizor(int idFurnizor) {
        try {
            furnizorDAO.deleteFurnizor(idFurnizor);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public Furnizor readFurnizor(int idFurnizor) {
        try {
            return furnizorDAO.readFurnizor(idFurnizor);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null
            return null;
        }
    }

    public List<String> getNumeColoane() {
        return furnizorDAO.getNumeColoane();
    }

    public void saveFurnizor(Furnizor furnizor) {
        try {
            if (furnizor.getIdFurnizor() <= 0) {
                // ID-ul este 0 sau negativ, deci inserăm un nou transportator
                furnizorDAO.createFurnizor(furnizor);
            } else {
                // ID-ul este pozitiv, deci actualizăm transportatorul existent
                furnizorDAO.updateFurnizor(furnizor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }
    public List<Furnizor> filterFurnizori(String id, String nume, String adresa, String contact) {
        List<Furnizor> filteredFurnizori = new ArrayList<>();
        try {
            List<Furnizor> allFurnizori = furnizorDAO.getAllFurnizori();

            for (Furnizor furnizor : allFurnizori) {
                boolean matches = true;

                if (id != null && !id.trim().isEmpty() && furnizor.getIdFurnizor() != Integer.parseInt(id.trim())) {
                    matches = false;
                }
                if (nume != null && !nume.trim().isEmpty() && !furnizor.getNume().toLowerCase().contains(nume.trim().toLowerCase())) {
                    matches = false;
                }
                if (adresa != null && !adresa.trim().isEmpty() && !furnizor.getAdresa().toLowerCase().contains(adresa.trim().toLowerCase())) {
                    matches = false;
                }
                if (contact != null && !contact.trim().isEmpty() && !furnizor.getContact().toLowerCase().contains(contact.trim().toLowerCase())) {
                    matches = false;
                }

                if (matches) {
                    filteredFurnizori.add(furnizor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția, de exemplu, arată un mesaj de eroare
        }
        return filteredFurnizori;
    }

}
