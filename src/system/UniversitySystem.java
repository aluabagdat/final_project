package system;

import models.*;
import java.util.*;
import java.io.Serializable;

public class UniversitySystem implements Serializable {

    private static final long serialVersionUID = 1L;

    private static UniversitySystem instance;

    private List<User> users;
    private List<Course> courses;
    private List<Student> students;
    private List<Teacher> teachers;

    private UniversitySystem() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        students = new ArrayList<>();
        teachers = new ArrayList<>();
    }

    public static UniversitySystem getInstance() {
        if (instance == null) {
            instance = new UniversitySystem();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void addUser(User user) {
        users.add(user);

        if (user instanceof Student) {
            students.add((Student) user);
        }

        if (user instanceof Teacher) {
            teachers.add((Teacher) user);
        }

        System.out.println("User added: " + user);
    }

    public void addCourse(Course course) {
        courses.add(course);
        System.out.println("Course added: " + course);
    }

    public User login(String login, String password) {
        for (User u : users) {
            if (u.getLogin().equals(login) && u.checkPassword(password)) {
                System.out.println("Welcome, " + u.getFirstName() + "!");
                return u;
            }
        }

        System.out.println("Invalid login or password.");
        return null;
    }
}