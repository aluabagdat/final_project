package models;

public abstract class Employee extends User {
    private double salary;
    private String department;

    public Employee(String id, String firstName, String lastName, String email, String login, String password) {
        super(id, firstName, lastName, email, login, password);
        this.salary = 0;
        this.department = "";
    }

    public double getSalary() { return salary; }
    public String getDepartment() { return department; }

    public void setSalary(double salary) { this.salary = salary; }
    public void setDepartment(String department) { this.department = department; }

    public void sendMessage(Employee to, String text) {
        System.out.println("Message to " + to.getFirstName() + ": " + text);
    }

    public void sendComplaint(String text) {
        System.out.println("Complaint sent: " + text);
    }
}