package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.Stock;
import ku.cs.models.StockList;
import ku.cs.services.Datasource;
import ku.cs.services.StockDataSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequisitionController {
    @FXML private TableView<Stock> itemTableView;
    private Datasource<StockList> stockListDatasource;
    private FilteredList<Stock> filteredList;
    @FXML
    private TextField keywordsTextField;

    private Map<String, Stock> stockMap = new HashMap<>();
    public void initialize(){
        loadData();
        initTableView();

        itemTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Stock selectedStock = itemTableView.getSelectionModel().getSelectedItem();
                if (selectedStock != null) {
                    try {
                        com.github.saacsos.FXRouter.goTo("requisition-manage", selectedStock.getShelfId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        keywordsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(stock -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return stock.getItemName().toLowerCase().contains(lowerCaseFilter)
                        || stock.getItemId().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(stock.getQty()).toLowerCase().contains(lowerCaseFilter)
                        || stock.getLocation().toLowerCase().contains(lowerCaseFilter)
                        || stock.getShelfId().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    public void loadData(){
        stockListDatasource = new StockDataSource();
        StockList stockList = stockListDatasource.readData();
        ObservableList<Stock> stockItems = FXCollections.observableArrayList(stockList.getStockList());

        // Group and sum items by category ID
        for (Stock stockItem : stockItems) {
            String categoryID = stockItem.getShelfId();
            if (stockMap.containsKey(categoryID)) {
                Stock existingItem = stockMap.get(categoryID);
                // Add the amount to the existing item
                existingItem.setQty(existingItem.getQty() + stockItem.getQty());
            } else {
                // Add the item to the map
                stockMap.put(categoryID, stockItem);
            }
        }

        filteredList = new FilteredList<>(FXCollections.observableArrayList(stockMap.values()), p -> true);
        itemTableView.setItems(filteredList);
    }
    private void initTableView() {
        TableColumn<Stock, String> idColumn = new TableColumn<>("SHELF_ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("shelfId"));

        TableColumn<Stock, String> nameColumn = new TableColumn<>("ITEM_NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Stock, String> qtyColumn = new TableColumn<>("QTY");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<Stock, String> unitColumn = new TableColumn<>("UNIT");
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));

        TableColumn<Stock, String> locationColumn = new TableColumn<>("LOCATION");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        itemTableView.getColumns().clear();
        itemTableView.getColumns().addAll(idColumn, nameColumn, qtyColumn, unitColumn, locationColumn);
    }
    @FXML
    public void onButtonClick() {
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
