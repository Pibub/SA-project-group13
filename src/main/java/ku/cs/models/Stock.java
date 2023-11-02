package ku.cs.models;

public class Stock {
    private String itemId;
    private String itemName;
    private int amount;
    private String location;
    private String storageDate;
    private String categoryId;
    private String databaseName = "Stock";

    public Stock(String itemId , String itemName , int amount , String location , String storageDate, String categoryId){
        this.itemId = itemId;
        this.itemName =itemName;
        this.amount = amount;
        this.location = location;
        this.storageDate = storageDate;
        this.categoryId = categoryId;
    }
    public String getCategoryId(){ return  categoryId;}

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(String storageDate) {
        this.storageDate = storageDate;
    }

    public String getDatabaseName() {
        return databaseName;
    }


}
