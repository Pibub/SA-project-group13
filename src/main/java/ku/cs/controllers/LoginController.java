package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.github.saacsos.FXRouter;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


public class LoginController {
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField usernameText;
    @FXML private TextField passwordText;

    private User user;

    private UserList userList;
    private Datasource<UserList> userListDatasource;

    public void initialize(){
        System.currentTimeMillis();
        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();
        usernameLabel.setText("");
        passwordLabel.setText("");
    }
    public void onConfirmButtonClick(){
        String username = usernameText.getText();
        String password = passwordText.getText();
        User user = userList.findUser(username, password);
        LocalDate now = LocalDate.now();
        LocalDate closeSystem = LocalDate.of(2023 , 12 , 6);
        LocalDate closeSystemFinalDay = LocalDate.of(2023 , 12 , 7);
        LocalDate closeSystemFirstHalf = LocalDate.of(2023 , 6 , 6);
        LocalDate closeSystemFinalFirstHalf = LocalDate.of(2023 , 6 , 7);
        if(user != null){
            try{
                if(now.equals(closeSystem) && !Objects.equals(user.getUserRole(), "admin") || now.equals(closeSystem) && !Objects.equals(user.getUserRole(), "Inputter")
                        ||now.equals(closeSystemFinalDay) && !Objects.equals(user.getUserRole(), "admin") || now.equals(closeSystemFinalDay) && !Objects.equals(user.getUserRole(), "Inputter")
                        || now.equals(closeSystem) && !Objects.equals(user.getUserRole(), "warehouse data")
                        ||now.equals(closeSystemFinalDay) && !Objects.equals(user.getUserRole(), "warehouse data") ||
                        now.equals(closeSystemFirstHalf) && !Objects.equals(user.getUserRole(), "admin") || now.equals(closeSystemFirstHalf) && !Objects.equals(user.getUserRole(), "Inputter")
                        ||now.equals(closeSystemFinalFirstHalf) && !Objects.equals(user.getUserRole(), "admin") || now.equals(closeSystemFinalFirstHalf) && !Objects.equals(user.getUserRole(), "Inputter")
                        || now.equals(closeSystemFirstHalf) && !Objects.equals(user.getUserRole(), "warehouse data")
                        ||now.equals(closeSystemFinalFirstHalf) && !Objects.equals(user.getUserRole(), "warehouse data")){
                    FXRouter.goTo("disable-service");
                }
                else {
                    FXRouter.goTo("home", user.getUserName());
                }
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

    @FXML
    public void onBypassClick() throws IOException {
        FXRouter.goTo("home", "Joe");
    }

}
