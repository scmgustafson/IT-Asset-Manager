package Controller;

import DAO.DAOComputers;
import DAO.DAOPeripherals;
import DAO.DAOUsers;
import DAO.DAOViewingDevice;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    private Stage stage;
    private Parent scene;

    private User sentUser;
    private FXMLLoader loader;

    @FXML
    private Label labelComputerCount;

    @FXML
    private Label labelPeripheralCount;

    @FXML
    private Label labelViewingDevicesCount;

    @FXML
    private ComboBox<String> comboType;

    @FXML
    private ComboBox<String> comboLocation;

    @FXML
    private TextField fieldResults;

    @FXML
    private ComboBox<User> comboUser;

    @FXML
    private TableView<Equipment> tableUserInventory;

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
    private Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loader = new FXMLLoader();

        //Set current amounts
        ObservableList<Computer> allComputers = DAOComputers.selectAllComputers();
        ObservableList<Peripheral> allPeripherals = DAOPeripherals.selectAllPeripherals();
        ObservableList<ViewingDevice> allViewingDevices = DAOViewingDevice.selectAllViewingDevices();
        labelComputerCount.setText(String.valueOf(allComputers.size()));
        labelPeripheralCount.setText(String.valueOf(allPeripherals.size()));
        labelViewingDevicesCount.setText(String.valueOf(allViewingDevices.size()));

        //Populate Combo Boxes
        ObservableList<String> equipmentTypes = FXCollections.observableArrayList();
        equipmentTypes.add("Computer");
        equipmentTypes.add("Peripheral");
        equipmentTypes.add("Viewing Device");
        comboType.setItems(equipmentTypes);

        ObservableList<String> locations = FXCollections.observableArrayList();
        locations.add("San Jose");
        locations.add("New York");
        locations.add("Austin");
        locations.add("Seattle");
        comboLocation.setItems(locations);

        ObservableList<User> users = DAOUsers.selectAllUsers();
        comboUser.setItems(users);

    }

    @FXML
    void onActionTypeLocationSelect(ActionEvent event) {
        String selectedType = comboType.getSelectionModel().getSelectedItem();
        String selectedLocation = comboLocation.getSelectionModel().getSelectedItem();

        if (selectedType == null || selectedLocation == null) {
            return;
        }
        else {
            if (selectedType.equals("Computer")) {
                fieldResults.setText(String.valueOf(DAOComputers.selectAllComputersByLocation(selectedLocation).size()));
            }
            else if (selectedType.equals("Peripheral")) {
                fieldResults.setText(String.valueOf(DAOPeripherals.selectAllPeripheralsByLocation(selectedLocation).size()));
            }
            else if (selectedType.equals("Viewing Device")) {
                fieldResults.setText(String.valueOf(DAOViewingDevice.selectAllViewingDevicesByLocation(selectedLocation).size()));
            }
        }
    }

    @FXML
    void onActionUserSelect(ActionEvent event) {
        User selectedUser = comboUser.getSelectionModel().getSelectedItem();

        ObservableList<Equipment> associatedEquipment = FXCollections.observableArrayList();
        ObservableList<Computer> associatedComputers = DAOComputers.selectAllComputersByUser(selectedUser);
        ObservableList<Peripheral> associatedPeripherals = DAOPeripherals.selectAllPeripheralsByUser(selectedUser);
        ObservableList<ViewingDevice> associatedViewingDevices = DAOViewingDevice.selectAllViewingDevicesByUser(selectedUser);
        associatedEquipment.addAll(associatedComputers);
        associatedEquipment.addAll(associatedPeripherals);
        associatedEquipment.addAll(associatedViewingDevices);

        if (associatedEquipment.size() > 0) {
            tableUserInventory.setItems(associatedEquipment);
            colEquipmentId.setCellValueFactory(new PropertyValueFactory<>("equipmentId"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colModel.setCellValueFactory(new PropertyValueFactory<>("modelNumber"));
            colSerial.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
            colEntryDateTime.setCellValueFactory(new PropertyValueFactory<>("entryDateTime"));
            colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

            //Sort TableView by Equipment ID
            colEquipmentId.setSortType(TableColumn.SortType.ASCENDING);
            tableUserInventory.getSortOrder().add(colEquipmentId);
            tableUserInventory.sort();
        }
        else {
            ObservableList<Equipment> emptyList = FXCollections.observableArrayList();
            tableUserInventory.setItems(emptyList);
            tableUserInventory.setPlaceholder(new Label("There are no associated inventory entries for User: " + selectedUser.getFullName()));
        }
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
