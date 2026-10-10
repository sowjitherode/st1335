interface MembershipPlan {
    int getDuration();
    double calculateFee();
    String getName();
}

class MonthlyPlan implements MembershipPlan {
    public int getDuration() {
        return 1;
    }

    public double calculateFee() {
        return 1000;
    }

    public String getName() {
        return "Monthly";
    }
}

class QuarterlyPlan implements MembershipPlan {
    public int getDuration() {
        return 3;
    }

    public double calculateFee() {
        return 3000 * 0.90;
    }

    public String getName() {
        return "Quarterly";
    }
}

class AnnualPlan implements MembershipPlan {
    public int getDuration() {
        return 12;
    }

    public double calculateFee() {
        return 12000 * 0.75;
    }

    public String getName() {
        return "Annual";
    }
}

class Member {
    String name;
    Membership membership;

    Member(String name) {
        this.name = name;
    }

    void buyMembership(MembershipPlan plan) {
        membership = new Membership(this, plan);

        System.out.printf("%s membership created for %s. Fee: ₹%.2f. Status: Active.%n",
                plan.getName(), name, plan.calculateFee());
    }

    void checkIn() {
        if (membership == null) {
            System.out.println("Check-in denied: No membership.");
        } else {
            membership.checkIn();
        }
    }
}

class Membership {
    private Member member;
    private MembershipPlan plan;
    private String status = "Active";

    Membership(Member member, MembershipPlan plan) {
        this.member = member;
        this.plan = plan;
    }

    void checkIn() {
        if (status.equals("Active")) {
            System.out.println(member.name + " checked in successfully.");
        } else {
            System.out.println("Check-in denied: " + member.name
                    + "'s membership is " + status + ".");
        }
    }

    void freeze() {
        if (status.equals("Expired")) {
            System.out.println("Cannot freeze an Expired membership.");
        } else if (status.equals("Frozen")) {
            System.out.println("Membership is already Frozen.");
        } else {
            status = "Frozen";
            System.out.println(member.name + "'s membership frozen. Status: Frozen.");
        }
    }

    void unfreeze() {
        if (status.equals("Expired")) {
            System.out.println("Cannot unfreeze an Expired membership.");
        } else if (status.equals("Frozen")) {
            status = "Active";
            System.out.println(member.name + "'s membership unfrozen. Status: Active.");
        }
    }

    void expire() {
        status = "Expired";
        System.out.println(member.name + "'s membership expired. Status: Expired.");
    }
}

public class Main {
    public static void main(String[] args) {
        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        asha.buyMembership(new QuarterlyPlan());
        ravi.buyMembership(new MonthlyPlan());

        asha.checkIn();
        asha.membership.freeze();
        asha.checkIn();

        ravi.membership.expire();
        ravi.membership.freeze();
    }
}
