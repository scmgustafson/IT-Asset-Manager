package DAO;

import Model.Peripheral;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DAOPeripherals {

    //Return a list of all Peripheral records as objects
    public static ObservableList<Peripheral> selectAllPeripherals() {
        ObservableList<Peripheral> peripherals = FXCollections.observableArrayList();
        try {
            String query = "Select * FROM peripherals;";
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
                String peripheralType = result.getString("peripheral_type");
                String condition = result.getString("equipment_condition");

                //Convert Timestamp to LocalDateTime
                LocalDateTime entryDateTime = entryDate.toLocalDateTime();

                //Create a new Computer object using that information and add to return list
                Peripheral peripheral = new Peripheral(equipmentId, type, modelNumber, serialNumber, location, entryDateTime, userId, peripheralType, condition);
                peripherals.add(peripheral);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return peripherals;
    }

    public static int insert(Peripheral peripheral) {
        int rowsAffected = 0;
        try {
            //Specify insert query and set bind variables with parameter object information
            String query = "INSERT INTO peripherals (equipment_id, type, model_number, serial_number, location, created_date, peripheral_type, equipment_condition, user_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setInt(1, peripheral.getEquipmentId());
            statement.setString(2, peripheral.getType());
            statement.setString(3, peripheral.getModelNumber());
            statement.setString(4, peripheral.getSerialNumber());
            statement.setString(5, peripheral.getLocation());
            statement.setTimestamp(6, Timestamp.valueOf(peripheral.getEntryDateTime()));
            statement.setString(7, peripheral.getPeripheralType());
            statement.setString(8, peripheral.getCondition());
            statement.setInt(9, peripheral.getUserId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int update(Peripheral peripheral) {
        int rowsAffected = 0;
        try {
            String query = "UPDATE peripherals SET type = ?, model_number = ?, serial_number = ?, location = ?, created_date = ?, peripheral_type = ?, equipment_condition = ?, user_ID = ? " +
                    "WHERE equipment_ID = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setString(1, peripheral.getType());
            statement.setString(2, peripheral.getModelNumber());
            statement.setString(3, peripheral.getSerialNumber());
            statement.setString(4, peripheral.getLocation());
            statement.setTimestamp(5, Timestamp.valueOf(peripheral.getEntryDateTime()));
            statement.setString(6, peripheral.getPeripheralType());
            statement.setString(7, peripheral.getCondition());
            statement.setInt(8, peripheral.getUserId());
            statement.setInt(9, peripheral.getEquipmentId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int delete(Peripheral peripheral) {
        int rowsAffected = 0;
        try {
            String query = "DELETE FROM peripherals WHERE equipment_id = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setInt(1, peripheral.getEquipmentId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
