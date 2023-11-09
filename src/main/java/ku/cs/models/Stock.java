package ku.cs.models;

public class Stock {
    private String itemId;
    private String itemName;
    private int qty;
    private String location;
    private String storageDate;
    private String shelfId;
    private String unit;
    private String databaseName = "Stock";

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public Stock(String itemId , String itemName , int qty , String location , String storageDate, String shelfId, String unit){
        this.itemId = itemId;
        this.itemName =itemName;
        this.qty = qty;
        this.location = location;
        this.storageDate = storageDate;
        this.shelfId = shelfId;
        this.unit = unit;
    }


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
