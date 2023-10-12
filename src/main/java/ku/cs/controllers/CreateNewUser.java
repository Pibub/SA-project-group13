package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;

import java.io.IOException;
import java.time.LocalDate;

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

    @FXML public void addNewUser(){
        String id = idTextField.getText();
        String name = nameTextField.getText();
        String sex = sexTextField.getText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();
        String role = roleTextField.getText();
        String password = passwordTextField.getText();
        String dob = dobPicker.getValue().toString();
        String swd = swdPicker.getValue().toString();

        User user = userList.findUserByIdAndUsername(id, name);

        if (user != null ){
            warningLabel.setText("The username is already exist.");
            warningLabel.setStyle("-fx-text-fill: red;");
        }
        else if (!id.isEmpty() && !name.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !role.isEmpty() && !password.isEmpty()
        && !dob.isEmpty() && !swd.isEmpty()){
            userList.addNewUser(id, name, dob, sex, address, phone, swd, password, role);
            userListDatasource.insertData(userList);
            warningLabel.setText("Add new user complete");
            warningLabel.setStyle("-fx-text-fill: green");
        }
        if (id.isEmpty() && name.isEmpty() && address.isEmpty() && phone.isEmpty() && role.isEmpty() && password.isEmpty()){
            warningLabel.setText("Please fill out the information completely");
            warningLabel.setStyle("-fx-text-fill: red;");
        }

        else if (name.isEmpty()){
            warningLabel.setText("Please fill out the information completely");
            warningLabel.setStyle("-fx-text-fill: red;");
        }

        else if (address.isEmpty()){
            warningLabel.setText("Please fill out the information completely");
            warningLabel.setStyle("-fx-text-fill: red;");
        }
        else if (phone.isEmpty()){
            warningLabel.setText("Please fill out the information completely");
            warningLabel.setStyle("-fx-text-fill: red;");
        }
        else if (role.isEmpty()){
            warningLabel.setText("Please fill out the information completely");
            warningLabel.setStyle("-fx-text-fill: red;");
        }

        else if (password.isEmpty()){
            warningLabel.setText("Please fill out the information completely");
            warningLabel.setStyle("-fx-text-fill: red;");
        }

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
    }
}
