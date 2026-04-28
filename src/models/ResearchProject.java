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

    public void addParticipant(User u) throws NotAResearcherException {
        if (!(u instanceof Researcher)) {
            throw new NotAResearcherException(u.getFirstName() + " is not a Researcher!");
        }
        participants.add((Researcher) u);
        System.out.println(u.getFirstName() + " added to project: " + topic);
    }

    public void addPaper(ResearchPaper paper) {
        publishedPapers.add(paper);
        System.out.println("Paper added: " + paper.getTitle());
    }

    @Override
    public String toString() {
        return "Project: " + topic + " | Participants: " + participants.size();
    }
}