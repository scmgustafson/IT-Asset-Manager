package Controller;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    private Stage stage;
    private Parent scene;

    private User sentUser;
    private FXMLLoader loader;

    @FXML
    private ComboBox<?> comboType;

    @FXML
    private ComboBox<?> comboLocation;

    @FXML
    private TextField fieldResults;

    @FXML
    private ComboBox<?> comboUser;

    @FXML
    private TableView<?> tableUserInventory;

    @FXML
    private TableColumn<?, ?> colEquipmentId;

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
    private Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loader = new FXMLLoader();

    }

    @FXML
    void onActionTypeLocationSelect(ActionEvent event) {

    }

    @FXML
    void onActionUserSelect(ActionEvent event) {

    }

    @FXML
    void onActionDisplaySubMenu(ActionEvent event) throws IOException {
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
