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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActualCountStockController {
    @FXML private TextField categoryIdTextField;
    @FXML private TextField firstCountTextField;
    @FXML private TextField secondCountTextField;
    @FXML private TextField thirdCountTextField;
    @FXML private TableView<ActualCountStock> itemTableView;
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;
    private CountStockList countStockList;
    private Datasource<CountStockList> countStockListDatasource;
    private ActualCountStockList actualCountStockList;
    private Datasource<ActualCountStockList> actualCountStockListDatasource;
    private AnalyzeList analyzeList;
    private Datasource<AnalyzeList> analyzeListDatasource;
    private StockList stockList;
    private Datasource<StockList> stockListDatasource;
    public void initialize() {
        loadData();
        initTableView();
        loadActualCountStockData();
        itemTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                ActualCountStock selectedActualCountStock = itemTableView.getSelectionModel().getSelectedItem();
                if (selectedActualCountStock != null) {
                    try {
                        // Create a Map to pass both shelfId and userId
                        Map<String, String> dataMap = new HashMap<>();
                        dataMap.put("shelfId", selectedActualCountStock.getShelfId());
                        dataMap.put("userId", user.getUserId());

                        // Pass the Map to the "notify" page
                        com.github.saacsos.FXRouter.goTo("notify", dataMap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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

        stockListDatasource = new StockDataSource();
        stockList = stockListDatasource.readData();

        String userName = (String) com.github.saacsos.FXRouter.getData();
        user = userList.findUserByUsername(userName);

    }

    private void initTableView() {
        TableColumn<ActualCountStock, String> idColumn = new TableColumn<>("SHELF_ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("shelfId"));

        TableColumn<ActualCountStock, String> locationColumn = new TableColumn<>("LOCATION");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<ActualCountStock, String> firstCountColumn = new TableColumn<>("FIRST_COUNT");
        firstCountColumn.setCellValueFactory(new PropertyValueFactory<>("firstCount"));

        TableColumn<ActualCountStock, String> secondCountColumn = new TableColumn<>("SECOND_COUNT");
        secondCountColumn.setCellValueFactory(new PropertyValueFactory<>("secondCount"));

        TableColumn<ActualCountStock, String> thirdCountColumn = new TableColumn<>("THIRD_COUNT");
        thirdCountColumn.setCellValueFactory(new PropertyValueFactory<>("thirdCount"));

        TableColumn<ActualCountStock, String> inputerIdColumn = new TableColumn<>("INPUTER-ID");
        inputerIdColumn.setCellValueFactory(new PropertyValueFactory<>("inputerId"));

        TableColumn<ActualCountStock, String> inputerNameColumn = new TableColumn<>("INPUTER-NAME");
        inputerNameColumn.setCellValueFactory(new PropertyValueFactory<>("inputerName"));

        itemTableView.getColumns().addAll(idColumn, locationColumn, firstCountColumn, secondCountColumn, thirdCountColumn, inputerIdColumn, inputerNameColumn);

        loadActualCountStockData();
    }
    private void loadActualCountStockData() {
        actualCountStockList = actualCountStockListDatasource.readData();
        ObservableList<ActualCountStock> countStockItems = FXCollections.observableArrayList(actualCountStockList.getActualCountStockList());

        itemTableView.setItems(countStockItems);
    }
    private String getLocationFromStockList(String shelfId) {
        return stockList.getLocationByShelfId(shelfId);
    }
    @FXML
    public void onInsertDataClick() {
        String shelfId = categoryIdTextField.getText();
        String firstCountText = firstCountTextField.getText();
        String secondCountText = secondCountTextField.getText();
        String thirdCountText = thirdCountTextField.getText();
        String location = getLocationFromStockList(shelfId);

        if (!isShelfIdValid(shelfId)) {
            showAlert("Error", "Invalid Shelf ID. Please enter a valid Shelf ID.");
            return; // Exit the method if shelfId is invalid
        }

        // Check if any of the fields are empty
        if (shelfId.isEmpty() || firstCountText.isEmpty() || secondCountText.isEmpty() || thirdCountText.isEmpty()) {
            showAlert("Missing Information", "Please fill out all the information fields.");
            return; // Exit the method if any field is empty
        }

        // Attempt to parse integer values from the count fields
        int firstCount;
        int secondCount;
        int thirdCount;
        try {
            firstCount = Integer.parseInt(firstCountText);
            secondCount = Integer.parseInt(secondCountText);
            thirdCount = Integer.parseInt(thirdCountText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Count", "Please enter valid integer values for the count fields.");
            return; // Exit the method if count fields are not valid
        }

        // Check if counts are non-negative
        if (firstCount < 0 || secondCount < 0 || thirdCount < 0) {
            showAlert("Invalid Count", "Count values must be non-negative.");
            return; // Exit the method if count values are negative
        }

        // Check if at least 2 out of 3 counts match
        int matchCount = (firstCount == secondCount ? 1 : 0) + (firstCount == thirdCount ? 1 : 0) + (secondCount == thirdCount ? 1 : 0);
        if (matchCount == 0) {
            showAlert("Invalid Count", "At least 2 out of 3 counts must match. Please retake the counts.");
            return; // Exit the method if not enough counts match
        } else {
            CountStock countStock = countStockList.findTotalByCategoryId(shelfId); // Retrieve the CountStock object

            if (countStock != null) {
                showAlert("Category ID Already Counted", "This category ID has already been counted.");
                return; // Exit the method if the category ID has already been counted
            } else {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Are you sure you want to add this category ID?");

                Optional<ButtonType> result = confirmationAlert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    ActualCountStock newActualCountStock = new ActualCountStock(shelfId, firstCount, secondCount, thirdCount, user.getUserId(), user.getUserName(), location);

                    actualCountStockList.addActualCountStock(newActualCountStock);
                    actualCountStockListDatasource.insertData(actualCountStockList);

                    clearInputFields();

                    itemTableView.getItems().add(newActualCountStock);
                    loadData();
                }
            }
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInputFields() {
        categoryIdTextField.clear();
        firstCountTextField.clear();
        secondCountTextField.clear();
        thirdCountTextField.clear();
    }
    private boolean isShelfIdValid(String shelfId) {
        // Check if the shelfId exists in the stock list
        for (Stock stock : stockList.getStockList()) {
            if (stock.getShelfId().equals(shelfId)) {
                return true; // Shelf ID is valid
            }
        }
        return false; // Shelf ID not found in stock list
    }

    public void onBackClick(){
        try {
            com.github.saacsos.FXRouter.goTo("count-stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
