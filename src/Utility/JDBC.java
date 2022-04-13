package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JDBC class is used to initiate, close, and manage the SQL database connection.
 * */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//capstone-database.c6z6xhfatfk7.us-west-1.rds.amazonaws.com/"; //Specifies URL/IP of DB
    private static final String databaseName = "asset_manager"; //Specifies DB to run queries on
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL //Allows to switch between server or mysql service timezone
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "dbadmin"; // Username
    private static String password = "capstone543!"; // Password
    private static Connection connection = null;  // Connection Interface
    private static PreparedStatement preparedStatement;

    /**
     * openConnection() method starts the initial connection to the SQL database.
     * Utilizes the DriverManager getConnection() method with the previously specified class fields to start the connection.
     * */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            //password = Details.getPassword(); // Assign password - WHY IS THIS HERE??
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // creates reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * closeConnection() method terminates the SQL database connection.
     * */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Getter method for connection.
     * @return the connection
     * */
    public static Connection getConnection() {
        return connection;
    }

    public static void setPreparedStatement(String sqlStatement, Connection conn) throws SQLException {
        if (conn != null)
            preparedStatement = conn.prepareStatement(sqlStatement);
        else
            System.out.println("Prepared Statement Creation Failed!");
    }
    /**
     * Getter method for preparedStatement.
     * @return the preparedStatement
     * */
    public static PreparedStatement getPreparedStatement() throws SQLException {
        if (preparedStatement != null)
            return preparedStatement;
        else System.out.println("Null reference to Prepared Statement");
        return null;
    }
}