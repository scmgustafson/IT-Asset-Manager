package Controller;

import Model.Equipment;
import Model.User;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class EquipmentController implements Initializable {
    private Stage stage;
    private Parent scene;

    private User sentUser;
    private FXMLLoader loader;

    @FXML
    private ComboBox comboEquipmentType;

    @FXML
    private TextField fieldEquipmentSearch;

    @FXML
    private TableView<Equipment> tableEquipment;

    @FXML
    private TableColumn<Equipment, Integer> colEquipmentId;

    @FXML
    private TableColumn<Equipment, String> colType;

    @FXML
    private TableColumn<Equipment, String> colModel;

    @FXML
    private TableColumn<Equipment, String> colSerial;

    @FXML
    private TableColumn<Equipment, LocalDateTime> colEntryDateTime;

    @FXML
    private TableColumn<Equipment, String> colLocation;

    @FXML
    private TableColumn<Equipment, String> colUser;

    @FXML
    private TableColumn<String[], String> colExtra1;

    @FXML
    private TableColumn<String[], String> colExtra2;

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
        loader = new FXMLLoader();
        //Hide extra data columns on view start
        colExtra1.setVisible(false);
        colExtra2.setVisible(false);
        colLocation.setPrefWidth(240);
        colUser.setPrefWidth(240);
    }

    public void onActionEquipmentTypeSelect(ActionEvent event) {

    }

    public void onKeyPressSearchEquipment(KeyEvent keyEvent) {

    }

    @FXML
    void onActionDisplayAddEquipment(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/View/ViewAddEquipment.fxml"));
        loader.load();
        AddEquipmentController controller = loader.getController();
        controller.sendUser(sentUser);

        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDisplayEditEquipment(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/View/ViewEditEquipment.fxml"));
        loader.load();
        EditEquipmentController controller = loader.getController();
        controller.passUser(sentUser);

        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteEquipment(ActionEvent event) {

    }

    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/View/ViewSubMenu.fxml"));
        loader.load();
        SubMenuController controller = loader.getController();
        controller.sendUser(sentUser);

        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void sendUser(User user) {
        this.sentUser = user;
    }
}
