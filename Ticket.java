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

    public int getId(){
        return id ;
    }

    public String getEventName(){
        return eventName;
    }

    public int getAvailableSeats(){
        return availableSeats;
    }
}
