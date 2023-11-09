package ku.cs;

import javafx.application.Application;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;
import ku.cs.controllers.ServiceDisabledController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        LocalDate now = LocalDate.now();
        LocalDate closeSystem = LocalDate.of(2023 , 6 , 11);
            com.github.saacsos.FXRouter.bind(this, stage, "Warehouse", 1000, 800);
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
        FXRouter.when("requisition-manage" , packageStr + "requisition-manage.fxml");
        FXRouter.when("count-stock" , packageStr + "count-stock.fxml");
        FXRouter.when("ware-house-count" , packageStr + "ware-house-count.fxml");
        FXRouter.when("actual-count-stock" , packageStr + "actual-count-stock.fxml");
        FXRouter.when("history" , packageStr + "history.fxml");
        FXRouter.when("notify" , packageStr + "notify.fxml");
        FXRouter.when("analyze" , packageStr + "analyze.fxml");
        FXRouter.when("analyzeDetails" , packageStr + "analyzeDetails.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}