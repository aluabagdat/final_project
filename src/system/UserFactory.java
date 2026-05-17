package system;

import models.*;
import models.Manager.ManagerType;
import models.Student.StudyYear;
import models.Teacher.TeacherTitle;

public class UserFactory {

    public static User createUser(String type, String id, String firstName, String lastName,
                                  String email, String login, String password) {

        switch (type.toLowerCase()) {

            case "student":
                return new Student(id, firstName, lastName, email, login, password,
                        Student.StudyYear.FIRST, "CS");

            case "teacher":
                return new Teacher(id, firstName, lastName, email, login, password,
                        Teacher.TeacherTitle.PROFESSOR);

            case "admin":
                return new Admin(id, firstName, lastName, email, login, password);

            case "manager":
                return new Manager(id, firstName, lastName, email, login, password,
                        Manager.ManagerType.OR);

            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }
}