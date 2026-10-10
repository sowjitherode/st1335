class LoanReceipt {
    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        this.memberId = memberId;
        this.bookIds = bookIds == null ? null : bookIds.clone();
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds == null ? null : bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (bookIds == null || index < 0 || index >= bookIds.length)
            throw new IndexOutOfBoundsException();

        String[] updated = bookIds.clone();
        updated[index] = newId;

        return new LoanReceipt(memberId, updated);
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}

class CirculationLedger {
    private static String branchCode;

    static {
        branchCode = "PT-001";
    }

    static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;

        if (receipts != null) {
            for (LoanReceipt receipt : receipts) {
                if (receipt == null) {
                    nullSkipped++;
                } else {
                    processed++;

                    if (receipt instanceof ReferenceOnlyLoanReceipt)
                        referenceOnly++;
                }
            }
        }

        return processed + " processed | " + nullSkipped +
               " null skipped | " + referenceOnly + " reference-only";
    }

    public static void main(String[] args) {
        LoanReceipt r = new LoanReceipt(
            "LIB-8841", new String[]{"BK-100", "BK-101"}
        );

        String[] ids = r.getBookIds();
        ids[0] = "HACKED";

        System.out.println(r.getBookIds()[0]);

        LoanReceipt corrected = r.withCorrectedBookId(1, "BK-102");

        System.out.println(java.util.Arrays.toString(r.getBookIds()));
        System.out.println(java.util.Arrays.toString(corrected.getBookIds()));

        LoanReceipt[] receipts = {
            new ReferenceOnlyLoanReceipt(
                "LIB-001", new String[]{"BK-200"}, "Reading Room 3"
            ),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };

        System.out.println(CirculationLedger.processNightlyCirculation(receipts));
    }
}
