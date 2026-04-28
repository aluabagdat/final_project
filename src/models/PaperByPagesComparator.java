package models;

import java.util.Comparator;

public class PaperByPagesComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(a.getPages(), b.getPages());
    }
}