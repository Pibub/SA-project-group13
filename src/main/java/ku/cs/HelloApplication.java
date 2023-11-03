package ku.cs;

import javafx.application.Application;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;
import ku.cs.controllers.ServiceDisabledController;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        com.github.saacsos.FXRouter.bind(this, stage, "Warehouse", 800, 600);
        configRoute();
        FXRouter.goTo("login");
    }


    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("home", packageStr + "home.fxml");
        FXRouter.when("disable-service" , packageStr + "service-disabled.fxml");
        FXRouter.when("login" , packageStr + "login.fxml");
        FXRouter.when("profile" , packageStr + "profile.fxml");
        FXRouter.when("editstock" ,packageStr+ "editstock.fxml");
        FXRouter.when("changePassword" ,packageStr+ "change-password.fxml");
        FXRouter.when("createNewUser" ,packageStr+ "create-new-user.fxml");
        FXRouter.when("receivingData" ,packageStr+ "receiving-data.fxml");
        FXRouter.when("invoiceManage" ,packageStr+ "invoice-manage.fxml");
        FXRouter.when("stockManage" ,packageStr+ "stock-manage.fxml");
        FXRouter.when("requisition" , packageStr + "requisition.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}