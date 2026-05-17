package models;

import java.util.*;
import exceptions.*;
import system.UniversitySystem;

public class Student extends User implements Researcher{

    public enum StudyYear { FIRST, SECOND, THIRD, FOURTH }

    private StudyYear year;
    private String major;
    private List<Course> registeredCourses;
    private List<Mark> marks;
    private int credits;
    private int failCount;
    private Researcher supervisor;

    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;
    private Map<Course, List<Boolean>> attendance;

    public Student(String id, String firstName, String lastName,
                   String email, String login, String password,
                   StudyYear year, String major) {
        super(id, firstName, lastName, email, login, password);
        this.year = year;
        this.major = major;
        this.registeredCourses = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.attendance = new HashMap<>();
        this.credits = 0;
        this.failCount = 0;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public StudyYear getYear() { return year; }
    public String getMajor() { return major; }
    public List<Course> getRegisteredCourses() { return registeredCourses; }
    public List<Mark> getMarks() { return marks; }
    public int getCredits() { return credits; }
    public int getFailCount() { return failCount; }
    public Researcher getSupervisor() { return supervisor; }
    public void setSupervisor(Researcher supervisor) { this.supervisor = supervisor; }

    public void registerForCourse(Course c) throws MaxCreditsException {
        if (credits + c.getCredits() > 21)
            throw new MaxCreditsException("Max 21 credits exceeded. Current: " + credits);
        registeredCourses.add(c);
        credits += c.getCredits();
        attendance.put(c, new ArrayList<>());
        System.out.println("Registered for: " + c.getName());
    }

    public void dropCourse(Course c) {
        if (registeredCourses.remove(c)) {
            credits -= c.getCredits();
            attendance.remove(c);
            System.out.println("Dropped: " + c.getName());
        } else {
            System.out.println("Not registered.");
        }
    }

    public void markAttendance(Course c, boolean present) {
        if (!registeredCourses.contains(c)) { System.out.println("Not registered."); return; }
        attendance.computeIfAbsent(c, k -> new ArrayList<>()).add(present);
    }

    public double getAttendanceRate(Course c) {
        List<Boolean> rec = attendance.getOrDefault(c, List.of());
        if (rec.isEmpty()) return 0;
        long present = rec.stream().filter(b -> b).count();
        return (double) present / rec.size() * 100;
    }

    public void viewAttendance() {
        System.out.println("Attendance for " + getFirstName() + " " + getLastName());
        for (var entry : attendance.entrySet()) {
            System.out.printf("%-20s %.1f%%%n", entry.getKey().getName(), getAttendanceRate(entry.getKey()));
        }
    }

    public double getGPA() {
        if (marks.isEmpty()) return 0;
        return marks.stream().mapToDouble(Mark::getTotal).average().orElse(0);
    }

    public void viewTranscript() {
        System.out.println("=== TRANSCRIPT ===");
        System.out.println("Student: " + getFirstName() + " " + getLastName());
        System.out.println("Major: " + major + ", Year: " + year);
        System.out.printf("GPA: %.2f%n", getGPA());
        for (int i = 0; i < registeredCourses.size(); i++) {
            String markStr = (marks.size() > i) ? marks.get(i).toString() : "N/A";
            System.out.printf("%-20s %s%n", registeredCourses.get(i).getName(), markStr);
        }
        System.out.println("Credits: " + credits);
    }

    public void addMark(Mark m) throws MaxFailException {
        marks.add(m);
        if (m.getTotal() < 50) {
            failCount++;
            if (failCount >= 3) throw new MaxFailException(getFirstName() + " failed " + failCount + " times.");
        }
    }

    public void rateTeacher(Teacher t, int rating) {
        if (rating < 1 || rating > 5) { System.out.println("Rating 1-5"); return; }
        t.addRating(rating);
        System.out.println("Rated " + t.getFirstName() + ": " + rating);
    }

    @Override
    public int getHIndex() {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        sorted.sort((a, b) -> Integer.compare(b.getCitations(), a.getCitations()));
        int h = 0;
        for (ResearchPaper p : sorted) {
            if (p.getCitations() >= h + 1) h++;
            else break;
        }
        return h;
    }

    @Override public List<ResearchPaper> getPapers() { return papers; }
    @Override public void addPaper(ResearchPaper paper) { papers.add(paper); }
    @Override public List<ResearchProject> getProjects() { return projects; }

    public void assignSupervisorIfFourthYear(Researcher potentialSupervisor) throws LowHIndexException {
        if (this.year == StudyYear.FOURTH) {
            if (potentialSupervisor.getHIndex() < 3)
                throw new LowHIndexException("H-index >= 3 required.");
            this.supervisor = potentialSupervisor;
            System.out.println("Supervisor assigned: " + ((User) potentialSupervisor).getFirstName());
        } else {
            System.out.println("Only 4th year can have supervisor.");
        }
    }

    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n=== STUDENT MENU ===");
            System.out.println("1. View registered courses");
            System.out.println("2. View transcript");
            System.out.println("3. Rate teacher");
            System.out.println("4. View attendance");
            System.out.println("5. View available courses");
            System.out.println("6. Register for course");
            System.out.println("7. My research papers");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Number only.");
                continue;
            }

            if (choice == 0) {
                System.out.println("Goodbye.");
            } else {
                handleStudentMenuChoice(choice, scanner);
            }
        }

    protected void handleStudentMenuChoice(int choice, Scanner scanner) {
        UniversitySystem sys = UniversitySystem.getInstance();
        switch (choice) {
            case 1:
                if (registeredCourses.isEmpty()) System.out.println("None.");
                else registeredCourses.forEach(c -> System.out.println("  - " + c));
                break;
            case 2:
                viewTranscript();
                break;
            case 3:
                System.out.print("Teacher name: ");
                String name = scanner.nextLine();
                System.out.print("Rating (1-5): ");
                int rating;
                try { rating = Integer.parseInt(scanner.nextLine()); }
                catch (NumberFormatException e) { System.out.println("Invalid rating."); break; }
                boolean found = false;
                for (Course c : registeredCourses) {
                    for (Teacher t : c.getInstructors()) {
                        if ((t.getFirstName() + " " + t.getLastName()).equalsIgnoreCase(name)) {
                            rateTeacher(t, rating);
                            found = true;
                        }
                    }
                }
                if (!found) System.out.println("Teacher not found.");
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
            default:
                System.out.println("Invalid option.");
        }
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " [" + year + ", " + major + "]";
    }
    }
}