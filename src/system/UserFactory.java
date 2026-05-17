package system;

import models.*;
import models.Student.StudyYear;
import models.Teacher.TeacherTitle;
import models.Manager.ManagerType;

public class UserFactory {

    public static User createStudent(String id, String firstName, String lastName, String email,
                                     String login, String password, StudyYear year, String major) {
        return new Student(id, firstName, lastName, email, login, password, year, major);
    }

    public static User createGraduateStudent(String id, String firstName, String lastName, String email,
                                             String login, String password, String major, String thesisTopic) {
        return new GraduateStudent(id, firstName, lastName, email, login, password, major, thesisTopic);
    }

    public static User createTeacher(String id, String firstName, String lastName, String email,
                                     String login, String password, TeacherTitle title) {
        return new Teacher(id, firstName, lastName, email, login, password, title);
    }

    public static User createAdmin(String id, String firstName, String lastName, String email,
                                   String login, String password) {
        return new Admin(id, firstName, lastName, email, login, password);
    }

    public static User createManager(String id, String firstName, String lastName, String email,
                                     String login, String password, ManagerType type) {
        return new Manager(id, firstName, lastName, email, login, password, type);
    }
}