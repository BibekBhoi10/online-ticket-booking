package online.ticketBooking;

import java.util.Scanner;

public class TicketBookingSystem {
     public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            UserService userService = new UserService();

            System.out.println("Welcome to Online Ticket Booking System");
            System.out.println("1. Register");
            System.out.println("2. Login");
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
                    // Proceed to booking system (Next Step)
                } else {
                    System.out.println("Invalid Username or Password!");
                }
            } else {
                System.out.println("Invalid Choice! Exiting...");
            }
            scanner.close();
        }
    }

