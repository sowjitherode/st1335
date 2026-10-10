abstract class Employee {
    String name;

    Employee(String name) {
        this.name = name;
    }

    abstract boolean canTakeLeave(int days);
}

class FullTimeEmployee extends Employee {
    FullTimeEmployee(String name) {
        super(name);
    }

    boolean canTakeLeave(int days) {
        return days > 0 && days <= 30;
    }
}

class PartTimeEmployee extends Employee {
    PartTimeEmployee(String name) {
        super(name);
    }

    boolean canTakeLeave(int days) {
        return days > 0 && days <= 10;
    }
}

class LeaveRequest {
    Employee employee;
    String startDate;
    String endDate;
    String status = "Pending";

    LeaveRequest(Employee employee, String startDate, String endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    void review(String decision, String reviewer) {
        if (!status.equals("Pending")) {
            System.out.println("Cannot change leave request status from "
                    + status + " to " + decision);
            return;
        }

        if (decision.equals("Approved") || decision.equals("Rejected")) {
            status = decision;
            System.out.println(employee.name + "'s leave request ("
                    + startDate + "-" + endDate + ") "
                    + decision.toLowerCase() + " by " + reviewer);
        }
    }

    void changeStatus(String newStatus) {
        if (!status.equals("Pending") || newStatus.equals("Pending")) {
            System.out.println("Cannot change leave request status from "
                    + status + " to " + newStatus);
            return;
        }

        status = newStatus;
    }
}

public class Main {
    public static void main(String[] args) {
        Employee john = new FullTimeEmployee("John");
        Employee jane = new PartTimeEmployee("Jane");

        LeaveRequest r1 = new LeaveRequest(john, "Jan 1", "Jan 5");
        LeaveRequest r2 = new LeaveRequest(jane, "Feb 10", "Feb 11");

        System.out.println("Leave request submitted for John (Jan 1-5)");
        System.out.println("Status: " + r1.status);
        r1.review("Approved", "Alice");
        System.out.println("Status: " + r1.status);

        System.out.println("Leave request submitted for Jane (Feb 10-11)");
        System.out.println("Status: " + r2.status);
        r2.review("Rejected", "Bob");
        System.out.println("Status: " + r2.status);

        r1.changeStatus("Pending");
    }
}
