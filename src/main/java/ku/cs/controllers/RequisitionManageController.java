package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.Stock;
import ku.cs.models.StockList;
import ku.cs.services.Datasource;
import ku.cs.services.StockDataSource;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RequisitionManageController {
    @FXML private TableView<Stock> itemTableView;
    @FXML private Label categoryIDLabel;
    @FXML private TextField amountField;
    @FXML private Label errorLabel;
    private  Stock stock;
    private Datasource<StockList> stockListDatasource;
    private StockList stockList;
    @FXML
    public void initialize() {
        loadData();
        initTableView();
        errorLabel.setText("");
        stockListDatasource = new StockDataSource();
        stockList = stockListDatasource.readData();
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
    public void onConfirmClick(){
        AtomicInteger amount = new AtomicInteger(Integer.parseInt(amountField.getText()));
        itemTableView.setOnMouseClicked(event -> {
            Stock selectedStock = itemTableView.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 1 && selectedStock != null && selectedStock.getAmount()!= 0) {
                    selectedStock.setAmount(selectedStock.getAmount() - amount.get());
                    itemTableView.refresh();
                    amount.set(0);
                    amountField.clear();
                    errorLabel.setText("Requisition successful!");
                    errorLabel.setStyle("-fx-text-fill: green;");

            }
            else {
                errorLabel.setText("Error please try again.");
                errorLabel.setStyle("-fx-text-fill: red;");
                amountField.clear();
                amount.set(0);
                itemTableView.refresh();
            }
        });
    }
}
