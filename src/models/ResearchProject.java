package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import exceptions.NotAResearcherException;

public class ResearchProject implements Serializable {

    private String topic;
    private List<ResearchPaper> publishedPapers;
    private List<Researcher> participants;

    public ResearchProject(String topic) {
        this.topic = topic;
        this.publishedPapers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public String getTopic() { return topic; }
    public List<ResearchPaper> getPublishedPapers() { return publishedPapers; }
    public List<Researcher> getParticipants() { return participants; }

    public void addParticipant(Researcher researcher) throws NotAResearcherException {
        if (researcher == null) {
            throw new IllegalArgumentException("Researcher cannot be null");
        }
        participants.add(researcher);
        User user = (User) researcher;
        System.out.println(user.getFirstName() + " added to project: " + topic);
    }

    public void addPaper(ResearchPaper paper) {
        if (paper == null) {
            throw new IllegalArgumentException("Paper cannot be null");
        }
        publishedPapers.add(paper);
        System.out.println("Paper added: " + paper.getTitle());
    }

    @Override
    public String toString() {
        return "Project: " + topic + " | Participants: " + participants.size();
    }
}