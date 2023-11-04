package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.github.saacsos.FXRouter;
import ku.cs.models.Stock;
import ku.cs.models.StockList;
import ku.cs.services.Datasource;
import ku.cs.services.StockDataSource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class StockManageController {
    @FXML private TableView<Stock> itemTableView;
    @FXML private Label categoryIDLabel;
    @FXML private TextField addItemIdTextField;
    @FXML private TextField addItemNameTextField;
    @FXML private TextField addAmountTextField;
    @FXML private TextField addLocationTextField;
    @FXML private DatePicker addStorageDatePicker;
    @FXML private Label warning;
    @FXML private Label successLabel;
    private Datasource<StockList> stockListDatasource;
    private StockList stockList;



    @FXML
    public void initialize() {
        loadData();
        initTableView();
        stockListDatasource = new StockDataSource();
        stockList = stockListDatasource.readData();
    }

    public void loadData() {
        stockListDatasource = new StockDataSource();
        stockList = stockListDatasource.readData();

        String categoryId = (String) FXRouter.getData();

        if (categoryId != null) {
            ObservableList<Stock> itemsWithSameCategory = FXCollections.observableArrayList(
                    stockList.getStockList().stream()
                            .filter(stock -> categoryId.equals(stock.getCategoryId()))
                            .collect(Collectors.toList())
            );
            itemTableView.setItems(itemsWithSameCategory);
            initTableView();
        }
        categoryIDLabel.setText(categoryId);
    }



    private void initTableView() {
        TableColumn<Stock, String> idColumn = new TableColumn<>("ITEM_ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Stock, String> nameColumn = new TableColumn<>("ITEM_NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Stock, Integer> amountColumn = new TableColumn<>("AMOUNT");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Stock, String> locationColumn = new TableColumn<>("LOCATION");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Stock, String> storageDateColumn = new TableColumn<>("STORAGE_DATE");
        storageDateColumn.setCellValueFactory(new PropertyValueFactory<>("storageDate"));

        TableColumn<Stock, String> categoryIdColumn = new TableColumn<>("CATEGORY_ID");
        categoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));

        itemTableView.getColumns().setAll(idColumn, nameColumn, amountColumn, locationColumn, storageDateColumn, categoryIdColumn);
    }

    @FXML public  void onDeleteItemButton(){
        Stock selectedStock = itemTableView.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            String itemId = selectedStock.getItemId();

            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Are you to delete this item?");
            confirmationAlert.setContentText("Please confirm your action.");

            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == confirmButton) {
                stockListDatasource.deleteData(itemId);
                loadData(); // Reload data in the table view after deletion
            }
        }
    }
    @FXML
    public void onAddItemButton() {
        String addItemID = addItemIdTextField.getText();
        String addItemName = addItemNameTextField.getText();
        Integer addAmountText = Integer.valueOf(addAmountTextField.getText());
        String addLocation = addLocationTextField.getText();
        String addDate = addStorageDatePicker.getValue().toString();

        Stock stock = stockList.findItemByIdAndName(addItemID, addItemName);

        if (!addItemID.isEmpty() && !addItemName.isEmpty() && addAmountText != null && !addLocation.isEmpty()) {
            try {
                int addAmount = Integer.parseInt(String.valueOf(addAmountText));

                Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText("Are you sure to add this item?");
                confirmationAlert.setContentText("Please confirm your action.");

                ButtonType confirmButton = new ButtonType("Confirm");
                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

                Optional<ButtonType> result = confirmationAlert.showAndWait();

                if (result.isPresent() && result.get() == confirmButton) {
                    LocalDateTime localDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm-yyyy-MM-dd");
                    String formattedTime = localDateTime.format(formatter);
                    String itemIdWithDate = addItemID + "-" + formattedTime.toString();
                    stockList.addStock(itemIdWithDate, addItemName, addAmount, addLocation, addDate, addItemID);
                    stockListDatasource.insertData(stockList);

                    successLabel.setText("Add item to stock complete");
                    successLabel.setStyle("-fx-text-fill: green");
                } else {
                    warning.setText("Action canceled.");
                }
            } catch (NumberFormatException e) {
                warning.setText("Please enter a valid amount.");
            }
        } else {
            warning.setText("Please fill in complete information.");
        }

        addItemIdTextField.clear();
        addItemNameTextField.clear();
        addAmountTextField.clear();
        addLocationTextField.clear();
        addStorageDatePicker.getEditor().clear();
        loadData(); // Reload data in the table view
    }

    @FXML
    public void onBackClick() {
        try {
            FXRouter.goTo("editstock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
