package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private ListView stockListView;
    @FXML
    public void onButtonClick() {
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
