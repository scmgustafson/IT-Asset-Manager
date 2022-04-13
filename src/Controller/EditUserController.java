package Controller;

import DAO.DAOUsers;
import Model.User;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {
    private Stage stage;
    private Parent scene;

    private User sentUser;

    @FXML
    private TextField fieldId;

    @FXML
    private TextField fieldFullName;

    @FXML
    private TextField fieldUsername;

    @FXML
    private TextField fieldPassword;

    @FXML
    private ComboBox<String> comboUserType;

    @FXML
    private ComboBox<String> comboDepartment;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populate combo boxes
        ObservableList<String> userTypes = FXCollections.observableArrayList();
        userTypes.add("Standard");
        userTypes.add("Admin");
        comboUserType.setItems(userTypes);

        ObservableList<String> departments = FXCollections.observableArrayList();
        departments.add("IT");
        departments.add("Accounting");
        departments.add("Executives");
        comboDepartment.setItems(departments);
    }

    @FXML
    void onActionSave(ActionEvent event) {
        //Prepare field validation alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Add User Error");

        //Prepare list of all users for validation
        ObservableList<User> allUsers = DAOUsers.selectAllUsers();

        //Initialize variables for object creation
        int userId = Integer.parseInt(fieldId.getText());
        String fullname = "";
        String username = "";
        String password = "";
        String type = "";
        String department = "";

        //Read in field inputs for validation
        String nameInput = fieldFullName.getText();
        String usernameInput = fieldUsername.getText();
        String passwordInput = fieldPassword.getText();
        String typeInput = comboUserType.getSelectionModel().getSelectedItem();
        String departmentInput = comboDepartment.getSelectionModel().getSelectedItem();

        //Begin field validation loop
        boolean loopFlag = true;
        while (loopFlag) {
            //Check for blank fields
            if (nameInput.isBlank() || usernameInput.isBlank() || passwordInput.isBlank() || typeInput.isBlank() || departmentInput.isBlank()) {
                alert.setContentText("All fields must be filled out.");
                alert.showAndWait();
                loopFlag = false;
                break;
            }
            //Check name for numbers or other characters
            if (!nameInput.matches("[a-zA-Z\\s]+")) {
                alert.setContentText("Full Name may not contain any numbers or special characters.");
                alert.showAndWait();
                loopFlag = false;
                break;
            }
            //Check name for numbers or other characters
            if (!usernameInput.matches("[a-zA-Z\\d]+")) {
                alert.setContentText("Username may not contain any special characters.");
                alert.showAndWait();
                loopFlag = false;
                break;
            }
            //Check that username is unique
            boolean isUnique = true;
            for (User user : allUsers) {
                if (usernameInput.equals(user.getUsername()) && userId != user.getUserId()) {
                    isUnique = false;
                }
            }
            if (!isUnique) {
                alert.setContentText("A user with that username already exists. Please choose a unique username.");
                alert.showAndWait();
                loopFlag = false;
                break;
            }
            //If passing, create new User object and add it to the database
            fullname = nameInput;
            username = usernameInput;
            password = passwordInput;
            type = typeInput;
            department = departmentInput;

            User user = new User(userId, fullname, username, password, type, department);
            try {
                DAOUsers.update(user);
                //Break out of loop and move to previous screen
                stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
                scene = FXMLLoader.load(getClass().getResource("/View/ViewUsers.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                loopFlag = false;
                break;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            loopFlag = false;
        }
    }


    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = FXMLLoader.load(getClass().getResource("/View/ViewUsers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void sendUser(User user) {
        this.sentUser = user;

        //Set text fields with User data
        fieldId.setText(String.valueOf(sentUser.getUserId()));
        fieldFullName.setText(sentUser.getFullName());
        fieldUsername.setText(sentUser.getUsername());
        fieldPassword.setText(sentUser.getPassword());

        //Set combo boxes with User data
        ObservableList<String> userTypes = FXCollections.observableArrayList();
        userTypes.add("Standard");
        userTypes.add("Admin");
        comboUserType.getSelectionModel().select(userTypes.indexOf(sentUser.getType()));
        ObservableList<String> departments = FXCollections.observableArrayList();
        departments.add("IT");
        departments.add("Accounting");
        departments.add("Executives");
        comboDepartment.getSelectionModel().select(departments.indexOf(sentUser.getDepartment()));
    }
}
