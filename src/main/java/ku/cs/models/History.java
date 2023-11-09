package ku.cs.models;

public class History {
    private String userId;
    private String itemId;
    private String date;
    private int qty;
    private String requisitionId;
    private String unit;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public History(String userId, String itemId, String date, int qty, String requisitionId, String unit) {
        this.userId = userId;
        this.itemId = itemId;
        this.date = date;
        this.qty = qty;
        this.requisitionId = requisitionId;
        this.unit = unit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }



}
