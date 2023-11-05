package ku.cs.models;

public class History {
    private String userId;
    private String itemId;
    private String date;
    private Float amount;
    private String requisitionId;
    public History(String userId, String itemId, String date, Float amount, String requisitionId) {
        this.userId = userId;
        this.itemId = itemId;
        this.date = date;
        this.amount = amount;
        this.requisitionId = requisitionId;
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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }



}
