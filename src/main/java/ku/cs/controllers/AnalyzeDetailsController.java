package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.models.Analyze;
import ku.cs.models.AnalyzeList;
import ku.cs.services.AnalyzeDataSource;
import ku.cs.services.Datasource;

import java.io.IOException;

public class AnalyzeDetailsController {
    @FXML private Label shelfId;
    @FXML private Label userId;
    @FXML private Label userName;
    @FXML private Label text;

    private Analyze selectedAnalyze;

    @FXML
    private void initialize() {
        loadData();
        displayDetails();
    }

    private void loadData() {
        Datasource<AnalyzeList> analyzeListDatasource = new AnalyzeDataSource();
        AnalyzeList analyzeList = analyzeListDatasource.readData();

        // Assume you have a way to get the selected Analyze object
        selectedAnalyze = getSelectedAnalyze(analyzeList);
    }

    private void displayDetails() {
        // Populate the labels with the details of the selected analyze
        shelfId.setText(selectedAnalyze.getCategoryId());
        userId.setText(selectedAnalyze.getUserId());
        userName.setText(selectedAnalyze.getUserName());
        text.setText(selectedAnalyze.getTextAnalyze());
    }

    private Analyze getSelectedAnalyze(AnalyzeList analyzeList) {
        // Assume you have a way to get the selected Analyze object
        // Here, I'm just returning the first one as an example
        if (!analyzeList.getAnalyzes().isEmpty()) {
            return analyzeList.getAnalyzes().get(0);
        } else {
            return null;
        }
    }

    @FXML
    public void onBackClick() {
        try {
            com.github.saacsos.FXRouter.goTo("analyze");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
