class Vehicle {
    protected String brand;
    protected String variant;
    protected int manufactureYear;
    protected double fuelCapacity;

    public Vehicle(String brand, String variant, int manufactureYear, double fuelCapacity) {
        this.brand = brand;
        this.variant = variant;
        this.manufactureYear = manufactureYear;
        this.fuelCapacity = fuelCapacity;
    }

    public void ignite() {
        System.out.println(manufactureYear + " " + brand + " " + variant + " engine started.");
    }

    public void halt() {
        System.out.println(manufactureYear + " " + brand + " " + variant + " engine stopped.");
    }

    public void addFuel(double liters) {
        fuelCapacity += liters;
        System.out.println(variant + " refilled by " + liters + " liters. Current fuel: " + fuelCapacity);
    }

    public void showDetails() {
        System.out.println("Vehicle: " + manufactureYear + " " + brand + " " + variant + ", Fuel: " + fuelCapacity);
    }
}

class Sedan extends Vehicle {
    private int numberOfDoors;

    public Sedan(String brand, String variant, int manufactureYear, double fuelCapacity, int numberOfDoors) {
        super(brand, variant, manufactureYear, fuelCapacity);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public void showDetails() {
        super.showDetails();
        System.out.println("Category: Sedan, Doors: " + numberOfDoors);
    }
}

class Lorry extends Vehicle {
    private double cargoLimit;

    public Lorry(String brand, String variant, int manufactureYear, double fuelCapacity, double cargoLimit) {
        super(brand, variant, manufactureYear, fuelCapacity);
        this.cargoLimit = cargoLimit;
    }

    @Override
    public void showDetails() {
        super.showDetails();
        System.out.println("Category: Lorry, Cargo Capacity: " + cargoLimit + " tons");
    }
}

class Bike extends Vehicle {
    private boolean isSportBike;

    public Bike(String brand, String variant, int manufactureYear, double fuelCapacity, boolean isSportBike) {
        super(brand, variant, manufactureYear, fuelCapacity);
        this.isSportBike = isSportBike;
    }

    @Override
    public void showDetails() {
        super.showDetails();
        System.out.println("Category: Bike, Sport Model: " + (isSportBike ? "Yes" : "No"));
    }
}

public class VehicleDemo {
    public static void main(String[] args) {
        Vehicle car = new Sedan("Honda", "Civic", 2023, 45, 4);
        Vehicle truck = new Lorry("Tata", "Prima", 2021, 120, 25);
        Vehicle motorcycle = new Bike("Yamaha", "R15", 2022, 18, true);

        Vehicle[] garage = {car, truck, motorcycle};

        for (Vehicle v : garage) {
            v.ignite();
            v.addFuel(8);
            v.showDetails();
            v.halt();
            System.out.println();
        }
    }
}
