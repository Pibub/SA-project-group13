package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class ProfileController {
    @FXML Label  idLabel;
    @FXML Label  nameLabel;
    @FXML Label  dobLabel;
    @FXML Label  roleLabel;
    @FXML Label  adressLabel;
    @FXML Label  sexLabel;
    @FXML Label  phoneLabel;
    @FXML Label  swdLabel;
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;
    public void initialize(){
        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();

        String userName = (String) FXRouter.getData();
        user = userList.findUserByUsername(userName);

        idLabel.setText(user.getUserId());
        nameLabel.setText(user.getUserName());
        dobLabel.setText(user.getBirthDate());
        roleLabel.setText(user.getUserRole());
        adressLabel.setText(user.getAddress());
        sexLabel.setText(user.getSex());
        phoneLabel.setText(user.getTel());
        swdLabel.setText(user.getWorkDate());
    }

    @FXML
    public void OnBackButtonClick() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
