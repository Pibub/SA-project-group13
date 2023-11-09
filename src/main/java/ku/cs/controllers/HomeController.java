package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import com.github.saacsos.FXRouter;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;

import java.io.IOException;
import java.time.LocalDate;

public class HomeController {
    @FXML private Label headerLabel;

    @FXML private  Label textLabel;
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;

    public HomeController() {
    }

    public void initialize(){
        LocalDate now = LocalDate.now();
        LocalDate closeSystem = LocalDate.of(2023 , 11 , 7);
        LocalDate closeSystemFinalDay = LocalDate.of(2023 , 12 , 7);
        LocalDate closeSystemFirstHalf = LocalDate.of(2023 , 6 , 6);
        LocalDate closeSystemFinalFirstHalf = LocalDate.of(2023 , 6 , 7);
        if(now.isBefore(closeSystemFirstHalf)){
            headerLabel.setText("System Disabling Announcement");
            textLabel.setText("system is going to disable in 6th June to 7th June.");
        }if(now.isBefore(closeSystem) && now.isAfter(closeSystemFirstHalf)){
            headerLabel.setText("System Disabling Announcement");
            textLabel.setText("system is going to disable in 6th December to 7th December.");
        }else {
            headerLabel.setText("");
            textLabel.setText("");
        }

        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();

        String userName = (String) FXRouter.getData();
        user = userList.findUserByUsername(userName);
    }


    @FXML
    protected void onProfileClick() {
        try {
            FXRouter.goTo("profile", user.getUserName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onLogoutClick() {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onEditStock() {
        // Check if the user's role is "admin" or "warehouse"
        if ("admin".equals(user.getUserRole()) || "warehouse".equals(user.getUserRole())) {
            try {
                FXRouter.goTo("editstock");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAccessDeniedAlert();
        }
    }

    @FXML
    public void onCreateNewUserClick() {
        // Check if the user's role is "admin"
        if ("admin".equals(user.getUserRole())) {
            try {
                FXRouter.goTo("createNewUser");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAccessDeniedAlert();
        }
    }
    @FXML
    public void onReceivingDataClick() {
        // Check if the user's role is "admin" or "warehouse"
        if ("admin".equals(user.getUserRole()) || "warehouse".equals(user.getUserRole())) {
            try {
                FXRouter.goTo("receivingData");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAccessDeniedAlert();
        }
    }

    @FXML
    public void onRequisitionClick(){
        try {
            FXRouter.goTo("requisition");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onCountStockClick() {
        // Check if the user's role is "admin" or "warehouse"
        if ("admin".equals(user.getUserRole()) || "warehouse".equals(user.getUserRole())) {
            try {
                FXRouter.goTo("count-stock", user.getUserName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAccessDeniedAlert();
        }
    }

    @FXML
    public void onHistoryButtonClick() {
        // Check if the user's role is "admin" or "warehouse"
        if ("admin".equals(user.getUserRole()) || "warehouse".equals(user.getUserRole())) {
            try {
                FXRouter.goTo("history");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAccessDeniedAlert();
        }
    }
    private void showAccessDeniedAlert() {
        Alert accessDeniedAlert = new Alert(Alert.AlertType.ERROR);
        accessDeniedAlert.setTitle("Access Denied");
        accessDeniedAlert.setHeaderText("Access Denied");
        accessDeniedAlert.setContentText("You do not have permission to access this page.");

        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        accessDeniedAlert.getButtonTypes().setAll(closeButton);

        accessDeniedAlert.showAndWait();
    }

    @FXML
    public void onAnalyzeHistoryClick(){
        try {
            FXRouter.goTo("analyze");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}