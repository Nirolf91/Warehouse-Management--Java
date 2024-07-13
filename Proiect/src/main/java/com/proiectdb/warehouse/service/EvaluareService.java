package main.java.com.proiectdb.warehouse.service;

import main.java.com.proiectdb.warehouse.dao.EvaluareDAO;
import main.java.com.proiectdb.warehouse.model.Evaluare;
import main.java.com.proiectdb.warehouse.model.Furnizor;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class EvaluareService {
    private final EvaluareDAO evaluareDAO;

    public EvaluareService() {
        this.evaluareDAO = new EvaluareDAO();
    }

    public List<Evaluare> getAllEvaluari() {
        try {
            return evaluareDAO.getAllEvaluari();
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null sau o listă goală
            return null;
        }
    }

    public void createEvaluare(Evaluare evaluare) {
        try {
            evaluareDAO.createEvaluare(evaluare);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void updateEvaluare(Evaluare evaluare) {
        try {
            evaluareDAO.updateEvaluare(evaluare);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public void deleteEvaluare(int idEvaluare) {
        try {
            evaluareDAO.deleteEvaluare(idEvaluare);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }

    public Evaluare readEvaluare(int idEvaluare) {
        try {
            return evaluareDAO.readEvaluare(idEvaluare);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția sau returnează null
            return null;
        }
    }

    public List<String> getNumeColoane() {
        return evaluareDAO.getNumeColoane();
    }

    public void saveEvaluare(Evaluare evaluare) {
        try {
            if (evaluare.getIdEvaluare() <= 0) {
                // ID-ul este 0 sau negativ, deci inserăm un nou transportator
                evaluareDAO.createEvaluare(evaluare);
            } else {
                // ID-ul este pozitiv, deci actualizăm transportatorul existent
                evaluareDAO.updateEvaluare(evaluare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția
        }
    }
    public List<Evaluare> filterEvaluari(String id, String idClient, String scor, String feedback, String dataEvaluarii) {
        List<Evaluare> filteredEvaluari = new ArrayList<>();
        try {
            List<Evaluare> allEvaluari = evaluareDAO.getAllEvaluari();

            for (Evaluare evaluare : allEvaluari) {
                boolean matches = true;

                // Verifică id
                if (id != null && !id.trim().isEmpty() && evaluare.getIdEvaluare() != Integer.parseInt(id.trim())) {
                    matches = false;
                }

                // Verifică idClient
                if (idClient != null && !idClient.trim().isEmpty() && evaluare.getIdClient() != Integer.parseInt(idClient.trim())) {
                    matches = false;
                }

                // Verifică scor
                if (scor != null && !scor.trim().isEmpty() && evaluare.getScor() != Integer.parseInt(scor.trim())) {
                    matches = false;
                }

                // Verifică feedback
                if (feedback != null && !feedback.trim().isEmpty() &&
                        !evaluare.getFeedback().toLowerCase().contains(feedback.trim().toLowerCase())) {
                    matches = false;
                }

                // Verifică dataEvaluarii
                if (dataEvaluarii != null && !dataEvaluarii.trim().isEmpty() &&
                        !evaluare.getDataEvaluarii().toString().toLowerCase().contains(dataEvaluarii.trim().toLowerCase())) {
                    matches = false;
                }

                if (matches) {
                    filteredEvaluari.add(evaluare);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează excepția, de exemplu, arată un mesaj de eroare
        }
        return filteredEvaluari;
    }
    public Map<String, Double> getScoruriMediiPeData() {
        List<Evaluare> evaluari = getAllEvaluari();
        if (evaluari == null) return new HashMap<>();

        return evaluari.stream()
                .collect(Collectors.groupingBy(
                        evaluare -> formatDate(evaluare.getDataEvaluarii()),
                        Collectors.averagingInt(Evaluare::getScor)
                ));
    }
    private String formatDate(Date date) {
        // Creează un obiect SimpleDateFormat care definește formatul dorit
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Formatează data și returnează reprezentarea ca string
        return dateFormat.format(date);
    }
}
