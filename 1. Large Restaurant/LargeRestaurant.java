// LARGE RESTAURANT:
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

abstract class Person {
    private String name;
    private String phoneNumber;

    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Getters:
    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setters:
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // getInfo method:
    public abstract String getInfo();
}

class Customer extends Person {
    private String customerID;
    private int loyaltyPoints;
    private static int nextCustomerID = 1;

    public Customer(String name, String phoneNumber) {
        super(name, phoneNumber);
        this.customerID = String.format("C%03d", nextCustomerID++);
        this.loyaltyPoints = 0;
    }
    // Getters:
    public String getCustomerID() {
        return customerID;
    }
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    // Setters:
    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public void addLoyaltyPoints(long totalPurchaseAmount) {
        // more than 1 million toman: +2 points
        if (totalPurchaseAmount > 1000000) {
            this.loyaltyPoints += 2;
        }
        // more than 5 thousands toman: +1 points
        else if (totalPurchaseAmount > 500000) {
            this.loyaltyPoints += 1;
        }
    }

    public double getDiscountPercentage() {
        if (this.loyaltyPoints >= 5) {
            return 0.10;
        }
        else if (this.loyaltyPoints >= 3) {
            return 0.05;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public String getInfo() {
        // ID: C00?, Name: (Name), Phone: (09876543210), Loyalty Points: ?
        return String.format("ID: %s, Name: %s, Phone: %s, Loyalty Points: %d",
                customerID, getName(), getPhoneNumber(), loyaltyPoints);
    }
}

class Employee extends Person {
    private String employeeID;
    private String position;
    private long salary;
    private int hoursWorked;
    private static int nextEmployeeID = 1;
    private static final int MIN_HOURS = 160;

    public Employee(String name, String phoneNumber, String position, long salary) {
        super(name, phoneNumber);
        this.employeeID = String.format("E%03d", nextEmployeeID++);
        this.position = position;
        this.salary = salary;
        this.hoursWorked = MIN_HOURS;
    }

    // Getters:
    public String getEmployeeID() {
        return employeeID;
    }
    public String getPosition() {
        return position;
    }
    public double getSalary() {
        return salary;
    }
    public int getHoursWorked() {
        return hoursWorked;
    }

    public void addHoursWorked(int hours) {
        this.hoursWorked += hours;
    }
    public double calculateSalary() {
        if (this.hoursWorked <= MIN_HOURS) {
            return salary;
        }
        else {
            int overtimeHours = this.hoursWorked - MIN_HOURS;
            double hourlyRate = (double) salary / (double) MIN_HOURS;
            double overtimePay = hourlyRate * overtimeHours * 1.5;
            double total = (double) salary + overtimePay;
            return Math.round(total);
        }
    }

    @Override
    public String getInfo() {
        // ID: E00?, Name: (Name), Phone: (09876543210), Position: (???) , HoursWorked: (160)
        return String.format("ID: %s, Name: %s, Phone: %s, Position: %s, HoursWorked: %d",
                employeeID, getName(), getPhoneNumber(), position, hoursWorked);
    }
}

abstract class MenuItem {
    private int itemID;
    private String name;
    private long price;
    private String category;
    private static int nextItemID = 1;

    public MenuItem(String name, long price, String category) {
        this.itemID = nextItemID++;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters:
    public int getItemID() {
        return itemID;
    }
    public String getName() {
        return name;
    }
    public long getPrice() {
        return price;
    }
    public String getCategory() {
        return category;
    }

    // Setters:
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(long price) {
        this.price = price;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public abstract String getDetails();
}

class Food extends MenuItem {
    private String spiceLevel; // mild, medium, spicy
    private int preparationTime;

    public Food(String name, long price, String spiceLevel, int preparationTime) {
        super(name, price, "food");
        this.spiceLevel = spiceLevel;
        this.preparationTime = preparationTime;
    }

    // Getters:
    public String getSpiceLevel() {
        return spiceLevel;
    }
    public int getPreparationTime() {
        return preparationTime;
    }

    // Setters:
    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }
    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    @Override
    public String getDetails() {
        // ID: ?, Name: (Name), Price: ???, Category: ???, Spice: ???, Preparation Time: ? min
        return String.format("ID: %d, Name: %s, Price: %d, Category: %s, Spice: %s, Preparation Time: %d min",
                getItemID(), getName(), getPrice(), getCategory(), spiceLevel, preparationTime);
    }
}

class Beverage extends MenuItem {
    private String size;
    private String temperature;

    public Beverage(String name, long price, String size, String temperature) {
        super(name, price, "beverage");
        this.size = size;
        this.temperature = temperature;
    }

    // Getters:
    public String getSize() {
        return size;
    }
    public String getTemperature() {
        return temperature;
    }

    // Setters:
    public void setSize(String size) {
        this.size = size;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String getDetails() {
        // ID: ?, Name: ???, Price: ???, Category: Beverage, Size: ???, Temperature: ???
        return String.format("ID: %d, Name: %s, Price: %d, Category: %s, Size: %s, Temperature: %s",
                getItemID(), getName(), getPrice(), getCategory(), size, temperature);
    }
}

class Order {
    private int orderID;
    private Customer customer;
    private List<MenuItem> items;
    private long totalAmount;
    private static int nextOrderID = 1;

    public Order(Customer customer, List<MenuItem> items) {
        this.orderID = nextOrderID++;
        this.customer = customer;
        this.items = items;
        this.totalAmount = 0;
    }

    // Getters:
    public int getOrderID() {
        return orderID;
    }
    public Customer getCustomer() {
        return customer;
    }
    public double getTotalAmount() {
        return totalAmount;
    }

    public void calculateTotal() {
        long subtotal = 0;
        for (MenuItem item : items) {
            subtotal += item.getPrice();
        }
        customer.addLoyaltyPoints(subtotal);
        double discountRate = customer.getDiscountPercentage();
        long discountAmount = Math.round(subtotal * discountRate);
        this.totalAmount = subtotal - discountAmount;
    }

    public String getOrderSummary() {
        // Order ID: ?, Customer: ?, Total Amount: ???
        StringBuilder summary = new StringBuilder();
        summary.append(String.format("Order ID: %d, Customer: %s, Total Amount: %d\n",
                orderID, customer.getName(), totalAmount));

        summary.append("Items: ");
        for (int i=0; i<items.size(); i++) {
            summary.append(items.get(i).getName());
            if (i < items.size() - 1) {
                summary.append(", ");
            }
        }
        return summary.toString();
    }
}

public class LargeRestaurant {
    public static void main(String[] args) {
        System.out.println("------------------------------------");
        System.out.println("Large Restaurant Management System: ");
        System.out.println("------------------------------------");

        // 1. Employees:
        Employee E1 = new Employee("Elham Karimi", "09876543210", "Chef", 10000000);
        Employee E2 = new Employee("Ameen Akbari", "09876543210", "Accountant", 8000000);
        Employee E3 = new Employee("Zahra Rasti", "09876543210", "Waiter", 6000000);
        List<Employee>  employees = Arrays.asList(E1, E2, E3);

        // 2.Customers:
        Customer C1 = new Customer("Reza Mohammadi", "09876543210");
        Customer C2 = new Customer ("Mina Ahmadi", "09876543210");
        Customer C3 = new Customer("Farhad Hassani", "0987654321");
        Customer C4 = new Customer("Fatemeh Afshar", "09876543210");
        Customer C5 = new Customer("Saman Naderi", "0987654321");
        List<Customer> customers = Arrays.asList(C1, C2, C3, C4, C5);

        // 3. Menu Items:
        MenuItem M1 = new Food("Pizza", 350000, "Spicy", 30);
        MenuItem M2 = new Food("Burger", 275000, "Mild", 30);
        MenuItem M3 = new Food("Caesar Salad", 150000, "Mild", 15);
        MenuItem M4 = new Beverage("Soda", 50000, "Medium", "Cold");
        MenuItem M5 = new Beverage("Smoothie", 100000, "Large", "Cold");
        MenuItem M6 = new Beverage("Espresso", 75000, "Small", "Hot");
        List<MenuItem> menu = Arrays.asList(M1, M2, M3, M4, M5, M6);

        // 4. Orders:
        System.out.println("------------------------------------");
        System.out.println("\nRegistering and calculating orders:");
        System.out.println("------------------------------------");

        // High Loyalty Points (Mina):
        System.out.println("\nOrders for: Mina Ahmadi");
        createAndProcessOrder(C2, Arrays.asList(M1, M3, M4)); // 550.000 >> +1 point
        createAndProcessOrder(C2, Arrays.asList(M1, M2, M2, M3, M5)); // 1.150.000 > +2 points > total points: 3
        // Next Order > 3 points > %5 off
        createAndProcessOrder(C2, Arrays.asList(M1, M1, M1, M4, M4, M4, M4)); // 1.250.000 - %5 > +2 points > total points: 5
        // Next Order > 5 points > %10 off
        createAndProcessOrder(C2, Arrays.asList(M2, M2, M2, M2, M3, M4, M4, M5)); // 1175000 - %10 (+2 points), total points: 7

        // Medium Loyalty Points:
        System.out.println("\nOrders for: Saman Naderi");
        createAndProcessOrder(C5, Arrays.asList(M2, M3, M5)); // 525.000 > +1 point
        createAndProcessOrder(C5, Arrays.asList(M1, M3, M4)); // 550000 > +1 Point > total points: 2
        createAndProcessOrder(C5, Arrays.asList(M1, M2, M3, M4)); // 875000 > +1 Point > total points: 3
        // Next order > 3 points > %5 off

        //Low Loyalty Points:
        System.out.println("\nOrders for: Farhad Hassnai");
        createAndProcessOrder(C3, Arrays.asList(M6, M6, M6, M6, M5, M5)); // 500.000 > +1 Point > total points: 1

        // 5. The Most Loyal Customer:
        System.out.println("------------------------------------");
        System.out.println("\nThe Most Loyal Customer:");
        System.out.println("------------------------------------");
        Customer mostLoyal = findMostLoyalCustomer(customers);
        if (mostLoyal != null) {
            System.out.printf("Most Loyal Customer: %s (ID: %s), Loyalty Points: %d\n",
                    mostLoyal.getName(), mostLoyal.getCustomerID(), mostLoyal.getLoyaltyPoints());
        }
        else {
            System.out.println("No customers available.");
        }

        // 6. Print Summary Information for Customers:
        System.out.println("------------------------------------");
        System.out.println("\nCustomer Information Summary: ");
        System.out.println("------------------------------------");
        for (Customer customer: customers) {
            System.out.println(customer.getInfo());
        }

        // 7. Salaries:
        System.out.println("------------------------------------");
        System.out.println("\nCalculating employee salaries: ");
        System.out.println("------------------------------------");

        // Add overtime hours (these add on top of the base 160 hours)
        E1.addHoursWorked(18); // total hours: 178
        E2.addHoursWorked(0); // total hours: 160
        E3.addHoursWorked(20); // total hours: 180

        for (Employee employee: employees) {
            double finalSalary = employee.calculateSalary();
            System.out.printf("Employee ID: %s, Name: %s, Hours Worked: %d, Final Salary: %.0f\n",
                    employee.getEmployeeID(), employee.getName(), employee.getHoursWorked(), finalSalary);

        }

        // 8. Print Summary Information for Employees:
        System.out.println("------------------------------------");
        System.out.println("\nEmployees Information Summary: ");
        System.out.println("------------------------------------");
        for (Employee employee: employees) {
            System.out.println(employee.getInfo());
        }

        // 9. Summary of Menu Items:
        System.out.println("------------------------------------");
        System.out.println("\nMenu Items Summary: ");
        System.out.println("------------------------------------");
        for (MenuItem item: menu) {
            System.out.println(item.getDetails());
        }
    }

    // Helping Methods:
    // Helping method for processing orders:
    private static Order createAndProcessOrder(Customer customer, List<MenuItem> items) {
        Order order = new Order(customer, items);
        order.calculateTotal();
        System.out.println(order.getOrderSummary());
        return order;
    }

    // Helping method for finding the loyal customers:
    private static Customer findMostLoyalCustomer(List<Customer> customers) {
        return customers.stream()
                .max(Comparator.comparingInt(Customer::getLoyaltyPoints))
                .orElse(null);
    }
}