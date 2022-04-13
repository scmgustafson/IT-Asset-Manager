package DAO;

import Model.User;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOUsers{

    //Return a list of all User records as objects
    public static ObservableList<User> selectAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            String query = "Select * FROM users;";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                //Get a record's information from SQL query
                int userId = result.getInt("user_ID");
                String fullname = result.getString("full_name");
                String username = result.getString("username");
                String password = result.getString("password");
                String type = result.getString("type");
                String department = result.getString("department");

                //Create a new User object using that information and add to return list
                User user = new User(userId, fullname, username, password, type, department);
                users.add(user);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static int insert(User user) {
        int rowsAffected = 0;
        try {
            //Specify insert query and set bind variables with parameter object information
            String query = "INSERT INTO users (user_ID, full_name, username, password, type, department) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getType());
            statement.setString(6, user.getDepartment());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int update(User user) {
        int rowsAffected = 0;
        try {
            String query = "UPDATE users SET full_name = ?, username = ?, password = ?, type = ?, department = ? " +
                    "WHERE user_ID = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getType());
            statement.setString(5, user.getDepartment());
            statement.setInt(6, user.getUserId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int delete(User user) {
        int rowsAffected = 0;
        try {
            String query = "DELETE FROM users WHERE user_ID = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(query);
            statement.setInt(1, user.getUserId());

            rowsAffected = statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return rowsAffected;
    }
}
