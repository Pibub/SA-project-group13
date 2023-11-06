package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class CreateNewUser {

    @FXML TextField idTextField;
    @FXML TextField nameTextField;
    @FXML TextField sexTextField;
    @FXML TextField addressTextField;
    @FXML TextField phoneTextField;
    @FXML TextField roleTextField;
    @FXML PasswordField passwordTextField;
    @FXML DatePicker swdPicker;
    @FXML DatePicker dobPicker;
    @FXML Label warningLabel;
    private UserList userList;
    private Datasource<UserList> userListDatasource;
    public void initialize(){
        userListDatasource = new UserDataSource();
        userList = userListDatasource.readData();

        dobPicker.setValue(LocalDate.now());
        swdPicker.setValue(LocalDate.now());
    }
    @FXML
    public void onBackButtonClick() {
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void addNewUser() {
        String id = idTextField.getText();
        String name = nameTextField.getText();
        String sex = sexTextField.getText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();
        String role = roleTextField.getText();
        String password = passwordTextField.getText();
        String dob = dobPicker.getValue().toString();
        String swd = swdPicker.getValue().toString();
        String defaultImage = "src/main/resources/ku/cs/image/user.png";

        if (id.isEmpty() || name.isEmpty() || sex.isEmpty() || address.isEmpty() || phone.isEmpty() || role.isEmpty() || password.isEmpty() || dob.isEmpty() || swd.isEmpty()) {
            // Some fields are empty, show an Alert
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText("Please fill out all the information fields.");
            alert.showAndWait();
        } else {
            User user = userList.findUserByIdAndUsername(id, name);

            // Create a confirmation dialog
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Are you sure to add this new user?");
            confirmationAlert.setContentText("Please confirm your action.");

            // Show the confirmation dialog and wait for a response
            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

            // Get the user's response
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == confirmButton) {
                if (user != null) {
                    warningLabel.setText("The username is already exist.");
                    warningLabel.setStyle("-fx-text-fill: red;");
                } else {
                    userList.addNewUser(id, name, dob, sex, address, phone, swd, password, role, defaultImage);
                    userListDatasource.insertData(userList);
                    warningLabel.setText("Add new user complete");
                    warningLabel.setStyle("-fx-text-fill: green");
                }

                // Clear the input fields and set default values
                idTextField.clear();
                nameTextField.clear();
                sexTextField.clear();
                addressTextField.clear();
                phoneTextField.clear();
                roleTextField.clear();
                passwordTextField.clear();
                dobPicker.getEditor().clear();
                dobPicker.setValue(null);
                swdPicker.getEditor().clear();
                swdPicker.setValue(null);
                dobPicker.setValue(LocalDate.now());
                swdPicker.setValue(LocalDate.now());
            } else {
                // User canceled the action
                warningLabel.setText("Action canceled");
            }
        }
    }

}
