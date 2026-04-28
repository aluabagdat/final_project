package models;

import java.util.Comparator;
import java.util.List;

public interface Researcher {

    int getHIndex();

    List<ResearchPaper> getPapers();

    void addPaper(ResearchPaper paper);

    List<ResearchProject> getProjects();

    default void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> sorted = getPapers();
        sorted.sort(comparator);
        System.out.println("=== RESEARCH PAPERS ===");
        for (ResearchPaper p : sorted) {
            System.out.println(p);
        }
    }
}