package main.java.com.proiectdb.warehouse.util;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import main.java.com.proiectdb.warehouse.model.*;


import java.util.Optional;

public class DialogUtil {

    public static Optional<Pair<String, String>> showLoginDialog() {
        // Crearea dialogului
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Logare Administrator");
        dialog.setHeaderText("Introduceți credențialele de administrator");

        // Setarea butoanelor.
        ButtonType loginButtonType = new ButtonType("Logare", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Crearea layout-ului pentru input
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Crearea câmpurilor pentru nume de utilizator și parolă
        TextField username = new TextField();
        username.setPromptText("Admin");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Parola");
        TextField passwordTextField = new TextField();
        passwordTextField.setManaged(false);
        passwordTextField.setVisible(false);

        // Crearea checkbox-ului pentru show/hide password
        CheckBox checkBox = new CheckBox("Arată parola");
        checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                // Schimbă la TextField pentru a arăta parola
                passwordTextField.setText(passwordField.getText());
                passwordTextField.setManaged(true);
                passwordTextField.setVisible(true);
                passwordField.setManaged(false);
                passwordField.setVisible(false);
            } else {
                // Schimbă înapoi la PasswordField pentru a ascunde parola
                passwordField.setText(passwordTextField.getText());
                passwordField.setManaged(true);
                passwordField.setVisible(true);
                passwordTextField.setManaged(false);
                passwordTextField.setVisible(false);
            }
        });

        grid.add(new Label("Utilizator:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Parolă:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(passwordTextField, 1, 1); // Aceeași poziție ca și passwordField
        grid.add(checkBox, 2, 1);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), passwordField.isManaged() ? passwordField.getText() : passwordTextField.getText());
            }
            return null;
        });

        return dialog.showAndWait();
    }
    public static void createNewTransportatorDialog(TableView<Transportator> tableView) {
        // Crearea dialogului
        Dialog<Transportator> dialog = new Dialog<>();
        dialog.setTitle("Adăugare Transportator Nou");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField numeField = new TextField();
        numeField.setPromptText("Nume");
        TextField contactField = new TextField();
        contactField.setPromptText("Contact");
        TextField pretPeKgField = new TextField();
        pretPeKgField.setPromptText("Preț pe Kg");

        grid.add(new Label("Nume:"), 0, 0);
        grid.add(numeField, 1, 0);
        grid.add(new Label("Contact:"), 0, 1);
        grid.add(contactField, 1, 1);
        grid.add(new Label("Preț pe Kg:"), 0, 2);
        grid.add(pretPeKgField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                Transportator newTransportator = new Transportator();
                newTransportator.setNume(numeField.getText());
                newTransportator.setContact(contactField.getText());
                try {
                    newTransportator.setPretPeKg(Double.parseDouble(pretPeKgField.getText()));
                } catch (NumberFormatException e) {
                    // Gestionați eroarea, de exemplu, prin afișarea unui mesaj
                }
                return newTransportator;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newTransportator -> {
            // Adăugați logica pentru a salva noul transportator în baza de date sau lista
            tableView.getItems().add(newTransportator);
        });
    }

    public static void createNewMaterialDialog(TableView<Material> tableView) {
        // Crearea dialogului
        Dialog<Material> dialog = new Dialog<>();
        dialog.setTitle("Adăugare Material Nou");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField numeField = new TextField();
        numeField.setPromptText("Nume");
        TextField descriereField = new TextField();
        descriereField.setPromptText("Descriere");
        TextField cantitateInStocField = new TextField();
        cantitateInStocField.setPromptText("Cantitate in Stoc");
        TextField pretUnitarField = new TextField();
        pretUnitarField.setPromptText("Preț unitar");
        TextField idFurnizorField = new TextField();
        idFurnizorField.setPromptText("Id Furnizor");

        grid.add(new Label("Nume:"), 0, 0);
        grid.add(numeField, 1, 0);
        grid.add(new Label("Descriere:"), 0, 1);
        grid.add(descriereField, 1, 1);
        grid.add(new Label("Cantitate in Stoc:"), 0, 2);
        grid.add(cantitateInStocField, 1, 2);
        grid.add(new Label("Pret unitar:"), 0, 3);
        grid.add(pretUnitarField, 1, 3);
        grid.add(new Label("Id Furnizor:"), 0, 4);
        grid.add(idFurnizorField, 1, 4);

        dialog.getDialogPane().setContent(grid);


        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                Material newMaterial = new Material();
                newMaterial.setNume(numeField.getText());
                newMaterial.setDescriere(descriereField.getText());
                newMaterial.setCantitateInStoc(Integer.parseInt(cantitateInStocField.getText()));
                newMaterial.setPretUnitar(Double.parseDouble(pretUnitarField.getText()));
                newMaterial.setIdFurnizor(Integer.parseInt(idFurnizorField.getText()));

                try {
                    newMaterial.setPretUnitar(Double.parseDouble(pretUnitarField.getText()));
                } catch (NumberFormatException e) {
                    // Gestionați eroarea, de exemplu, prin afișarea unui mesaj
                }
                return newMaterial;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newMaterial -> {
            // Adăugați logica pentru a salva noul transportator în baza de date sau lista
            tableView.getItems().add(newMaterial);
        });
    }

    public static void createNewFurnizorDialog(TableView<Furnizor> tableView) {
        // Crearea dialogului
        Dialog<Furnizor> dialog = new Dialog<>();
        dialog.setTitle("Adăugare Furnizor Nou");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

    /*
    ID_FURNIZOR
    NUME
    ADRESA
    CONTACT
  */

        TextField numeField = new TextField();
        numeField.setPromptText("Nume");
        TextField adresaField = new TextField();
        adresaField.setPromptText("Adresa");
        TextField contactFieldField = new TextField();
        contactFieldField.setPromptText("Contact");

        grid.add(new Label("Nume:"), 0, 0);
        grid.add(numeField, 1, 0);
        grid.add(new Label("Adresa:"), 0, 1);
        grid.add(adresaField, 1, 1);
        grid.add(new Label("Contact:"), 0, 2);
        grid.add(contactFieldField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                Furnizor newFurnizor = new Furnizor();
                newFurnizor.setNume(numeField.getText());
                newFurnizor.setAdresa(adresaField.getText());
                try {
                    newFurnizor.setContact(String.valueOf(Double.parseDouble(contactFieldField.getText())));
                } catch (NumberFormatException e) {
                    // Gestionați eroarea, de exemplu, prin afișarea unui mesaj
                }
                return newFurnizor;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newFurnizor -> {
            // Adăugați logica pentru a salva noul transportator în baza de date sau lista
            tableView.getItems().add(newFurnizor);
        });
    }


    public static void createNewEvaluareDialog(TableView<Evaluare> tableView) {
        // Crearea dialogului
        Dialog<Evaluare> dialog = new Dialog<>();
        dialog.setTitle("Evaluare  noua");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        TextField idClienField = new TextField();
        idClienField.setPromptText("Id Client");
        TextField scorField = new TextField();
        scorField.setPromptText("Scor");
        TextField feedbackField = new TextField();
        feedbackField.setPromptText("Feedback");
        TextField dataEvaluareField = new TextField();
        dataEvaluareField.setPromptText("Data Evaluare");


        grid.add(new Label("Id Client:"), 0, 0);
        grid.add(idClienField, 1, 0);
        grid.add(new Label("Scor:"), 0, 1);
        grid.add(scorField, 1, 1);
        grid.add(new Label("Feedback:"), 0, 2);
        grid.add(feedbackField, 1, 2);
        grid.add(new Label("Data Evaluarii:"), 0, 3);
        grid.add(dataEvaluareField, 1, 3);


        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                Evaluare newEvaluare = new Evaluare();
                newEvaluare.setIdClient(Integer.parseInt(idClienField.getText()));
                newEvaluare.setScor(Integer.parseInt(scorField.getText()));
                newEvaluare.setFeedback(feedbackField.getText());

                try {
                    newEvaluare.setDataEvaluarii(DataUtil.convertStringToDate(dataEvaluareField.getText()));
                } catch (NumberFormatException e) {
                    // Gestionați eroarea, de exemplu, prin afișarea unui mesaj
                }
                return newEvaluare;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newEvaluare -> {
            // Adăugați logica pentru a salva noul transportator în baza de date sau lista
            tableView.getItems().add(newEvaluare);
        });
    }


    public static void createNewComandaDialog(TableView<Comanda> tableView) {
        // Crearea dialogului
        Dialog<Comanda> dialog = new Dialog<>();
        dialog.setTitle("Adăugare Comanda Noua");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


       // *pune codul aici*
        TextField dataComandaField = new TextField();
        dataComandaField.setPromptText("Data Comanda");
        TextField idClientField = new TextField();
        idClientField.setPromptText("Id Client");
        TextField idFurnizorField = new TextField();
        idFurnizorField.setPromptText("Id Furnizor");
        TextField idAngajatField = new TextField();
        idAngajatField.setPromptText("Id Angajat");
        TextField idMaterialField = new TextField();
        idMaterialField.setPromptText("Id Material");
        TextField totalComandField = new TextField();
        totalComandField.setPromptText("Total Comanda");
        TextField statutComandaField = new TextField();
        statutComandaField.setPromptText("Statut Comanda");
        TextField cantitateField = new TextField();
        cantitateField.setPromptText("Cantitate");
        TextField pretTotalField = new TextField();
        pretTotalField.setPromptText("Pret total");


        ToggleButton btnAprovizionare = new ToggleButton("Aprovizionare");
        ToggleButton btnExpediere = new ToggleButton("Expediere");
        ToggleGroup groupTipComanda = new ToggleGroup();

        btnAprovizionare.setToggleGroup(groupTipComanda);
        btnExpediere.setToggleGroup(groupTipComanda);
        btnAprovizionare.setUserData("Aprovizionare");
        btnExpediere.setUserData("Expediere");


// Adăugarea listener-ilor pentru butoanele de tip toggle
        btnAprovizionare.setOnAction(e -> {
            if (btnAprovizionare.isSelected()) {
                idClientField.setText("0"); // Setează ID client la 0 pentru aprovizionare
                if(idFurnizorField.getText().equals("0"))
                    idFurnizorField.setText("");
                // Logica pentru aprovizionare
            }
        });

        btnExpediere.setOnAction(e -> {
            if (btnExpediere.isSelected()) {
                idFurnizorField.setText("0"); // Reset ID client pentru expediere
                if(idClientField.getText().equals("0"))
                    idClientField.setText("");
                // Logica pentru expediere
            }
        });

// Adăugarea butoanelor de tip toggle la grid
        HBox toggleButtonsBox = new HBox(10, btnAprovizionare, btnExpediere);
        grid.add(toggleButtonsBox, 0, 0); // Adaugă HBox la grid, în rândul 7

        grid.add(new Label("Data Comenzii:"), 0, 1);
        grid.add(dataComandaField, 1, 1);
        grid.add(new Label("Id Client:"), 0, 2);
        grid.add(idClientField, 1, 2);
        grid.add(new Label("Id Furnizor:"), 0, 3);
        grid.add(idFurnizorField, 1, 3);
        grid.add(new Label("Id Angajat:"), 0, 4);
        grid.add(idAngajatField, 1, 4);
        grid.add(new Label("Id Material:"), 0, 5);
        grid.add(idMaterialField, 1, 5);
        grid.add(new Label("Total Comanda:"), 0, 6);
        grid.add(totalComandField, 1, 6);
        grid.add(new Label("Statut Comanda:"), 0, 7);
        grid.add(statutComandaField, 1, 7);
        grid.add(new Label("Cantitate:"), 0, 8);
        grid.add(cantitateField, 1, 8);
        grid.add(new Label("Pret total:"), 0, 9);
        grid.add(pretTotalField, 1, 9);

        dialog.getDialogPane().setContent(grid);


        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                Comanda newComanda = new Comanda();
                newComanda.setDataComenzii(DataUtil.convertStringToDate(dataComandaField.getText()));
                newComanda.setIdClient(Integer.parseInt(idClientField.getText()));
                newComanda.setIdFurnizor(Integer.parseInt(idFurnizorField.getText()));
                newComanda.setIdAngajat(Integer.parseInt(idAngajatField.getText()));
                newComanda.setIdMaterial(Integer.parseInt(idMaterialField.getText()));
                newComanda.setTotalComanda(Double.parseDouble(totalComandField.getText()));
                newComanda.setStatutComanda(statutComandaField.getText());
                newComanda.setTipComanda((groupTipComanda.getSelectedToggle().getUserData().toString()));
                newComanda.setCantitate(Integer.parseInt(cantitateField.getText()));

                try {
                    newComanda.setPretTotal(Double.parseDouble((pretTotalField.getText())));
                    newComanda.setDataComenzii(DataUtil.convertStringToDate(dataComandaField.getText()));
                } catch (NumberFormatException e) {
                    // Gestionați eroarea, de exemplu, prin afișarea unui mesaj
                }
                return newComanda;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newComanda -> {
            // Adăugați logica pentru a salva noul transportator în baza de date sau lista
            tableView.getItems().add(newComanda);
        });
    }

    public static void createNewClientDialog(TableView<Client> tableView) {
        // Crearea dialogului
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Client  nou");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField numeField = new TextField();
        numeField.setPromptText("Nume");
        TextField adresaField = new TextField();
        adresaField.setPromptText("Adresa");
        TextField contactField = new TextField();
        contactField.setPromptText("Contact");

        grid.add(new Label("Nume:"), 0, 0);
        grid.add(numeField, 1, 0);
        grid.add(new Label("Adresa:"), 0, 1);
        grid.add(adresaField, 1, 1);
        grid.add(new Label("Contact:"), 0, 2);
        grid.add(contactField, 1, 2);


        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                Client newClient = new Client();
                newClient.setNume((numeField.getText()));
                newClient.setAdresa((adresaField.getText()));

                try {
                    newClient.setContact(((contactField.getText())));
                } catch (NumberFormatException e) {
                    // Gestionați eroarea, de exemplu, prin afișarea unui mesaj
                }
                return newClient;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newClient -> {
            // Adăugați logica pentru a salva noul transportator în baza de date sau lista
            tableView.getItems().add(newClient);
        });
    }

    public static void createNewAngajatDialog(TableView<Angajat> tableView) {
        // Crearea dialogului
        Dialog<Angajat> dialog = new Dialog<>();
        dialog.setTitle("Angajat nou");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField numeField = new TextField();
        numeField.setPromptText("Nume");
        TextField functieField = new TextField();
        functieField.setPromptText("Functie");
        TextField dateContactField = new TextField();
        dateContactField.setPromptText("Date de Contact");

        grid.add(new Label("Nume:"), 0, 0);
        grid.add(numeField, 1, 0);
        grid.add(new Label("Functie:"), 0, 1);
        grid.add(functieField, 1, 1);
        grid.add(new Label("Date de Contact:"), 0, 2);
        grid.add(dateContactField, 1, 2);


        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                Angajat newAngajat = new Angajat();
                newAngajat.setNume(((numeField.getText())));
                newAngajat.setFunctie(((functieField.getText())));

  /*
      try {
                    newFurnizor.setContact(String.valueOf(Double.parseDouble(contactFieldField.getText())));
                } catch (NumberFormatException e) {
                    // Gestionați eroarea, de exemplu, prin afișarea unui mesaj
                }
                return newFurnizor;
 */
                try {
                    newAngajat.setDateDeContact(((dateContactField.getText())));
                } catch (NumberFormatException e) {
                    // Gestionați eroarea, de exemplu, prin afișarea unui mesaj
                }
                return newAngajat;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newAngajat -> {
            // Adăugați logica pentru a salva noul transportator în baza de date sau lista
            tableView.getItems().add(newAngajat);
        });
    }

    public static void editEvaluareDialog(Evaluare evaluare, TableView<Evaluare> tableView) {
        Dialog<Evaluare> dialog = new Dialog<>();
        dialog.setTitle("Editare Evaluare");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Crearea formularului în dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Crearea câmpurilor cu valorile precompletate din obiectul evaluare
        TextField idClientField = new TextField(String.valueOf(evaluare.getIdClient()));
        TextField scorField = new TextField(String.valueOf(evaluare.getScor()));
        TextField feedbackField = new TextField(evaluare.getFeedback());
        TextField dataEvaluariiField = new TextField(evaluare.getDataEvaluarii().toString());

        grid.add(new Label("ID Client:"), 0, 0);
        grid.add(idClientField, 1, 0);
        grid.add(new Label("Scor:"), 0, 1);
        grid.add(scorField, 1, 1);
        grid.add(new Label("Feedback:"), 0, 2);
        grid.add(feedbackField, 1, 2);
        grid.add(new Label("Data Evaluării:"), 0, 3);
        grid.add(dataEvaluariiField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Citirea valorilor modificate și actualizarea obiectului Evaluare
                evaluare.setIdClient(Integer.parseInt(idClientField.getText()));
                evaluare.setScor(Integer.parseInt(scorField.getText()));
                evaluare.setFeedback(feedbackField.getText());
                evaluare.setDataEvaluarii(DataUtil.convertStringToDate(dataEvaluariiField.getText()));

                return evaluare;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newEvaluare -> {
            // Actualizați rândul în TableView
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().set(selectedIndex, newEvaluare);
            // Opțional: actualizați datele în baza de date...
        });
    }

    public static void editComandaDialog(Comanda comanda, TableView<Comanda> tableView) {
        Dialog<Comanda> dialog = new Dialog<>();
        dialog.setTitle("Editare Comanda");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Crearea formularului în dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Crearea câmpurilor cu valorile precompletate din obiectul comanda
        TextField idComandaField = new TextField(String.valueOf(comanda.getIdComanda()));
        TextField dataComenziiField = new TextField(String.valueOf(comanda.getDataComenzii()));
        TextField idClientField = new TextField(String.valueOf(comanda.getIdClient()));
        TextField idFurnizorField = new TextField(String.valueOf(comanda.getIdFurnizor()));
        TextField idAngajatField = new TextField(String.valueOf(comanda.getIdAngajat()));
        TextField idMaterialField = new TextField(String.valueOf(comanda.getIdMaterial()));
        TextField totalComandField = new TextField(String.valueOf(comanda.getTotalComanda()));
        TextField statutComandaField = new TextField(String.valueOf(comanda.getStatutComanda()));
        TextField tipComandaField = new TextField(String.valueOf(comanda.getTipComanda()));
        TextField cantitateField = new TextField(String.valueOf(comanda.getCantitate()));
        TextField pretTotalField = new TextField(String.valueOf(comanda.getPretTotal()));

        grid.add(new Label("Id Comanda:"), 0, 0);
        grid.add(idComandaField, 1, 0);
        grid.add(new Label("Data Comenzii:"), 0, 1);
        grid.add(dataComenziiField, 1, 1);
        grid.add(new Label("Id Client:"), 0, 2);
        grid.add(idClientField, 1, 2);
        grid.add(new Label("Id Furnizor:"), 0, 3);
        grid.add(idFurnizorField, 1, 3);
        grid.add(new Label("Id Angajat:"), 0, 4);
        grid.add(idAngajatField, 1, 4);
        grid.add(new Label("Id Material:"), 0, 5);
        grid.add(idMaterialField, 1, 5);
        grid.add(new Label("Total Comanda:"), 0, 6);
        grid.add(totalComandField, 1, 6);
        grid.add(new Label("Statut Comanda:"), 0, 7);
        grid.add(statutComandaField, 1, 7);
        grid.add(new Label("Cantitate:"), 0, 8);
        grid.add(cantitateField, 1, 8);
        grid.add(new Label("Pret total:"), 0, 9);
        grid.add(pretTotalField, 1, 9);


        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Citirea valorilor modificate și actualizarea obiectului Comanda
                comanda.setIdComanda(Integer.parseInt(idComandaField.getText()));
                comanda.setDataComenzii(DataUtil.convertStringToDate(dataComenziiField.getText()));
                comanda.setIdClient(Integer.parseInt(idClientField.getText()));
                comanda.setIdFurnizor(Integer.parseInt(idFurnizorField.getText()));
                comanda.setIdAngajat(Integer.parseInt(idAngajatField.getText()));
                comanda.setIdMaterial(Integer.parseInt(idMaterialField.getText()));
                comanda.setTotalComanda(Double.parseDouble(totalComandField.getText()));
                comanda.setStatutComanda(statutComandaField.getText());
                comanda.setTipComanda(tipComandaField.getText());
                comanda.setCantitate(Integer.parseInt(cantitateField.getText()));
                comanda.setPretTotal(Double.parseDouble(pretTotalField.getText()));

                return comanda;
            }
            return null;
        });


        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newComanda -> {
            // Actualizați rândul în TableView
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().set(selectedIndex, newComanda);
            // Opțional: actualizați datele în baza de date...
        });
    }

    public static void editAngajatDialog(Angajat angajat, TableView<Angajat> tableView) {
        Dialog<Angajat> dialog = new Dialog<>();
        dialog.setTitle("Editare Angajat");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Crearea formularului în dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        // Crearea câmpurilor cu valorile precompletate din obiectul evaluare
        TextField idAngajatField = new TextField(String.valueOf(angajat.getIdAngajat()));
        TextField numeField = new TextField(String.valueOf(angajat.getNume()));
        TextField functieField = new TextField(angajat.getFunctie());
        TextField dateDeContactField = new TextField(angajat.getDateDeContact());

        grid.add(new Label("ID Angajat:"), 0, 0);
        grid.add(idAngajatField, 1, 0);
        grid.add(new Label("Nume:"), 0, 1);
        grid.add(numeField, 1, 1);
        grid.add(new Label("Functie:"), 0, 2);
        grid.add(functieField, 1, 2);
        grid.add(new Label("Data de Contact:"), 0, 3);
        grid.add(dateDeContactField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Citirea valorilor modificate și actualizarea obiectului Evaluare
                angajat.setIdAngajat(Integer.parseInt(idAngajatField.getText()));
                angajat.setNume(numeField.getText());
                angajat.setFunctie(functieField.getText());
                angajat.setDateDeContact(dateDeContactField.getText());

                return angajat;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newAngajat -> {
            // Actualizați rândul în TableView
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().set(selectedIndex, newAngajat);
            // Opțional: actualizați datele în baza de date...
        });
    }

    public static void editClientDialog(Client client, TableView<Client> tableView) {
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Editare Client");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Crearea formularului în dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Crearea câmpurilor cu valorile precompletate din obiectul evaluare
        TextField idClientField = new TextField(String.valueOf(client.getIdClient()));
        TextField numeField = new TextField(client.getNume());
        TextField adresaField = new TextField(client.getAdresa());
        TextField ContactField = new TextField(client.getContact());

        grid.add(new Label("ID Client:"), 0, 0);
        grid.add(idClientField, 1, 0);
        grid.add(new Label("Nume:"), 0, 1);
        grid.add(numeField, 1, 1);
        grid.add(new Label("Adresa:"), 0, 2);
        grid.add(adresaField, 1, 2);
        grid.add(new Label("Contact:"), 0, 3);
        grid.add(ContactField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Citirea valorilor modificate și actualizarea obiectului Evaluare
                client.setIdClient(Integer.parseInt(idClientField.getText()));
                client.setNume(numeField.getText());
                client.setAdresa(adresaField.getText());
                client.setContact(ContactField.getText());

                return client;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newAngajat -> {
            // Actualizați rândul în TableView
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().set(selectedIndex, newAngajat);
            // Opțional: actualizați datele în baza de date...
        });
    }


    public static void editFurnizorDialog(Furnizor furnizor, TableView<Furnizor> tableView) {
        Dialog<Furnizor> dialog = new Dialog<>();
        dialog.setTitle("Editare Furnizor");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Crearea formularului în dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Crearea câmpurilor cu valorile precompletate din obiectul evaluare
        TextField idFurnizorField = new TextField(String.valueOf(furnizor.getIdFurnizor()));
        TextField numeField = new TextField(furnizor.getNume());
        TextField adresaField = new TextField(furnizor.getAdresa());
        TextField ContactField = new TextField(furnizor.getContact());

        grid.add(new Label("ID Furnizor:"), 0, 0);
        grid.add(idFurnizorField, 1, 0);
        grid.add(new Label("Nume:"), 0, 1);
        grid.add(numeField, 1, 1);
        grid.add(new Label("Adresa:"), 0, 2);
        grid.add(adresaField, 1, 2);
        grid.add(new Label("Contact:"), 0, 3);
        grid.add(ContactField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Citirea valorilor modificate și actualizarea obiectului Evaluare
                furnizor.setIdFurnizor(Integer.parseInt(idFurnizorField.getText()));
                furnizor.setNume(numeField.getText());
                furnizor.setAdresa(adresaField.getText());
                furnizor.setContact(ContactField.getText());

                return furnizor;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newFurnizor -> {
            // Actualizați rândul în TableView
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().set(selectedIndex, newFurnizor);
            // Opțional: actualizați datele în baza de date...
        });
    }


    public static void editMaterialDialog(Material material, TableView<Material> tableView) {
        Dialog<Material> dialog = new Dialog<>();
        dialog.setTitle("Editare Material");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Crearea formularului în dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Crearea câmpurilor cu valorile precompletate din obiectul evaluare
        TextField idMaterialField = new TextField(String.valueOf(material.getIdMaterial()));
        TextField numeField = new TextField(material.getNume());
        TextField descriereField = new TextField(material.getDescriere());
        TextField cantitateInStocField = new TextField(String.valueOf(material.getCantitateInStoc()));
        TextField pretUnitarField = new TextField(String.valueOf(material.getPretUnitar()));
        TextField idFurnizorField = new TextField(String.valueOf(material.getIdFurnizor()));

        /*
         comanda.setIdMaterial(Integer.parseInt(idMaterialField.getText()));
                comanda.setTotalComanda(Double.parseDouble(totalComandField.getText()));
         */

        grid.add(new Label("ID Material:"), 0, 0);
        grid.add(idMaterialField, 1, 0);
        grid.add(new Label("Nume:"), 0, 1);
        grid.add(numeField, 1, 1);
        grid.add(new Label("Descriere:"), 0, 2);
        grid.add(descriereField, 1, 2);
        grid.add(new Label("Cantitate in Stoc:"), 0, 3);
        grid.add(cantitateInStocField, 1, 3);
        grid.add(new Label("Pret Unitar:"), 0, 4);
        grid.add(pretUnitarField, 1, 4);
        grid.add(new Label("Id Furnzior:"), 0, 5);
        grid.add(idFurnizorField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Citirea valorilor modificate și actualizarea obiectului Evaluare
                material.setIdMaterial(Integer.parseInt(idMaterialField.getText()));
                material.setNume(numeField.getText());
                material.setDescriere(descriereField.getText());
                material.setCantitateInStoc(Integer.parseInt(cantitateInStocField.getText()));
                material.setPretUnitar(Double.parseDouble(pretUnitarField.getText()));
                material.setIdFurnizor(Integer.parseInt(idFurnizorField.getText()));


                return material;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newMaterial -> {
            // Actualizați rândul în TableView
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().set(selectedIndex, newMaterial);
            // Opțional: actualizați datele în baza de date...
        });
    }
    public static void editTransportatorDialog(Transportator transportator, TableView<Transportator> tableView) {
        Dialog<Transportator> dialog = new Dialog<>();
        dialog.setTitle("Editare Transportator");

        // Setarea tipurilor de butoane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Crearea formularului în dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        /*
ID_TRANSPORTATOR
NUME
CONTACT
PRET_PE_KG
         */
        // Crearea câmpurilor cu valorile precompletate din obiectul evaluare
        TextField idTransportatorField = new TextField(String.valueOf(transportator.getIdTransportator()));
        TextField numeField = new TextField(transportator.getNume());
        TextField contactField = new TextField(transportator.getContact());
        TextField pretPeKgField = new TextField(String.valueOf(transportator.getPretPeKg()));


        grid.add(new Label("ID Transporator:"), 0, 0);
        grid.add(idTransportatorField, 1, 0);
        grid.add(new Label("Nume:"), 0, 1);
        grid.add(numeField, 1, 1);
        grid.add(new Label("Contact:"), 0, 2);
        grid.add(contactField, 1, 2);
        grid.add(new Label("Pret pe Kg:"), 0, 3);
        grid.add(pretPeKgField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Convertirea rezultatului când butonul OK este apăsat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Citirea valorilor modificate și actualizarea obiectului Evaluare
                transportator.setIdTransportator(Integer.parseInt(idTransportatorField.getText()));
                transportator.setNume(numeField.getText());
                transportator.setContact(contactField.getText());
                transportator.setPretPeKg(Double.parseDouble(pretPeKgField.getText()));



                return transportator;
            }
            return null;
        });

        // Afișarea dialogului și procesarea rezultatului
        dialog.showAndWait().ifPresent(newTransportator -> {
            // Actualizați rândul în TableView
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().set(selectedIndex, newTransportator);
            // Opțional: actualizați datele în baza de date...
        });
    }


//Pentru restul claselor la fel

}

