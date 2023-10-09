package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.github.saacsos.FXRouter;
import ku.cs.models.UserList;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;

import java.io.IOException;
import java.util.ArrayList;


public class LoginController {
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField usernameText;
    @FXML private TextField passwordText;

    private UserList userList;
    private Datasource<UserList> userListDatasource;

    public void initialize(){
        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();
        usernameLabel.setText("");
        passwordLabel.setText("");
    }
    public void onConfirmButtonClick(){
        if(userList.isCorrectPair(usernameText.getText() , passwordText.getText() )){
            try{
                FXRouter.goTo("home");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (usernameText.getText().equals("")){
            usernameLabel.setText("Please enter username.");
        }
        if (passwordText.getText().equals("")){
            passwordLabel.setText("Please enter password.");
        }
        else if (usernameText.getText().equals("") && passwordText.equals("")){
            usernameLabel.setText("Please enter username.");
            passwordLabel.setText("Please enter password.");
        }

        else{
            usernameLabel.setText("Username or password is incorrect , please try again.");
        }
    }

    public void onClearButtonClick(){
        usernameText.clear();
        passwordText.clear();
        usernameLabel.setText("");
        passwordLabel.setText("");
    }
}
