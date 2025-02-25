package online.ticketBooking;

import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketBookingSystem {

    public static int getUserId(String username) {
        int userId = -1; // Default value if user is not found
        String query = "SELECT id FROM users WHERE username = ?";

        try {
            Connection conn = DatabaseConnection.getConnection(); // Use existing connection
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id"); // Fetch userId
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        TicketService ticketService = new TicketService();

        while (true) { // Keep showing the menu until the user logs out
            System.out.println("\nWelcome to Online Ticket Booking System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                // User Registration
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                User user = new User(username, password);
                if (userService.registerUser(user)) {
                    System.out.println("Registration Successful!");
                } else {
                    System.out.println("Registration Failed!");
                }

            } else if (choice == 2) {
                // User Login
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                if (userService.loginUser(username, password)) {
                    System.out.println("Login Successful! Welcome " + username);

                    int userId = getUserId(username); // Get user ID from DB

                    while (true) { // Keep showing the main menu after login
                        System.out.println("\nMain Menu:");
                        System.out.println("1. View Available Tickets");
                        System.out.println("2. Book a Ticket");
                        System.out.println("3. Cancel a Booking");
                        System.out.println("4. Logout");
                        System.out.print("Enter your choice: ");
                        int option = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        if (option == 1) {
                            // Display available tickets
                            List<Ticket> tickets = ticketService.getAvailableTickets();
                            if (tickets.isEmpty()) {
                                System.out.println("No tickets available.");
                            } else {
                                System.out.println("Available Tickets:");
                                for (Ticket ticket : tickets) {
                                    System.out.println("ID: " + ticket.getId() + " | Event: " + ticket.getEventName() +
                                            " | Available Seats: " + ticket.getAvailableSeats());
                                }
                            }

                        } else if (option == 2) {
                            // Booking tickets
                            System.out.print("Enter the Ticket ID: ");
                            int ticketId = scanner.nextInt();
                            System.out.print("Enter number of seats: ");
                            int seatCount = scanner.nextInt();

                            if (ticketService.bookTicket(userId, ticketId)) {
                                System.out.println(seatCount + " seats booked successfully!");
                            } else {
                                System.out.println("Booking failed! Not enough seats available.");
                            }

                        } else if (option == 3) {
                            // Cancel booking
                            System.out.println("Your Booked Tickets:");
                            List<Ticket> bookedTickets = ticketService.getUserBookings(userId);
                            if (bookedTickets.isEmpty()) {
                                System.out.println("You have no booked tickets.");
                            } else {
                                for (Ticket ticket : bookedTickets) {
                                    System.out.println("ID: " + ticket.getId() + " | Event: " + ticket.getEventName());
                                }
                                System.out.print("Enter the Ticket ID to cancel: ");
                                int ticketId = scanner.nextInt();

                                if (ticketService.cancelBooking(userId, ticketId)) {
                                    System.out.println("Ticket cancellation successful!");
                                } else {
                                    System.out.println("Cancellation failed! Invalid ticket ID.");
                                }
                            }

                        } else if (option == 4) {
                            System.out.println("Logging out...");
                            break; // Exit inner menu loop, return to login/register menu
                        } else {
                            System.out.println("Invalid choice! Please try again.");
                        }
                    }

                } else {
                    System.out.println("Invalid Username or Password! Try again.");
                }

            } else if (choice == 3) {
                System.out.println("Exiting system...");
                break; // Exit the program
            } else {
                System.out.println("Invalid Choice! Please enter a valid option.");
            }
        }

        scanner.close();
    }
    }

