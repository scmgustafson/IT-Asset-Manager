package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEquipmentController implements Initializable {
    private Stage stage;
    private Parent scene;

    @FXML
    private TextField fieldId;

    @FXML
    private ComboBox<String> comboType;

    @FXML
    private TextField fieldModel;

    @FXML
    private TextField fieldSerial;

    @FXML
    private ComboBox<String> comboLocation;

    @FXML
    private TextField fieldUser;

    @FXML
    private TextField fieldQuantity;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

    @FXML
    void onActionCancel(ActionEvent event) {

    }
}
