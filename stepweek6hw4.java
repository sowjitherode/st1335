class GymMember {
    String memberId;
    int monthlyFee;
    int sessionsAttended;

    public GymMember(String memberId, int monthlyFee) {
        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
    }

    void attendSession() {
        sessionsAttended++;
    }

    int getSessionsAttended() {
        return sessionsAttended;
    }

    void displayInfo() {
        System.out.print("Standard | Sessions: " + sessionsAttended);
    }
}

class PremiumMember extends GymMember {
    String trainerName;

    public PremiumMember(String memberId, int monthlyFee, String trainerName) {
        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    @Override
    void displayInfo() {
        System.out.print("Premium | Trainer: " + trainerName
                + " | Sessions: " + sessionsAttended);
    }
}

public class Main {
    static String batchPrint(GymMember[] members) {
        StringBuilder result = new StringBuilder();

        for (GymMember member : members) {
            result.append("");

            java.io.ByteArrayOutputStream output =
                    new java.io.ByteArrayOutputStream();
            java.io.PrintStream original = System.out;

            try {
                System.setOut(new java.io.PrintStream(output));
                member.displayInfo();
            } finally {
                System.setOut(original);
            }

            result.append(output.toString());

            if (member instanceof PremiumMember) {
                PremiumMember premium = (PremiumMember) member;
                result.append(" [Trainer via downcast: ")
                      .append(premium.trainerName)
                      .append("]");
            }

            result.append(" | ");
        }

        return result.toString();
    }

    public static void main(String[] args) {
        GymMember[] members = {
            new GymMember("MEM6", 1000),
            new PremiumMember("MEM7", 2000, "Coach Riya")
        };

        System.out.println(batchPrint(members));
    }
}
