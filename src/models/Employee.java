package models;

import java.util.ArrayList;
import java.util.List;

import system.UniversitySystem;

public abstract class Employee extends User {
    private double salary;
    private String department;
    private List<Message> inbox;
    private List<Message> outbox;

    public Employee(String id, String firstName, String lastName, String email, String login, String password) {
        super(id, firstName, lastName, email, login, password);
        this.salary = 0;
        this.department = "";
        this.inbox = new ArrayList<>();
        this.outbox = new ArrayList<>();
    }

    public double getSalary() { return salary; }
    public String getDepartment() { return department; }

    public void setSalary(double salary) { this.salary = salary; }
    public void setDepartment(String department) { this.department = department; }

    public void sendMessage(Employee to, String text) {
        Message msg = new Message(this, to, text, false);
        outbox.add(msg);
        to.inbox.add(msg);
        UniversitySystem.getInstance().addLog("Message from " + getFirstName() + " to " + to.getFirstName());
        System.out.println("Message sent to " + to.getFirstName());
    }


    public void sendComplaint(String text) {
        User admin = UniversitySystem.getInstance().getUsers().stream()
            .filter(u -> u instanceof Admin).findFirst().orElse(null);
        if (admin != null) {
            Message complaint = new Message(this, admin, text, true);
            outbox.add(complaint);
            ((Employee)admin).inbox.add(complaint);
            UniversitySystem.getInstance().addLog("Complaint from " + getFirstName());
            System.out.println("Complaint sent to admin.");
        } else {
            System.out.println("No admin found to send complaint.");
        }
    }
    public void viewInbox() {
        System.out.println("=== INBOX ===");
        for (Message m : inbox) {
            System.out.println(m);
        }
    }
}