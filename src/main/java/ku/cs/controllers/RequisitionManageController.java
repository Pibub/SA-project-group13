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
    public void loadData() {
        stockListDatasource = new StockDataSource();
        stockList = stockListDatasource.readData();

        String categoryId = (String) com.github.saacsos.FXRouter.getData();

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

            if (selectedStock != null && selectedStock.getAmount() != 0) {
                int requisitionAmount = Integer.parseInt(amountField.getText());

                if (requisitionAmount <= selectedStock.getAmount()) {
                    // Create a new instance of HistoryList and HistoryDataSource
                    HistoryList historyList = new HistoryList();
                    HistoryDataSource historyListDatasource = new HistoryDataSource();

                    // Create a confirmation dialog
                    Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation");
                    confirmationAlert.setHeaderText("Are you sure to add this item?");
                    confirmationAlert.setContentText("Please confirm your action.");

                    // Show the confirmation dialog and wait for a response
                    ButtonType confirmButton = new ButtonType("Confirm");
                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

                    // Get the user's response
                    Optional<ButtonType> result = confirmationAlert.showAndWait();

                    if (result.isPresent() && result.get() == confirmButton) {
                        // User confirmed the action
                        selectedStock.setAmount(selectedStock.getAmount() - requisitionAmount);

                        // Check if the amount becomes 0 and delete the item from the database
                        if (selectedStock.getAmount() == 0) {
                            stockListDatasource.deleteData(selectedStock.getItemId());
                            stockList.getStockList().remove(selectedStock);
                        } else {
                            LocalDate now = LocalDate.now();
                            stockListDatasource.insertData(stockList);
                            String requisitionID = generateRequisitionID();
                            LocalDateTime localDateTime = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmyyyyMMdd");
                            String formattedTime = localDateTime.format(formatter);
                            historyList.addHistory(new History(user.getUserId(), selectedStock.getItemId(), now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), requisitionAmount, "REQ" + formattedTime));
                            historyListDatasource.insertData(historyList);
                        }

                        itemTableView.refresh();
                        amountField.clear();
                        errorLabel.setText("Requisition successful!");
                        errorLabel.setStyle("-fx-text-fill: green;");
                    } else {
                        // User canceled the action
                        errorLabel.setText("Requisition canceled.");
                        errorLabel.setStyle("-fx-text-fill: red;");
                        amountField.clear();
                    }
                } else {
                    errorLabel.setText("Error: Insufficient stock amount for requisition.");
                    errorLabel.setStyle("-fx-text-fill: red;");
                }
            } else {
                errorLabel.setText("Error: Please try again.");
                errorLabel.setStyle("-fx-text-fill: red;");
                amountField.clear();
            }
        }
        loadData();
    }



}
