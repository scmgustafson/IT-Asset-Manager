package Controller;

import DAO.DAOComputers;
import DAO.DAOPeripherals;
import DAO.DAOViewingDevice;
import Model.Computer;
import Model.Peripheral;
import Model.User;
import Model.ViewingDevice;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SubMenuController implements Initializable {
    private Stage stage;
    private Parent scene;

    private User sentUser;

    private FXMLLoader loader;

    @FXML
    private Button btnInventory;

    @FXML
    private Button btnUserManagement;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnExit;

    @FXML
    private Label labelUserName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loader = new FXMLLoader();
    }

    @FXML
    void onActionDisplayEquipment(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/View/ViewEquipment.fxml"));
        loader.load();
        EquipmentController controller = loader.getController();
        controller.sendUser(sentUser);

        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDisplayUserManagement(ActionEvent event) throws IOException {
        if (sentUser.getType().toLowerCase(Locale.ROOT).equals("admin")) {
            loader.setLocation(getClass().getResource("/View/ViewUsers.fxml"));
            loader.load();
            UsersController controller = loader.getController();
            controller.sendUser(sentUser);

            stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You do not have the correct user permissions to access User Management!\n\nPlease contact your administrator for assistance.");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionDisplayReports(ActionEvent event) throws IOException {
        loader.setLocation(getClass().getResource("/View/ViewReports.fxml"));
        loader.load();
        ReportsController controller = loader.getController();
        controller.sendUser(sentUser);

        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = FXMLLoader.load(getClass().getResource("/View/ViewLoginMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void sendUser(User user) {
        this.sentUser = user;

        labelUserName.setText(sentUser.getFullName());
    }
}
