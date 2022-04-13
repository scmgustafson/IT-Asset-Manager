package Controller;

import DAO.DAOComputers;
import DAO.DAOPeripherals;
import DAO.DAOUsers;
import DAO.DAOViewingDevice;
import Model.Computer;
import Model.Peripheral;
import Model.User;
import Model.ViewingDevice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    private Stage stage;
    private Parent scene;

    @FXML
    private TextField fieldUserSearch;

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Integer> colUserId;

    @FXML
    private TableColumn<User, String> colFullName;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colDepartment;

    @FXML
    private TableColumn<User, String> colType;

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
        //Hide UI feedback message
        labelUIMessage.setVisible(false);
        //Populate User TableView with User objects from the database
        try {
            tableUsers.setItems(DAOUsers.selectAllUsers());
            colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onKeyPressSearchUsers(KeyEvent event) {
        //Only run search logic if Enter key pressed
        if (event.getCode() == KeyCode.ENTER) {
            //Refresh table so that search is only run on the entire User list
            refreshTable();
            //Get search text
            String search = fieldUserSearch.getText().toLowerCase(Locale.ROOT);
            //Determine if using is searching by User ID or by full name
            try {
                //Search for user by ID, highlighting it in the TableView if found
                int searchId = Integer.parseInt(search);
                ObservableList<User> foundUsers = FXCollections.observableArrayList();
                for (User user : DAOUsers.selectAllUsers()) {
                    if (user.getUserId() == searchId) {
                        foundUsers.add(user);
                    }
                }
                if (foundUsers.size() > 0) {
                    tableUsers.setItems(foundUsers);
                }
                else {
                    refreshTable();
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No matching user found!");
                    alert.setTitle("Search Message");
                    alert.showAndWait();
                }
            }
            catch (Exception e){
                //Search for user by Full Name, adding it to the filtered list if containing search string
                ObservableList<User> filteredUsers = FXCollections.observableArrayList();
                for (User user : DAOUsers.selectAllUsers()) {
                    if (user.getFullName().toLowerCase(Locale.ROOT).contains(search)) {
                        filteredUsers.add(user);
                    }
                }
                //Check to see if there is no match found
                if (filteredUsers.size() > 0) {
                    //Check if there is only 1 match or multiple matches
                    tableUsers.setItems(filteredUsers);
                }
                else {
                    refreshTable();
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No matching user found!");
                    alert.setTitle("Search Message");
                    alert.showAndWait();
                }
            }
        }
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
        //Prepare alert object
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Delete User Error");

        //Attempt to delete selected user
        User selectedUser = tableUsers.getSelectionModel().getSelectedItem();
        //Check that a user is selected
        if (selectedUser == null) {
            alert.setContentText("A user is not selected in the user table!");
            alert.showAndWait();
        }
        else {
            //Check that the selected user does not have any associated equipment entries, if so - prevent deletion
            ObservableList<Computer> associatedComputers = DAOComputers.selectAllComputersByUser(selectedUser);
            ObservableList<Peripheral> associatedPeripherals = DAOPeripherals.selectAllPeripheralsByUser(selectedUser);
            ObservableList<ViewingDevice> associatedViewingDevices = DAOViewingDevice.selectAllViewingDevicesByUser(selectedUser);
            if (associatedComputers.size() > 0 || associatedPeripherals.size() > 0 || associatedViewingDevices.size() > 0) {
                alert.setContentText("The selected user is associated with at least one equipment entry. Please delete any associated equipment entries and then try again.");
                alert.showAndWait();
            }
            else {
                //Delete the specified object and refresh the TableView
                DAOUsers.delete(selectedUser);
                refreshTable();
            }
        }
    }

    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = FXMLLoader.load(getClass().getResource("/View/ViewSubMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void refreshTable() {
        tableUsers.setItems(DAOUsers.selectAllUsers());
    }
}
