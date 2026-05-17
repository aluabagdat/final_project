package models;

import exceptions.LowHIndexException;
import exceptions.MaxCreditsException;
import system.UniversitySystem;
import java.util.List;
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

    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        UniversitySystem sys = UniversitySystem.getInstance();
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

            switch (choice) {
                case 1:
                    if (getRegisteredCourses().isEmpty()) System.out.println("None.");
                    else getRegisteredCourses().forEach(c -> System.out.println("  - " + c));
                    break;
                case 2:
                    viewTranscript();
                    break;
                case 3:
                    System.out.print("Teacher name: ");
                    String name = scanner.nextLine();
                    System.out.print("Rating (1-5): ");
                    try {
                        int rating = Integer.parseInt(scanner.nextLine());
                        boolean found = false;
                        for (Course c : getRegisteredCourses()) {
                            for (Teacher t : c.getInstructors()) {
                                if ((t.getFirstName() + " " + t.getLastName()).equalsIgnoreCase(name)) {
                                    rateTeacher(t, rating);
                                    found = true;
                                }
                            }
                        }
                        if (!found) System.out.println("Teacher not found.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid rating.");
                    }
                    break;
                case 4:
                    viewAttendance();
                    break;
                case 5:
                    System.out.println("Available courses:");
                    List<Course> all = sys.getCourses();
                    for (int i = 0; i < all.size(); i++) {
                        System.out.println((i + 1) + ". " + all.get(i));
                    }
                    break;
                case 6:
                    System.out.print("Course number: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine()) - 1;
                        if (idx >= 0 && idx < sys.getCourses().size()) {
                            try { registerForCourse(sys.getCourses().get(idx)); }
                            catch (MaxCreditsException e) { System.out.println("Error: " + e.getMessage()); }
                        } else { System.out.println("Invalid number."); }
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                case 7:
                    printPapers(new PaperByCitationComparator());
                    break;
                case 8:
                    System.out.print("Enter teacher's full name: ");
                    String tName = scanner.nextLine().trim();
                    boolean tFound = false;
                    for (Course c : getRegisteredCourses()) {
                        for (Teacher t : c.getInstructors()) {
                            if ((t.getFirstName() + " " + t.getLastName()).equalsIgnoreCase(tName)) {
                                try { requestSupervisor(t); }
                                catch (LowHIndexException e) { System.out.println("Error: " + e.getMessage()); }
                                tFound = true;
                            }
                        }
                    }
                    if (!tFound) System.out.println("Teacher not found in your courses.");
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
        return getFirstName() + " " + getLastName()
            + " [Graduate, thesis: " + thesisTopic + "]";
    }
}