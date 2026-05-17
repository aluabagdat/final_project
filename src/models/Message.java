package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private User from;
    private User to;
    private String text;
    private LocalDateTime timestamp;
    private boolean isComplaint;

    public Message(User from, User to, String text, boolean isComplaint) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.timestamp = LocalDateTime.now();
        this.isComplaint = isComplaint;
    }

    public User getFrom() { return from; }
    public User getTo() { return to; }
    public String getText() { return text; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isComplaint() { return isComplaint; }

    @Override
    public String toString() {
        return (isComplaint ? "[COMPLAINT] " : "[MESSAGE] ") +
               from.getFirstName() + " -> " + (to != null ? to.getFirstName() : "Admin") +
               ": " + text + " (" + timestamp + ")";
    }
}