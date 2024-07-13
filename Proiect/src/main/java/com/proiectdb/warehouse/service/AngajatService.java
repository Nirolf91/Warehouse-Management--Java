package main.java.com.proiectdb.warehouse.service;

import main.java.com.proiectdb.warehouse.dao.AngajatDAO;
import main.java.com.proiectdb.warehouse.model.Angajat;
import main.java.com.proiectdb.warehouse.model.Comanda;
import main.java.com.proiectdb.warehouse.model.Evaluare;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AngajatService {
    private final AngajatDAO angajatDAO;

    public AngajatService() {
        this.angajatDAO = new AngajatDAO();
    }

    public List<Angajat> getAllAngajati() {
        try {
            return angajatDAO.getAllAngajati();
        } catch (SQLException e) {
            e.printStackTrace();
            // Aici poți trata excepția sau returna null sau o listă goală
            return null;
        }
    }

    public void createAngajat(Angajat angajat) {
        try {
            angajatDAO.createAngajat(angajat);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția, de exemplu, arată un mesaj de eroare
        }
    }

    public void updateAngajat(Angajat angajat) {
        try {
            angajatDAO.updateAngajat(angajat);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void deleteAngajat(int idAngajat) {
        try {
            angajatDAO.deleteAngajat(idAngajat);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public Angajat readAngajat(int idAngajat) {
        try {
            return angajatDAO.readAngajat(idAngajat);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția, returnează null sau aruncă o excepție personalizată
            return null;
        }
    }

    public List<String> getNumeColoane() {
        return angajatDAO.getNumeColoane();
    }
    public void saveAngajat(Angajat angajat) {
        try {
            if (angajat.getIdAngajat() <= 0) {
                // ID-ul este 0 sau negativ, deci inserăm un nou transportator
                angajatDAO.createAngajat(angajat);
            } else {
                // ID-ul este pozitiv, deci actualizăm transportatorul existent
                angajatDAO.updateAngajat(angajat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    // În clasa AngajatService

    public List<Angajat> filterAngajati(String id, String nume, String functie, String dateDeContact) {
        List<Angajat> filteredAngajati = new ArrayList<>();
        try {
            List<Angajat> allAngajati = angajatDAO.getAllAngajati();

            for (Angajat angajat : allAngajati) {
                boolean matches = true;

                if (id != null && !id.trim().isEmpty() && !String.valueOf(angajat.getIdAngajat()).equals(id.trim())) {
                    matches = false;
                }
                if (nume != null && !nume.trim().isEmpty() && !angajat.getNume().toLowerCase().contains(nume.trim().toLowerCase())) {
                    matches = false;
                }
                if (functie != null && !functie.trim().isEmpty() && !angajat.getFunctie().toLowerCase().contains(functie.trim().toLowerCase())) {
                    matches = false;
                }
                if (dateDeContact != null && !dateDeContact.trim().isEmpty() && !angajat.getDateDeContact().toLowerCase().contains(dateDeContact.trim().toLowerCase())) {
                    matches = false;
                }

                if (matches) {
                    filteredAngajati.add(angajat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția, de exemplu, arată un mesaj de eroare
        }
        return filteredAngajati;
    }

    public Map<String, Integer> getPondereFunctiiAngajati() {
        List<Angajat> angajati = getAllAngajati();
        if (angajati == null) return new HashMap<>();

        return angajati.stream()
                .collect(Collectors.groupingBy(Angajat::getFunctie, Collectors.reducing(0, e -> 1, Integer::sum)));
    }

}