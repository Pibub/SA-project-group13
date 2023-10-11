package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;

import java.io.IOException;

public class ChangePasswordController {
    @FXML Label errorLabel;
    @FXML PasswordField currentPasswordField;
    @FXML PasswordField newPasswordField;
    @FXML PasswordField confirmNewPasswordField;
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    private User user;
    public void initialize(){
        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();
    }

    @FXML
    public void updatePassword(ActionEvent actionEvent){
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmNewPasswordField.getText();

        String userName = (String) FXRouter.getData();

        user = userList.findUser(userName, currentPassword);


        if (user != null && !currentPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty() && newPassword != confirmPassword){
            errorLabel.setText("Error password do not match");
            errorLabel.setStyle("-fx-text-fill: red;");
            currentPasswordField.clear();
            newPasswordField.clear();
            confirmNewPasswordField.clear();
        }

        if (!currentPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty()){
            if (!currentPassword.equals(newPassword) && newPassword.equals(confirmPassword)){
                user.setPassword(newPassword);

                userListDatasource.insertData(userList);
                errorLabel.setText("Update password complete");
                errorLabel.setStyle("-fx-text-fill: Green;");
                currentPasswordField.clear();
                newPasswordField.clear();
                confirmNewPasswordField.clear();
            }
        }
        else {
            if (currentPassword == ""){
                errorLabel.setText("Please enter current password");
                errorLabel.setStyle("-fx-text-fill: red;");
                currentPasswordField.clear();
                newPasswordField.clear();
                confirmNewPasswordField.clear();
            }
            if (confirmPassword == ""){
                errorLabel.setText("Please enter confirm new password");
                errorLabel.setStyle("-fx-text-fill: red;");
                currentPasswordField.clear();
                newPasswordField.clear();
                confirmNewPasswordField.clear();
            }
            if (newPassword == ""){
                errorLabel.setText("Please enter new password");
                errorLabel.setStyle("-fx-text-fill: red;");
                currentPasswordField.clear();
                newPasswordField.clear();
                confirmNewPasswordField.clear();
            }
        }
    }

    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
