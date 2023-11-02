package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.github.saacsos.FXRouter;
import ku.cs.models.Stock;
import ku.cs.models.StockList;
import ku.cs.services.Datasource;
import ku.cs.services.StockDataSource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StockManageController {
    @FXML private TableView<Stock> itemTableView;
    private Datasource<StockList> stockListDatasource;
    private FilteredList<Stock> filteredList;
    private Stock stock;
    @FXML private Label categoryIDLabel;

    @FXML
    public void initialize() {
        loadData();
        initTableView();
    }

    public void loadData() {
        stockListDatasource = new StockDataSource();
        StockList stockList = stockListDatasource.readData();
        ObservableList<Stock> stockItems = FXCollections.observableArrayList(stockList.getStockList());

        // Retrieve the selected category ID from the parameters
        Map<String, Object> params = FXRouter.getParams();
        if (params != null && params.containsKey("categoryId")) {
            String selectedCategoryId = (String) params.get("categoryId");

            // Clear the existing items in the table view
            itemTableView.getItems().clear();

            // Filter items by the selected category ID
            filteredList = new FilteredList<>(stockItems.filtered(stock -> stock.getCategoryId().equals(selectedCategoryId)), p -> true);
            itemTableView.setItems(filteredList);
        } else {
            // If no category ID is provided, show all items
            itemTableView.setItems(stockItems);
        }
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

    @FXML
    public void onBackClick() {
        try {
            FXRouter.goTo("editstock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
