package ku.cs.controllers;

import javafx.fxml.FXML;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;

import java.io.IOException;

public class CountStockController {
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;
    public void initialize(){
        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();

        String userName = (String) com.github.saacsos.FXRouter.getData();
        user = userList.findUserByUsername(userName);
    }
    @FXML
    public void onBackClick(){
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML public void onWareHouseCountClick(){
        try {
            com.github.saacsos.FXRouter.goTo("ware-house-count", user.getUserName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML public void onActualCountStockClick(){
        try {
            com.github.saacsos.FXRouter.goTo("actual-count-stock", user.getUserName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
