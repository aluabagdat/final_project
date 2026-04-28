package models;

import java.util.Comparator;

public class PaperByCitationComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(b.getCitations(), a.getCitations());
    }
}