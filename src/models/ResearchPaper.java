package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class ResearchPaper implements Comparable<ResearchPaper>, Serializable {

    private String title;
    private List<String> authors;
    private String journal;
    private LocalDate publishedDate;
    private int citations;
    private int pages;
    private String doi;

    public ResearchPaper(String title, String journal, LocalDate publishedDate, int pages, String doi) {
        this.title = title;
        this.journal = journal;
        this.publishedDate = publishedDate;
        this.pages = pages;
        this.doi = doi;
        this.citations = 0;
        this.authors = new ArrayList<>();
    }

    public String getTitle() { return title; }
    public List<String> getAuthors() { return authors; }
    public String getJournal() { return journal; }
    public LocalDate getPublishedDate() { return publishedDate; }
    public int getCitations() { return citations; }
    public int getPages() { return pages; }
    public String getDoi() { return doi; }

    public void setCitations(int citations) { this.citations = citations; }
    public void addAuthor(String author) { authors.add(author); }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    @Override
    public String toString() {
        return title + " | " + journal + " | Citations: " + citations;
    }
}