package models;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Employee {

    public Admin(String id, String firstName, String lastName, String email, String login, String password) {
        super(id, firstName, lastName, email, login, password);
    }

    public void addUser(User u) {
        System.out.println("User added: " + u);
    }

    public void removeUser(User u) {
        System.out.println("User removed: " + u);
    }

    public void updateUser(User u) {
        System.out.println("User updated: " + u);
    }

    public void viewLogs() {
        System.out.println("Viewing system logs...");
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