class LibraryMember {
    protected String memberId;
    protected int borrowLimit;
    protected int booksBorrowed;

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

    public void displayInfo() {
        System.out.print("General | Books: " + booksBorrowed);
    }
}

class StudentMember extends LibraryMember {
    protected String course;

    public StudentMember(String memberId, int borrowLimit, String course) {
        super(memberId, borrowLimit);
        this.course = course;
    }

    @Override
    public void displayInfo() {
        System.out.print("Student | Course: " + course +
                         " | Books: " + booksBorrowed);
    }
}

class HonorsStudentMember extends StudentMember {
    private int bonusLimit;

    public HonorsStudentMember(String memberId, int borrowLimit,
                               String course, int bonusLimit) {
        super(memberId, borrowLimit, course);
        this.bonusLimit = bonusLimit;
    }

    @Override
    public void displayInfo() {
        System.out.print("Honors Student | Course: " + course +
                         " | Bonus Limit: " + bonusLimit +
                         " | Books: " + booksBorrowed);
    }
}

class FacultyMember extends LibraryMember {
    private String department;

    public FacultyMember(String memberId, int borrowLimit, String department) {
        super(memberId, borrowLimit);
        this.department = department;
    }

    @Override
    public void displayInfo() {
        System.out.print("Faculty | Department: " + department +
                         " | Books: " + booksBorrowed);
    }
}

class Main {
    static String batchPrint(LibraryMember[] members) {
        StringBuilder report = new StringBuilder();

        for (LibraryMember member : members) {
            if (member instanceof StudentMember) {
                StudentMember student = (StudentMember) member;
                member.displayInfo();
                report.append("Student | Course: ")
                      .append(student.course)
                      .append(" | Books: ")
                      .append(member.getBooksBorrowed())
                      .append(" [Course via downcast: ")
                      .append(student.course)
                      .append("] | ");
            } else {
                report.append("General | Books: ")
                      .append(member.getBooksBorrowed())
                      .append(" | ");
            }
        }

        return report.toString();
    }

    public static void main(String[] args) {
        LibraryMember[] members = {
            new LibraryMember("LB5", 3),
            new StudentMember("STU6", 3, "ECE")
        };

        System.out.println(batchPrint(members));
    }
}
