package ku.cs.models;

import java.util.ArrayList;
public class HistoryList{
    private ArrayList<History> histories;
    public HistoryList() { this.histories = new ArrayList<>();}
    public void addHistory(History history){histories.add(history);}
    public ArrayList<History> getHistories(){
        return histories;
    }

}
