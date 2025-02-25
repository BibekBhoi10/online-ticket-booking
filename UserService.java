package online.ticketBooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private Connection conn ;

    public UserService() {
        conn = DatabaseConnection.getConnection();
    }

    // Register a new user
    public boolean registerUser (User user){
        String query = "INSERT INTO users( username , password ) VALUES (?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
     }


     // login user
    public boolean loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If a row is found, login is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


