package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.github.saacsos.FXRouter;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;

import java.io.IOException;

public class HomeController {
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;
    public void initialize(){
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
        try {
            FXRouter.goTo("editstock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onCreateNewUserClick() {
        try {
            FXRouter.goTo("createNewUser");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onReceivingDataClick() {
        try {
            FXRouter.goTo("receivingData");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    @FXML public void onCountStockClick(){
        try {
            FXRouter.goTo("count-stock", user.getUserName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}