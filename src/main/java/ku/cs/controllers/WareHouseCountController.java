package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.*;
import ku.cs.services.CountStockDataSource;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;

import java.io.IOException;
import java.util.Optional;

public class WareHouseCountController {
    @FXML private TextField itemNameTextField;
    @FXML private TextField categoryIdTextField;
    @FXML private TextField totalTextfield;
    @FXML private TextField locationTextField;
    @FXML private TableView<CountStock> itemTableView;
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;
    private CountStockList countStockList;
    private Datasource<CountStockList> countStockListDatasource;
    private CountStock countStock;

    public void initialize(){
        loadData();
        initTableView();
    }
    public void loadData(){
        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();

        countStockListDatasource = new CountStockDataSource();
        countStockList = countStockListDatasource.readData();


        String userName = (String) com.github.saacsos.FXRouter.getData();
        user = userList.findUserByUsername(userName);

        loadCountStockData();
    }
    private void initTableView(){
        TableColumn<CountStock, String> idColumn = new TableColumn<>("CATEGORY_ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));

        TableColumn<CountStock, String> nameColumn = new TableColumn<>("ITEM_NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<CountStock, String> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<CountStock, String> locationColumn = new TableColumn<>("LOCATION");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<CountStock, String> userIdColumn = new TableColumn<>("USER-ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<CountStock, String> userNameColumn = new TableColumn<>("USER-Name");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        itemTableView.getColumns().addAll(idColumn, nameColumn, totalColumn, locationColumn, userIdColumn, userNameColumn);
    }
    @FXML
    public void onInsertDataClick() {
        String itemName = itemNameTextField.getText();
        String categoryId = categoryIdTextField.getText();
        String totalText = totalTextfield.getText();
        String location = locationTextField.getText();

        // Check if any of the fields are empty
        if (itemName.isEmpty() || categoryId.isEmpty() || totalText.isEmpty() || location.isEmpty()) {
            showErrorAlert("Error", "Please fill out all the information fields.");
            return; // Exit the method if any field is empty
        }

        try {
            float total = Float.parseFloat(totalText);

            // Create a new CountStock object
            CountStock newCountStock = new CountStock(user.getUserId(), itemName, categoryId, total, location, user.getUserName());

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

                // Clear the input fields
                itemNameTextField.clear();
                categoryIdTextField.clear();
                totalTextfield.clear();
                locationTextField.clear();

                // Refresh the table view to display the new data
                itemTableView.getItems().add(newCountStock);
            }
            loadCountStockData();
        } catch (NumberFormatException e) {
            showErrorAlert("Error", "Invalid value for 'Total'. Please enter a valid number.");
        }
    }

    @FXML
    public void onDeleteDataFromTableView() {
        CountStock selectedItem = itemTableView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Create a confirmation dialog
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Are you sure to delete this item?");
            confirmationAlert.setContentText("Please confirm your action.");

            // Show the confirmation dialog and wait for a response
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed the action, delete the item
                itemTableView.getItems().remove(selectedItem);
                countStockList.getCountStocks().remove(selectedItem);

                // Update the database by calling your deleteData method
                // Make sure to implement the deleteData method in your CountStockDataSource
                countStockListDatasource.deleteData(selectedItem.getCategoryId());

                // Display a success message
                showErrorAlert("Success", "Item deleted successfully.");
            }
        } else {
            // No item selected, show an error alert
            showErrorAlert("Error", "Please select an item to delete.");
        }
        loadCountStockData();
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
