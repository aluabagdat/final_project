package models;

import system.Observer;
import system.UniversitySystem;

public class Admin extends Employee implements Observer {

    public Admin(String id, String firstName, String lastName, String email, String login, String password) {
        super(id, firstName, lastName, email, login, password);
    }

    @Override
    public void update(String message) {
        System.out.println("[ADMIN LOG] " + message);
    }

    public void addUser(User u) {
        UniversitySystem.getInstance().addUser(u);
    }

    public void removeUser(User u) {
        UniversitySystem sys = UniversitySystem.getInstance();
        sys.getUsers().remove(u);
        if (u instanceof Student) sys.getStudents().remove(u);
        if (u instanceof Teacher) sys.getTeachers().remove(u);
        sys.addLog("User removed: " + u.getFirstName() + " " + u.getLastName());
        System.out.println("User removed: " + u);
    }

    public void updateUser(User u) {
        UniversitySystem.getInstance().addLog("User updated: " + u.getFirstName() + " " + u.getLastName());
        System.out.println("User updated: " + u);
    }

    public void viewLogs() {
        System.out.println("=== SYSTEM LOGS ===");
        for (String log : UniversitySystem.getInstance().getSystemLogs()) {
            System.out.println(log);
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("=== ADMIN MENU ===");
        System.out.println("1. Add user");
        System.out.println("2. Remove user");
        System.out.println("3. Update user");
        System.out.println("4. View logs");
        System.out.println("0. Exit");
    }
}