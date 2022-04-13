package Controller;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditEquipmentController implements Initializable {
    private Stage stage;
    private Parent scene;

    private User sentUser;
    private User passUser;
    private FXMLLoader loader;

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
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loader = new FXMLLoader();
    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/View/ViewEquipment.fxml"));
        loader.load();
        EquipmentController controller = loader.getController();
        controller.sendUser(passUser);

        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void passUser(User user) {
        this.passUser = user;
    }
}
