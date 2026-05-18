package models;

import system.Observer;
import system.UniversitySystem;
import java.util.List;
import java.util.Scanner;

public class Admin extends Employee implements Observer {

    public Admin(String id, String firstName, String lastName, String email, String login, String password) {
        super(id, firstName, lastName, email, login, password);
    }

    @Override
    public void update(String message) {
        System.out.println("[ADMIN LOG] " + message);
    }

    public void addUser(User u) {
        if (u == null) {
            System.out.println("Cannot add null user");
            return;
        }
        UniversitySystem.getInstance().addUser(u);
    }

    public void removeUser(User u) {
        if (u == null) {
            System.out.println("Cannot remove null user");
            return;
        }
        UniversitySystem sys = UniversitySystem.getInstance();
        sys.getUsers().remove(u);
        if (u instanceof Student) {
            sys.getStudents().remove(u);
            // Remove student from all courses
            for (Course c : sys.getCourses()) {
                c.getStudents().remove(u);
            }
        }
        if (u instanceof Teacher) {
            sys.getTeachers().remove(u);
        }
        sys.addLog("User removed: " + u.getFirstName() + " " + u.getLastName());
        System.out.println("User removed: " + u);
    }

    public void updateUser(User u) {
        if (u == null) {
            System.out.println("Cannot update null user");
            return;
        }
        UniversitySystem.getInstance().addLog("User updated: " + u.getFirstName() + " " + u.getLastName());
        System.out.println("User updated: " + u);
    }

    public void viewLogs() {
        System.out.println("=== SYSTEM LOGS ===");
        List<String> logs = UniversitySystem.getInstance().getSystemLogs();
        if (logs.isEmpty()) {
            System.out.println("  (no logs yet)");
        }
        for (String log : logs) {
            System.out.println(log);
        }
    }
    
    @Override
    public void viewInbox() {
        System.out.println("=== ADMIN INBOX ===");
        List<Message> messages = getInbox();
        if (messages.isEmpty()) {
            System.out.println("  (empty)");
        }
        for (Message m : messages) {
            System.out.println(m);
        }
    }

    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        UniversitySystem sys = UniversitySystem.getInstance();
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. View all users");
            System.out.println("2. Remove user");
            System.out.println("3. View system logs");
            System.out.println("4. View all courses");
            System.out.println("5. View inbox");
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
                    List<User> users = sys.getUsers();
                    if (users.isEmpty()) {
                        System.out.println("No users.");
                    } else {
                        users.forEach(u -> System.out.println("  - " + u));
                    }
                    break;
                case 2:
                    List<User> all = sys.getUsers();
                    if (all.isEmpty()) { 
                        System.out.println("No users."); 
                        break; 
                    }
                    for (int i = 0; i < all.size(); i++) {
                        System.out.println("  " + (i + 1) + ". " + all.get(i));
                    }
                    System.out.print("Select user number to remove: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < all.size()) {
                            removeUser(all.get(idx));
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                    break;
                case 3:
                    viewLogs();
                    break;
                case 4:
                    sys.getCourses().forEach(c -> System.out.println("  - " + c));
                    break;
                case 5:
                    viewInbox();
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
        return getFirstName() + " " + getLastName() + " [Admin]";
    }
}