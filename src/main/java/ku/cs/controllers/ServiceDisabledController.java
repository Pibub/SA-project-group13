package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class ServiceDisabledController {
    public void onBackButtonClick(){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

