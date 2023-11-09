package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.*;
import ku.cs.services.CountStockDataSource;
import ku.cs.services.Datasource;
import ku.cs.services.StockDataSource;
import ku.cs.services.UserDataSource;

import java.io.IOException;
import java.util.Optional;

public class WareHouseCountController {
    @FXML private TextField categoryIdTextField;
    @FXML private TextField totalTextfield;
    @FXML private TableView<CountStock> itemTableView;
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;
    private CountStockList countStockList;
    private Datasource<CountStockList> countStockListDatasource;
    private StockList stockList;
    private Datasource<StockList> stockListDatasource;

    public void initialize(){
        loadData();
        initTableView();
    }
    public void loadData(){
        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();

        countStockListDatasource = new CountStockDataSource();
        countStockList = countStockListDatasource.readData();

        stockListDatasource = new StockDataSource();
        stockList = stockListDatasource.readData();


        String userName = (String) com.github.saacsos.FXRouter.getData();
        user = userList.findUserByUsername(userName);

        loadCountStockData();
    }
    private void initTableView(){
        TableColumn<CountStock, String> idColumn = new TableColumn<>("SHELF_ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("shelfId"));

        TableColumn<CountStock, String> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<CountStock, String> locationColumn = new TableColumn<>("LOCATION");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<CountStock, String> userIdColumn = new TableColumn<>("USER-ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<CountStock, String> userNameColumn = new TableColumn<>("USER-Name");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        itemTableView.getColumns().addAll(idColumn, totalColumn, locationColumn, userIdColumn, userNameColumn);
    }
    private String getLocationFromStockList(String shelfId) {
        return stockList.getLocationByShelfId(shelfId);
    }
    @FXML
    public void onInsertDataClick() {
        String shelfId = categoryIdTextField.getText();
        String totalText = totalTextfield.getText();
        String location = getLocationFromStockList(shelfId);

        // Check if the shelfId exists in the stock list
        if (!isShelfIdValid(shelfId)) {
            showErrorAlert("Error", "Invalid Shelf ID. Please enter a valid Shelf ID.");
            return; // Exit the method if shelfId is invalid
        }

        // Check if any of the fields are empty
        if (shelfId.isEmpty() || totalText.isEmpty()) {
            showErrorAlert("Error", "Please fill out all the information fields.");
            return; // Exit the method if any field is empty
        }

        try {
            int total = Integer.parseInt(totalText);

            // Check if the total is not negative
            if (total < 0) {
                showErrorAlert("Error", "Total cannot be negative. Please enter a non-negative number.");
                return; // Exit the method if total is negative
            }

            // Create a new CountStock object
            CountStock newCountStock = new CountStock(user.getUserId(), shelfId, total, location, user.getUserName());

            // Create a confirmation dialog
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Are you sure to add this item?");
            confirmationAlert.setContentText("Please confirm your action.");

            // Show the confirmation dialog and wait for a response
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed the action
                // Add the new CountStock to the CountStockList and update the database
                countStockList.addCountStock(newCountStock);
                CountStockDataSource countStockDataSource = new CountStockDataSource();
                countStockDataSource.insertData(countStockList);

                categoryIdTextField.clear();
                totalTextfield.clear();

                // Refresh the table view to display the new data
                itemTableView.getItems().add(newCountStock);


            }
            loadCountStockData();
        } catch (NumberFormatException e) {
            showErrorAlert("Error", "Invalid value for 'Total'. Please enter a valid non-negative integer.");
        }
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



    private void showErrorAlert(String title, String contentText) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(contentText);

        errorAlert.showAndWait();
    }


    private void loadCountStockData() {
        CountStockList countStockList = countStockListDatasource.readData();
        ObservableList<CountStock> countStockItems = FXCollections.observableArrayList(countStockList.getCountStocks());

        itemTableView.setItems(countStockItems);
    }


    @FXML
    public void onBackClick(){
        try {
            com.github.saacsos.FXRouter.goTo("count-stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
