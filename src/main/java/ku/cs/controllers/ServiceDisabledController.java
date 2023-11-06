package ku.cs.controllers;
import com.github.saacsos.FXRouter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
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

