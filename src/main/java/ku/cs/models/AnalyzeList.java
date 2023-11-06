package ku.cs.models;

import java.util.ArrayList;

public class AnalyzeList {
    private ArrayList<Analyze> analyzes;

    public AnalyzeList() {
        analyzes = new ArrayList<>();
    }

    public void addAnalyze(Analyze analyze) {
        analyzes.add(analyze);
    }

    public ArrayList<Analyze> getAnalyzes() {
        return analyzes;
    }

}
