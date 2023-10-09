package ku.cs;

import javafx.application.Application;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        com.github.saacsos.FXRouter.bind(this, stage, "Warehouse", 600, 400);
        configRoute();
        FXRouter.goTo("login");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("home", packageStr + "hello-view.fxml");
        FXRouter.when("disable-service" , packageStr + "service-disabled.fxml");
        FXRouter.when("login" , packageStr + "login.fxml");

    }

    public static void main(String[] args) {
        launch();
    }
}