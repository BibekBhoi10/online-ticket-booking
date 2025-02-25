package online.ticketBooking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private Connection conn;

    public TicketService(){
        conn = DatabaseConnection.getConnection();
    }

    // Get available tickets
    public List<Ticket> getAvailableTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM tickets WHERE available_seats > 0";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String eventName = rs.getString("event_name");
                int availableSeats = rs.getInt("available_seats");
                tickets.add(new Ticket(id, eventName, availableSeats));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

}
