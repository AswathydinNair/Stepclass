import java.util.*;

class Product {
    String productId;
    String productName;
    double price;
    String category;
    int stockQuantity;

    // Static variables
    static int totalProducts = 0;
    static Set<String> categories = new HashSet<>();

    // Constructor
    public Product(String productId, String productName, double price, String category, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;

        totalProducts++;
        categories.add(category);
    }

    // Static method: find by ID
    public static Product findProductById(Product[] products, String productId) {
        for (Product p : products) {
            if (p != null && p.productId.equals(productId)) {
                return p;
            }
        }
        return null;
    }

    // Static method: get by category
    public static List<Product> getProductsByCategory(Product[] products, String category) {
        List<Product> list = new ArrayList<>();
        for (Product p : products) {
            if (p != null && p.category.equalsIgnoreCase(category)) {
                list.add(p);
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return productId + " - " + productName + " (" + category + ") $" + price + " | Stock: " + stockQuantity;
    }
}

class ShoppingCart {
    String cartId;
    String customerName;
    List<Product> products;
    List<Integer> quantities;
    double cartTotal;

    public ShoppingCart(String cartId, String customerName) {
        this.cartId = cartId;
        this.customerName = customerName;
        this.products = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.cartTotal = 0.0;
    }

    public void addProduct(Product product, int quantity) {
        if (product.stockQuantity < quantity) {
            System.out.println("Not enough stock for: " + product.productName);
            return;
        }
        int index = products.indexOf(product);
        if (index >= 0) {
            quantities.set(index, quantities.get(index) + quantity);
        } else {
            products.add(product);
            quantities.add(quantity);
        }
        product.stockQuantity -= quantity;
        calculateTotal();
        System.out.println(quantity + " x " + product.productName + " added to cart.");
    }

    public void removeProduct(String productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).productId.equals(productId)) {
                Product p = products.get(i);
                int qty = quantities.get(i);
                p.stockQuantity += qty;
                products.remove(i);
                quantities.remove(i);
                calculateTotal();
                System.out.println("Removed " + p.productName + " from cart.");
                return;
            }
        }
        System.out.println("Product not found in cart.");
    }

    public void calculateTotal() {
        cartTotal = 0.0;
        for (int i = 0; i < products.size(); i++) {
            cartTotal += products.get(i).price * quantities.get(i);
        }
    }

    public void displayCart() {
        if (products.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("\n--- Shopping Cart ---");
        for (int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i).productName + " x " + quantities.get(i) + " = $" +
                    (products.get(i).price * quantities.get(i)));
        }
        System.out.println("Cart Total: $" + cartTotal);
    }

    public void checkout() {
        if (products.isEmpty()) {
            System.out.println("Cart is empty. Cannot checkout.");
            return;
        }
        displayCart();
        System.out.println("Thank you for your purchase, " + customerName + "!");
        products.clear();
        quantities.clear();
        cartTotal = 0.0;
    }
}

public class ShoppingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create product catalog
        Product[] catalog = {
            new Product("P001", "Laptop", 1200.50, "Electronics", 5),
            new Product("P002", "Smartphone", 800.00, "Electronics", 10),
            new Product("P003", "Headphones", 150.00, "Electronics", 15),
            new Product("P004", "Shoes", 75.00, "Fashion", 20),
            new Product("P005", "T-Shirt", 20.00, "Fashion", 30),
            new Product("P006", "Backpack", 50.00, "Fashion", 12),
            new Product("P007", "Book", 12.99, "Books", 40),
            new Product("P008", "Notebook", 5.99, "Books", 25),
            new Product("P009", "Table Lamp", 45.00, "Home", 10),
            new Product("P010", "Coffee Maker", 99.99, "Home", 8)
        };

        System.out.print("Enter your name: ");
        String customerName = sc.nextLine();
        ShoppingCart cart = new ShoppingCart("CART001", customerName);

        int choice;
        do {
            System.out.println("\n--- Online Shopping Menu ---");
            System.out.println("1. View All Products");
            System.out.println("2. View Products by Category");
            System.out.println("3. Add Product to Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Checkout");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Product Catalog ---");
                    for (Product p : catalog) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.println("Available Categories: " + Product.categories);
                    System.out.print("Enter category: ");
                    String cat = sc.nextLine();
                    List<Product> byCat = Product.getProductsByCategory(catalog, cat);
                    if (byCat.isEmpty()) {
                        System.out.println("No products found in this category.");
                    } else {
                        for (Product p : byCat) {
                            System.out.println(p);
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter product ID: ");
                    String pid = sc.nextLine();
                    Product prod = Product.findProductById(catalog, pid);
                    if (prod == null) {
                        System.out.println("Product not found.");
                    } else {
                        System.out.print("Enter quantity: ");
                        int qty = sc.nextInt();
                        cart.addProduct(prod, qty);
                    }
                    break;
                case 4:
                    System.out.print("Enter product ID to remove: ");
                    String rid = sc.nextLine();
                    cart.removeProduct(rid);
                    break;
                case 5:
                    cart.displayCart();
                    break;
                case 6:
                    cart.checkout();
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
