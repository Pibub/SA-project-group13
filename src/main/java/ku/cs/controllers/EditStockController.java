package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class EditStockController {
    public TableView dataTableField;
    public TableColumn idTable;
    public TableColumn nameTable;
    public TableColumn amountTable;
    public TableColumn dateTable;
    public TableColumn locationTavble;
    public TextField itemField;
    public TextField itemNameField;
    public TextField amountField;
    public TextField storageDateField;
    public TextField locationField;
    @FXML
    Label warningLabel;
    @FXML
    public void onButtonClick() {
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void saveButton() {
            warningLabel.setText("Success");
    }

    @FXML
    public void editButton() {
            warningLabel.setText("Success");

    }

    @FXML
    public void deleteButton() {

            warningLabel.setText("Success");
    }
}
