class FoodOrder {
    String customerName;
    String foodItem;
    int quantity;
    double price;

    FoodOrder() {
        this.customerName = "dinesh";
        this.foodItem = "soup";
        this.quantity = 4;
        this.price = 350.0;
    }

    FoodOrder(String foodItem) {
        this.customerName = "anjali";
        this.foodItem = foodItem;
        this.quantity = 1;
        this.price = 100.0; 
    }

    FoodOrder(String foodItem, int quantity) {
        this.customerName = "aswathy";
        this.foodItem = foodItem;
        this.quantity = quantity;
        double fixedRate = 100.0;
        this.price = quantity * fixedRate;
    }

    FoodOrder(String customerName, String foodItem, int quantity, double pricePerItem) {
        this.customerName = customerName;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.price = quantity * pricePerItem;
    }

    void printBill() {
        System.out.println("Food Order Bill");
        System.out.println("Customer : " + customerName);
        System.out.println("Food Item: " + foodItem);
        System.out.println("Quantity : " + quantity);
        System.out.println("Total    : ₹" + price);
    }
}

public class FoodDeliverySystem {
    public static void main(String[] args) {
        FoodOrder o1 = new FoodOrder();
        FoodOrder o2 = new FoodOrder("Pizza");
        FoodOrder o3 = new FoodOrder("Burger", 3);
        FoodOrder o4 = new FoodOrder("Alia", "Pasta", 2, 150.0);

        o1.printBill();
        o2.printBill();
        o3.printBill();
        o4.printBill();
    }
}

