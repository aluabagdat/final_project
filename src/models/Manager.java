package models;

import java.util.List;

public class Manager extends Employee {

    public enum ManagerType { OR, DEPARTMENT, DEAN_OFFICE }

    private ManagerType type;

    public Manager(String id, String firstName, String lastName, String email, String login, String password, ManagerType type) {
        super(id, firstName, lastName, email, login, password);
        this.type = type;
    }

    public ManagerType getType() { return type; }

    public void approveRegistration(Student s, Course c) {
        System.out.println("Approved " + s.getFirstName() + " for " + c.getName());
    }

    public void assignTeacher(Teacher t, Course c) {
        System.out.println("Assigned " + t.getFirstName() + " to " + c.getName());
    }

    public void createReport() {
        System.out.println("Report created by manager: " + getFirstName());
    }

    public void manageNews(String news) {
        System.out.println("News published: " + news);
    }

    public void viewStudentsSortedByGPA(List<Student> students) {
        students.sort((a, b) -> Double.compare(b.getGPA(), a.getGPA()));
        System.out.println("=== STUDENTS BY GPA ===");
        for (Student s : students) {
            System.out.println(s + " - GPA: " + s.getGPA());
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
        System.out.println("0. Exit");
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " [" + type + "]";
    }
}