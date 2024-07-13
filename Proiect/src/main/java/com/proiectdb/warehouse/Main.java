package main.java.com.proiectdb.warehouse;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.java.com.proiectdb.warehouse.factory.ServiceFactory;
import main.java.com.proiectdb.warehouse.model.Angajat;
import main.java.com.proiectdb.warehouse.model.Client;
import main.java.com.proiectdb.warehouse.model.Comanda;
import main.java.com.proiectdb.warehouse.service.*;
import main.java.com.proiectdb.warehouse.util.DialogUtil;
import main.java.com.proiectdb.warehouse.util.UtilUI;
import oracle.net.ano.AuthenticationService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class Main extends Application {

    private static final EvaluareService evaluareService = ServiceFactory.getEvaluareService();
    private static final MaterialService materialService = ServiceFactory.getMaterialService();
    private static final ComandaService comandaService = ServiceFactory.getComandaService();
    private AuthenticationService authService = new AuthenticationService();

    private TableView<?> currentTableView;
    private VBox filterBox;

    @Override
    public void start(Stage primaryStage) {


        Button btnExport = new Button("Export");
        btnExport.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                ObservableList<?> selectedItems = currentTableView.getSelectionModel().getSelectedItems();

                if (!selectedItems.isEmpty()) {
                    if (selectedItems.get(0) instanceof Client) {
                        List<Client> clientsToExport = new ArrayList<>((Collection<? extends Client>) selectedItems);
                        ClientService clientService = new ClientService();
                        List<String> clientColumnNames = clientService.getNumeColoane();
                        UtilUI.exportClientsToCSV(clientsToExport, clientColumnNames, file);
                    } else if (selectedItems.get(0) instanceof Angajat) {
                        List<Angajat> angajatiToExport = new ArrayList<>((Collection<? extends Angajat>) selectedItems);
                        AngajatService angajatService = new AngajatService();
                        List<String> angajatColumnNames = angajatService.getNumeColoane();
                        UtilUI.exportAngajatiToCSV(angajatiToExport, angajatColumnNames, file);
                    } else if (selectedItems.get(0) instanceof Comanda) {
                        List<Comanda> comenziToExport = new ArrayList<>((Collection<? extends Comanda>) selectedItems);
                        ComandaService comandaService = new ComandaService();
                        List<String> comandaColumnNames = comandaService.getNumeColoane();
                        UtilUI.exportComenziToCSV(comenziToExport, comandaColumnNames, file);
                    }
                }
            }
        });



        // Afiseaza dialogul de logare inainte de a afisa fereastra principala
        Optional<Pair<String, String>> result = DialogUtil.showLoginDialog();
        result.ifPresent(usernamePassword -> {
            if (checkCredentials(usernamePassword.getKey(), usernamePassword.getValue())) {
                // Credențiale valide, afișează interfața
                primaryStage.show();
            } else {
                // Credențiale invalide, afișează o eroare și închide aplicația
                UtilUI.showErrorDialog("Logare eșuată", "Numele de utilizator sau parola incorectă.");
                Platform.exit();
            }
        });


        ListView<String> listView = new ListView<>();
        listView.setItems(FXCollections.observableArrayList(UtilUI.SELECTED_TABLE_LIST));


        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(listView);

        Button btnSave = new Button("Save");
        Button btnDelete = new Button("Delete");
        Button btnNew = new Button("New");
        Button btnEdit = new Button("Edit");



        HBox hBox = new HBox(10, btnSave, btnDelete, btnNew, btnEdit, btnExport);
        borderPane.setBottom(hBox);

        // Inițializează secțiunea de filtrare cu prima selecție (dacă există)
        filterBox = UtilUI.createFilterBox("Angajati", null);
        borderPane.setRight(filterBox);

        // Listener pentru schimbarea selecției
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Crearea TableView pentru selecția curentă
                currentTableView = UtilUI.createTableViewForSelection(newSelection);
                ScrollPane scrollPane = new ScrollPane(currentTableView);
                scrollPane.setFitToWidth(true);

                // Verifică dacă selecția este "Angajati" pentru a adăuga PieChart-ul
                // Crearea VBox pentru a conține atât tabelul cât și graficul
                VBox vbox = new VBox();

// Verifică dacă selecția este "Angajati" pentru a adăuga PieChart-ul
                if ("Angajati".equals(newSelection)) {
                    // Obține serviciul de angajați și creează graficul de tip placinta
                    PieChart pieChart = UtilUI.createPieChartForAngajatiFunction(UtilUI.getAngajatService());
                    vbox.getChildren().addAll(scrollPane, pieChart); // Adaugă tabelul și graficul în vbox
                } else if ("Materiale".equals(newSelection)) {
                    // Obține serviciul pentru materiale și creează graficul de tip bare
                    BarChart<String, Number> barChart = UtilUI.createBarChartForCantitatePeCategorie(materialService);
                    vbox.getChildren().addAll(scrollPane, barChart); // Adaugă tabelul și graficul în vbox
                }
                else if ("Comenzi".equals(newSelection)) {
                    StackedBarChart<String, Number> stackedBarChart = UtilUI.createStackedBarChartForComenzi(comandaService);
                    vbox.getChildren().addAll(scrollPane, stackedBarChart);
                }
                else if ("Evaluari".equals(newSelection)) {
                    LineChart<String, Number> lineChart = UtilUI.createLineChartForEvaluari(evaluareService);
                    vbox.getChildren().addAll(scrollPane, lineChart);
                } else {
                    vbox.getChildren().add(scrollPane);
                }

                borderPane.setCenter(vbox);

                // Actualizează acțiunile butoanelor...
                UtilUI.setButtonActions(btnEdit, btnSave, btnDelete, btnNew, currentTableView, newSelection);

                // Actualizează secțiunea de filtrare...
                VBox newFilterBox = UtilUI.createFilterBox(newSelection, currentTableView);
                borderPane.setRight(newFilterBox);
            }
        });

        // Restul setărilor pentru primaryStage...

        primaryStage.setScene(new Scene(borderPane, 1000, 600));
        primaryStage.show();
    }

    private boolean checkCredentials(String username, String password) {
        // Verifică dacă numele de utilizator și parola sunt cele așteptate
        return "Admin".equals(username) && "Nirolf918".equals(password);
    }


    // Metoda main
    public static void main(String[] args) {
        launch(args);
    }
}


