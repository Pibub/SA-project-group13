package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.Analyze;
import ku.cs.models.AnalyzeList;
import ku.cs.services.AnalyzeDataSource;
import ku.cs.services.Datasource;

import java.io.IOException;

public class AnalyzeHistoryController {
    @FXML
    private TableView<Analyze> itemTableView;
    @FXML
    private TextField keywordsTextField;
    private FilteredList<Analyze> filteredList;

    @FXML
    private void initialize() {
        initTableView();
        loadData();

        itemTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Analyze selectedAnalyze = itemTableView.getSelectionModel().getSelectedItem();
                if (selectedAnalyze != null) {
                    try {
                        // Adjust the route and parameters accordingly
                        com.github.saacsos.FXRouter.goTo("analyzeDetails", selectedAnalyze.getCategoryId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void loadData() {
        Datasource<AnalyzeList> analyzeListDatasource = new AnalyzeDataSource();
        AnalyzeList analyzeList = analyzeListDatasource.readData();
        ObservableList<Analyze> analyzes = FXCollections.observableArrayList(analyzeList.getAnalyzes());
        filteredList = new FilteredList<>(analyzes, p -> true); // Initialize the filtered list

        itemTableView.setItems(filteredList);
    }

    private void initTableView() {
        TableColumn<Analyze, String> categoryIdColumn = new TableColumn<>("Category ID");
        categoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));

        TableColumn<Analyze, String> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<Analyze, String> userNameColumn = new TableColumn<>("User Name");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Analyze, String> textAnalyzeColumn = new TableColumn<>("Text Analyze");
        textAnalyzeColumn.setCellValueFactory(new PropertyValueFactory<>("textAnalyze"));

        itemTableView.getColumns().clear();
        itemTableView.getColumns().addAll(categoryIdColumn, userIdColumn, userNameColumn, textAnalyzeColumn);
    }

    @FXML
    public void onBackClick() {
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
