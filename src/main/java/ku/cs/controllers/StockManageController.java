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
import java.time.LocalDate;


public class StockManageController {
    @FXML private TableView<Stock> itemTableView;
    @FXML private Label categoryIDLabel;
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

        String shelfId = (String) FXRouter.getData();

        if (shelfId != null) {
            ObservableList<Stock> itemsWithSameCategory = FXCollections.observableArrayList(
                    stockList.getStockList().stream()
                            .filter(stock -> shelfId.equals(stock.getShelfId()))
                            .collect(Collectors.toList())
            );
            itemTableView.setItems(itemsWithSameCategory);
            initTableView();
        }
        categoryIDLabel.setText(shelfId);
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

        TableColumn<Stock, String> shelfIdIdColumn = new TableColumn<>("SHELF_ID");
        shelfIdIdColumn.setCellValueFactory(new PropertyValueFactory<>("shelfId"));

        itemTableView.getColumns().setAll(idColumn, nameColumn, qtyColumn, unitColumn ,locationColumn, storageDateColumn, shelfIdIdColumn);
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
