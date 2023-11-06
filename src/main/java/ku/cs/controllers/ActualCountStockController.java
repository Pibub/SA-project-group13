package ku.cs.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.*;
import ku.cs.services.ActualCountStockDataSource;
import ku.cs.services.CountStockDataSource;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;

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
    @FXML private TableView<CountStock> itemCountStockTableView;
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;
    private CountStockList countStockList;
    private Datasource<CountStockList> countStockListDatasource;
    private ActualCountStockList actualCountStockList;
    private Datasource<ActualCountStockList> actualCountStockListDatasource;
    private CountStock countStock;
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
    private void loadCountStockData() {
        ObservableList<CountStock> countStockItems = FXCollections.observableArrayList(countStockList.getCountStocks());
        itemCountStockTableView.setItems(countStockItems);
    }

    @FXML
    public void onInsertDataClick() {
        String categoryId = categoryIdTextField.getText();
        String itemName = itemNameTextField.getText();
        String location = locationTextField.getText();
        float firstCount = Float.parseFloat(firstCountTextField.getText());
        float secondCount = Float.parseFloat(secondCountTextField.getText());
        float thirdCount = Float.parseFloat(thirdCountTextField.getText());
        String inputerId = inputerIdTextField.getText();
        String inputerName = inputerNameTextField.getText();

        // Create a confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to add this category ID?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Check if a record with the same category ID already exists
            if (actualCountStockList.isCategoryIdExists(categoryId)) {
                // Display an alert to the user
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


    public void onBackClick(){
        try {
            com.github.saacsos.FXRouter.goTo("count-stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
