import java.util.*;

abstract class Question {
    String text;
    int marks;

    Question(String text, int marks) {
        this.text = text;
        this.marks = marks;
    }

    abstract boolean evaluate(String answer);
}

class MCQ extends Question {
    String correctAnswer;

    MCQ(String text, int marks, String correctAnswer) {
        super(text, marks);
        this.correctAnswer = correctAnswer;
    }

    boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {
    String correctAnswer;

    TrueFalseQuestion(String text, int marks, String correctAnswer) {
        super(text, marks);
        this.correctAnswer = correctAnswer;
    }

    boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Examination {
    String name;
    List<Question> questions = new ArrayList<>();

    Examination(String name) {
        this.name = name;
    }

    void addQuestion(Question q) {
        questions.add(q);
    }
}

class Attempt {
    String student;
    Examination exam;
    Map<Integer, String> answers = new HashMap<>();
    boolean submitted = false;

    Attempt(String student, Examination exam) {
        this.student = student;
        this.exam = exam;
        System.out.println(exam.name + " started by " + student);
    }

    void answer(int questionNumber, String answer) {
        if (submitted) {
            System.out.println("Cannot change answers for a submitted examination");
            return;
        }

        if (questionNumber < 1 || questionNumber > exam.questions.size()) {
            System.out.println("Invalid question number");
            return;
        }

        answers.put(questionNumber, answer);
        System.out.println("Answer recorded for Question " + questionNumber);
    }

    void submit() {
        if (submitted)
            return;

        submitted = true;
        int total = 0;
        int score = 0;

        System.out.println(exam.name + " submitted by " + student);

        for (int i = 0; i < exam.questions.size(); i++) {
            Question q = exam.questions.get(i);
            String answer = answers.get(i + 1);

            boolean correct = answer != null && q.evaluate(answer);
            int points = correct ? q.marks : 0;

            score += points;
            total += q.marks;

            System.out.println("Question " + (i + 1) + ": "
                    + (correct ? "Correct" : "Incorrect")
                    + " (" + points + " points)");
        }

        System.out.println("Total score: " + score + "/" + total);
    }
}

public class Main {
    public static void main(String[] args) {
        Examination exam = new Examination("Exam A");

        exam.addQuestion(new MCQ("Question 1", 5, "C"));
        exam.addQuestion(new TrueFalseQuestion("Question 2", 5, "False"));

        Attempt attempt = new Attempt("Student 1", exam);

        attempt.answer(1, "C");
        attempt.answer(2, "True");
        attempt.submit();
        attempt.answer(1, "B");
    }
}
