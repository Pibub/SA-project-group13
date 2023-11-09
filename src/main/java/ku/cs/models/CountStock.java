package ku.cs.models;

public class CountStock {
    private String userId;
    private String shelfId;
    private int total;
    private String location;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public CountStock(String userId, String shelfId, int total, String location, String userName) {
        this.userId = userId;
        this.shelfId = shelfId;
        this.total = total;
        this.location = location;
        this.userName = userName;
    }
}
