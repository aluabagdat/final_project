package models;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

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
        System.out.println("=== MANAGER MENU ===");
        System.out.println("1. Approve registration");
        System.out.println("2. Assign teacher to course");
        System.out.println("3. Create report");
        System.out.println("4. Manage news");
        System.out.println("5. View students by GPA");
        System.out.println("6. Search students (regex)");
        System.out.println("7. View schedule");
        System.out.println("0. Exit");
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " [" + type + "]";
    }
}