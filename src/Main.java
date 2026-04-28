import models.*;
import exceptions.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== University System ===");

        Admin admin = new Admin("1", "Alua", "Bagdat", "aluabagdat@kbtu.kz", "alua", "1234");
        admin.displayMenu();

        Teacher teacher = new Teacher("2", "Assem", "Mukhtarkyzy", "assemmukhtarkyzy@kbtu.kz", "assem", "1234", Teacher.TeacherTitle.PROFESSOR);
        teacher.displayMenu();

        Student student = new Student("3", "Valeriya", "Demchenko", "valeriyademchenko@uni.kz", "valeriya", "1234", Student.StudyYear.SECOND, "CS");
        student.displayMenu();

        Manager manager = new Manager("4", "Arsen", "Akhmetolla", "arsen@uni.kz", "arsen", "1234", Manager.ManagerType.OR);
        manager.displayMenu();

        Course course = new Course("OOP", "IS", 2, 5);
        System.out.println(course);

        Mark mark = new Mark(25, 30, 38);
        System.out.println(mark);
    }
}