import java.util.*;

abstract class Seat {
    String seatId;

    Seat(String seatId) {
        this.seatId = seatId;
    }

    abstract double getPrice();
}

class RegularSeat extends Seat {
    RegularSeat(String id) {
        super(id);
    }

    double getPrice() {
        return 150;
    }
}

class PremiumSeat extends Seat {
    PremiumSeat(String id) {
        super(id);
    }

    double getPrice() {
        return 250;
    }
}

class ReclinerSeat extends Seat {
    ReclinerSeat(String id) {
        super(id);
    }

    double getPrice() {
        return 400;
    }
}

class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Show {
    String time;
    private Set<String> bookedSeats = new HashSet<>();
    private boolean started = false;

    Show(String time) {
        this.time = time;
    }

    boolean bookSeats(List<Seat> seats) {
        if (started) {
            System.out.println("Cannot book: Show has already started.");
            return false;
        }

        if (seats.size() == 0 || seats.size() > 6) {
            System.out.println("A booking must contain 1 to 6 seats.");
            return false;
        }

        Set<String> requested = new HashSet<>();

        for (Seat seat : seats) {
            if (!requested.add(seat.seatId) || bookedSeats.contains(seat.seatId)) {
                System.out.println("Seat " + seat.seatId + " is already booked for this show.");
                return false;
            }
        }

        for (Seat seat : seats) {
            bookedSeats.add(seat.seatId);
        }

        return true;
    }

    void releaseSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            bookedSeats.remove(seat.seatId);
        }
    }

    void startShow() {
        started = true;
    }

    boolean hasStarted() {
        return started;
    }
}

class Booking {
    Customer customer;
    Show show;
    List<Seat> seats;
    private boolean cancelled = false;

    Booking(Customer customer, Show show, List<Seat> seats) {
        this.customer = customer;
        this.show = show;
        this.seats = new ArrayList<>(seats);
    }

    void cancel() {
        if (cancelled) {
            System.out.println("Booking is already cancelled.");
            return;
        }

        if (show.hasStarted()) {
            System.out.println("Cannot cancel after the show starts.");
            return;
        }

        show.releaseSeats(seats);
        cancelled = true;

        List<String> ids = new ArrayList<>();
        for (Seat seat : seats) {
            ids.add(seat.seatId);
        }

        System.out.println(customer.name + "'s booking cancelled. Seats "
                + String.join(", ", ids) + " released.");
    }
}

public class Main {
    static void book(Customer customer, Show show, Seat... seats) {
        List<Seat> list = Arrays.asList(seats);

        if (show.bookSeats(list)) {
            double total = 0;
            List<String> ids = new ArrayList<>();

            for (Seat seat : seats) {
                total += seat.getPrice();
                ids.add(seat.seatId);
            }

            new Booking(customer, show, list);

            System.out.printf("Booking confirmed for %s: %s. Total: ₹%.2f.%n",
                    customer.name, String.join(", ", ids), total);
        }
    }

    public static void main(String[] args) {
        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Show show = new Show("7 PM");

        Booking ashaBooking = new Booking(asha, show, Arrays.asList(
                new RegularSeat("A1"),
                new RegularSeat("A2"),
                new PremiumSeat("F5")
        ));

        if (show.bookSeats(ashaBooking.seats)) {
            System.out.printf("Booking confirmed for Asha: A1, A2, F5. Total: ₹%.2f.%n",
                    150.0 + 150.0 + 250.0);
        }

        book(ravi, show, new RegularSeat("A2"));
        book(ravi, show, new ReclinerSeat("R1"));

        ashaBooking.cancel();

        book(neha, show, new RegularSeat("A2"));
    }
}
