package main.java.com.proiectdb.warehouse.service;

import main.java.com.proiectdb.warehouse.dao.TransportatorDAO;
import main.java.com.proiectdb.warehouse.model.Transportator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportatorService {
    private final TransportatorDAO transportatorDAO;

    public TransportatorService() {
        this.transportatorDAO = new TransportatorDAO();
    }

    public List<Transportator> getAllTransportatori() {
        try {
            return transportatorDAO.getAllTransportatori();
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null sau o listă goală
            return null;
        }
    }

    public void createTransportator(Transportator transportator) {
        try {
            transportatorDAO.createTransportator(transportator);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void updateTransportator(Transportator transportator) {
        try {
            transportatorDAO.updateTransportator(transportator);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void deleteTransportator(int idTransportator) {
        try {
            transportatorDAO.deleteTransportator(idTransportator);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public Transportator readTransportator(int idTransportator) {
        try {
            return transportatorDAO.readTransportator(idTransportator);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null
            return null;
        }
    }

    public List<String> getNumeColoane() {
        return transportatorDAO.getNumeColoane();
    }

    public void saveTransportator(Transportator transportator) {
        try {
            if (transportator.getIdTransportator() <= 0) {
                // ID-ul este 0 sau negativ, deci inserăm un nou transportator
                transportatorDAO.createTransportator(transportator);
            } else {
                // ID-ul este pozitiv, deci actualizăm transportatorul existent
                transportatorDAO.updateTransportator(transportator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public List<Transportator> filterTransportatori(String id, String nume, String contact, String pretPeKg) {
        List<Transportator> filteredTransportatori = new ArrayList<>();
        try {
            List<Transportator> allTransportatori = transportatorDAO.getAllTransportatori();

            for (Transportator transportator : allTransportatori) {
                boolean matches = true;

                if (id != null && !id.trim().isEmpty() && !String.valueOf(transportator.getIdTransportator()).equals(id.trim())) {
                    matches = false;
                }
                if (nume != null && !nume.trim().isEmpty() && transportator.getNume() != null &&
                        !transportator.getNume().toLowerCase().contains(nume.trim().toLowerCase())) {
                    matches = false;
                }
                if (contact != null && !contact.trim().isEmpty() && transportator.getContact() != null &&
                        !transportator.getContact().toLowerCase().contains(contact.trim().toLowerCase())) {
                    matches = false;
                }
                if (pretPeKg != null && !pretPeKg.trim().isEmpty() && transportator.getPretPeKg() != 0.0 &&
                        !String.valueOf(transportator.getPretPeKg()).toLowerCase().contains(pretPeKg.trim().toLowerCase())) {
                    matches = false;
                }

                if (matches) {
                    filteredTransportatori.add(transportator);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția, de exemplu, arată un mesaj de eroare
        }
        return filteredTransportatori;
    }


}
