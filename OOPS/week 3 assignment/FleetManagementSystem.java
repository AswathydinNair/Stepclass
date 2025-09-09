import java.util.*;

abstract class Vehicle {
    String vehicleId;
    String brand;
    String model;
    int year;
    double mileage;
    String fuelType;
    String currentStatus; // Available, In Trip, Under Maintenance
    Driver assignedDriver;
    double runningCost;

    static int totalVehicles = 0;
    static double fleetValue = 0;
    static String companyName = "Mystery Transport";
    static double totalFuelConsumption = 0;

    public Vehicle(String vehicleId, String brand, String model, int year, double mileage, String fuelType, double value) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.currentStatus = "Available";
        this.runningCost = 0;
        totalVehicles++;
        fleetValue += value;
    }

    public void assignDriver(Driver d) {
        if (assignedDriver == null) {
            assignedDriver = d;
            d.assignedVehicle = this;
            System.out.println("Driver " + d.driverName + " assigned to vehicle " + vehicleId);
        } else {
            System.out.println("Vehicle already has a driver assigned.");
        }
    }

    public void scheduleMaintenance() {
        currentStatus = "Under Maintenance";
        runningCost += 500; // flat maintenance cost
        System.out.println(vehicleId + " is scheduled for maintenance.");
    }

    public void updateMileage(double km, double fuelConsumed) {
        mileage += km;
        totalFuelConsumption += fuelConsumed;
        runningCost += km * 0.5; // cost per km
        System.out.println(vehicleId + " updated mileage by " + km + " km.");
    }

    public void checkServiceDue() {
        if (mileage % 10000 < 500) {
            System.out.println("Vehicle " + vehicleId + " is due for service soon.");
        } else {
            System.out.println("Vehicle " + vehicleId + " service not due yet.");
        }
    }

    public abstract double calculateRunningCost();

    public static void getFleetUtilization(List<Vehicle> vehicles) {
        int available = 0, inTrip = 0, maintenance = 0;
        for (Vehicle v : vehicles) {
            if (v.currentStatus.equals("Available")) available++;
            else if (v.currentStatus.equals("In Trip")) inTrip++;
            else if (v.currentStatus.equals("Under Maintenance")) maintenance++;
        }
        System.out.println("\n--- Fleet Utilization Report ---");
        System.out.println("Available: " + available);
        System.out.println("In Trip: " + inTrip);
        System.out.println("Under Maintenance: " + maintenance);
    }

    public static double calculateTotalMaintenanceCost(List<Vehicle> vehicles) {
        double total = 0;
        for (Vehicle v : vehicles) total += v.runningCost;
        return total;
    }

    public static void getVehiclesByType(List<Vehicle> vehicles, String type) {
        System.out.println("\n--- Vehicles of type: " + type + " ---");
        for (Vehicle v : vehicles) {
            if (v.getClass().getSimpleName().equalsIgnoreCase(type)) {
                System.out.println(v.vehicleId + " - " + v.brand + " " + v.model + " (" + v.year + ")");
            }
        }
    }
}

class Car extends Vehicle {
    int passengerCapacity;

    public Car(String vehicleId, String brand, String model, int year, double mileage, String fuelType, double value, int passengerCapacity) {
        super(vehicleId, brand, model, year, mileage, fuelType, value);
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public double calculateRunningCost() {
        return runningCost + (passengerCapacity * 2);
    }
}

class Bus extends Vehicle {
    int seatingCapacity;

    public Bus(String vehicleId, String brand, String model, int year, double mileage, String fuelType, double value, int seatingCapacity) {
        super(vehicleId, brand, model, year, mileage, fuelType, value);
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public double calculateRunningCost() {
        return runningCost + (seatingCapacity * 0.5);
    }
}

class Truck extends Vehicle {
    double loadCapacity; // in tons

    public Truck(String vehicleId, String brand, String model, int year, double mileage, String fuelType, double value, double loadCapacity) {
        super(vehicleId, brand, model, year, mileage, fuelType, value);
        this.loadCapacity = loadCapacity;
    }

    @Override
    public double calculateRunningCost() {
        return runningCost + (loadCapacity * 10);
    }
}

class Driver {
    String driverId;
    String driverName;
    String licenseType;
    Vehicle assignedVehicle;
    int totalTrips;

    public Driver(String driverId, String driverName, String licenseType) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.licenseType = licenseType;
        this.totalTrips = 0;
    }

    public void completeTrip(double km, double fuelUsed) {
        if (assignedVehicle != null) {
            assignedVehicle.currentStatus = "In Trip";
            assignedVehicle.updateMileage(km, fuelUsed);
            totalTrips++;
            assignedVehicle.currentStatus = "Available";
            System.out.println("Trip completed by " + driverName + " with vehicle " + assignedVehicle.vehicleId);
        } else {
            System.out.println(driverName + " has no assigned vehicle.");
        }
    }
}

public class FleetManagementSystem {
    public static void main(String[] args) {
        List<Vehicle> fleet = new ArrayList<>();
        List<Driver> drivers = new ArrayList<>();

        Vehicle car1 = new Car("C1", "Toyota", "Camry", 2020, 25000, "Petrol", 30000, 5);
        Vehicle bus1 = new Bus("B1", "Volvo", "X900", 2019, 80000, "Diesel", 80000, 50);
        Vehicle truck1 = new Truck("T1", "Scania", "G450", 2021, 40000, "Diesel", 100000, 15);

        fleet.add(car1);
        fleet.add(bus1);
        fleet.add(truck1);

        Driver d1 = new Driver("D1", "Aswathy", "Car");
        Driver d2 = new Driver("D2", "krishan", "Bus");
        Driver d3 = new Driver("D3", "Carry", "Truck");

        drivers.add(d1);
        drivers.add(d2);
        drivers.add(d3);

        car1.assignDriver(d1);
        bus1.assignDriver(d2);
        truck1.assignDriver(d3);

        d1.completeTrip(120, 10);
        d2.completeTrip(300, 50);
        d3.completeTrip(200, 40);

        car1.checkServiceDue();
        bus1.scheduleMaintenance();
        truck1.checkServiceDue();

        for (Vehicle v : fleet) {
            System.out.println(v.vehicleId + " running cost: " + v.calculateRunningCost());
        }

        Vehicle.getFleetUtilization(fleet);
        System.out.println("\nTotal Maintenance Cost: " + Vehicle.calculateTotalMaintenanceCost(fleet));
        Vehicle.getVehiclesByType(fleet, "Truck");

        System.out.println("\nTotal Vehicles: " + Vehicle.totalVehicles);
        System.out.println("Fleet Value: " + Vehicle.fleetValue);
        System.out.println("Total Fuel Consumed: " + Vehicle.totalFuelConsumption + " liters");
    }
}
