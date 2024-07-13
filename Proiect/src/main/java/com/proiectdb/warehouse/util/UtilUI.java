package main.java.com.proiectdb.warehouse.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.com.proiectdb.warehouse.factory.ServiceFactory;
import main.java.com.proiectdb.warehouse.model.*;
import main.java.com.proiectdb.warehouse.service.*;

import java.io.File;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class UtilUI {
    public static final List<String> SELECTED_TABLE_LIST = Stream.of("Angajati", "Clienti", "Comenzi", "Evaluari", "Furnizori", "Materiale", "Transportatori").toList();

    private static final AngajatService angajatService = ServiceFactory.getAngajatService();
    private static final ClientService clientService = ServiceFactory.getClientService();
    private static final FurnizorService furnizorService = ServiceFactory.getFurnizorService();
    private static final EvaluareService evaluareService = ServiceFactory.getEvaluareService();
    private static final TransportatorService transportatorService = ServiceFactory.getTransportatorService();
    private static final MaterialService materialService = ServiceFactory.getMaterialService();
    private static final ComandaService comandaService = ServiceFactory.getComandaService();

    public static TableView createTableViewForSelection(String selection) {
        TableView tableView = new TableView();
        List<String> coloane = null;
        ObservableList<?> data = null;

        switch (selection) {
            case "Angajati":
                tableView = createAngajatTableView();
                break;
            case "Clienti":
                tableView = createClientTableView();
                break;
            case "Comenzi":
                tableView = createComandaTableView();
                break;
            case "Evaluari":
                tableView = createEvaluareTableView();
                break;
            case "Furnizori":
                tableView = createFurnizorTableView();
                break;
            case "Materiale":
                tableView = createMaterialTableView();
                break;
            case "Transportatori":
                tableView = createTransportatorTableView();
                break;
        }


        // Aici poți adăuga logica pentru a încărca datele în tableView
        // de exemplu: tableView.setItems(getAllAngajati()); pentru cazul Angajat

        return tableView;
    }

    private static TableView createTransportatorTableView() {
        TableView<Transportator> tableView = new TableView<>();
        ObservableList<Transportator> data = FXCollections.observableArrayList(transportatorService.getAllTransportatori());
        tableView.setItems(data);

        TableColumn<Transportator, Number> idCol = new TableColumn<>("ID_TRANSPORTATOR");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idTransportator"));

        TableColumn<Transportator, String> numeCol = new TableColumn<>("NUME");
        numeCol.setCellValueFactory(new PropertyValueFactory<>("nume"));

        TableColumn<Transportator, String> contactCol = new TableColumn<>("CONTACT");
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        TableColumn<Transportator, Number> pretCol = new TableColumn<>("PRET_PE_KG");
        pretCol.setCellValueFactory(new PropertyValueFactory<>("pretPeKg"));

        tableView.getColumns().addAll(idCol, numeCol, contactCol, pretCol);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        return tableView;
    }

    private static TableView<Angajat> createAngajatTableView() {
        TableView<Angajat> tableView = new TableView<>();
        ObservableList<Angajat> data = FXCollections.observableArrayList(angajatService.getAllAngajati());
        tableView.setItems(data);

        TableColumn<Angajat, Number> idCol = new TableColumn<>("ID_ANGAJAT");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idAngajat"));

        TableColumn<Angajat, String> numeCol = new TableColumn<>("NUME");
        numeCol.setCellValueFactory(new PropertyValueFactory<>("nume"));

        TableColumn<Angajat, String> functieCol = new TableColumn<>("FUNCTIE");
        functieCol.setCellValueFactory(new PropertyValueFactory<>("functie"));

        TableColumn<Angajat, String> dateContactCol = new TableColumn<>("DATE_DE_CONTACT");
        dateContactCol.setCellValueFactory(new PropertyValueFactory<>("dateDeContact"));

        // Adaugă coloanele la TableView
        tableView.getColumns().addAll(idCol, numeCol, functieCol, dateContactCol);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // Asigură-te că TableView poate fi editabil dacă este necesar
        tableView.setEditable(true);

        // Adaugă aici celule editabile dacă este necesar
        // ...

        return tableView;
    }

    private static TableView<Client> createClientTableView() {
        TableView<Client> tableView = new TableView<>();
        ObservableList<Client> data = FXCollections.observableArrayList(clientService.getAllClienti());
        tableView.setItems(data);

        TableColumn<Client, Number> idCol = new TableColumn<>("ID_CLIENT");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idClient"));

        TableColumn<Client, String> numeCol = new TableColumn<>("NUME");
        numeCol.setCellValueFactory(new PropertyValueFactory<>("nume"));

        TableColumn<Client, String> adresaCol = new TableColumn<>("ADRESA");
        adresaCol.setCellValueFactory(new PropertyValueFactory<>("adresa"));

        TableColumn<Client, String> contactCol = new TableColumn<>("CONTACT");
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Adaugă coloanele la TableView
        tableView.getColumns().addAll(idCol, numeCol, adresaCol, contactCol);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Asigură-te că TableView poate fi editabil dacă este necesar
        tableView.setEditable(true);

        // Adaugă aici celule editabile dacă este necesar
        // ...

        return tableView;
    }

    private static TableView<Comanda> createComandaTableView() {
        TableView<Comanda> tableView = new TableView<>();
        ObservableList<Comanda> data = FXCollections.observableArrayList(comandaService.getAllComenzi());
        tableView.setItems(data);

        TableColumn<Comanda, Number> idCol = new TableColumn<>("ID_COMANDA");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idComanda"));

        TableColumn<Comanda, Date> dataComenziCol = new TableColumn<>("DATA_COMENZII");
        dataComenziCol.setCellValueFactory(new PropertyValueFactory<>("dataComenzii"));

        TableColumn<Comanda, Number> idClientCol = new TableColumn<>("ID_CLIENT");
        idClientCol.setCellValueFactory(new PropertyValueFactory<>("idClient"));

        TableColumn<Comanda, Number> idFurnizorCol = new TableColumn<>("ID_FURNIZOR");
        idFurnizorCol.setCellValueFactory(new PropertyValueFactory<>("idFurnizor"));

        TableColumn<Comanda, Number> idAngajatCol = new TableColumn<>("ID_ANGAJAT");
        idAngajatCol.setCellValueFactory(new PropertyValueFactory<>("idAngajat"));

        TableColumn<Comanda, Number> idMaterialCol = new TableColumn<>("ID_MATERIAL");
        idMaterialCol.setCellValueFactory(new PropertyValueFactory<>("idMaterial"));

        TableColumn<Comanda, Number> totalComandaCol = new TableColumn<>("TOTAL_COMANDA");
        totalComandaCol.setCellValueFactory(new PropertyValueFactory<>("totalComanda"));

        TableColumn<Comanda, String> statutComandaCol = new TableColumn<>("STATUT_COMANDA");
        statutComandaCol.setCellValueFactory(new PropertyValueFactory<>("statutComanda"));

        TableColumn<Comanda, String> tipComandaCol = new TableColumn<>("TIP_COMANDA");
        tipComandaCol.setCellValueFactory(new PropertyValueFactory<>("tipComanda"));

        TableColumn<Comanda, Number> cantitateCol = new TableColumn<>("CANTITATE");
        cantitateCol.setCellValueFactory(new PropertyValueFactory<>("cantitate"));

        TableColumn<Comanda, Number> pretTotalCol = new TableColumn<>("PRET_TOTAL");
        pretTotalCol.setCellValueFactory(new PropertyValueFactory<>("pretTotal"));

        // Adaugă coloanele la TableView
        tableView.getColumns().addAll(idCol, dataComenziCol, idClientCol, idFurnizorCol, idAngajatCol, idMaterialCol, totalComandaCol, statutComandaCol, tipComandaCol, cantitateCol, pretTotalCol);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Asigură-te că TableView poate fi editabil dacă este necesar
        tableView.setEditable(true);

        // Adaugă aici celule editabile dacă este necesar
        // ...

        return tableView;
    }

    private static TableView<Evaluare> createEvaluareTableView() {
        TableView<Evaluare> tableView = new TableView<>();
        ObservableList<Evaluare> data = FXCollections.observableArrayList(evaluareService.getAllEvaluari());
        tableView.setItems(data);

        TableColumn<Evaluare, Number> idCol = new TableColumn<>("ID_EVALUARE");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idEvaluare"));

        TableColumn<Evaluare, Number> idClientCol = new TableColumn<>("ID_CLIENT");
        idClientCol.setCellValueFactory(new PropertyValueFactory<>("idClient"));

        TableColumn<Evaluare, Number> scorCol = new TableColumn<>("SCOR");
        scorCol.setCellValueFactory(new PropertyValueFactory<>("scor"));

        TableColumn<Evaluare, String> feedbackCol = new TableColumn<>("FEEDBACK");
        feedbackCol.setCellValueFactory(new PropertyValueFactory<>("feedback"));
        feedbackCol.setOnEditCommit(
                event -> {
                    Evaluare evaluare = event.getRowValue();
                    evaluare.setFeedback(event.getNewValue());
                    // Aici puteți adăuga logica de actualizare în baza de date sau lista dvs.
                }
        );
        feedbackCol.setEditable(true);

        TableColumn<Evaluare, Date> dataEvaluariiCol = new TableColumn<>("DATA_EVALUARII");
        dataEvaluariiCol.setCellValueFactory(new PropertyValueFactory<>("dataEvaluarii"));

        // Adaugă coloanele la TableView
        tableView.getColumns().addAll(idCol, idClientCol, scorCol, feedbackCol, dataEvaluariiCol);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // Asigură-te că TableView poate fi editabil dacă este necesar
        tableView.setEditable(true);

        // Adaugă aici celule editabile dacă este necesar
        // ...

        return tableView;
    }

    private static TableView<Furnizor> createFurnizorTableView() {
        TableView<Furnizor> tableView = new TableView<>();
        ObservableList<Furnizor> data = FXCollections.observableArrayList(furnizorService.getAllFurnizori());
        tableView.setItems(data);

        TableColumn<Furnizor, Number> idCol = new TableColumn<>("ID_FURNIZOR");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idFurnizor"));

        TableColumn<Furnizor, String> numeCol = new TableColumn<>("NUME");
        numeCol.setCellValueFactory(new PropertyValueFactory<>("nume"));

        TableColumn<Furnizor, String> adresaCol = new TableColumn<>("ADRESA");
        adresaCol.setCellValueFactory(new PropertyValueFactory<>("adresa"));

        TableColumn<Furnizor, String> contactCol = new TableColumn<>("CONTACT");
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Adaugă coloanele la TableView
        tableView.getColumns().addAll(idCol, numeCol, adresaCol, contactCol);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // Asigură-te că TableView poate fi editabil dacă este necesar
        tableView.setEditable(true);

        // Adaugă aici celule editabile dacă este necesar
        // ...

        return tableView;
    }

    private static TableView<Material> createMaterialTableView() {
        TableView<Material> tableView = new TableView<>();
        ObservableList<Material> data = FXCollections.observableArrayList(materialService.getAllMaterials());
        tableView.setItems(data);

        TableColumn<Material, Number> idCol = new TableColumn<>("ID_MATERIAL");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idMaterial"));

        TableColumn<Material, String> numeCol = new TableColumn<>("NUME");
        numeCol.setCellValueFactory(new PropertyValueFactory<>("nume"));

        TableColumn<Material, String> descriereCol = new TableColumn<>("DESCRIERE");
        descriereCol.setCellValueFactory(new PropertyValueFactory<>("descriere"));

        TableColumn<Material, Number> cantitateInStocCol = new TableColumn<>("CANTITATE_IN_STOC");
        cantitateInStocCol.setCellValueFactory(new PropertyValueFactory<>("cantitateInStoc"));

        TableColumn<Material, Number> pretUnitarCol = new TableColumn<>("PRET_UNITAR");
        pretUnitarCol.setCellValueFactory(new PropertyValueFactory<>("pretUnitar"));

        TableColumn<Material, Number> idFurnizorCol = new TableColumn<>("ID_FURNIZOR");
        idFurnizorCol.setCellValueFactory(new PropertyValueFactory<>("idFurnizor"));

        // Adaugă coloanele la TableView
        tableView.getColumns().addAll(idCol, numeCol, descriereCol, cantitateInStocCol, pretUnitarCol, idFurnizorCol);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Asigură-te că TableView poate fi editabil dacă este necesar
        tableView.setEditable(true);

        // Adaugă aici celule editabile dacă este necesar
        // ...

        return tableView;
    }


    public static void setButtonActions(Button btnEdit, Button btnSave, Button btnDelete, Button btnNew, TableView tableView, String selection) {
        btnEdit.setOnAction(event -> {

            if (selection.equals("Evaluari")) {
                Evaluare selectedEvaluare = (Evaluare) tableView.getSelectionModel().getSelectedItem();
                DialogUtil.editEvaluareDialog(selectedEvaluare, tableView);
            }
            if (selection.equals("Comenzi")) {
                Comanda selectedComanda = (Comanda) tableView.getSelectionModel().getSelectedItem();
                DialogUtil.editComandaDialog(selectedComanda, tableView);
            }

            if (selection.equals("Angajati")) {
                Angajat selectedAngajat = (Angajat) tableView.getSelectionModel().getSelectedItem();
                DialogUtil.editAngajatDialog(selectedAngajat, tableView);
            }

            if (selection.equals("Clienti")) {
                Client selectedClient = (Client) tableView.getSelectionModel().getSelectedItem();
                DialogUtil.editClientDialog(selectedClient, tableView);
            }

            if (selection.equals("Furnizori")) {
                Furnizor selectedFurnizor = (Furnizor) tableView.getSelectionModel().getSelectedItem();
                DialogUtil.editFurnizorDialog(selectedFurnizor, tableView);
            }

            if (selection.equals("Materiale")) {
                Material selectedMaterial = (Material) tableView.getSelectionModel().getSelectedItem();
                DialogUtil.editMaterialDialog(selectedMaterial, tableView);
            }

            if (selection.equals("Transportatori")) {
                Transportator selectedTransportator = (Transportator) tableView.getSelectionModel().getSelectedItem();
                DialogUtil.editTransportatorDialog(selectedTransportator, tableView);
            }
        });

        btnNew.setOnAction(event -> {
            if (selection.equals("Transportatori")) {
                DialogUtil.createNewTransportatorDialog(tableView);
            }
            if (selection.equals("Materiale")) {
                DialogUtil.createNewMaterialDialog(tableView);
            }
            if (selection.equals("Furnizori")) {
                DialogUtil.createNewFurnizorDialog(tableView);
            }
            if (selection.equals("Evaluari")) {
                DialogUtil.createNewEvaluareDialog(tableView);
            }
            if (selection.equals("Comenzi")) {
                DialogUtil.createNewComandaDialog(tableView);
            }

            if (selection.equals("Clienti")) {
                DialogUtil.createNewClientDialog(tableView);
            }

            if (selection.equals("Angajati")) {
                DialogUtil.createNewAngajatDialog(tableView);
            }
            //la fel pt restu
        });

        btnSave.setOnAction(event -> {
            // Logica de salvare
            saveData(tableView, selection);
        });

        btnDelete.setOnAction(event -> {
            ObservableList<?> selectedRows = tableView.getSelectionModel().getSelectedItems();

            if (selectedRows.isEmpty()) {
                // Afișează un mesaj dacă nu sunt rânduri selectate
                showAlert(Alert.AlertType.WARNING, "Warning", "No rows selected", "Please select at least one row to delete.");
                return;
            }

            // Dialog de confirmare
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete selected row(s)?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                deleteSelectedRows(selectedRows, selection);
                refreshTableView(tableView, selection); // Reîncărcarea datelor în TableView
            }
        });
    }

    private static void deleteSelectedRows(ObservableList<?> selectedRows, String selection) {
        if (selection.equals("Transportatori")) {
            for (Object item : new ArrayList<>(selectedRows)) { // Copie pentru evitarea modificărilor concurente
                if (item instanceof Transportator transportator) {
                    transportatorService.deleteTransportator(transportator.getIdTransportator());
                }
            }
        }
        if (selection.equals("Materiale")) {
            for (Object item : new ArrayList<>(selectedRows)) { // Copie pentru evitarea modificărilor concurente
                if (item instanceof Material material) {
                    materialService.deleteMaterial(material.getIdMaterial());
                }
            }
        }
        if (selection.equals("Furnizori")) {
            for (Object item : new ArrayList<>(selectedRows)) { // Copie pentru evitarea modificărilor concurente
                if (item instanceof Furnizor furnizor) {
                    furnizorService.deleteFurnizor(furnizor.getIdFurnizor());
                }
            }
        }

        if (selection.equals("Evaluari")) {
            for (Object item : new ArrayList<>(selectedRows)) { // Copie pentru evitarea modificărilor concurente
                if (item instanceof Evaluare evaluare) {
                    evaluareService.deleteEvaluare(evaluare.getIdEvaluare());
                }
            }
        }

        if (selection.equals("Comenzi")) {
            for (Object item : new ArrayList<>(selectedRows)) { // Copie pentru evitarea modificărilor concurente
                if (item instanceof Comanda comanda) {
                    comandaService.deleteComanda(comanda.getIdComanda());
                }
            }
        }

        if (selection.equals("Clienti")) {
            for (Object item : new ArrayList<>(selectedRows)) { // Copie pentru evitarea modificărilor concurente
                if (item instanceof Client client) {
                    clientService.deleteClient(client.getIdClient());
                }
            }
        }

        if (selection.equals("Angajati")) {
            for (Object item : new ArrayList<>(selectedRows)) { // Copie pentru evitarea modificărilor concurente
                if (item instanceof Angajat angajat) {
                    angajatService.deleteAngajat(angajat.getIdAngajat());
                }
            }
        }
        // Similar pentru celelalte selecții...
    }

    private static void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static void saveData(TableView tableView, String selection) {
        // Salvează datele din tableView, în funcție de selecție
        if (selection.equals("Transportatori")) {
            ObservableList<Transportator> data = tableView.getItems();
            for (Transportator transportator : data) {
                transportatorService.saveTransportator(transportator);
            }
        }
        if (selection.equals("Materiale")) {
            ObservableList<Material> data = tableView.getItems();
            for (Material material : data) {
                materialService.saveMaterial(material);
            }
        }

        if (selection.equals("Furnizori")) {
            ObservableList<Furnizor> data = tableView.getItems();
            for (Furnizor furnizor : data) {
                furnizorService.saveFurnizor(furnizor);
            }
        }
        if (selection.equals("Evaluari")) {
            ObservableList<Evaluare> data = tableView.getItems();
            for (Evaluare evaluare : data) {
                evaluareService.saveEvaluare(evaluare);
            }
        }
        if (selection.equals("Comenzi")) {
            ObservableList<Comanda> data = tableView.getItems();
            for (Comanda comanda : data) {
                comandaService.saveComanda(comanda);
            }
        }
        if (selection.equals("Clienti")) {
            ObservableList<Client> data = tableView.getItems();
            for (Client client : data) {
                clientService.saveClient(client);
            }
        }
        // Similar pentru celelalte selecții...

        if (selection.equals("Angajati")) {
            ObservableList<Angajat> data = tableView.getItems();
            for (Angajat angajat : data) {
                angajatService.saveAngajat(angajat);
            }
        }

        refreshTableView(tableView, selection);
    }

    private static void refreshTableView(TableView tableView, String selection) {
        if (selection.equals("Transportatori")) {
            ObservableList<Transportator> updatedData = FXCollections.observableArrayList(transportatorService.getAllTransportatori());
            tableView.setItems(updatedData);
        }
        if (selection.equals("Materiale")) {
            ObservableList<Material> updatedData = FXCollections.observableArrayList(materialService.getAllMaterials());
            tableView.setItems(updatedData);
        }

        if (selection.equals("Furnizori")) {
            ObservableList<Furnizor> updatedData = FXCollections.observableArrayList(furnizorService.getAllFurnizori());
            tableView.setItems(updatedData);
        }
        if (selection.equals("Evaluari")) {
            ObservableList<Evaluare> updatedData = FXCollections.observableArrayList(evaluareService.getAllEvaluari());
            tableView.setItems(updatedData);
        }
        if (selection.equals("Comenzi")) {
            ObservableList<Comanda> updatedData = FXCollections.observableArrayList(comandaService.getAllComenzi());
            tableView.setItems(updatedData);
        }
        if (selection.equals("Clienti")) {
            ObservableList<Client> updatedData = FXCollections.observableArrayList(clientService.getAllClienti());
            tableView.setItems(updatedData);
        }

        if (selection.equals("Angajati")) {
            ObservableList<Angajat> updatedData = FXCollections.observableArrayList(angajatService.getAllAngajati());
            tableView.setItems(updatedData);
        }
        // Similar pentru celelalte selecții...
    }

    // Metoda pentru a obține numele coloanelor pe baza selecției
    private static List<String> getColumnNamesForSelection(String selection) {
        switch (selection) {
            case "Angajati":
                return angajatService.getNumeColoane();
            // Adaugă cazuri pentru alte selecții
            case "Clienti":
                return clientService.getNumeColoane(); // Adaugă metoda getNumeColoane() în clasa ClientService
            // Adaugă cazuri pentru alte selecții
            case "Comenzi":
                return comandaService.getNumeColoane();
            case "Evaluari":
                return evaluareService.getNumeColoane();

            case "Furnizori":
                return furnizorService.getNumeColoane();
            case "Materiale":
                return materialService.getNumeColoane();
            case "Transportatori":
                return transportatorService.getNumeColoane();
            default:
                return new ArrayList<>();
        }
    }

    // Metoda pentru a gestiona acțiunea de filtrare
    private static void handleFilterAction(TableView tableView, String selection, VBox filterBox) {
        // Preiau valorile din fiecare TextField de filtrare


        // Aplică filtrarea bazată pe selecție
        switch (selection) {
            case "Angajati":
                String id = ((TextField) filterBox.lookup("#filter_ID_ANGAJAT")).getText();
                String nume = ((TextField) filterBox.lookup("#filter_NUME")).getText();
                String functie = ((TextField) filterBox.lookup("#filter_FUNCTIE")).getText();
                String dateDeContact = ((TextField) filterBox.lookup("#filter_DATE_DE_CONTACT")).getText();
                List<Angajat> filteredAngajati = angajatService.filterAngajati(id, nume, functie, dateDeContact);
                tableView.setItems(FXCollections.observableArrayList(filteredAngajati));
                break;
            case "Clienti":
                String idClient = ((TextField) filterBox.lookup("#filter_ID_CLIENT")).getText();
                String numeClient = ((TextField) filterBox.lookup("#filter_NUME")).getText();
                String adresaClient = ((TextField) filterBox.lookup("#filter_ADRESA")).getText();
                String contactClient = ((TextField) filterBox.lookup("#filter_CONTACT")).getText();
                List<Client> filteredClienti = clientService.filterClienti(idClient, numeClient, adresaClient, contactClient);
                tableView.setItems(FXCollections.observableArrayList(filteredClienti));
                break;
            case "Comenzi":
                String idComanda = ((TextField) filterBox.lookup("#filter_ID_COMANDA")).getText();
                String dataComenzii = ((TextField) filterBox.lookup("#filter_DATA_COMENZII")).getText();
                String idClientComanda = ((TextField) filterBox.lookup("#filter_ID_CLIENT")).getText();
                String idFurnizor = ((TextField) filterBox.lookup("#filter_ID_FURNIZOR")).getText();
                String idAngajatComanda = ((TextField) filterBox.lookup("#filter_ID_ANGAJAT")).getText();
                String idMaterial = ((TextField) filterBox.lookup("#filter_ID_MATERIAL")).getText();
                String totalComanda = ((TextField) filterBox.lookup("#filter_TOTAL_COMANDA")).getText();
                String statutComanda = ((TextField) filterBox.lookup("#filter_STATUT_COMANDA")).getText();
                String tipComanda = ((TextField) filterBox.lookup("#filter_TIP_COMANDA")).getText();
                String cantitate = ((TextField) filterBox.lookup("#filter_CANTITATE")).getText();
                String pretTotal = ((TextField) filterBox.lookup("#filter_PRET_TOTAL")).getText();
                List<Comanda> filteredComenzi = comandaService.filterComenzi(idComanda, dataComenzii, idClientComanda, idFurnizor,
                        idAngajatComanda, idMaterial, totalComanda, statutComanda, tipComanda, cantitate, pretTotal);
                tableView.setItems(FXCollections.observableArrayList(filteredComenzi));
                break;
            case "Evaluari":
                String idEvaluare = ((TextField) filterBox.lookup("#filter_ID_EVALUARE")).getText();
                String idClientEvaluare = ((TextField) filterBox.lookup("#filter_ID_CLIENT")).getText();
                String scor = ((TextField) filterBox.lookup("#filter_SCOR")).getText();
                String feedback = ((TextField) filterBox.lookup("#filter_FEEDBACK")).getText();
                String dataEvaluarii = ((TextField) filterBox.lookup("#filter_DATA_EVALUARII")).getText();
                List<Evaluare> filteredEvaluari = evaluareService.filterEvaluari(idEvaluare, idClientEvaluare, scor, feedback, dataEvaluarii);
                tableView.setItems(FXCollections.observableArrayList(filteredEvaluari));
                break;
            case "Furnizori":
                String filterIdFurnizor = ((TextField) filterBox.lookup("#filter_ID_FURNIZOR")).getText();
                String filterNumeFurnizor = ((TextField) filterBox.lookup("#filter_NUME")).getText();
                String filterAdresaFurnizor = ((TextField) filterBox.lookup("#filter_ADRESA")).getText();
                String filterContactFurnizor = ((TextField) filterBox.lookup("#filter_CONTACT")).getText();
                List<Furnizor> filteredFurnizori = furnizorService.filterFurnizori(filterIdFurnizor, filterNumeFurnizor, filterAdresaFurnizor, filterContactFurnizor);
                tableView.setItems(FXCollections.observableArrayList(filteredFurnizori));
                break;
            case "Materiale":
                String idMaterialFilter = ((TextField) filterBox.lookup("#filter_ID_MATERIAL")).getText();
                String numeMaterial = ((TextField) filterBox.lookup("#filter_NUME")).getText();
                String descriereMaterial = ((TextField) filterBox.lookup("#filter_DESCRIERE")).getText();
                String cantitateInStocMaterial = ((TextField) filterBox.lookup("#filter_CANTITATE_IN_STOC")).getText();
                String pretUnitarMaterial = ((TextField) filterBox.lookup("#filter_PRET_UNITAR")).getText();
                String idFurnizorMaterial = ((TextField) filterBox.lookup("#filter_ID_FURNIZOR")).getText();
                List<Material> filteredMateriale = materialService.filterMateriale(idMaterialFilter, numeMaterial, descriereMaterial, cantitateInStocMaterial, pretUnitarMaterial, idFurnizorMaterial);
                tableView.setItems(FXCollections.observableArrayList(filteredMateriale));
                break;
            case "Transportatori":
                String idTransportator = ((TextField) filterBox.lookup("#filter_ID_TRANSPORTATOR")).getText();
                String numeTransportator = ((TextField) filterBox.lookup("#filter_NUME")).getText();
                String contactTransportator = ((TextField) filterBox.lookup("#filter_CONTACT")).getText();
                String pretPeKgTransportator = ((TextField) filterBox.lookup("#filter_PRET_PE_KG")).getText();
                List<Transportator> filteredTransportatori = transportatorService.filterTransportatori(idTransportator, numeTransportator, contactTransportator, pretPeKgTransportator);
                tableView.setItems(FXCollections.observableArrayList(filteredTransportatori));
                break;


            // Adaugă cazuri pentru celelalte selecții
        }
    }


    // Metoda pentru a filtra angajații (de exemplu)
    private static ObservableList<Angajat> filtrareAngajati(VBox filterBox) {
        // Aici aplici filtrarea pe baza valorilor din câmpurile de text
        ObservableList<Angajat> filteredList = FXCollections.observableArrayList();

        // Presupunem că ai un câmp de text pentru ID și unul pentru nume
        String id = ((TextField) filterBox.getChildren().get(1)).getText(); // Indexul poate varia
        String nume = ((TextField) filterBox.getChildren().get(2)).getText();

        List<Angajat> allAngajati = angajatService.getAllAngajati();
        if (allAngajati != null) {
            Stream<Angajat> stream = allAngajati.stream();
            if (id != null && !id.isEmpty()) {
                stream = stream.filter(a -> String.valueOf(a.getIdAngajat()).equals(id));
            }
            if (nume != null && !nume.isEmpty()) {
                stream = stream.filter(a -> a.getNume().toLowerCase().contains(nume.toLowerCase()));
            }
            filteredList.setAll(stream.collect(Collectors.toList()));
        }

        return filteredList;
    }

    public static VBox createFilterBox(String selection, TableView<?> tableView) {
        VBox filterBox = new VBox(5);
        filterBox.setPadding(new Insets(10));
        filterBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1;" +
                "-fx-border-insets: 5; -fx-border-radius: 5; -fx-border-color: grey;");

        Label filterLabel = new Label("Filtrare " + selection);
        filterBox.getChildren().add(filterLabel);

        // Creează câmpurile de filtrare pe baza selecției
        List<String> columnNames = getColumnNamesForSelection(selection);
        for (String columnName : columnNames) {
            HBox fieldBox = new HBox(5);
            Label label = new Label(columnName + ":");
            TextField textField = new TextField();
            textField.setId("filter_" + columnName); // Set ID-ul pentru fiecare câmp de text
            textField.setPromptText("Filtrare după " + columnName);
            fieldBox.getChildren().addAll(label, textField);
            filterBox.getChildren().add(fieldBox);
        }

        Button filterButton = new Button("Aplică Filtrul");
        filterButton.setOnAction(event -> handleFilterAction(tableView, selection, filterBox));
        filterBox.getChildren().add(filterButton);

        return filterBox;
    }

    public static PieChart createPieChartForAngajatiFunction(AngajatService angajatService) {
        Map<String, Integer> functiiCount = angajatService.getPondereFunctiiAngajati();

        // Calcul total angajați pentru a determina procentele
        int totalAngajati = functiiCount.values().stream().mapToInt(Integer::intValue).sum();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        functiiCount.forEach((functie, count) -> {
            // Calcul procentaj
            double procent = (count * 100.0) / totalAngajati;
            // Formatare procentaj cu o zecimală
            String label = String.format("%s: %.1f%%", functie, procent);
            PieChart.Data data = new PieChart.Data(label, count);
            pieChartData.add(data);
        });

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Pondere Funcții Angajați");

        return pieChart;
    }

    public static AngajatService getAngajatService() {
        return angajatService;

    }
    public static BarChart<String, Number> createBarChartForCantitatePeCategorie(MaterialService materialService) {
        // Obținerea cantităților totale pe categorie de materiale
        Map<String, Integer> cantitatePeCategorie = materialService.getCantitatePeCategorie();

        // Definirea axelor
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Categorie");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cantitate Totală în Stoc");

        // Crearea graficului cu bare
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Cantitatea Totală de Materiale pe Categorie în Stoc");

        // Crearea unei serii de date pentru grafic
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Cantitate Totală");

        // Adăugarea datelor la serie
        cantitatePeCategorie.forEach((categorie, total) -> {
            series.getData().add(new XYChart.Data<>(categorie, total));
        });

        // Adăugarea seriei la grafic
        barChart.getData().add(series);

        return barChart;
    }
    public static StackedBarChart<String, Number> createStackedBarChartForComenzi(ComandaService comandaService) {
        try {
            // Obținerea datelor agregate pe tip de comandă
            Map<String, Double> totalComenziPeTip = comandaService.getTotalComenziPeTip();

            // Crearea axelor
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Tip Comandă");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Total Comandă");

            // Crearea graficului stivuit
            StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
            stackedBarChart.setTitle("Totalul Comenzilor pe Tip");

            // Crearea unei serii de date și adăugarea lor la grafic
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Total Comandă");

            // Adăugarea datelor la serie
            totalComenziPeTip.forEach((tip, total) -> {
                series.getData().add(new XYChart.Data<>(tip, total));
            });

            // Adăugarea seriei la grafic
            stackedBarChart.getData().add(series);

            return stackedBarChart;

        } catch (SQLException e) {
            e.printStackTrace(); // Tratează excepția, de exemplu prin logare sau afișarea unui mesaj de eroare
            return new StackedBarChart<>(new CategoryAxis(), new NumberAxis()); // Returnează un grafic gol sau gestionează altfel
        }
    }

    // Metoda pentru crearea unui Line Chart pentru evaluări
    public static LineChart<String, Number> createLineChartForEvaluari(EvaluareService evaluareService) {
        // Obține datele necesare de la EvaluareService
        Map<String, Double> averageScorePerDay = evaluareService.getScoruriMediiPeData();

        // Crearea axelor
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Scor Mediu");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Data Evaluării");

        // Crearea graficului linie
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Evoluția Scorurilor de-a lungul Timpului");

        // Crearea seriei de date
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Scoruri Evaluări");

        // Sortarea datelor după dată și adăugarea lor la serie
        averageScorePerDay.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                });

        // Adăugarea seriei la grafic
        lineChart.getData().add(series);

        return lineChart;
    }

    public static void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    public static void exportClientsToCSV(List<Client> clients, List<String> columnNames, File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            // Scrie antetul cu numele coloanelor
            String header = String.join(",", columnNames);
            writer.println(header);

            // Scrie datele fiecărui client
            for (Client client : clients) {
                String line = convertClientToCSVLine(client);
                writer.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Afișează un dialog de eroare sau gestionează eroarea cum consideri necesar
        }
    }

    private static String convertClientToCSVLine(Client client) {
        // Preia fiecare valoare din client și o transformă într-un șir CSV
        String[] data = {
                String.valueOf(client.getIdClient()),
                client.getNume(),
                client.getAdresa(),
                client.getContact()
        };
        return String.join(",", data);
    }

    public static void exportAngajatiToCSV(List<Angajat> angajati, List<String> columnNames, File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            // Scrie antetul cu numele coloanelor
            String header = String.join(",", columnNames);
            writer.println(header);

            // Scrie datele fiecărui angajat
            for (Angajat angajat : angajati) {
                String line = convertAngajatToCSVLine(angajat);
                writer.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Afișează un dialog de eroare sau gestionează eroarea cum consideri necesar
        }
    }
    private static String convertAngajatToCSVLine(Angajat angajat) {
        String[] data = {
                String.valueOf(angajat.getIdAngajat()),
                angajat.getNume(),
                angajat.getFunctie(),
                angajat.getDateDeContact()
                // Adaugă aici alte proprietăți dacă sunt necesare
        };

        // Pentru a evita problemele cu virgulele în date, înconjoară fiecare element cu ghilimele duble
        return "\"" + String.join("\",\"", data) + "\"";
    }

    private static String convertComandaToCSVLine(Comanda comanda) {
        String[] data = {
                String.valueOf(comanda.getIdComanda()),
                comanda.getDataComenzii().toString(), // Presupunând că getDataComenzii() returnează un obiect Date sau similar
                String.valueOf(comanda.getIdClient()),
                String.valueOf(comanda.getIdFurnizor()),
                String.valueOf(comanda.getIdAngajat()),
                String.valueOf(comanda.getIdMaterial()),
                String.valueOf(comanda.getTotalComanda()),
                comanda.getStatutComanda(),
                comanda.getTipComanda(),
                String.valueOf(comanda.getCantitate()),
                String.valueOf(comanda.getPretTotal())
                // ... adaugă restul proprietăților necesare
        };
        return "\"" + String.join("\",\"", data) + "\"";
    }

    public static void exportComenziToCSV(List<Comanda> comenzi, List<String> columnNames, File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            String header = String.join(",", columnNames);
            writer.println(header);

            for (Comanda comanda : comenzi) {
                String line = convertComandaToCSVLine(comanda);
                writer.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Afișează un dialog de eroare sau gestionează eroarea cum consideri necesar
        }
    }

    // ... alte metode ...
    }


    // Restul codului pentru clasa MainApplication...





