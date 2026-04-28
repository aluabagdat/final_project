package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Lesson implements Serializable {

    public enum LessonType { LECTURE, PRACTICE }

    private LessonType type;
    private Teacher teacher;
    private Course course;
    private LocalDateTime dateTime;

    public Lesson(LessonType type, Teacher teacher, Course course, LocalDateTime dateTime) {
        this.type = type;
        this.teacher = teacher;
        this.course = course;
        this.dateTime = dateTime;
    }

    public LessonType getType() { return type; }
    public Teacher getTeacher() { return teacher; }
    public Course getCourse() { return course; }
    public LocalDateTime getDateTime() { return dateTime; }

    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    @Override
    public String toString() {
        return type + " | " + course.getName() + " | " + teacher.getFirstName() + " | " + dateTime;
    }
}