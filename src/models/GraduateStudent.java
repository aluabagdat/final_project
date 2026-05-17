package models;

import exceptions.LowHIndexException;
import java.util.Scanner;

public class GraduateStudent extends Student {

    private String thesisTopic;

    public GraduateStudent(String id, String firstName, String lastName,
                           String email, String login, String password,
                           String major, String thesisTopic) {
        super(id, firstName, lastName, email, login, password, StudyYear.FOURTH, major);
        this.thesisTopic = thesisTopic;
    }

    public String getThesisTopic() { return thesisTopic; }

    // ✅ FIX #3: Changed threshold from <= 3 to < 3.
    // Original threw if h-index <= 3 (rejecting h-index of 3),
    // but Student.assignSupervisorIfFourthYear() throws if h-index < 3 (accepting 3).
    // Both now consistently require h-index >= 3.
    public void requestSupervisor(Teacher t) throws LowHIndexException {
        if (t.getHIndex() < 3) {
            throw new LowHIndexException(
                t.getFirstName() + " has h-index " + t.getHIndex()
                + " — must be >= 3 to supervise.");
        }
        setSupervisor(t);
        System.out.println("Supervisor assigned: " + t.getFirstName()
            + " (h-index: " + t.getHIndex() + ")");
    }

    // ✅ FIX #2: Original override printed the header and option 8, then called
    // super.displayMenu() which runs its own full while-loop. Option 8 was printed
    // AFTER the loop completed and was never reachable during the session.
    // Replaced with a proper self-contained menu loop that includes the graduate option.
    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n=== GRADUATE STUDENT MENU ===");
            System.out.println("1. View registered courses");
            System.out.println("2. View transcript");
            System.out.println("3. Rate teacher");
            System.out.println("4. View attendance");
            System.out.println("5. View available courses");
            System.out.println("6. Register for course");
            System.out.println("7. My research papers");
            System.out.println("8. Request supervisor");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Number only.");
                continue;
            }

            if (choice == 8) {
                // Graduate-specific: request a supervisor by name
                System.out.print("Enter teacher's full name: ");
                String name = scanner.nextLine().trim();
                boolean found = false;
                for (Course c : getRegisteredCourses()) {
                    for (Teacher t : c.getInstructors()) {
                        if ((t.getFirstName() + " " + t.getLastName()).equalsIgnoreCase(name)) {
                            try {
                                requestSupervisor(t);
                            } catch (LowHIndexException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                            found = true;
                        }
                    }
                }
                if (!found) System.out.println("Teacher not found in your courses.");
            } else if (choice == 0) {
                System.out.println("Goodbye, " + getFirstName() + "!");
            } else {
                // Delegate shared options to a helper so we don't duplicate the switch
                handleStudentMenuChoice(choice, scanner);
            }
        }
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName()
            + " [Graduate, thesis: " + thesisTopic + "]";
    }
}