package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.*;
import ku.cs.services.Datasource;
import ku.cs.services.HistoryDataSource;
import ku.cs.services.StockDataSource;
import com.github.saacsos.FXRouter;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.scene.control.Alert.AlertType;
import ku.cs.services.UserDataSource;

public class RequisitionManageController {
    @FXML private TableView<Stock> itemTableView;
    @FXML private Label categoryIDLabel;
    @FXML private TextField amountField;
    @FXML private Label errorLabel;
    private  Stock stock;
    private Datasource<StockList> stockListDatasource;
    private Datasource<UserList> userListDatasource;

    private Datasource<HistoryList> historyListDatasource;
    private StockList stockList;
    private User user;
    private UserList userList;

    private HistoryList historyList;
    @FXML
    public void initialize() {
        loadData();
        initTableView();
        errorLabel.setText("");
        stockListDatasource = new StockDataSource();
        userListDatasource = new UserDataSource();
        stockList = stockListDatasource.readData();
        userList = userListDatasource.readData();
        user = userList.getUsers().get(0);

        historyList = new HistoryList();
        historyListDatasource = new HistoryDataSource();
    }
    private String generateRequisitionID() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uniqueIdentifier = "REQ";
        return uniqueIdentifier + timestamp;
    }
    private void initTableView() {
        TableColumn<Stock, String> idColumn = new TableColumn<>("ITEM_ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Stock, String> nameColumn = new TableColumn<>("ITEM_NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Stock, Integer> qtyColumn = new TableColumn<>("QTY");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<Stock, String> unitColumn = new TableColumn<>("UNIT");
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));

        TableColumn<Stock, String> locationColumn = new TableColumn<>("LOCATION");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Stock, String> storageDateColumn = new TableColumn<>("STORAGE_DATE");
        storageDateColumn.setCellValueFactory(new PropertyValueFactory<>("storageDate"));

        TableColumn<Stock, String> shelfIdColumn = new TableColumn<>("SHELF_ID");
        shelfIdColumn.setCellValueFactory(new PropertyValueFactory<>("shelfId"));

        itemTableView.getColumns().setAll(idColumn, nameColumn, qtyColumn, unitColumn, locationColumn, storageDateColumn, shelfIdColumn);
    }
    public void loadData() {
        stockListDatasource = new StockDataSource();
        stockList = stockListDatasource.readData();

        String categoryId = (String) com.github.saacsos.FXRouter.getData();

        if (categoryId != null) {
            ObservableList<Stock> itemsWithSameCategory = FXCollections.observableArrayList(
                    stockList.getStockList().stream()
                            .filter(stock -> categoryId.equals(stock.getShelfId()))
                            .collect(Collectors.toList())
            );
            itemTableView.setItems(itemsWithSameCategory);
            initTableView();
        }
        categoryIDLabel.setText(categoryId);
    }
    @FXML
    public void onBackClick() {
        try {
            com.github.saacsos.FXRouter.goTo("requisition");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onConfirmClick() {
        String selectedItemId = itemTableView.getSelectionModel().getSelectedItem().getItemId();
        if (selectedItemId != null && !selectedItemId.isEmpty()) {
            Stock selectedStock = stockList.findItemById(selectedItemId);

            if (selectedStock != null) {
                int requisitionAmount;
                try {
                    requisitionAmount = Integer.parseInt(amountField.getText());
                } catch (NumberFormatException e) {
                    // Handle the case where the input is not a valid number
                    showErrorAlert("Invalid Input", "Qty must be a valid number.");
                    return;
                }

                if (requisitionAmount <= 0) {
                    // Handle the case where the amount is negative or zero
                    showErrorAlert("Invalid Input", "Qty must be greater than 0.");
                } else if (requisitionAmount > selectedStock.getQty()) {
                    // Handle the case where the amount exceeds available stock
                    showErrorAlert("Insufficient Stock", "The requisition qty exceeds the available stock.");
                } else {
                    Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation");
                    confirmationAlert.setHeaderText("Are you sure to add this item?");
                    confirmationAlert.setContentText("Please confirm your action.");

                    ButtonType confirmButton = new ButtonType("Confirm");
                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

                    Optional<ButtonType> result = confirmationAlert.showAndWait();

                    if (result.isPresent() && result.get() == confirmButton) {
                        selectedStock.setQty(selectedStock.getQty() - requisitionAmount);

                        if (selectedStock.getQty() == 0) {
                            stockListDatasource.deleteData(selectedStock.getItemId());
                            stockList.getStockList().remove(selectedStock);

                            LocalDate now = LocalDate.now();
                            LocalDateTime localDateTime = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmyyyyMMdd");
                            String formattedTime = localDateTime.format(formatter);
                            historyList.addHistory(new History(user.getUserId(), selectedStock.getItemId(), now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), requisitionAmount, "REQ" + formattedTime, selectedStock.getUnit()));
                            historyListDatasource.insertData(historyList);
                        } else {
                            LocalDate now = LocalDate.now();
                            stockListDatasource.insertData(stockList);
                            String requisitionID = generateRequisitionID();
                            LocalDateTime localDateTime = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmyyyyMMdd");
                            String formattedTime = localDateTime.format(formatter);
                            historyList.addHistory(new History(user.getUserId(), selectedStock.getItemId(), now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), requisitionAmount, "REQ" + formattedTime, selectedStock.getUnit()));
                            historyListDatasource.insertData(historyList);
                        }


                        itemTableView.refresh();
                        amountField.clear();

                        showInformationAlert("Requisition Successful", "Requisition completed successfully.");
                    } else {
                        amountField.clear();

                        showWarningAlert("Requisition Canceled", "Requisition has been canceled.");
                    }
                }
            } else {
                showErrorAlert("Error", "Selected stock item is null.");
            }
        }
        loadData();
    }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showWarningAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }





}
