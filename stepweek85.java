import java.util.*;

class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Product {
    String name;
    double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

interface PaymentMethod {
    boolean processPayment(double amount);
    String getName();
}

class CreditCardPayment implements PaymentMethod {
    public boolean processPayment(double amount) {
        return true;
    }

    public String getName() {
        return "Credit Card";
    }
}

class PayPalPayment implements PaymentMethod {
    public boolean processPayment(double amount) {
        return false;
    }

    public String getName() {
        return "PayPal";
    }
}

class BankTransferPayment implements PaymentMethod {
    public boolean processPayment(double amount) {
        return true;
    }

    public String getName() {
        return "Bank Transfer";
    }
}

class Order {
    String orderId;
    Customer customer;
    Map<Product, Integer> items = new LinkedHashMap<>();
    String status = "Pending";

    Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        System.out.println("Order created for " + customer.name);
    }

    void addProduct(Product product, int quantity) {
        if (quantity > 0)
            items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    double getTotal() {
        double total = 0;

        for (Map.Entry<Product, Integer> item : items.entrySet())
            total += item.getKey().price * item.getValue();

        return total;
    }

    void pay(PaymentMethod method) {
        if (items.isEmpty()) {
            System.out.println("Cannot process payment for an empty order");
            return;
        }

        if (status.equals("Paid")) {
            System.out.println("Order is already paid");
            return;
        }

        System.out.println("Payment initiated via " + method.getName()
                + " for Order " + orderId);

        if (method.processPayment(getTotal())) {
            status = "Paid";
            System.out.println("Payment for Order " + orderId + " successful");
        } else {
            System.out.println("Payment for Order " + orderId + " failed");
        }

        System.out.println("Order status: " + status);
    }
}

public class Main {
    public static void main(String[] args) {
        Customer x = new Customer("Customer X");
        Order orderX = new Order("X", x);

        orderX.addProduct(new Product("Product A", 20), 2);
        orderX.addProduct(new Product("Product B", 30), 1);
        orderX.pay(new CreditCardPayment());

        Customer y = new Customer("Customer Y");
        Order orderY = new Order("Y", y);
        orderY.pay(new CreditCardPayment());

        Customer z = new Customer("Customer Z");
        Order orderZ = new Order("Z", z);

        orderZ.addProduct(new Product("Product C", 50), 1);
        orderZ.pay(new PayPalPayment());
    }
}
