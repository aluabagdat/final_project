package models;

import java.util.*;
import exceptions.MaxCreditsException;
import exceptions.LowHIndexException;

public class GraduateStudent extends Student implements Researcher {

    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;
    private String thesisTopic;

    public GraduateStudent(String id, String firstName, String lastName,
                           String email, String login, String password,
                           String major, String thesisTopic) {
        super(id, firstName, lastName, email, login, password,
              StudyYear.FOURTH, major);
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.thesisTopic = thesisTopic;
    }

    public String getThesisTopic() { return thesisTopic; }

    // Проверка что можно быть супервизором (h-index > 3)
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

    @Override
    public void displayMenu() {
        System.out.println("=== GRADUATE STUDENT MENU ===");
        System.out.println("1. View courses");
        System.out.println("2. Register for course");
        System.out.println("3. View transcript");
        System.out.println("4. View research papers");
        System.out.println("5. Join research project");
        System.out.println("6. Request supervisor");
        System.out.println("7. View attendance");
        System.out.println("0. Exit");
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName()
            + " [Graduate, thesis: " + thesisTopic + "]";
    }
}