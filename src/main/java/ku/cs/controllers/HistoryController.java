package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.History;
import ku.cs.models.HistoryList;
import ku.cs.models.Stock;
import ku.cs.services.Datasource;
import ku.cs.services.HistoryDataSource;
import ku.cs.services.StockDataSource;
import ku.cs.services.UserDataSource;

import java.io.IOException;
import java.util.stream.Collectors;

public class HistoryController {
    @FXML
    private TableView<History> itemTableView;
    private Datasource<HistoryList> historyListDatasource;
    private HistoryList historyList;
    @FXML
    public void initialize() {
        loadData();
        initTableView();
        historyList = new HistoryList();
        historyListDatasource = new HistoryDataSource();
    }
    private void initTableView() {
        TableColumn<History, String> userIdColumn = new TableColumn<>("USER_ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<History, String> itemIdColumn = new TableColumn<>("ITEM_ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<History, String> dateColumn = new TableColumn<>("DATE");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<History, Integer> amountColumn = new TableColumn<>("AMOUNT");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<History, String> requisitionIdColumn = new TableColumn<>("REQUISITION_ID");
        requisitionIdColumn.setCellValueFactory(new PropertyValueFactory<>("requisitionId"));




        itemTableView.getColumns().setAll(userIdColumn, itemIdColumn, amountColumn, dateColumn, amountColumn, requisitionIdColumn);
    }
    public void loadData() {
        historyListDatasource = new HistoryDataSource();
        historyList = historyListDatasource.readData();

        String categoryId = (String) com.github.saacsos.FXRouter.getData();

        if (categoryId != null) {
            ObservableList<History> itemsWithSameCategory = FXCollections.observableArrayList(
                    historyList.getHistories().stream()
                            .filter(history -> categoryId.equals(history.getItemId()))
                            .collect(Collectors.toList())
            );
            itemTableView.setItems(itemsWithSameCategory);
            initTableView();
        }
    }
    public void onBackButtonClick(){
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
