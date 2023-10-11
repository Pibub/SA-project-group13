package ku.cs.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

public class CreateNewUser {
    @FXML
    public void onBackButtonClick() {
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
