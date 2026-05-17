package models;

import java.time.LocalDateTime;
import java.util.*;

public class Teacher extends Employee implements Researcher {

    public enum TeacherTitle { TUTOR, LECTOR, SENIOR_LECTOR, PROFESSOR }

    private TeacherTitle title;
    private List<Course> courses;
    private List<Integer> ratings;   // список всех оценок от студентов
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;

    public Teacher(String id, String firstName, String lastName,
                   String email, String login, String password,
                   TeacherTitle title) {
        super(id, firstName, lastName, email, login, password);
        this.title = title;
        this.courses = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.ratings = new ArrayList<>();
    }

    public TeacherTitle getTitle() { return title; }
    public List<Course> getCourses() { return courses; }

    public double getRating() {
        if (ratings.isEmpty()) return 0.0;
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public void addRating(int rating) {
        ratings.add(rating);
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public void putMark(Student s, Course c, Mark m) {
        try {
            s.addMark(m);
            System.out.println("Mark given to " + s.getFirstName()
                + ": " + m + " in " + c.getName());
        } catch (exceptions.MaxFailException e) {
            System.out.println("WARNING: " + e.getMessage());
        }
    }

    public void viewStudents(Course c) {
        System.out.println("Students in " + c.getName() + ":");
        if (c.getStudents().isEmpty()) {
            System.out.println("  (no students)");
        }
        for (Student s : c.getStudents()) {
            System.out.println("  - " + s);
        }
    }

    // БОНУС: отчёт по оценкам для преподавателя
    public void generateMarksReport(Course c) {
        System.out.println("=== MARKS REPORT: " + c.getName() + " ===");
        System.out.printf("%-22s %6s %6s %6s %6s %5s%n",
            "Student", "Att1", "Att2", "Final", "Total", "Grade");
        System.out.println("-".repeat(57));

        List<Student> students = c.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }

        double classTotal = 0;
        for (Student s : students) {
            List<Mark> marks = s.getMarks();
            if (marks.isEmpty()) {
                System.out.printf("%-22s %s%n", s.getFirstName() + " " + s.getLastName(), "No marks yet");
            } else {
                Mark m = marks.get(marks.size() - 1); // последняя оценка
                classTotal += m.getTotal();
                System.out.printf("%-22s %6.1f %6.1f %6.1f %6.1f %5s%n",
                    s.getFirstName() + " " + s.getLastName(),
                    m.getFirstAttestation(), m.getSecondAttestation(),
                    m.getFinalExam(), m.getTotal(), m.getGrade());
            }
        }
        System.out.println("-".repeat(57));
        System.out.printf("Class average: %.2f%n", classTotal / students.size());
    }

    // БОНУС: отметить посещаемость на уроке
    public void markAttendance(Course c, Student s, boolean present) {
        s.markAttendance(c, present);
        System.out.println(s.getFirstName() + " marked as "
            + (present ? "PRESENT" : "ABSENT") + " in " + c.getName());
    }

    // БОНУС: создать урок
    public Lesson createLesson(Lesson.LessonType type, Course c, LocalDateTime dt) {
        Lesson lesson = new Lesson(type, this, c, dt);
        c.addLesson(lesson);
        System.out.println("Lesson created: " + lesson);
        return lesson;
    }

    // H-index
    @Override
    public int getHIndex() {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        sorted.sort((a, b) -> Integer.compare(b.getCitations(), a.getCitations()));
        int h = 0;
        for (ResearchPaper p : sorted) {
            if (p.getCitations() >= h + 1) h++;
            else break;
        }
        return h;
    }

    @Override public List<ResearchPaper> getPapers() { return papers; }
    @Override public void addPaper(ResearchPaper paper) { papers.add(paper); }
    @Override public List<ResearchProject> getProjects() { return projects; }

    @Override
    public void displayMenu() {
        System.out.println("=== TEACHER MENU ===");
        System.out.println("1. View my courses");
        System.out.println("2. Put mark");
        System.out.println("3. View students");
        System.out.println("4. Generate marks report");
        System.out.println("5. Mark attendance");
        System.out.println("6. View research papers");
        System.out.println("7. Create lesson");
        System.out.println("0. Exit");
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName()
            + " [" + title + ", rating: " + String.format("%.1f", getRating()) + "]";
    }
}