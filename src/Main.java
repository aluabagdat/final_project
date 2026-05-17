import models.*;
import models.Manager.ManagerType;
import models.Student.StudyYear;
import models.Teacher.TeacherTitle;
import exceptions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("       UNIVERSITY MANAGEMENT SYSTEM     ");
        System.out.println("========================================\n");

        UniversitySystem sys = UniversitySystem.getInstance();

        System.out.println("--- Creating users ---");
        Admin admin = new Admin("A01", "Alua", "Bagdat",
            "alua@kbtu.kz", "alua", "1234");

        Teacher teacher = new Teacher("T01", "Assem", "Mukhtarkyzy",
            "assem@kbtu.kz", "assem", "1234", TeacherTitle.PROFESSOR);

        Teacher teacher2 = new Teacher("T02", "Dauren", "Seitkali",
            "dauren@kbtu.kz", "dauren", "1234", TeacherTitle.SENIOR_LECTOR);

        Student student1 = new Student("S01", "Valeriya", "Demchenko",
            "val@kbtu.kz", "val", "1234", StudyYear.SECOND, "IS");

        Student student2 = new Student("S02", "Aizat", "Nurlanovna",
            "aizat@kbtu.kz", "aizat", "1234", StudyYear.FIRST, "CS");

        Student student3 = new Student("S03", "Alibek", "Dzhaksybekov",
            "alibek@kbtu.kz", "alibek", "1234", StudyYear.THIRD, "IS");

        GraduateStudent grad = new GraduateStudent("G01", "Nurbol", "Akhmetov",
            "nurbol@kbtu.kz", "nurbol", "1234", "CS", "AI in Education");

        Manager manager = new Manager("M01", "Arsen", "Akhmetolla",
            "arsen@kbtu.kz", "arsen", "1234", ManagerType.OR);

        sys.addUser(admin);
        sys.addUser(teacher);
        sys.addUser(teacher2);
        sys.addUser(student1);
        sys.addUser(student2);
        sys.addUser(student3);
        sys.addUser(grad);
        sys.addUser(manager);

        System.out.println("\n--- Creating courses ---");
        Course oop = new Course("OOP", "IS", 2, 5);
        Course math = new Course("Calculus", "CS", 1, 6);
        Course db = new Course("Databases", "IS", 2, 4);

        sys.addCourse(oop);
        sys.addCourse(math);
        sys.addCourse(db);

        System.out.println("\n--- Assigning teachers ---");
        manager.assignTeacher(teacher, oop);
        manager.assignTeacher(teacher, db);
        manager.assignTeacher(teacher2, math);

        System.out.println("\n--- Student registration ---");
        try {
            student1.registerForCourse(oop);
            student1.registerForCourse(db);
            student2.registerForCourse(math);
            student2.registerForCourse(oop);
            student3.registerForCourse(oop);
            grad.registerForCourse(db);
        } catch (MaxCreditsException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n--- Testing MaxCreditsException ---");
        try {
            Course extra1 = new Course("Extra1", "IS", 2, 6);
            Course extra2 = new Course("Extra2", "IS", 2, 6);
            student2.registerForCourse(extra1);
            student2.registerForCourse(extra2); // должно упасть
        } catch (MaxCreditsException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("\n--- Approving registrations ---");
        manager.approveRegistration(student1, oop);
        manager.approveRegistration(student2, oop);
        manager.approveRegistration(student3, oop);
        manager.approveRegistration(student1, db);
        manager.approveRegistration(grad, db);

        System.out.println("\n--- Putting marks ---");
        Mark m1 = new Mark(25, 28, 35);
        Mark m2 = new Mark(20, 22, 30);
        Mark m3 = new Mark(28, 30, 38);
        Mark failMark = new Mark(10, 10, 15);

        teacher.putMark(student1, oop, m1);
        teacher.putMark(student2, oop, m2);
        teacher.putMark(student3, oop, m3);

        System.out.println("\n--- Testing MaxFailException ---");
        try {
            student2.addMark(failMark);
            student2.addMark(new Mark(5, 5, 10));
            student2.addMark(new Mark(8, 8, 12)); // 3-й провал — бросает исключение
        } catch (MaxFailException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("\n--- Transcripts ---");
        student1.viewTranscript();
        System.out.println();
        student3.viewTranscript();

        System.out.println("\n--- Attendance (BONUS) ---");
        teacher.markAttendance(oop, student1, true);
        teacher.markAttendance(oop, student1, true);
        teacher.markAttendance(oop, student1, false);
        teacher.markAttendance(oop, student2, false);
        teacher.markAttendance(oop, student2, true);
        student1.viewAttendance();

        System.out.println("\n--- Marks Report (BONUS) ---");
        teacher.generateMarksReport(oop);

        System.out.println("\n--- Schedule (BONUS) ---");
        teacher.createLesson(Lesson.LessonType.LECTURE, oop,
            LocalDateTime.of(2026, 5, 20, 9, 0));
        teacher.createLesson(Lesson.LessonType.PRACTICE, oop,
            LocalDateTime.of(2026, 5, 22, 11, 0));
        teacher.createLesson(Lesson.LessonType.LECTURE, oop,
            LocalDateTime.of(2026, 5, 19, 14, 0));
        manager.viewSchedule(oop);

        System.out.println("\n--- Regex Search (BONUS) ---");
        manager.searchStudents(sys.getStudents(), "^A");     // имя начинается с A
        manager.searchStudents(sys.getStudents(), "enko$");  // фамилия на -enko
        manager.searchTeachers(sys.getTeachers(), "assem");

        System.out.println("\n--- Students by GPA ---");
        manager.viewStudentsSortedByGPA(sys.getStudents());

        System.out.println("\n--- Teacher rating ---");
        student1.rateTeacher(teacher, 5);
        student2.rateTeacher(teacher, 4);
        student3.rateTeacher(teacher, 5);
        System.out.println(teacher.getFirstName()
            + " avg rating: " + String.format("%.1f", teacher.getRating()));

        System.out.println("\n--- Research module ---");
        ResearchPaper paper1 = new ResearchPaper(
            "Deep Learning in NLP", "IEEE", LocalDate.of(2024, 3, 1), 12, "10.1/abc");
        paper1.addAuthor("Assem Mukhtarkyzy");
        paper1.setCitations(15);

        ResearchPaper paper2 = new ResearchPaper(
            "Quantum Computing Basics", "ACM", LocalDate.of(2023, 7, 15), 8, "10.2/xyz");
        paper2.addAuthor("Assem Mukhtarkyzy");
        paper2.setCitations(4);

        ResearchPaper paper3 = new ResearchPaper(
            "OOP Patterns in Java", "Springer", LocalDate.of(2025, 1, 10), 20, "10.3/oop");
        paper3.addAuthor("Assem Mukhtarkyzy");
        paper3.setCitations(9);

        teacher.addPaper(paper1);
        teacher.addPaper(paper2);
        teacher.addPaper(paper3);

        System.out.println("Teacher H-index: " + teacher.getHIndex());

        System.out.println("\nPapers by citations:");
        teacher.printPapers(new PaperByCitationComparator());

        System.out.println("\nPapers by date:");
        teacher.printPapers(new PaperByDateComparator());

        System.out.println("\nPapers by pages:");
        teacher.printPapers(new PaperByPagesComparator());

        ResearchProject project = new ResearchProject("AI in University Systems");
        try {
            project.addParticipant(teacher);
            project.addParticipant(grad);
            project.addParticipant(student1); // должно упасть
        } catch (NotAResearcherException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        project.addPaper(paper1);
        System.out.println(project);

        System.out.println("\n--- Graduate student supervisor (BONUS) ---");
        try {
            grad.requestSupervisor(teacher);  // h-index = 3, должно упасть
        } catch (LowHIndexException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("\n--- University Report ---");
        manager.createReport(sys.getCourses());

        System.out.println("\n--- Login test ---");
        sys.login("val", "1234");
        sys.login("hacker", "wrong");

        System.out.println("\n--- Display menus ---");
        admin.displayMenu();
        System.out.println();
        teacher.displayMenu();
        System.out.println();
        student1.displayMenu();
        System.out.println();
        manager.displayMenu();
        System.out.println();
        grad.displayMenu();

        System.out.println("\n--- Saving data ---");
        DataManager.save(sys);
        System.out.println("\n=== Demo complete ===");
    }
}