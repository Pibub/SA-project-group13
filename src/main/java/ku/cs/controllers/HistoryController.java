package ku.cs.controllers;

import java.io.IOException;

public class HistoryController {
    public void onBackButtonClick(){
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
