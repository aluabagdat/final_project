package models;

import java.util.*;
import java.util.regex.*;
import exceptions.MaxCreditsException;
import exceptions.MaxFailException;
import java.util.Scanner;
import java.util.List;

public class Student extends User {

    public enum StudyYear { FIRST, SECOND, THIRD, FOURTH }

    private StudyYear year;
    private String major;
    private List<Course> registeredCourses;
    private List<Mark> marks;
    private int credits;
    private int failCount;
    private Researcher supervisor;

    private Map<Course, List<Boolean>> attendance;

    public Student(String id, String firstName, String lastName,
                   String email, String login, String password,
                   StudyYear year, String major) {
        super(id, firstName, lastName, email, login, password);
        this.year = year;
        this.major = major;
        this.registeredCourses = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.attendance = new HashMap<>();
        this.credits = 0;
        this.failCount = 0;
    }

    public StudyYear getYear() { return year; }
    public String getMajor() { return major; }
    public List<Course> getRegisteredCourses() { return registeredCourses; }
    public List<Mark> getMarks() { return marks; }
    public int getCredits() { return credits; }
    public int getFailCount() { return failCount; }
    public Researcher getSupervisor() { return supervisor; }
    public void setSupervisor(Researcher supervisor) { this.supervisor = supervisor; }

    public void registerForCourse(Course c) throws MaxCreditsException {
        if (credits + c.getCredits() > 21) {
            throw new MaxCreditsException(
                "Cannot register! Max 21 credits exceeded. Current: " + credits);
        }
        registeredCourses.add(c);
        credits += c.getCredits();
        attendance.put(c, new ArrayList<>());
        System.out.println("Registered for: " + c.getName());
    }

    public void dropCourse(Course c) {
        if (registeredCourses.remove(c)) {
            credits -= c.getCredits();
            attendance.remove(c);
            System.out.println("Dropped course: " + c.getName());
        } else {
            System.out.println("You are not registered for: " + c.getName());
        }
    }

    public void markAttendance(Course c, boolean present) {
        if (!registeredCourses.contains(c)) {
            System.out.println("Not registered for this course.");
            return;
        }
        attendance.computeIfAbsent(c, k -> new ArrayList<>()).add(present);
    }

    public double getAttendanceRate(Course c) {
        List<Boolean> records = attendance.getOrDefault(c, List.of());
        if (records.isEmpty()) return 0.0;
        long present = records.stream().filter(b -> b).count();
        return (double) present / records.size() * 100;
    }

    public void viewAttendance() {
        System.out.println("=== ATTENDANCE REPORT: "
            + getFirstName() + " " + getLastName() + " ===");
        if (attendance.isEmpty()) {
            System.out.println("No attendance records.");
            return;
        }
        for (Map.Entry<Course, List<Boolean>> entry : attendance.entrySet()) {
            double rate = getAttendanceRate(entry.getKey());
            System.out.printf("%-20s  %.1f%%%n", entry.getKey().getName(), rate);
        }
    }

    public double getGPA() {
        if (marks.isEmpty()) return 0.0;
        double total = 0;
        for (Mark m : marks) total += m.getTotal();
        return total / marks.size();
    }

    public void viewTranscript() {
        System.out.println("=== TRANSCRIPT ===");
        System.out.println("Student : " + getFirstName() + " " + getLastName());
        System.out.println("Major   : " + major);
        System.out.println("Year    : " + year);
        System.out.printf ("GPA     : %.2f%n", getGPA());
        System.out.println("----------------------------------");
        for (int i = 0; i < registeredCourses.size(); i++) {
            String markStr = (marks.size() > i)
                ? marks.get(i).toString()
                : "N/A";
            System.out.printf("%-20s %s%n",
                registeredCourses.get(i).getName(), markStr);
        }
        System.out.println("----------------------------------");
        System.out.println("Total credits: " + credits);
    }

    public void addMark(Mark m) throws MaxFailException {
        marks.add(m);
        if (m.getTotal() < 50) {
            failCount++;
            if (failCount >= 3) {
                throw new MaxFailException(
                    getFirstName() + " has failed " + failCount + " courses!");
            }
        }
    }

    public void rateTeacher(Teacher t, int rating) {
        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return;
        }
        t.addRating(rating);
        System.out.println("Rated teacher " + t.getFirstName() + ": " + rating + "/5");
    }

    @Override
public void displayMenu() {
    Scanner scanner = new Scanner(System.in);
    int choice = -1;

    while (choice != 0) {
        System.out.println("\n=== STUDENT MENU ===");
        System.out.println("1. View registered courses");
        System.out.println("2. View transcript");
        System.out.println("3. Rate teacher");
        System.out.println("4. View attendance");
        System.out.println("0. Exit");
        System.out.print("Choose: ");

        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
            continue;
        }

        switch (choice) {
            case 1:
                if (registeredCourses.isEmpty()) {
                    System.out.println("You have no registered courses.");
                } else {
                    System.out.println("Your courses:");
                    for (Course c : registeredCourses) {
                        System.out.println("  - " + c);
                    }
                }
                break;
            case 2:
                viewTranscript();
                break;
            case 3:
                System.out.print("Enter teacher name to rate: ");
                String name = scanner.nextLine().trim();
                System.out.print("Enter rating (1-5): ");
                try {
                    int rating = Integer.parseInt(scanner.nextLine().trim());
                    // найти преподавателя по имени среди курсов
                    boolean found = false;
                    for (Course c : registeredCourses) {
                        for (Teacher t : c.getInstructors()) {
                            if ((t.getFirstName() + " " + t.getLastName())
                                    .equalsIgnoreCase(name)) {
                                rateTeacher(t, rating);
                                found = true;
                            }
                        }
                    }
                    if (!found) System.out.println("Teacher not found in your courses.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid rating.");
                }
                break;
            case 4:
                viewAttendance();
                break;
            case 0:
                System.out.println("Goodbye, " + getFirstName() + "!");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " [" + year + ", " + major + "]";
    }
}