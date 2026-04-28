package models;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Employee implements Researcher {
    
    public enum TeacherTitle { TUTOR, LECTOR, SENIOR_LECTOR, PROFESSOR }

    private TeacherTitle title;
    private List<Course> courses;
    private double rating;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;

    public Teacher(String id, String firstName, String lastName, String email, String login, String password, TeacherTitle title) {
        super(id, firstName, lastName, email, login, password);
        this.title = title;
        this.courses = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.rating = 0;
    }

    // Getters
    public TeacherTitle getTitle() { return title; }
    public List<Course> getCourses() { return courses; }
    public double getRating() { return rating; }

    public void putMark(Student s, Course c, Mark m) {
        System.out.println("Mark given to " + s.getFirstName());
    }

    public void viewStudents(Course c) {
        System.out.println("Students in " + c.getName() + ":");
        for (Student s : c.getStudents()) {
            System.out.println("- " + s);
        }
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    // Researcher interface methods
    @Override
    public int getHIndex() {
        int h = 0;
        for (ResearchPaper p : papers) {
            if (p.getCitations() >= h + 1) h++;
        }
        return h;
    }

    @Override
    public List<ResearchPaper> getPapers() { return papers; }

    @Override
    public void addPaper(ResearchPaper paper) { papers.add(paper); }

    @Override
    public List<ResearchProject> getProjects() { return projects; }

    @Override
    public void displayMenu() {
        System.out.println("=== TEACHER MENU ===");
        System.out.println("1. View my courses");
        System.out.println("2. Put mark");
        System.out.println("3. View students");
        System.out.println("4. View research papers");
        System.out.println("0. Exit");
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " [" + title + "]";
    }
}