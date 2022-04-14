package Controller;

import DAO.DAOComputers;
import DAO.DAOEquipment;
import DAO.DAOPeripherals;
import DAO.DAOViewingDevice;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditEquipmentController implements Initializable {
    private Stage stage;
    private Parent scene;

    private Computer sentComputer;
    private Peripheral sentPeripheral;
    private ViewingDevice sentViewingDevice;

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
    private Label labelExtra1;

    @FXML
    private ComboBox comboExtra1;

    @FXML
    private Label labelExtra2;

    @FXML
    private ComboBox comboExtra2;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loader = new FXMLLoader();

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
    }

    @FXML
    void onActionSave(ActionEvent event) {
        //Prepare field validation alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Edit Equipment Error");

        //Get User and ID value
        int equipmentId = Integer.parseInt(fieldId.getText());
        int userId = passUser.getUserId();

        //Initialize variables for object creation
        String type = "";
        String modelNumber = "";
        String serialNumber = "";
        String location = "";
        String extra1 = "";
        String extra2 = "";

        //Read in field inputs for validation
        String typeInput = comboType.getSelectionModel().getSelectedItem();
        String modelInput = fieldModel.getText();
        String serialInput = fieldSerial.getText();
        String locationInput = comboLocation.getSelectionModel().getSelectedItem();
        Object extra1Input = comboExtra1.getSelectionModel().getSelectedItem();
        Object extra2Input = comboExtra2.getSelectionModel().getSelectedItem();

        //Begin field validation loop
        boolean loopFlag = true;
        while (loopFlag) {
            //Check for blank fields
            if (typeInput.isBlank() || modelInput.isBlank() || serialInput.isBlank() || locationInput == null || extra1Input == null || extra2Input == null) {
                alert.setContentText("All fields must be filled out.");
                alert.showAndWait();
                loopFlag = false;
                break;
            }
            type = typeInput;
            modelNumber = modelInput;
            serialNumber = serialInput;
            location = locationInput;
            extra1 = extra1Input.toString();
            extra2 = extra2Input.toString();
            //If passing, create new equipment object and add to the DB based on selected Type
            if (type.toLowerCase().equals("computer")) {
                Computer computer = new Computer(equipmentId, type, modelNumber, serialNumber, location, LocalDateTime.now(), userId, extra1, extra2);
                try {
                    DAOComputers.update(computer);

                    //Break out of loop and move to previous screen
                    loader.setLocation(getClass().getResource("/View/ViewEquipment.fxml"));
                    loader.load();
                    EquipmentController controller = loader.getController();
                    controller.sendUser(passUser);

                    stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
                    scene = loader.getRoot();
                    stage.setScene(new Scene(scene));
                    stage.show();

                    loopFlag = false;
                    break;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (type.toLowerCase().equals("peripheral")) {
                Peripheral peripheral = new Peripheral(equipmentId, type, modelNumber, serialNumber, location, LocalDateTime.now(), userId, extra1, extra2);
                try {
                    DAOPeripherals.update(peripheral);

                    //Break out of loop and move to previous screen
                    loader.setLocation(getClass().getResource("/View/ViewEquipment.fxml"));
                    loader.load();
                    EquipmentController controller = loader.getController();
                    controller.sendUser(passUser);

                    stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
                    scene = loader.getRoot();
                    stage.setScene(new Scene(scene));
                    stage.show();

                    loopFlag = false;
                    break;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (type.toLowerCase(Locale.ROOT).equals("viewing device")) {
                ViewingDevice viewingDevice = new ViewingDevice(equipmentId, type, modelNumber, serialNumber, location, LocalDateTime.now(), userId, extra1, extra2);
                try {
                    DAOViewingDevice.update(viewingDevice);

                    //Break out of loop and move to previous screen
                    loader.setLocation(getClass().getResource("/View/ViewEquipment.fxml"));
                    loader.load();
                    EquipmentController controller = loader.getController();
                    controller.sendUser(passUser);

                    stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
                    scene = loader.getRoot();
                    stage.setScene(new Scene(scene));
                    stage.show();

                    loopFlag = false;
                    break;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            loopFlag = false;
        }
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

    public void onActionTypeComboSelect(ActionEvent event) {
        if (comboType.getSelectionModel().getSelectedItem().toLowerCase().equals("computer")) {
            labelExtra1.setText("GPU Type");
            labelExtra2.setText("Purchase Price");

            ObservableList<String> comboChoice1 = FXCollections.observableArrayList();
            comboChoice1.add("Integrated");
            comboChoice1.add("External");
            comboExtra1.setItems(comboChoice1);
            ObservableList<String> comboChoice2 = FXCollections.observableArrayList();
            comboChoice2.add("0.00 - 200.00");
            comboChoice2.add("200.00 - 1000.00");
            comboChoice2.add("> 1000.00");
            comboExtra2.setItems(comboChoice2);
        }
        else if (comboType.getSelectionModel().getSelectedItem().toLowerCase().equals("peripheral")) {
            labelExtra1.setText("Peripheral Type");
            labelExtra2.setText("Condition");

            ObservableList<String> comboChoice1 = FXCollections.observableArrayList();
            comboChoice1.add("Keyboard");
            comboChoice1.add("Mouse");
            comboChoice1.add("Speakers");
            comboExtra1.setItems(comboChoice1);
            ObservableList<String> comboChoice2 = FXCollections.observableArrayList();
            comboChoice2.add("Like New");
            comboChoice2.add("Good");
            comboChoice2.add("Fair");
            comboChoice2.add("Poor");
            comboExtra2.setItems(comboChoice2);
        }
        else if (comboType.getSelectionModel().getSelectedItem().toLowerCase().equals("viewing device") || comboType.getSelectionModel().getSelectedItem().toLowerCase().equals("viewing_device")) {
            labelExtra1.setText("Screen Size");
            labelExtra2.setText("Input Type");

            ObservableList<String> comboChoice1 = FXCollections.observableArrayList();
            comboChoice1.add("18\"");
            comboChoice1.add("20\"");
            comboChoice1.add("24\"");
            comboChoice1.add("27\"");
            comboExtra1.setItems(comboChoice1);
            ObservableList<String> comboChoice2 = FXCollections.observableArrayList();
            comboChoice2.add("HDMI");
            comboChoice2.add("DisplayPort");
            comboChoice2.add("DVI / VGA");
            comboExtra2.setItems(comboChoice2);
        }
    }

    public void passUser(User user) {
        this.passUser = user;
        fieldUser.setText(passUser.getFullName());
    }

    public void sendComputer(Computer computer) {
        this.sentComputer = computer;
        fieldId.setText(String.valueOf(computer.getEquipmentId()));
        //Select an initial combo box
        comboType.getSelectionModel().select("Computer");
        onActionTypeComboSelect(new ActionEvent());
        fieldModel.setText(computer.getModelNumber());
        fieldSerial.setText(computer.getSerialNumber());
        comboLocation.getSelectionModel().select(computer.getLocation());
        comboExtra1.getSelectionModel().select(computer.getGpuType());
        comboExtra2.getSelectionModel().select(computer.getPurchasePrice());
    }

    public void sendPeripheral(Peripheral peripheral) {
        this.sentPeripheral = peripheral;
        fieldId.setText(String.valueOf(peripheral.getEquipmentId()));
        //Select an initial combo box
        comboType.getSelectionModel().select("Peripheral");
        onActionTypeComboSelect(new ActionEvent());
        fieldModel.setText(peripheral.getModelNumber());
        fieldSerial.setText(peripheral.getSerialNumber());
        comboLocation.getSelectionModel().select(peripheral.getLocation());
        comboExtra1.getSelectionModel().select(peripheral.getPeripheralType());
        comboExtra2.getSelectionModel().select(peripheral.getCondition());
    }

    public void sendViewingDevice(ViewingDevice viewingDevice) {
        this.sentViewingDevice = viewingDevice;
        fieldId.setText(String.valueOf(viewingDevice.getEquipmentId()));
        //Select an initial combo box
        comboType.getSelectionModel().select("Viewing Device");
        onActionTypeComboSelect(new ActionEvent());
        fieldModel.setText(viewingDevice.getModelNumber());
        fieldSerial.setText(viewingDevice.getSerialNumber());
        comboLocation.getSelectionModel().select(viewingDevice.getLocation());
        comboExtra1.getSelectionModel().select(viewingDevice.getScreenSize());
        comboExtra2.getSelectionModel().select(viewingDevice.getInputType());
    }
}
