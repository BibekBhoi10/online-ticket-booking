package online.ticketBooking;

public class Ticket {
    private int id ;
    private String eventName ;

    private int availableSeats;

    public Ticket (int id , String eventName , int availableSeats){
        this.id = id ;
        this.eventName = eventName;
        this.availableSeats= availableSeats;
    }
}
