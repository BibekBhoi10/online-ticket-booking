package online.ticketBooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ticketBookingdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Bhoi@2003";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) { // Create connection only if it doesn't exist
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Load Driver
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to MySQL successfully!");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to connect to MySQL.");
            }
        }
        return connection;
    }

    public static void main(String[] args) {
        // Test connection
        getConnection();
    }
}
