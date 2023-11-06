package ku.cs.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.*;
import ku.cs.services.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

public class ActualCountStockController {
    @FXML private TextField categoryIdTextField;
    @FXML private TextField itemNameTextField;
    @FXML private TextField firstCountTextField;
    @FXML private TextField secondCountTextField;
    @FXML private TextField thirdCountTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField inputerIdTextField;
    @FXML private TextField inputerNameTextField;
    @FXML private TableView<ActualCountStock> itemTableView;
    @FXML private TextField analyzeCategoryId;
    @FXML private TextField analyzeUserId;
    @FXML private TextField analyzeUserName;
    @FXML private TextField analyzeText;
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;
    private CountStockList countStockList;
    private Datasource<CountStockList> countStockListDatasource;
    private ActualCountStockList actualCountStockList;
    private Datasource<ActualCountStockList> actualCountStockListDatasource;
    private AnalyzeList analyzeList;
    private Datasource<AnalyzeList> analyzeListDatasource;
    public void initialize() {
        loadData();
        initTableView();
    }

    public void loadData() {
        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();

        countStockListDatasource = new CountStockDataSource();
        countStockList = countStockListDatasource.readData();

        actualCountStockListDatasource = new ActualCountStockDataSource();
        actualCountStockList = actualCountStockListDatasource.readData();

        analyzeListDatasource = new AnalyzeDataSource();
        analyzeList = analyzeListDatasource.readData();

        String userName = (String) com.github.saacsos.FXRouter.getData();
        user = userList.findUserByUsername(userName);

    }

