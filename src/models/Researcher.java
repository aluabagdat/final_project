package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public interface Researcher {

    int getHIndex();
    List<ResearchPaper> getPapers();
    void addPaper(ResearchPaper paper);
    List<ResearchProject> getProjects();

    default void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> sorted = new ArrayList<>(getPapers());
        sorted.sort(comparator);
        if (sorted.isEmpty()) {
            System.out.println("  (no papers)");
        } else {
            for (ResearchPaper p : sorted) {
                System.out.println("  " + p);
            }
        }
        System.out.println("  H-Index: " + getHIndex());
    }
}