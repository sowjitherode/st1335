abstract class Vehicle {
    String name;
    boolean available = true;

    Vehicle(String name) {
        this.name = name;
    }

    abstract double calculateCharge(int days);
}

class Sedan extends Vehicle {
    Sedan(String name) {
        super(name);
    }

    double calculateCharge(int days) {
        return days * 50;
    }
}

class SUV extends Vehicle {
    SUV(String name) {
        super(name);
    }

    double calculateCharge(int days) {
        return days * 80;
    }
}

class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Rental {
    Vehicle vehicle;
    Customer customer;
    int days;

    Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
    }

    void returnVehicle() {
        vehicle.available = true;
        System.out.println(vehicle.name + " returned by " + customer.name);
    }
}

public class Main {
    static Rental rentVehicle(Vehicle v, Customer c, int days) {
        if (!v.available) {
            System.out.println(v.name + " is currently unavailable");
            return null;
        }

        v.available = false;
        Rental rental = new Rental(v, c, days);

        System.out.println(v.name + " rented successfully by " + c.name);
        System.out.println("Rental charge: $" + v.calculateCharge(days));

        return rental;
    }

    public static void main(String[] args) {
        Sedan sedan = new Sedan("Sedan A");
        SUV suv = new SUV("SUV B");

        Customer c1 = new Customer("Customer 1");
        Customer c2 = new Customer("Customer 2");
        Customer c3 = new Customer("Customer 3");

        Rental r1 = rentVehicle(sedan, c1, 3);
        rentVehicle(sedan, c2, 2);

        if (r1 != null)
            r1.returnVehicle();

        rentVehicle(suv, c3, 5);
    }
}
