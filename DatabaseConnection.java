package online.ticketBooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            // If the connection is closed or null, create a new one
            if (conn == null || conn.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/ticketBookingdb"; // Change database name if needed
                String user = "root";  // Change to your database username
                String password = "Bhoi@2003";  // Change to your database password
                conn = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
