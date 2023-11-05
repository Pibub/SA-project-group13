package ku.cs.models;

import java.util.ArrayList;

public class HistoryList {
    private ArrayList<History> history;
    public HistoryList(){
        history = new ArrayList<>();
    }
    public void addHistory(History history){history.add(history);}

    public void addHistory(String user_id , String item_id , String date) {
        user_id = user_id.trim();
        item_id = item_id.trim();
        date = date.trim();
        if (!item_id.equals("") && !user_id.equals("")) {
            History exist = findItem(item_id);
            if (exist == null) {
                history.add(new History(user_id , item_id , date));
            }
        }
    }

    public History findItem(String id){
        for(History history : history)
        {
            if (history.getItem_id().equals(id)){
                return history;
            }
        }
        return null;
    }

    public ArrayList<Stock> getHistoryList(){
        return history;
    }
}
