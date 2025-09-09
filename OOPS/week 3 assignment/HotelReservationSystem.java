import java.util.*;

class Room {
    String roomNumber;
    String roomType;
    double pricePerNight;
    boolean isAvailable;
    int maxOccupancy;

    public Room(String roomNumber, String roomType, double pricePerNight, int maxOccupancy) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.maxOccupancy = maxOccupancy;
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " [" + roomType + "] - $" + pricePerNight + "/night | " +
                (isAvailable ? "Available" : "Occupied") + " | Max Occupancy: " + maxOccupancy;
    }
}

class Guest {
    String guestId;
    String guestName;
    String phoneNumber;
    String email;
    List<String> bookingHistory;

    public Guest(String guestId, String guestName, String phoneNumber, String email) {
        this.guestId = guestId;
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bookingHistory = new ArrayList<>();
    }

    public void addBookingHistory(String bookingId) {
        bookingHistory.add(bookingId);
    }

    @Override
    public String toString() {
        return guestName + " (ID: " + guestId + ", Phone: " + phoneNumber + ", Email: " + email + ")";
    }
}

class Booking {
    String bookingId;
    Guest guest;
    Room room;
    String checkInDate;
    String checkOutDate;
    double totalAmount;

    static int totalBookings = 0;
    static double hotelRevenue = 0.0;
    static String hotelName = "Mystery Hotel";

    public Booking(String bookingId, Guest guest, Room room, String checkInDate, String checkOutDate, double totalAmount) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;

        totalBookings++;
        hotelRevenue += totalAmount;
        guest.addBookingHistory(bookingId);
    }

    @Override
    public String toString() {
        return "Booking " + bookingId + ": " + guest.guestName + " booked Room " +
                room.roomNumber + " (" + room.roomType + ") from " + checkInDate +
                " to " + checkOutDate + " | Total: $" + totalAmount;
    }

    public static double getTotalRevenue() {
        return hotelRevenue;
    }

    public static double getOccupancyRate(List<Room> rooms) {
        long occupied = rooms.stream().filter(r -> !r.isAvailable).count();
        return (double) occupied / rooms.size() * 100;
    }

    public static String getMostPopularRoomType(List<Booking> bookings) {
        Map<String, Integer> counter = new HashMap<>();
        for (Booking b : bookings) {
            counter.put(b.room.roomType, counter.getOrDefault(b.room.roomType, 0) + 1);
        }
        return counter.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("None");
    }
}

class HotelManagement {
    List<Room> rooms;
    List<Guest> guests;
    List<Booking> bookings;

    public HotelManagement() {
        rooms = new ArrayList<>();
        guests = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public Room checkAvailability(String roomType) {
        for (Room r : rooms) {
            if (r.isAvailable && r.roomType.equalsIgnoreCase(roomType)) {
                return r;
            }
        }
        return null;
    }

    public Booking makeReservation(Guest guest, String roomType, String checkIn, String checkOut, int nights) {
        Room availableRoom = checkAvailability(roomType);
        if (availableRoom == null) {
            System.out.println("No available room of type: " + roomType);
            return null;
        }
        double bill = calculateBill(availableRoom.pricePerNight, nights);
        String bookingId = "B" + (bookings.size() + 1);
        Booking booking = new Booking(bookingId, guest, availableRoom, checkIn, checkOut, bill);
        bookings.add(booking);
        availableRoom.isAvailable = false;
        System.out.println("Reservation successful: " + booking);
        return booking;
    }

    public void cancelReservation(String bookingId) {
        Iterator<Booking> it = bookings.iterator();
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.bookingId.equals(bookingId)) {
                b.room.isAvailable = true;
                it.remove();
                System.out.println("Booking " + bookingId + " has been canceled.");
                return;
            }
        }
        System.out.println("Booking not found.");
    }

    public double calculateBill(double pricePerNight, int nights) {
        return pricePerNight * nights;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addGuest(Guest guest) {
        guests.add(guest);
    }

    public void displayBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HotelManagement hotel = new HotelManagement();

        hotel.addRoom(new Room("101", "Single", 100, 1));
        hotel.addRoom(new Room("102", "Single", 100, 1));
        hotel.addRoom(new Room("201", "Double", 180, 2));
        hotel.addRoom(new Room("202", "Double", 180, 2));
        hotel.addRoom(new Room("301", "Suite", 300, 4));
        hotel.addRoom(new Room("302", "Suite", 350, 4));

        int choice;
        do {
            System.out.println("\n--- " + Booking.hotelName + " Management System ---");
            System.out.println("1. Register Guest");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Make Reservation");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View All Bookings");
            System.out.println("6. Reports");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter guest ID: ");
                    String gid = sc.nextLine();
                    System.out.print("Enter guest name: ");
                    String gname = sc.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    Guest guest = new Guest(gid, gname, phone, email);
                    hotel.addGuest(guest);
                    System.out.println("Guest registered: " + guest);
                    break;

                case 2:
                    System.out.println("\n--- Available Rooms ---");
                    for (Room r : hotel.rooms) {
                        if (r.isAvailable) {
                            System.out.println(r);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Guest ID: ");
                    String gId = sc.nextLine();
                    Guest foundGuest = null;
                    for (Guest g : hotel.guests) {
                        if (g.guestId.equals(gId)) {
                            foundGuest = g;
                            break;
                        }
                    }
                    if (foundGuest == null) {
                        System.out.println("Guest not found. Register first.");
                        break;
                    }
                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String rType = sc.nextLine();
                    System.out.print("Enter check-in date (YYYY-MM-DD): ");
                    String inDate = sc.nextLine();
                    System.out.print("Enter check-out date (YYYY-MM-DD): ");
                    String outDate = sc.nextLine();
                    System.out.print("Enter number of nights: ");
                    int nights = sc.nextInt();
                    hotel.makeReservation(foundGuest, rType, inDate, outDate, nights);
                    break;

                case 4:
                    System.out.print("Enter booking ID to cancel: ");
                    String bId = sc.nextLine();
                    hotel.cancelReservation(bId);
                    break;

                case 5:
                    hotel.displayBookings();
                    break;

                case 6:
                    System.out.println("Total Revenue: $" + Booking.getTotalRevenue());
                    System.out.println("Occupancy Rate: " + Booking.getOccupancyRate(hotel.rooms) + "%");
                    System.out.println("Most Popular Room Type: " + Booking.getMostPopularRoomType(hotel.bookings));
                    break;

                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);

        sc.close();
    }
}
