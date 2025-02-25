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

    // ticket booking

    public boolean bookTicket(int userId, int ticketId) {
        String checkSeatsQuery = "SELECT available_seats FROM tickets WHERE id = ?";
        String updateSeatsQuery = "UPDATE tickets SET available_seats = available_seats - 1 WHERE id = ? AND available_seats > 0";
        String insertBookingQuery = "INSERT INTO bookings (user_id, ticket_id) VALUES (?, ?)";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSeatsQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateSeatsQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertBookingQuery)) {

            // Check available seats
            checkStmt.setInt(1, ticketId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt("available_seats") > 0) {

                // Update available seats
                updateStmt.setInt(1, ticketId);
                int updatedRows = updateStmt.executeUpdate();
                if (updatedRows > 0) {

                    // Insert booking record
                    insertStmt.setInt(1, userId);
                    insertStmt.setInt(2, ticketId);
                    insertStmt.executeUpdate();

                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // cancel Booking

    public boolean cancelBooking(int userId, int ticketId) {
        String deleteBookingQuery = "DELETE FROM bookings WHERE user_id = ? AND ticket_id = ? LIMIT 1";
        String updateSeatsQuery = "UPDATE tickets SET available_seats = available_seats + 1 WHERE id = ?";

        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteBookingQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateSeatsQuery)) {

            deleteStmt.setInt(1, userId);
            deleteStmt.setInt(2, ticketId);
            if (deleteStmt.executeUpdate() > 0) {
                updateStmt.setInt(1, ticketId);
                updateStmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
