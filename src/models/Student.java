package models;

import java.util.ArrayList;
import java.util.List;
import exceptions.MaxCreditsException;
import exceptions.MaxFailException;

public class Student extends User {

    public enum StudyYear { FIRST, SECOND, THIRD, FOURTH }

    private StudyYear year;
    private String major;
    private List<Course> registeredCourses;
    private List<Mark> marks;
    private int credits;
    private int failCount;
    private Researcher supervisor;

    public Student(String id, String firstName, String lastName, String email, String login, String password, StudyYear year, String major) {
        super(id, firstName, lastName, email, login, password);
        this.year = year;
        this.major = major;
        this.registeredCourses = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.credits = 0;
        this.failCount = 0;
    }

    public StudyYear getYear() { return year; }
    public String getMajor() { return major; }
    public List<Course> getRegisteredCourses() { return registeredCourses; }
    public List<Mark> getMarks() { return marks; }
    public int getCredits() { return credits; }
    public Researcher getSupervisor() { return supervisor; }

    public void setSupervisor(Researcher supervisor) { this.supervisor = supervisor; }

    public void registerForCourse(Course c) throws MaxCreditsException {
        if (credits + c.getCredits() > 21) {
            throw new MaxCreditsException("Cannot register! Max 21 credits exceeded.");
        }
        registeredCourses.add(c);
        credits += c.getCredits();
        System.out.println("Registered for: " + c.getName());
    }

    public double getGPA() {
        if (marks.isEmpty()) return 0;
        double total = 0;
        for (Mark m : marks) {
            total += m.getTotal();
        }
        return total / marks.size();
    }

    public void viewTranscript() {
        System.out.println("=== TRANSCRIPT ===");
        System.out.println("Student: " + getFirstName() + " " + getLastName());
        System.out.println("GPA: " + getGPA());
        for (int i = 0; i < registeredCourses.size(); i++) {
            System.out.println(registeredCourses.get(i).getName() + " - " + 
                (marks.size() > i ? marks.get(i).getTotal() : "N/A"));
        }
    }

    public void rateTeacher(Teacher t, int rating) {
        System.out.println("Rated teacher " + t.getFirstName() + ": " + rating);
    }

    public void addMark(Mark m) {
        marks.add(m);
        if (m.getTotal() < 50) failCount++;
    }

    @Override
    public void displayMenu() {
        System.out.println("=== STUDENT MENU ===");
        System.out.println("1. View courses");
        System.out.println("2. Register for course");
        System.out.println("3. View transcript");
        System.out.println("4. Rate teacher");
        System.out.println("0. Exit");
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " [" + year + "]";
    }
}