package ku.cs.models;

public class CountStock {
    private String userId;
    private String itemName;
    private String categoryId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
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

    public CountStock(String userId, String itemName, String categoryId, float total, String location, String userName) {
        this.userId = userId;
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.total = total;
        this.location = location;
        this.userName = userName;
    }

    private float total;
    private String location;
    private String userName;

}
