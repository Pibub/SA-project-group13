package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.models.*;
import ku.cs.services.ActualCountStockDataSource;
import ku.cs.services.AnalyzeDataSource;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;
import com.github.saacsos.FXRouter;


import java.io.IOException;
import java.util.Map;

public class NotifyController {
    @FXML private Label shelfIdLabel;
    @FXML private Label firstCountLabel;
    @FXML private Label secondCountLabel;
    @FXML private Label thirdCountLabel;
    @FXML private TextField notify;
    @FXML private TextField updateStock;

    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;

    private ActualCountStockList actualCountStockList;
    private ActualCountStockDataSource actualCountStockDataSource;
    private ActualCountStock actualCountStock;

    @FXML
    public void initialize() {
        actualCountStockDataSource = new ActualCountStockDataSource();
        actualCountStockList = actualCountStockDataSource.readData();

        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();

        // Retrieve the dataMap from FXRouter
        Map<String, String> dataMap = (Map<String, String>) com.github.saacsos.FXRouter.getData();

        // Extract shelfId and userId from the dataMap
        String shelfId = dataMap.get("shelfId");
        String userId = dataMap.get("userId");

        user = userList.findUserById(userId);

        actualCountStock = actualCountStockList.findActualCountStockByShelfId(shelfId);

        if (actualCountStock != null) {
            // Populate the labels with the data
            shelfIdLabel.setText(actualCountStock.getShelfId());
            firstCountLabel.setText(String.valueOf(actualCountStock.getFirstCount()));
            secondCountLabel.setText(String.valueOf(actualCountStock.getSecondCount()));
            thirdCountLabel.setText(String.valueOf(actualCountStock.getThirdCount()));
        }
    }

    @FXML
    public void onSummitButton() {
        String notificationText = notify.getText();

        if (actualCountStock != null) {
            String shelfId = actualCountStock.getShelfId();

            Analyze analyze = new Analyze(shelfId, user.getUserId(), user.getUserName(), notificationText);

            AnalyzeDataSource analyzeDataSource = new AnalyzeDataSource();
            AnalyzeList analyzeList = new AnalyzeList();
            analyzeList.addAnalyze(analyze);
            analyzeDataSource.insertData(analyzeList);

            // Optionally, clear the text field
            notify.clear();
        }
    }
    @FXML void onUpdateToStock() {
    }


    @FXML
    public void onBackClick() {
        try {
            com.github.saacsos.FXRouter.goTo("actual-count-stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

