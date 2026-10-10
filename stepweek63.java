class LibraryMember {
    protected String memberId;
    protected int borrowLimit;
    protected int booksBorrowed;
    private int[] fineHistory = new int[10];
    private int fineCount;
    private int totalFine;

    public LibraryMember(String memberId, int borrowLimit) {
        if (memberId == null || memberId.trim().length() < 4 || borrowLimit <= 0)
            throw new IllegalArgumentException();

        this.memberId = memberId;
        this.borrowLimit = borrowLimit;
    }

    public void borrowBook() {
        if (booksBorrowed < borrowLimit)
            booksBorrowed++;
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    protected void chargeFine(int amount) {
        if (fineCount < fineHistory.length) {
            fineHistory[fineCount++] = amount;
            totalFine += amount;
        }
    }

    public int[] getFineHistory() {
        return java.util.Arrays.copyOf(fineHistory, fineCount);
    }

    public int getTotalFine() {
        return totalFine;
    }
}

class StudentMember extends LibraryMember {
    private String course;

    public StudentMember(String memberId, int borrowLimit, String course) {
        super(memberId, borrowLimit);
        this.course = course;
    }

    @Override
    protected void chargeFine(int amount) {
        super.chargeFine(amount / 2);
    }
}

class Main {
    public static void main(String[] args) {
        StudentMember s = new StudentMember("STU5", 3, "CSE");

        s.chargeFine(100);

        System.out.println(s.getTotalFine());
        System.out.println(java.util.Arrays.toString(s.getFineHistory()));
    }
}
