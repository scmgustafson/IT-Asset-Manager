package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    private Stage stage;
    private Parent scene;

    @FXML
    private TextField fieldUserSearch;

    @FXML
    private TableView<?> tableUsers;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableColumn<?, ?> colFullName;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private TableColumn<?, ?> colDepartment;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnBack;

    @FXML
    private Label labelUIMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onKeyPressSearchUsers(KeyEvent event) {

    }

    @FXML
    void onActionDisplayAddUser(ActionEvent event) throws IOException {
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = FXMLLoader.load(getClass().getResource("/View/ViewAddUser.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDisplayEditUser(ActionEvent event) throws IOException {
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = FXMLLoader.load(getClass().getResource("/View/ViewEditUser.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteUser(ActionEvent event) {

    }

    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = FXMLLoader.load(getClass().getResource("/View/ViewSubMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
