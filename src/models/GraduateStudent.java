package models;

import exceptions.LowHIndexException;

public class GraduateStudent extends Student {

    private String thesisTopic;

    public GraduateStudent(String id, String firstName, String lastName,
                           String email, String login, String password,
                           String major, String thesisTopic) {
        super(id, firstName, lastName, email, login, password, StudyYear.FOURTH, major);
        this.thesisTopic = thesisTopic;
    }

    public String getThesisTopic() { return thesisTopic; }

    public void requestSupervisor(Teacher t) throws LowHIndexException {
        if (t.getHIndex() <= 3) {
            throw new LowHIndexException(
                t.getFirstName() + " has h-index " + t.getHIndex()
                + " — must be > 3 to supervise.");
        }
        setSupervisor(t);
        System.out.println("Supervisor assigned: " + t.getFirstName()
            + " (h-index: " + t.getHIndex() + ")");
    }
    @Override
    public void displayMenu() {
        System.out.println("=== GRADUATE STUDENT MENU ===");
        super.displayMenu();
        System.out.println("8. Request supervisor (custom)");
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName()
            + " [Graduate, thesis: " + thesisTopic + "]";
    }
}