package Controller;

import DAO.*;
import Model.*;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    private TableColumn<Equipment, String> colExtra1;

    @FXML
    private TableColumn<Equipment, String> colExtra2;

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

        //Hide UI feedback message
        labelUIMessage.setVisible(false);

        //Hide extra data columns on view start
        colExtra1.setVisible(false);
        colExtra2.setVisible(false);
        colLocation.setPrefWidth(240);
        colUser.setPrefWidth(240);

        //Populate Type Combo Box
        ObservableList<String> equipmentTypes = FXCollections.observableArrayList();
        equipmentTypes.add("All");
        equipmentTypes.add("Computers");
        equipmentTypes.add("Peripherals");
        equipmentTypes.add("Viewing Devices");
        comboEquipmentType.setItems(equipmentTypes);
        comboEquipmentType.getSelectionModel().select("All");

        //Populate TableView
        ObservableList<Computer> allComputers = DAOComputers.selectAllComputers();
        ObservableList<Peripheral> allPeripherals = DAOPeripherals.selectAllPeripherals();
        ObservableList<ViewingDevice> allViewingDevices = DAOViewingDevice.selectAllViewingDevices();
        ObservableList<Equipment> allEquipment = FXCollections.observableArrayList();
        allEquipment.addAll(allComputers);
        allEquipment.addAll(allPeripherals);
        allEquipment.addAll(allViewingDevices);

        tableEquipment.setItems(allEquipment);
        colEquipmentId.setCellValueFactory(new PropertyValueFactory<>("equipmentId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("modelNumber"));
        colSerial.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        colEntryDateTime.setCellValueFactory(new PropertyValueFactory<>("entryDateTime"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("userId"));

        //Sort TableView by Equipment ID
        colEquipmentId.setSortType(TableColumn.SortType.ASCENDING);
        tableEquipment.getSortOrder().add(colEquipmentId);
        tableEquipment.sort();
    }

    public void onActionEquipmentTypeSelect(ActionEvent event) {
        refreshTable();
    }

    public void onKeyPressSearchEquipment(KeyEvent event) {
        //Only run search logic if Enter key pressed
        if (event.getCode() == KeyCode.ENTER) {
            //Refresh table so that search is only run on the entire User list
            refreshTable();
            //Get search text
            String search = fieldEquipmentSearch.getText().toLowerCase(Locale.ROOT);
            if (search.isBlank()) {
                refreshTable();
                return;
            }
            //Determine if using is searching by User ID or by full name
            try {
                //Search for user by ID, highlighting it in the TableView if found
                int searchId = Integer.parseInt(search);
                ObservableList<Equipment> foundEquipment = FXCollections.observableArrayList();
                for (Equipment equipment : DAOEquipment.selectAllEquipment()) {
                    if (equipment.getEquipmentId() == searchId) {
                        foundEquipment.add(equipment);
                    }
                }
                if (foundEquipment.size() > 0) {
                    tableEquipment.setItems(foundEquipment);
                    comboEquipmentType.getSelectionModel().select("All");
                }
                else {
                    refreshTable();
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No matching inventory item found!");
                    alert.setTitle("Search Message");
                    alert.showAndWait();
                }
            }
            catch (Exception e){
                String newSearch = search.substring(1);
                //Search for user by Full Name, adding it to the filtered list if containing search string
                ObservableList<Equipment> filteredEquipment = FXCollections.observableArrayList();
                for (Equipment equipment : DAOEquipment.selectAllEquipment()) {
                    if (equipment.getSerialNumber().toLowerCase(Locale.ROOT).contains(newSearch)) {
                        filteredEquipment.add(equipment);
                    }
                }
                //Check to see if there is no match found
                if (filteredEquipment.size() > 0) {
                    //Check if there is only 1 match or multiple matches
                    tableEquipment.setItems(filteredEquipment);
                }
                else {
                    refreshTable();
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No matching inventory item found!");
                    alert.setTitle("Search Message");
                    alert.showAndWait();
                }
            }
        }
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
        //Send tracked user information
        loader.setLocation(getClass().getResource("/View/ViewEditEquipment.fxml"));
        loader.load();
        EditEquipmentController controller = loader.getController();
        controller.passUser(sentUser);

        //Determine what kind of object is being sent to next screen, then send data
        Equipment selectedEquipment = tableEquipment.getSelectionModel().getSelectedItem();
        String selectedType = selectedEquipment.getType().toLowerCase();

        if (selectedType.equals("computer")) {
            ObservableList<Computer> allComputers = DAOComputers.selectAllComputers();
            Computer selectedComputer = null;
            for (Computer computer : allComputers) {
                if (selectedEquipment.getEquipmentId() == computer.getEquipmentId()) {
                    selectedComputer = computer;
                    controller.sendComputer(selectedComputer);
                }
            }

        }
        else if (selectedType.equals("peripheral")) {
            ObservableList<Peripheral> allPeripherals = DAOPeripherals.selectAllPeripherals();
            Peripheral selectedPeripheral = null;
            for (Peripheral peripheral : allPeripherals) {
                if (selectedEquipment.getEquipmentId() == peripheral.getEquipmentId()) {
                    selectedPeripheral = peripheral;
                    controller.sendPeripheral(selectedPeripheral);
                }
            }
        }
        else if (selectedType.equals("viewing device")) {
            ObservableList<ViewingDevice> allVDs = DAOViewingDevice.selectAllViewingDevices();
            ViewingDevice selectedVD = null;
            for (ViewingDevice vd : allVDs) {
                if (selectedEquipment.getEquipmentId() == vd.getEquipmentId()) {
                    selectedVD = vd;
                    controller.sendViewingDevice(selectedVD);
                }
            }
        }
        //Mvoe to next View
        stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteEquipment(ActionEvent event) {
        try {
            Computer computer = (Computer)tableEquipment.getSelectionModel().getSelectedItem();
            DAOComputers.delete(computer);
            labelUIMessage.setTextFill(Color.GREEN);
            labelUIMessage.setText("Equipment - ID: " + computer.getEquipmentId() + " has been deleted successfully!");
            labelUIMessage.setVisible(true);
        }
        catch (Exception e) {
            System.out.println("Not a computer, trying others");
        }
        try {
            Peripheral peripheral = (Peripheral)tableEquipment.getSelectionModel().getSelectedItem();
            DAOPeripherals.delete(peripheral);
            labelUIMessage.setTextFill(Color.GREEN);
            labelUIMessage.setText("Equipment - ID: " + peripheral.getEquipmentId() + " has been deleted successfully!");
            labelUIMessage.setVisible(true);
        }
        catch (Exception e) {
            System.out.println("Not a peripheral, trying others");
        }
        try {
            ViewingDevice viewingDevice = (ViewingDevice)tableEquipment.getSelectionModel().getSelectedItem();
            DAOViewingDevice.delete(viewingDevice);
            labelUIMessage.setTextFill(Color.GREEN);
            labelUIMessage.setText("Equipment - ID: " + viewingDevice.getEquipmentId() + " has been deleted successfully!");
            labelUIMessage.setVisible(true);
        }
        catch (Exception e) {
            System.out.println("Not a viewing device, trying others");
        }

        refreshTable();
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

    public void refreshTable() {
        ObservableList<Equipment> allEquipment = FXCollections.observableArrayList();
        if (comboEquipmentType.getSelectionModel().getSelectedItem().equals("Computers")) {
            ObservableList<Computer> allComputers = DAOComputers.selectAllComputers();
            //Set TableView to all Computer objects
            allEquipment.addAll(allComputers);
            tableEquipment.setItems(allEquipment);

            //Sort TableView by Equipment ID
            colEquipmentId.setSortType(TableColumn.SortType.ASCENDING);
            tableEquipment.getSortOrder().add(colEquipmentId);
            tableEquipment.sort();
        }
        else if (comboEquipmentType.getSelectionModel().getSelectedItem().equals("Peripherals")) {
            allEquipment.addAll(DAOPeripherals.selectAllPeripherals());
            tableEquipment.setItems(allEquipment);
            //Sort TableView by Equipment ID
            colEquipmentId.setSortType(TableColumn.SortType.ASCENDING);
            tableEquipment.getSortOrder().add(colEquipmentId);
            tableEquipment.sort();
        }
        else if (comboEquipmentType.getSelectionModel().getSelectedItem().equals("Viewing Devices")) {
            allEquipment.addAll(DAOViewingDevice.selectAllViewingDevices());
            tableEquipment.setItems(allEquipment);
            //Sort TableView by Equipment ID
            colEquipmentId.setSortType(TableColumn.SortType.ASCENDING);
            tableEquipment.getSortOrder().add(colEquipmentId);
            tableEquipment.sort();
        }
        else {
            ObservableList<Computer> allComputers = DAOComputers.selectAllComputers();
            ObservableList<Peripheral> allPeripherals = DAOPeripherals.selectAllPeripherals();
            ObservableList<ViewingDevice> allViewingDevices = DAOViewingDevice.selectAllViewingDevices();
            allEquipment.addAll(allComputers);
            allEquipment.addAll(allPeripherals);
            allEquipment.addAll(allViewingDevices);
            tableEquipment.setItems(allEquipment);
            //Sort TableView by Equipment ID
            colEquipmentId.setSortType(TableColumn.SortType.ASCENDING);
            tableEquipment.getSortOrder().add(colEquipmentId);
            tableEquipment.sort();
        }
    }
}
