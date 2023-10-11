package ku.cs;

import javafx.application.Application;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        com.github.saacsos.FXRouter.bind(this, stage, "Warehouse", 800, 600);
        configRoute();
        FXRouter.goTo("home");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("home", packageStr + "home.fxml");
        FXRouter.when("disable-service" , packageStr + "service-disabled.fxml");
        FXRouter.when("login" , packageStr + "login.fxml");
        FXRouter.when("profile" , packageStr + "profile.fxml");
        FXRouter.when("editstock" ,packageStr+ "editstock.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}