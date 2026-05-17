package models;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.List;
import system.UniversitySystem;
public class Manager extends Employee {

    public enum ManagerType { OR, DEPARTMENT, DEAN_OFFICE }

    private ManagerType type;

    public Manager(String id, String firstName, String lastName,
                   String email, String login, String password,
                   ManagerType type) {
        super(id, firstName, lastName, email, login, password);
        this.type = type;
    }

    public ManagerType getType() { return type; }

    public void approveRegistration(Student s, Course c) {
        System.out.println("Approved " + s.getFirstName() + " for " + c.getName());
        c.addStudent(s);
    }

    public void assignTeacher(Teacher t, Course c) {
        c.addInstructor(t);
        t.addCourse(c);
        System.out.println("Assigned " + t.getFirstName() + " to " + c.getName());
    }

    // БОНУС: генерация отчёта по всем курсам
    public void createReport(List<Course> courses) {
        System.out.println("=== UNIVERSITY REPORT ===");
        System.out.printf("%-20s %8s %8s%n", "Course", "Students", "Credits");
        System.out.println("-".repeat(38));
        for (Course c : courses) {
            System.out.printf("%-20s %8d %8d%n",
                c.getName(), c.getStudents().size(), c.getCredits());
        }
        System.out.println("-".repeat(38));
        System.out.println("Total courses: " + courses.size());
    }

    public void manageNews(String news) {
        System.out.println("[NEWS] " + news);
    }

    public void viewStudentsSortedByGPA(List<Student> students) {
        students.sort((a, b) -> Double.compare(b.getGPA(), a.getGPA()));
        System.out.println("=== STUDENTS BY GPA ===");
        for (Student s : students) {
            System.out.printf("%-25s GPA: %.2f%n",
                s.getFirstName() + " " + s.getLastName(), s.getGPA());
        }
    }

    // БОНУС: поиск студентов по регулярному выражению
    public List<Student> searchStudents(List<Student> students, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        List<Student> result = students.stream()
            .filter(s -> pattern.matcher(
                s.getFirstName() + " " + s.getLastName()).find())
            .collect(Collectors.toList());

        System.out.println("=== SEARCH RESULTS for \"" + regex + "\" ===");
        if (result.isEmpty()) {
            System.out.println("No students found.");
        } else {
            result.forEach(s -> System.out.println("  " + s));
        }
        return result;
    }

    // БОНУС: поиск преподавателей по regex
    public List<Teacher> searchTeachers(List<Teacher> teachers, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        List<Teacher> result = teachers.stream()
            .filter(t -> pattern.matcher(
                t.getFirstName() + " " + t.getLastName()).find())
            .collect(Collectors.toList());

        System.out.println("=== TEACHER SEARCH for \"" + regex + "\" ===");
        result.forEach(t -> System.out.println("  " + t));
        return result;
    }

    // БОНУС: просмотр расписания курса
    public void viewSchedule(Course c) {
        System.out.println("=== SCHEDULE: " + c.getName() + " ===");
        List<Lesson> lessons = c.getLessons();
        if (lessons.isEmpty()) {
            System.out.println("No lessons scheduled.");
            return;
        }
        lessons.sort(Comparator.comparing(Lesson::getDateTime));
        for (Lesson l : lessons) {
            System.out.println("  " + l);
        }
    }

    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        UniversitySystem sys = UniversitySystem.getInstance();
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n=== MANAGER MENU ===");
            System.out.println("1. View all courses");
            System.out.println("2. Create report");
            System.out.println("3. View students by GPA");
            System.out.println("4. Search students");
            System.out.println("5. View schedule");
            System.out.println("6. Manage news");
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
                    List<Course> courses = sys.getCourses();
                    if (courses.isEmpty()) {
                        System.out.println("No courses.");
                    } else {
                        courses.forEach(c -> System.out.println("  - " + c));
                    }
                    break;
                case 2:
                    createReport(sys.getCourses());
                    break;
                case 3:
                    viewStudentsSortedByGPA(sys.getStudents());
                    break;
                case 4:
                    System.out.print("Enter search pattern (regex): ");
                    String regex = scanner.nextLine().trim();
                    searchStudents(sys.getStudents(), regex);
                    break;
                case 5:
                    List<Course> all = sys.getCourses();
                    if (all.isEmpty()) {
                        System.out.println("No courses.");
                        break;
                    }
                    System.out.println("Select course:");
                    for (int i = 0; i < all.size(); i++) {
                        System.out.println("  " + (i + 1) + ". " + all.get(i).getName());
                    }
                    System.out.print("Choose: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < all.size()) {
                            viewSchedule(all.get(idx));
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                    break;
                case 6:
                    System.out.print("Enter news text: ");
                    String news = scanner.nextLine().trim();
                    manageNews(news);
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
        return getFirstName() + " " + getLastName() + " [" + type + "]";
    }
}