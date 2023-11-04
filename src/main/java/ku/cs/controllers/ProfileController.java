package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.Datasource;
import ku.cs.services.UserDataSource;
import com.github.saacsos.FXRouter;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ProfileController {
    @FXML Label  idLabel;
    @FXML Label  nameLabel;
    @FXML Label  dobLabel;
    @FXML Label  roleLabel;
    @FXML Label  adressLabel;
    @FXML Label  sexLabel;
    @FXML Label  phoneLabel;
    @FXML Label  swdLabel;
    @FXML
    ImageView imageUserView;
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

        File imageFile = new File(user.getUserImage());
        imageUserView.setImage(new Image(imageFile.toURI().toString()));
    }

    @FXML
    public void onUploadImageUser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Node source = imageUserView; // You can use the ImageView node as the source
        File selectedFile = fileChooser.showOpenDialog(((Node) source).getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Copy the selected image file to the specified directory
                String destinationDirectory = "src/main/resources/ku/cs/user-image";
                String fileName = selectedFile.getName();
                File destination = new File(destinationDirectory, fileName);

                // Check if the destination directory exists, and create it if necessary
                File directory = new File(destinationDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Copy the file
                Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Update the user's image URL in the User object
                user.setUserImage("src/main/resources/ku/cs/user-image/" + fileName);

                // Update the user's image URL in the database using the insertData method
                userList.getUsers().clear(); // Clear the user list
                userList.addUser(user); // Add the updated user to the list
                userListDatasource.insertData(userList); // Update the user's information in the database

                // Update the ImageView to display the uploaded image
                imageUserView.setImage(new Image(destination.toURI().toString()));
            } catch (IOException e) {
                e.printStackTrace();
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

    @FXML
    public void onChangePasswordClick() {
        try {
            FXRouter.goTo("changePassword", user.getUserName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
