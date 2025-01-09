import java.util.*;

public class ERationShopSystem {
    private static List<User> users = new ArrayList<>();
    private static List<InventoryItem> inventory = new ArrayList<>();
    private static List<Sale> sales = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static String loggedInUserRole;

    public static void main(String[] args) {
        initializeSampleData();
        while (true) {
            System.out.println("Welcome to the E-Ration Shop System");
            System.out.println("1. Admin Login");
            System.out.println("2. Shopkeeper Login");
            System.out.println("3. Customer Login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    shopkeeperLogin();
                    break;
                case 3:
                    customerLogin();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void initializeSampleData() {
        users.add(new User("admin", "admin123", "Admin"));
        users.add(new User("shopkeeper1", "shop123", "Shopkeeper"));
        users.add(new User("customer1", "cust123", "Customer"));

        inventory.add(new InventoryItem("Rice", 100, 40.0));
        inventory.add(new InventoryItem("Wheat", 50, 30.0));
    }

    private static void adminLogin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("admin123")) {
            loggedInUserRole = "Admin";
            adminMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage Users");
            System.out.println("2. View Inventory");
            System.out.println("3. Generate Report");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageUsers();
                    break;
                case 2:
                    viewInventory();
                    break;
                case 3:
                    generateReport();
                    break;
                case 4:
                    loggedInUserRole = null;
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void manageUsers() {
        System.out.println("Managing Users...");
        // Simple user management logic
        System.out.println("1. Add User");
        System.out.println("2. Remove User");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();
                System.out.print("Enter Role (Admin/Shopkeeper/Customer): ");
                String role = scanner.nextLine();
                users.add(new User(username, password, role));
                System.out.println("User added successfully.");
                break;
            case 2:
                System.out.print("Enter Username to remove: ");
                String usernameToRemove = scanner.nextLine();
                users.removeIf(user -> user.getUsername().equals(usernameToRemove));
                System.out.println("User removed successfully.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void viewInventory() {
        System.out.println("Current Inventory:");
        for (InventoryItem item : inventory) {
            System.out.println(item);
        }
    }

    private static void generateReport() {
        System.out.println("Sales Report:");
        for (Sale sale : sales) {
            System.out.println(sale);
        }
    }

    private static void shopkeeperLogin() {
        System.out.print("Enter Shopkeeper Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (users.stream().anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password) && user.getRole().equals("Shopkeeper"))) {
            loggedInUserRole = "Shopkeeper";
            shopkeeperMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void shopkeeperMenu() {
        while (true) {
            System.out.println("\nShopkeeper Menu:");
            System.out.println("1. Record Sale");
            System.out.println("2. View Inventory");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    recordSale();
                    break;
                case 2:
                    viewInventory();
                    break;
                case 3:
                    loggedInUserRole = null;
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void recordSale() {
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (InventoryItem item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName) && item.getStock() >= quantity) {
                item.setStock(item.getStock() - quantity);
                double totalPrice = item.getPrice() * quantity;
                sales.add(new Sale(itemName, quantity, totalPrice));
                System.out.println("Sale recorded successfully.");
                return;
            }
        }
        System.out.println("Item not available or insufficient stock.");
    }

    private static void customerLogin() {
        System.out.print("Enter Customer Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (users.stream().anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password) && user.getRole().equals("Customer"))) {
            loggedInUserRole = "Customer";
            customerMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void customerMenu() {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. View Stock Availability");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewStockAvailability();
                    break;
                case 2:
                    loggedInUserRole = null;
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void viewStockAvailability() {
        System.out.println("Available Stock:");
        for (InventoryItem item : inventory) {
            System.out.println(item.getName() + " - " + item.getStock() + " available at price " + item.getPrice());
        }
    }
}

class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

class InventoryItem {
    private String name;
    private int stock;
    private double price;

    public InventoryItem(String name, int stock, double price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - " + stock + " in stock at $" + price;
    }
}

class Sale {
    private String itemName;
    private int quantity;
    private double totalPrice;

    public Sale(String itemName, int quantity, double totalPrice) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Sold: " + quantity + " of " + itemName + " for $" + totalPrice;
    }
}