    private void initTableView() {
        TableColumn<ActualCountStock, String> idColumn = new TableColumn<>("CATEGORY_ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));

        TableColumn<ActualCountStock, String> nameColumn = new TableColumn<>("ITEM_NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<ActualCountStock, String> locationColumn = new TableColumn<>("LOCATION");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<ActualCountStock, String> firstCountColumn = new TableColumn<>("FIRST_COUNT");
        firstCountColumn.setCellValueFactory(new PropertyValueFactory<>("firstCount"));

        TableColumn<ActualCountStock, String> secondCountColumn = new TableColumn<>("SECOND_COUNT");
        secondCountColumn.setCellValueFactory(new PropertyValueFactory<>("secondCount"));

        TableColumn<ActualCountStock, String> thirdCountColumn = new TableColumn<>("THIRD_COUNT");
        thirdCountColumn.setCellValueFactory(new PropertyValueFactory<>("thirdCount"));

        TableColumn<ActualCountStock, String> totalColumn = new TableColumn<>("ON-HAND");
        totalColumn.setCellValueFactory(param -> {
            ActualCountStock actualCountStock = param.getValue();
            String categoryId = actualCountStock.getCategoryId();

            // Find the corresponding CountStock item with the same categoryId
            CountStock matchingCountStock = countStockList.findTotalByCategoryId(categoryId);

            if (matchingCountStock != null) {
                return new SimpleStringProperty(String.valueOf(matchingCountStock.getTotal())); // Convert float to String
            } else {
                return new SimpleStringProperty("0.0"); // Default value if no match is found
            }
        });

        TableColumn<ActualCountStock, String> inputerIdColumn = new TableColumn<>("INPUTER-ID");
        inputerIdColumn.setCellValueFactory(new PropertyValueFactory<>("inputerId"));

        TableColumn<ActualCountStock, String> inputerNameColumn = new TableColumn<>("INPUTER-NAME");
        inputerNameColumn.setCellValueFactory(new PropertyValueFactory<>("inputerName"));

        itemTableView.getColumns().addAll(idColumn, nameColumn, locationColumn, firstCountColumn, secondCountColumn, thirdCountColumn, totalColumn, inputerIdColumn, inputerNameColumn);

        loadActualCountStockData();
    }
    private void loadActualCountStockData() {
        actualCountStockList = actualCountStockListDatasource.readData();
        ObservableList<ActualCountStock> countStockItems = FXCollections.observableArrayList(actualCountStockList.getActualCountStockList());

        itemTableView.setItems(countStockItems);
    }

    @FXML
    public void onInsertDataClick() {
        String categoryId = categoryIdTextField.getText();
        String itemName = itemNameTextField.getText();
        String location = locationTextField.getText();
        String firstCountText = firstCountTextField.getText();
        String secondCountText = secondCountTextField.getText();
        String thirdCountText = thirdCountTextField.getText();
        String inputerId = inputerIdTextField.getText();
        String inputerName = inputerNameTextField.getText();

        // Check if any of the fields are empty
        if (categoryId.isEmpty() || itemName.isEmpty() || location.isEmpty() || firstCountText.isEmpty() || secondCountText.isEmpty() || thirdCountText.isEmpty() || inputerId.isEmpty() || inputerName.isEmpty()) {
            showAlert("Missing Information", "Please fill out all the information fields.");
            return; // Exit the method if any field is empty
        }

        // Attempt to parse float values from the count fields
        float firstCount;
        float secondCount;
        float thirdCount;
        try {
            firstCount = Float.parseFloat(firstCountText);
            secondCount = Float.parseFloat(secondCountText);
            thirdCount = Float.parseFloat(thirdCountText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Count", "Please enter valid numerical values for the count fields.");
            return; // Exit the method if count fields are not valid
        }

        // Create a confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to add this category ID?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Check if a record with the same category ID already exists
            if (actualCountStockList.isCategoryIdExists(categoryId)) {
                showAlert("Category ID Already Counted", "This category ID has already been counted.");
            } else {
                ActualCountStock newActualCountStock = new ActualCountStock(categoryId, itemName, firstCount, secondCount, thirdCount, inputerId, inputerName, location);

                actualCountStockList.addActualCountStock(newActualCountStock);
                actualCountStockListDatasource.insertData(actualCountStockList);

                clearInputFields();

                itemTableView.getItems().add(newActualCountStock);
                loadData();
            }
        }
    }

    @FXML
    public void onDeleteData() {
        ActualCountStock selectedCountStock = itemTableView.getSelectionModel().getSelectedItem();
        if (selectedCountStock == null) {
            showAlertDel("No Item Selected", "Please select an item to delete.");
            return;
        }

        // Create a confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this item?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Remove the selected item from the table view and your data source
            itemTableView.getItems().remove(selectedCountStock);
            actualCountStockList.getActualCountStockList().remove(selectedCountStock);
            actualCountStockListDatasource.deleteData(selectedCountStock.getCategoryId());
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlertDel(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInputFields() {
        categoryIdTextField.clear();
        itemNameTextField.clear();
        locationTextField.clear();
        firstCountTextField.clear();
        secondCountTextField.clear();
        thirdCountTextField.clear();
        inputerIdTextField.clear();
        inputerNameTextField.clear();
    }
    @FXML
    public void sendAnalyze() {
        String categoryId = analyzeCategoryId.getText();
        String userId = analyzeUserId.getText();
        String userName = analyzeUserName.getText();
        String textAnalyze = analyzeText.getText();

        // Check if any of the fields are empty
        if (categoryId.isEmpty() || userId.isEmpty() || userName.isEmpty() || textAnalyze.isEmpty()) {
            showAlert("Missing Information", "Please fill out all the information fields.");
            return; // Exit the method if any field is empty
        }

        // Create a confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to send this analysis?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Analyze newAnalyze = new Analyze(categoryId, userId, userName, textAnalyze);

            AnalyzeDataSource analyzeDataSource = new AnalyzeDataSource();

            AnalyzeList analyzeList = new AnalyzeList();
            analyzeList.addAnalyze(newAnalyze);
            analyzeDataSource.insertData(analyzeList);

            clearAnalyzeFields();

            showAlert("Analysis Sent", "Your analysis has been sent successfully.");
        }
    }
    private void clearAnalyzeFields() {
        analyzeCategoryId.clear();
        analyzeUserId.clear();
        analyzeUserName.clear();
        analyzeText.clear();
    }



    public void onBackClick(){
        try {
            com.github.saacsos.FXRouter.goTo("count-stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onGotoStockEditClick(){
        try {
            com.github.saacsos.FXRouter.goTo("editstock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
