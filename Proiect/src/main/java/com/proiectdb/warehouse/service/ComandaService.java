package main.java.com.proiectdb.warehouse.service;
import main.java.com.proiectdb.warehouse.dao.ComandaDAO;
import main.java.com.proiectdb.warehouse.model.Comanda;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComandaService {
    private final ComandaDAO comandaDAO;

    public ComandaService() {
        this.comandaDAO = new ComandaDAO();
    }

    public List<Comanda> getAllComenzi() {
        try {
            return comandaDAO.getAllComenzi();
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null sau o listă goală
            return null;
        }
    }

    public void createComanda(Comanda comanda) {
        try {
            comandaDAO.createComanda(comanda);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void createComandaExpediere(Comanda comanda) {
        comanda.setIdFurnizor(0);
        try {
            comandaDAO.createComanda(comanda);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void createComandaPrimire(Comanda comanda) {
        comanda.setIdClient(0);
        try {
            comandaDAO.createComanda(comanda);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void updateComanda(Comanda comanda) {
        try {
            comandaDAO.updateComanda(comanda);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void deleteComanda(int idComanda) {
        try {
            comandaDAO.deleteComanda(idComanda);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public Comanda readComanda(int idComanda) {
        try {
            return comandaDAO.readComanda(idComanda);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null
            return null;
        }
    }

    public List<String> getNumeColoane() {
        return comandaDAO.getNumeColoane();
    }
    public void saveComanda(Comanda comanda) {
        try {
            if (comanda.getIdComanda() <= 0) {
                // ID-ul este 0 sau negativ, deci inserăm un nou transportator
                comandaDAO.createComanda(comanda);
            } else {
                // ID-ul este pozitiv, deci actualizăm transportatorul existent
                comandaDAO.updateComanda(comanda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }
    public List<Comanda> filterComenzi(String idComanda, String dataComenzii, String idClient, String idFurnizor,
                                       String idAngajat, String idMaterial, String totalComanda, String statutComanda,
                                       String tipComanda, String cantitate, String pretTotal) {
        List<Comanda> filteredComenzi = new ArrayList<>();
        try {
            List<Comanda> allComenzi = comandaDAO.getAllComenzi();

            for (Comanda comanda : allComenzi) {
                boolean matches = true;

                if (idComanda != null && !idComanda.trim().isEmpty() &&
                        !String.valueOf(comanda.getIdComanda()).equals(idComanda.trim())) {
                    matches = false;
                }
                if (dataComenzii != null && !dataComenzii.trim().isEmpty() &&
                        !comanda.getDataComenzii().toString().toLowerCase().contains(dataComenzii.trim().toLowerCase())) {
                    matches = false;
                }
                if (idClient != null && !idClient.trim().isEmpty() &&
                        !String.valueOf(comanda.getIdClient()).equals(idClient.trim())) {
                    matches = false;
                }
                // ... continuați cu celelalte condiții pentru celelalte câmpuri ale comenzii ...

                if (matches) {
                    filteredComenzi.add(comanda);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția, de exemplu, arată un mesaj de eroare
        }
        return filteredComenzi;
    }
    public Map<String, Double> getTotalComenziPeTip() throws SQLException {
        List<Comanda> comenzi = comandaDAO.getAllComenzi();
        if (comenzi == null) return new HashMap<>();

        return comenzi.stream()
                .collect(Collectors.groupingBy(
                        Comanda::getTipComanda,
                        Collectors.summingDouble(Comanda::getTotalComanda)));
    }

}
