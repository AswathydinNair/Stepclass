class MovieTicket {

    String movieName;
    String theatreName;
    int seatNumber;
    double price;

    MovieTicket() {
        this.movieName = "Unknown";
        this.theatreName = "Not Assigned";
        this.seatNumber = -1;
        this.price = 0.0;
    }

    MovieTicket(String movieName) {
        this.movieName = movieName;
        this.theatreName = "Not Assigned";
        this.seatNumber = -1;
        this.price = 200.0; 
    }

    MovieTicket(String movieName, int seatNumber) {
        this.movieName = movieName;
        this.theatreName = "PVR";  
        this.seatNumber = seatNumber;
        this.price = 200.0; 
    }

    MovieTicket(String movieName, String theatreName, int seatNumber, double price) {
        this.movieName = movieName;
        this.theatreName = theatreName;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    void printTicket() {
        System.out.println(" Movie Ticket Details ");
        System.out.println("Movie Name  : " + movieName);
        System.out.println("Theatre     : " + theatreName);
        System.out.println("Seat Number : " + seatNumber);
        System.out.println("Price       : ₹" + price);
    }
}

// Main class
public class MovieTicketSystem {
    public static void main(String[] args) {
        MovieTicket t1 = new MovieTicket();  
        MovieTicket t2 = new MovieTicket("Inception");  
        MovieTicket t3 = new MovieTicket("Interstellar", 12);  
        MovieTicket t4 = new MovieTicket("Avengers", "IMAX", 45, 350.0);  

        t1.printTicket();
        t2.printTicket();
        t3.printTicket();
        t4.printTicket();
    }
}

