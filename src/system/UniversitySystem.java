package system;

import models.*;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;

public class UniversitySystem implements Serializable {

    private static final long serialVersionUID = 1L;
    private static volatile UniversitySystem instance;

    private List<User> users;
    private List<Course> courses;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<String> systemLogs;
    private transient Subject eventBus;

    private UniversitySystem() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        systemLogs = new ArrayList<>();
        eventBus = new Subject();
    }

    public static UniversitySystem getInstance() {
        if (instance == null) {
            synchronized (UniversitySystem.class) {
                if (instance == null) {
                    instance = new UniversitySystem();
                }
            }
        }
        return instance;
    }

    public static void setInstance(UniversitySystem sys) {
        instance = sys;
    }

    protected Object readResolve() throws ObjectStreamException {
        instance = this;
        if (eventBus == null) eventBus = new Subject();
        return instance;
    }

    public List<User> getUsers() { return users; }
    public List<Course> getCourses() { return courses; }
    public List<Student> getStudents() { return students; }
    public List<Teacher> getTeachers() { return teachers; }
    public Subject getEventBus() { return eventBus; }
    public List<String> getSystemLogs() { return systemLogs; }

    public void addLog(String log) {
        String entry = LocalDateTime.now() + " : " + log;
        systemLogs.add(entry);
        if (eventBus != null) eventBus.notifyObservers(entry);
    }

    public void addUser(User user) {
        users.add(user);
        if (user instanceof Student) students.add((Student) user);
        if (user instanceof Teacher) teachers.add((Teacher) user);
        addLog("User added: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("User added: " + user);
    }

    public void addCourse(Course course) {
        courses.add(course);
        addLog("Course added: " + course.getName());
        System.out.println("Course added: " + course);
    }

    public User login(String login, String password) {
        for (User u : users) {
            if (u.getLogin().equals(login) && u.checkPassword(password)) {
                addLog("User logged in: " + u.getLogin());
                System.out.println("Welcome, " + u.getFirstName());
                return u;
            }
        }
        addLog("Failed login attempt: " + login);
        System.out.println("Invalid login or password.");
        return null;
    }

    public void printAllResearchersPapers(Comparator<ResearchPaper> comparator) {
        List<Researcher> allResearchers = new ArrayList<>();
        for (User u : users) if (u instanceof Researcher) allResearchers.add((Researcher) u);
        System.out.println("=== ALL RESEARCHERS PAPERS ===");
        for (Researcher r : allResearchers) {
            System.out.println("\n--- " + ((User) r).getFirstName() + " " + ((User) r).getLastName() + " ---");
            r.printPapers(comparator);
        }
    }

    public Researcher getTopCitedResearcher() {
        Researcher top = null;
        int max = -1;
        for (User u : users) {
            if (u instanceof Researcher) {
                int total = ((Researcher) u).getPapers().stream().mapToInt(ResearchPaper::getCitations).sum();
                if (total > max) { max = total; top = (Researcher) u; }
            }
        }
        return top;
    }

    public Researcher getTopCitedResearcherByYear(int year) {
        Researcher top = null;
        int max = -1;
        for (User u : users) {
            if (u instanceof Researcher) {
                int total = ((Researcher) u).getPapers().stream()
                    .filter(p -> p.getPublishedDate().getYear() == year)
                    .mapToInt(ResearchPaper::getCitations).sum();
                if (total > max) { max = total; top = (Researcher) u; }
            }
        }
        return top;
    }
}