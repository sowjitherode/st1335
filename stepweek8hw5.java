import java.util.*;

interface NotificationChannel {
    void send(String studentName, String title);
}

class EmailChannel implements NotificationChannel {
    public void send(String name, String title) {
        System.out.println("[Email → " + name + "] " + title);
    }
}

class SmsChannel implements NotificationChannel {
    public void send(String name, String title) {
        System.out.println("[SMS → " + name + "] " + title);
    }
}

class AppChannel implements NotificationChannel {
    public void send(String name, String title) {
        System.out.println("[App → " + name + "] " + title);
    }
}

class Student {
    String name;
    String department;
    List<NotificationChannel> channels = new ArrayList<>();

    Student(String name, String department) {
        this.name = name;
        this.department = department;
    }

    void addChannel(NotificationChannel channel) {
        channels.add(channel);
    }
}

class Notice {
    String title;
    Set<String> departments;

    Notice(String title, Set<String> departments) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Notice title is required.");
        }

        if (departments == null || departments.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one target department is required.");
        }

        this.title = title;
        this.departments = new LinkedHashSet<>(departments);
    }
}

class NoticeBoard {
    private List<Student> students = new ArrayList<>();

    void addStudent(Student student) {
        students.add(student);
    }

    void postNotice(String title, String... departments) {
        try {
            Notice notice = new Notice(title,
                    new LinkedHashSet<>(Arrays.asList(departments)));

            System.out.println("Notice '" + notice.title
                    + "' posted to " + String.join(", ", notice.departments) + ".");

            for (Student student : students) {
                if (notice.departments.contains(student.department)) {
                    for (NotificationChannel channel : student.channels) {
                        channel.send(student.name, notice.title);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot post notice: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Student asha = new Student("Asha", "CSE");
        asha.addChannel(new EmailChannel());
        asha.addChannel(new AppChannel());

        Student ravi = new Student("Ravi", "ECE");
        ravi.addChannel(new SmsChannel());

        NoticeBoard board = new NoticeBoard();
        board.addStudent(asha);
        board.addStudent(ravi);

        board.postNotice("Lab Closed Tomorrow", "CSE");
        board.postNotice("Fee Deadline Extended", "CSE", "ECE");
        board.postNotice("Sports Day");
    }
}
