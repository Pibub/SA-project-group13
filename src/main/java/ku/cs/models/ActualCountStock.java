package ku.cs.models;

public class ActualCountStock {
    private String categoryId;
    private String itemName;
    private float actualTotal;
    private float firstCount;
    private float secondCount;
    private float thirdCount;
    private String inputerId;
    private String inputerName;
    private float onHand;
    private String location;

    public String getLocation() {
        return location;
    }

    public ActualCountStock(String categoryId, String itemName, float firstCount, float secondCount, float thirdCount, String inputerId, String inputerName, String location) {
        this.categoryId = categoryId;
        this.itemName = itemName;
        this.firstCount = firstCount;
        this.secondCount = secondCount;
        this.thirdCount = thirdCount;
        this.inputerId = inputerId;
        this.inputerName = inputerName;
        this.location =location;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(float actualTotal) {
        this.actualTotal = actualTotal;
    }

    public float getFirstCount() {
        return firstCount;
    }

    public void setFirstCount(float firstCount) {
        this.firstCount = firstCount;
    }

    public float getSecondCount() {
        return secondCount;
    }

    public void setSecondCount(float secondCount) {
        this.secondCount = secondCount;
    }

    public float getThirdCount() {
        return thirdCount;
    }

    public void setThirdCount(float thirdCount) {
        this.thirdCount = thirdCount;
    }

    public String getInputerId() {
        return inputerId;
    }

    public void setInputerId(String inputerId) {
        this.inputerId = inputerId;
    }

    public String getInputerName() {
        return inputerName;
    }

    public void setInputerName(String inputerName) {
        this.inputerName = inputerName;
    }

    public void setOnHand(float onHand) {
        this.onHand = onHand;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
