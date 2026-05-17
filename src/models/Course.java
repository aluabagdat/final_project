package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {

    private String name;
    private List<Teacher> instructors;
    private List<Student> students;
    private List<Lesson> lessons;
    private String major;
    private int year;
    private int credits;

    public Course(String name, String major, int year, int credits) {
        this.name = name;
        this.major = major;
        this.year = year;
        this.credits = credits;
        this.instructors = new ArrayList<>();
        this.students = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public String getName() { return name; }
    public List<Teacher> getInstructors() { return instructors; }
    public List<Student> getStudents() { return students; }
    public List<Lesson> getLessons() { return lessons; }
    public String getMajor() { return major; }
    public int getYear() { return year; }
    public int getCredits() { return credits; }

    public void addInstructor(Teacher t) {
        instructors.add(t);
        System.out.println("Teacher " + t.getFirstName() + " added to " + name);
    }

    public void addStudent(Student s) {
        if (students.contains(s)) {
            System.out.println("Student " + s.getFirstName() + " is already enrolled in " + name);
            return;
        }
        students.add(s);
        System.out.println("Student " + s.getFirstName() + " added to " + name);
    }

    public void addLesson(Lesson l) {
        lessons.add(l);
    }

    @Override
    public String toString() {
        return name + " [" + major + ", year " + year + ", " + credits + " credits]";
    }
}