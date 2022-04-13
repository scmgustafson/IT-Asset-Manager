package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EquipmentController implements Initializable {
    private Stage stage;
    private Parent scene;

    @FXML
    private TextField fieldEquipmentSearch;

    @FXML
    private TableView<?> tableEquipment;

    @FXML
    private TableColumn<?, ?> colEquipmentId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colSerial;

    @FXML
    private TableColumn<?, ?> colEntryDateTime;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colUser;

    @FXML
    private Label labelUIMessage;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onKeyPressSearchEquipment(javafx.scene.input.KeyEvent keyEvent) {

    }

    @FXML
    void onActionDisplayAddEquipment(ActionEvent event) {

    }

    @FXML
    void onActionDisplayEditEquipment(ActionEvent event) {

    }

    @FXML
    void onActionDeleteEquipment(ActionEvent event) {

    }

    @FXML
    void onActionExit(ActionEvent event) {
        //Close the application
        System.exit(0);
    }
}
