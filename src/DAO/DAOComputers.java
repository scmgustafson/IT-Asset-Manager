package DAO;

import Model.Computer;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DAOComputers{
    //Return a list of all Computer records as objects
    public static ObservableList<Computer> selectAllComputers() {
        ObservableList<Computer> computers = FXCollections.observableArrayList();
        try {
            String query = "Select * FROM computers;";
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
                String gpuType = result.getString("gpu_type");
                String purchasePrice = result.getString("purchase_price");

                //Convert Timestamp to LocalDateTime
                LocalDateTime entryDateTime = entryDate.toLocalDateTime();

                //Create a new Computer object using that information and add to return list
                Computer computer = new Computer(equipmentId, type, modelNumber, serialNumber, location, entryDateTime, userId, gpuType, purchasePrice);
                computers.add(computer);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return computers;
    }

    public static int insert(Computer computer) {
        int rowsAffected = 0;
        try {
            //Specify insert query and set bind variables with parameter object information
            String query = "INSERT INTO computers (equipment_id, type, model_number, serial_number, location, created_date, gpu_type, purchase_price, user_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setInt(1, computer.getEquipmentId());
            statement.setString(2, computer.getType());
            statement.setString(3, computer.getModelNumber());
            statement.setString(4, computer.getSerialNumber());
            statement.setString(5, computer.getLocation());
            statement.setTimestamp(6, Timestamp.valueOf(computer.getEntryDateTime()));
            statement.setString(7, computer.getGpuType());
            statement.setString(8, computer.getPurchasePrice());
            statement.setInt(9, computer.getUserId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int update(Computer computer) {
        int rowsAffected = 0;
        try {
            String query = "UPDATE computers SET type = ?, model_number = ?, serial_number = ?, location = ?, created_date = ?, gpu_type = ?, purchase_price = ?, user_ID = ? " +
                    "WHERE equipment_ID = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setString(1, computer.getType());
            statement.setString(2, computer.getModelNumber());
            statement.setString(3, computer.getSerialNumber());
            statement.setString(4, computer.getLocation());
            statement.setTimestamp(5, Timestamp.valueOf(computer.getEntryDateTime()));
            statement.setString(6, computer.getGpuType());
            statement.setString(7, computer.getPurchasePrice());
            statement.setInt(8, computer.getUserId());
            statement.setInt(9, computer.getEquipmentId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int delete(Computer computer) {
        int rowsAffected = 0;
        try {
            String query = "DELETE FROM computers WHERE equipment_id = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setInt(1, computer.getEquipmentId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
