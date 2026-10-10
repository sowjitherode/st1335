import java.time.LocalDate;
import java.util.*;

abstract class Room {
    int roomNumber;

    Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    abstract double calculatePrice(long days);
}

class StandardRoom extends Room {
    StandardRoom(int roomNumber) {
        super(roomNumber);
    }

    double calculatePrice(long days) {
        return days * 100;
    }
}

class DeluxeRoom extends Room {
    DeluxeRoom(int roomNumber) {
        super(roomNumber);
    }

    double calculatePrice(long days) {
        return days * 200;
    }
}

class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Reservation {
    Room room;
    Customer customer;
    LocalDate start;
    LocalDate end;
    LocalDate cancellationDeadline;
    boolean active = true;

    Reservation(Room room, Customer customer, LocalDate start,
                LocalDate end, LocalDate deadline) {
        this.room = room;
        this.customer = customer;
        this.start = start;
        this.end = end;
        this.cancellationDeadline = deadline;
    }

    boolean overlaps(LocalDate s, LocalDate e) {
        return active && start.isBefore(e) && s.isBefore(end);
    }

    void cancel(LocalDate today) {
        if (!active) {
            System.out.println("Reservation already cancelled");
        } else if (today.isAfter(cancellationDeadline)) {
            System.out.println("Cancellation deadline has passed");
        } else {
            active = false;
            System.out.println("Reservation for " + customer.name
                    + ", Room " + room.roomNumber
                    + " cancelled successfully");
        }
    }
}

public class Main {
    static List<Reservation> reservations = new ArrayList<>();

    static boolean isAvailable(Room room, LocalDate start, LocalDate end) {
        for (Reservation r : reservations) {
            if (r.room == room && r.overlaps(start, end))
                return false;
        }
        return true;
    }

    static Reservation book(Room room, Customer customer,
                            LocalDate start, LocalDate end,
                            LocalDate deadline) {
        if (!start.isBefore(end)) {
            System.out.println("Invalid reservation dates");
            return null;
        }

        if (!isAvailable(room, start, end)) {
            System.out.println("Room " + room.roomNumber
                    + " is not available from " + start + " to " + end);
            return null;
        }

        Reservation r = new Reservation(
                room, customer, start, end, deadline);

        reservations.add(r);

        long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);

        System.out.println("Reservation confirmed for " + customer.name
                + ", Room " + room.roomNumber + " (" + start + "-" + end + ")");
        System.out.println("Price: $" + room.calculatePrice(days));

        return r;
    }

    public static void main(String[] args) {
        Room standard = new StandardRoom(101);
        Room deluxe = new DeluxeRoom(201);

        Customer a = new Customer("Customer A");
        Customer b = new Customer("Customer B");
        Customer c = new Customer("Customer C");

        LocalDate start1 = LocalDate.of(2027, 1, 1);
        LocalDate end1 = LocalDate.of(2027, 1, 5);

        if (isAvailable(standard, start1, end1))
            System.out.println("Standard Room 101 is available");

        Reservation r = book(standard, a, start1, end1,
                LocalDate.of(2026, 12, 25));

        book(standard, b, LocalDate.of(2027, 1, 3),
                LocalDate.of(2027, 1, 7), LocalDate.of(2027, 1, 1));

        if (r != null)
            r.cancel(LocalDate.of(2026, 12, 20));

        book(deluxe, c, LocalDate.of(2027, 2, 10),
                LocalDate.of(2027, 2, 12), LocalDate.of(2027, 2, 1));
    }
}
