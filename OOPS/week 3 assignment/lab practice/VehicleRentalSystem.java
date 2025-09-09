class Vehicle {
    private String vehicleId;
    private String brand;
    private String model;
    private double rentPerDay;
    private boolean isAvailable;
    private int rentedDays; 

    private static int totalVehicles = 0;
    private static double totalRevenue = 0;
    private static String companyName = "Default Rental Co.";
    private static int rentalDays = 0;
    private static int vehicleCounter = 1;

    public Vehicle(String brand, String model, double rentPerDay) {
        this.vehicleId = generateVehicleId();
        this.brand = brand;
        this.model = model;
        this.rentPerDay = rentPerDay;
        this.isAvailable = true;
        this.rentedDays = 0;
        totalVehicles++;
    }

    private static String generateVehicleId() {
        return String.format("VH%03d", vehicleCounter++);
    }

    public double calculateRent(int days) {
        double amount = rentPerDay * days;
        totalRevenue += amount;
        rentalDays += days;
        rentedDays += days;
        return amount;
    }

    public void rentVehicle(int days) {
        if (isAvailable) {
            double rentAmount = calculateRent(days);
            isAvailable = false;
            System.out.println("Vehicle " + vehicleId + " rented for " + days + " days. Rent: " + rentAmount);
        } else {
            System.out.println("Vehicle " + vehicleId + " is not available for rent.");
        }
    }

    public void returnVehicle() {
        if (!isAvailable) {
            isAvailable = true;
            System.out.println("Vehicle " + vehicleId + " has been returned and is now available.");
        } else {
            System.out.println("Vehicle " + vehicleId + " was not rented out.");
        }
    }

    public void displayVehicleInfo() {
        System.out.println("\n--- Vehicle Info ---");
        System.out.println("Vehicle ID: " + vehicleId);
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Rent Per Day: " + rentPerDay);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
        System.out.println("Total Rented Days: " + rentedDays);
    }

    public static void setCompanyName(String name) {
        companyName = name;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }

    public static double getAverageRentPerDay() {
        if (rentalDays == 0) return 0;
        return totalRevenue / rentalDays;
    }

    public static void displayCompanyStats() {
        System.out.println("\n=== Company Stats ===");
        System.out.println("Company Name: " + companyName);
        System.out.println("Total Vehicles: " + totalVehicles);
        System.out.println("Total Revenue: " + totalRevenue);
        System.out.println("Total Rental Days: " + rentalDays);
        System.out.println("Average Rent Per Day: " + getAverageRentPerDay());
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        Vehicle.setCompanyName("CityRide Rentals");

        Vehicle v1 = new Vehicle("Toyota", "Corolla", 50);
        Vehicle v2 = new Vehicle("Honda", "Civic", 60);
        Vehicle v3 = new Vehicle("Ford", "Focus", 55);

        v1.displayVehicleInfo();
        v2.displayVehicleInfo();
        v3.displayVehicleInfo();

        v1.rentVehicle(5); 
        v2.rentVehicle(3); 
        v1.returnVehicle(); 
        v1.rentVehicle(2); 

        v1.displayVehicleInfo();
        v2.displayVehicleInfo();

        Vehicle.displayCompanyStats();
    }
}

