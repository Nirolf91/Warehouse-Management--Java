package main.java.com.proiectdb.warehouse.service;

import main.java.com.proiectdb.warehouse.dao.ClientDAO;
import main.java.com.proiectdb.warehouse.model.Client;
import main.java.com.proiectdb.warehouse.model.Evaluare;

import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final ClientDAO clientDAO;

    public ClientService() {
        this.clientDAO = new ClientDAO();
    }

    public List<Client> getAllClienti() {
        try {
            return clientDAO.getAllClienti();
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null sau o listă goală
            return null;
        }
    }

    public void createClient(Client client) {
        try {
            clientDAO.createClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void updateClient(Client client) {
        try {
            clientDAO.updateClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void deleteClient(int idClient) {
        try {
            clientDAO.deleteClient(idClient);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public Client readClient(int idClient) {
        try {
            return clientDAO.readClient(idClient);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null
            return null;
        }
    }

    public List<String> getNumeColoane() {
        return clientDAO.getNumeColoane();
    }
    public void saveClient(Client client) {
        try {
            if (client.getIdClient() <= 0) {
                // ID-ul este 0 sau negativ, deci inserăm un nou transportator
                clientDAO.createClient(client);
            } else {
                // ID-ul este pozitiv, deci actualizăm transportatorul existent
                clientDAO.updateClient(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

        public List<Client> filterClienti (String id, String nume, String adresa, String contact){
            List<Client> filteredClienti = new ArrayList<>();
            try {
                List<Client> allClienti = clientDAO.getAllClienti();

                for (Client client : allClienti) {
                    boolean matches = true;

                    if (id != null && !id.trim().isEmpty() && !String.valueOf(client.getIdClient()).equals(id.trim())) {
                        matches = false;
                    }
                    if (nume != null && !nume.trim().isEmpty() && !client.getNume().toLowerCase().contains(nume.trim().toLowerCase())) {
                        matches = false;
                    }
                    if (adresa != null && !adresa.trim().isEmpty() && !client.getAdresa().toLowerCase().contains(adresa.trim().toLowerCase())) {
                        matches = false;
                    }
                    if (contact != null && !contact.trim().isEmpty() && !client.getContact().toLowerCase().contains(contact.trim().toLowerCase())) {
                        matches = false;
                    }

                    if (matches) {
                        filteredClienti.add(client);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Tratează excepția, de exemplu, arată un mesaj de eroare
            }
            return filteredClienti;
        }

    }

