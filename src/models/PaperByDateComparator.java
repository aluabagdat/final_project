package models;

import java.util.Comparator;

public class PaperByDateComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper a, ResearchPaper b) {
        return a.getPublishedDate().compareTo(b.getPublishedDate());
    }
}