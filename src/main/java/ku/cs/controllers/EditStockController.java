package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class EditStockController {
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
