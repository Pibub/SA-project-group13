package ku.cs.controllers;
import com.github.saacsos.FXRouter;
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
    public static class CurrentDateTime {
        public void DateTimeFormatter (String[] args) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
        }
    }
    public DateTimeFormatter getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf;
    }
}

