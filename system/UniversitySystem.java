import models.*;
import java.util.ArrayList;
import java.util.List;

public class UniversitySystem {

    private static UniversitySystem instance;

    private List<User> users;
    private List<Course> courses;

    private UniversitySystem() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
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

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added to system: " + user);
    }

    public void addCourse(Course course) {
        courses.add(course);
        System.out.println("Course added: " + course);
    }
}