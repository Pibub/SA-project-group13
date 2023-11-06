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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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
    public void updatePassword(ActionEvent actionEvent) {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmNewPasswordField.getText();

        String userName = (String) FXRouter.getData();

        user = userList.findUser(userName, currentPassword);

        if (user != null && !currentPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty() && newPassword.equals(confirmPassword)) {
            // Display a confirmation dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to change the password?");
            alert.setContentText("Click OK to proceed, or Cancel to abort.");

            // Show the dialog and wait for the user's response
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                // User confirmed, proceed with password update
                user.setPassword(newPassword);
                userListDatasource.insertData(userList);
                errorLabel.setText("Update password complete");
                errorLabel.setStyle("-fx-text-fill: Green;");
                currentPasswordField.clear();
                newPasswordField.clear();
                confirmNewPasswordField.clear();
            } else {
                // User canceled, do nothing
            }
        } else {
            // Error handling for different cases
            if (user == null) {
                errorLabel.setText("Current password is incorrect");
                errorLabel.setStyle("-fx-text-fill: red;");
            } else if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                errorLabel.setText("Please enter both new and confirmation passwords");
                errorLabel.setStyle("-fx-text-fill: red;");
            } else if (!newPassword.equals(confirmPassword)) {
                errorLabel.setText("New password and confirmation password do not match");
                errorLabel.setStyle("-fx-text-fill: red;");
            }

            // Clear the input fields
            currentPasswordField.clear();
            newPasswordField.clear();
            confirmNewPasswordField.clear();
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
