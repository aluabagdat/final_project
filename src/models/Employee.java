package models;

import java.util.ArrayList;
import java.util.List;

import system.UniversitySystem;

public abstract class Employee extends User {
    private double salary;
    private String department;
    protected List<Message> inbox;
    protected List<Message> outbox;

    public Employee(String id, String firstName, String lastName, String email, String login, String password) {
        super(id, firstName, lastName, email, login, password);
        this.salary = 0;
        this.department = "";
        this.inbox = new ArrayList<>();
        this.outbox = new ArrayList<>();
    }

    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
    
    public List<Message> getInbox() {
        return new ArrayList<>(inbox);
    }
    
    public List<Message> getOutbox() {
        return new ArrayList<>(outbox);
    }

    public void setSalary(double salary) { this.salary = salary; }
    public void setDepartment(String department) { this.department = department; }
    
    protected void addToInbox(Message msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        this.inbox.add(msg);
    }
    
    protected void addToOutbox(Message msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        this.outbox.add(msg);
    }
    
    protected void clearInbox() {
        this.inbox.clear();
    }
    
    protected void clearOutbox() {
        this.outbox.clear();
    }

    public void sendMessage(Employee to, String text) {
        if (to == null) {
            System.out.println("Cannot send message to null recipient");
            return;
        }
        Message msg = new Message(this, to, text, false);
        addToOutbox(msg);
        to.addToInbox(msg);
        UniversitySystem.getInstance().addLog("Message from " + getFirstName() + " to " + to.getFirstName());
        System.out.println("Message sent to " + to.getFirstName());
    }

    public void sendComplaint(String text) {
        Admin admin = UniversitySystem.getInstance().getUsers().stream()
            .filter(u -> u instanceof Admin)
            .map(u -> (Admin) u)
            .findFirst()
            .orElse(null);

        if (admin != null) {
            Message complaint = new Message(this, admin, text, true);
            addToOutbox(complaint);
            admin.addToInbox(complaint);
            UniversitySystem.getInstance().addLog("Complaint from " + getFirstName());
            System.out.println("Complaint sent to admin.");
        } else {
            System.out.println("No admin found to send complaint.");
        }
    }
    
    public void viewInbox() {
        System.out.println("=== INBOX ===");
        if (inbox.isEmpty()) {
            System.out.println("  (empty)");
        }
        for (Message m : inbox) {
            System.out.println(m);
        }
    }
    
    public void viewOutbox() {
        System.out.println("=== OUTBOX ===");
        if (outbox.isEmpty()) {
            System.out.println("  (empty)");
        }
        for (Message m : outbox) {
            System.out.println(m);
        }
    }
}