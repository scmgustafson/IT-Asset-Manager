package DAO;

import Model.ViewingDevice;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DAOViewingDevice {
    //Return a list of all Viewing Device records as objects
    public static ObservableList<ViewingDevice> selectAllViewingDevices() {
        ObservableList<ViewingDevice> viewingDevices = FXCollections.observableArrayList();
        try {
            String query = "Select * FROM viewing_devices;";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                //Get a record's information from SQL query
                int equipmentId = result.getInt("equipment_ID");
                String type = result.getString("type");
                String modelNumber = result.getString("model_number");
                String serialNumber = result.getString("serial_number");
                String location = result.getString("location");
                Timestamp entryDate = result.getTimestamp("created_date");
                int userId = result.getInt("user_ID");
                String screenSize = result.getString("screen_size");
                String inputType = result.getString("input_type");

                //Convert Timestamp to LocalDateTime
                LocalDateTime entryDateTime = entryDate.toLocalDateTime();

                //Create a new ViewingDevice object using that information and add to return list
                ViewingDevice viewingDevice = new ViewingDevice(equipmentId, type, modelNumber, serialNumber, location, entryDateTime, userId, screenSize, inputType);
                viewingDevices.add(viewingDevice);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return viewingDevices;
    }

    public static int insert(ViewingDevice viewingDevice) {
        int rowsAffected = 0;
        try {
            //Specify insert query and set bind variables with parameter object information
            String query = "INSERT INTO viewing_devices (equipment_id, type, model_number, serial_number, location, created_date, screen_size, input_type, user_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setInt(1, viewingDevice.getEquipmentId());
            statement.setString(2, viewingDevice.getType());
            statement.setString(3, viewingDevice.getModelNumber());
            statement.setString(4, viewingDevice.getSerialNumber());
            statement.setString(5, viewingDevice.getLocation());
            statement.setTimestamp(6, Timestamp.valueOf(viewingDevice.getEntryDateTime()));
            statement.setString(7, viewingDevice.getScreenSize());
            statement.setString(8, viewingDevice.getInputType());
            statement.setInt(9, viewingDevice.getUserId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int update(ViewingDevice viewingDevice) {
        int rowsAffected = 0;
        try {
            String query = "UPDATE viewing_devices SET type = ?, model_number = ?, serial_number = ?, location = ?, created_date = ?, screen_size = ?, input_type = ?, user_ID = ? " +
                    "WHERE equipment_ID = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setString(1, viewingDevice.getType());
            statement.setString(2, viewingDevice.getModelNumber());
            statement.setString(3, viewingDevice.getSerialNumber());
            statement.setString(4, viewingDevice.getLocation());
            statement.setTimestamp(5, Timestamp.valueOf(viewingDevice.getEntryDateTime()));
            statement.setString(6, viewingDevice.getScreenSize());
            statement.setString(7, viewingDevice.getInputType());
            statement.setInt(8, viewingDevice.getUserId());
            statement.setInt(9, viewingDevice.getEquipmentId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int delete(ViewingDevice viewingDevice) {
        int rowsAffected = 0;
        try {
            String query = "DELETE FROM peripherals WHERE equipment_id = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setInt(1, viewingDevice.getEquipmentId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
