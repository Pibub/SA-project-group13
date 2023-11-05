package ku.cs.models;

public class History {
    private String user_id;
    private String item_id;

    private String date;
    private String databaseName = "History";

    public History(String user_id , String item_id , String date){
        this.user_id = user_id;
        this.item_id = item_id;
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void add(History history) {
    }
}
