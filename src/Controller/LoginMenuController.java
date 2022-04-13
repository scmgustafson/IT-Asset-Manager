package Controller;

import DAO.DAOUsers;
import Model.User;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginMenuController implements Initializable {
    private Stage stage;
    private Parent scene;

    private FXMLLoader loader;

    @FXML
    private TextField fieldUsername;

    @FXML
    private TextField fieldPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Prepare loader to pass User data
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ViewSubMenu.fxml"));
        try {
            loader.load();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        //Prepare alert object
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Credential Error");
        //Declare variables for input validation
        String enteredUser = "";
        String enteredPass = "";
        //Begin input validation loop
        boolean loopFlag = true;
        while (loopFlag) {
            //Read in input values of user credentials
            enteredUser = fieldUsername.getText();
            enteredPass = fieldPassword.getText();
            //Check for blanks
            if (enteredUser.isBlank() || enteredPass.isBlank()) {
                alert.setContentText("Username or password field cannot be blank");
                alert.showAndWait();
                loopFlag = false;
                break;
            }
            //Get list of users from DB and create a list of usernames
            ObservableList<User> allUsers = FXCollections.observableArrayList();
            try {
                allUsers = DAOUsers.selectAllUsers();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            List<String> usernames = new ArrayList<String>();
            for (User user : allUsers) {
                usernames.add(user.getUsername());
            }
            //Check if entered username matches an existing user
            if (usernames.contains(enteredUser)) {
                //Check entered credentials for validity, displaying an error message if invalid
                for (User user : allUsers) {
                    if (enteredUser.equals(user.getUsername())) {
                        if (enteredPass.equals(user.getPassword())) {
                            //Send User object to next screen
                            SubMenuController controller = loader.getController();
                            controller.sendUser(user);
                            //Change view to SubMenu view
                            stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
                            scene = loader.getRoot();
                            stage.setScene(new Scene(scene));
                            stage.show();

                            loopFlag = false;
                            break;
                        }
                        else {
                            alert.setContentText("Username or password is incorrect.");
                            alert.showAndWait();
                            loopFlag = false;
                            break;
                        }
                    }
                }
            }
            else {
                alert.setContentText("Username or password is incorrect.");
                alert.showAndWait();
                loopFlag = false;
                break;
            }
            loopFlag = false;
        }
    }

    @FXML
    void onActionExit(ActionEvent event) {
        //Close the application
        JDBC.closeConnection();
        System.exit(0);
    }
}
