package DAO;

import Model.Computer;
import Model.Equipment;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DAOEquipment {

    public static ObservableList<Equipment> selectAllEquipment() {
        ObservableList<Equipment> equipment = FXCollections.observableArrayList();
        try {
            String query = "Select * FROM " +
                    "(SELECT * FROM computers UNION SELECT * FROM peripherals UNION SELECT * FROM viewing_devices) " +
                    "AS A ORDER BY equipment_ID ASC";
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

                //Convert Timestamp to LocalDateTime
                LocalDateTime entryDateTime = entryDate.toLocalDateTime();

                //Create a new Computer object using that information and add to return list
                Equipment newEquipment = new Equipment(equipmentId, type, modelNumber, serialNumber, location, entryDateTime, userId);
                equipment.add(newEquipment);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return equipment;
    }
}
