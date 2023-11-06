package ku.cs.models;

public class Analyze {
    private String categoryId;
    private String userId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTextAnalyze() {
        return textAnalyze;
    }

    public void setTextAnalyze(String textAnalyze) {
        this.textAnalyze = textAnalyze;
    }

    public Analyze(String categoryId, String userId, String userName, String textAnalyze) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.userName = userName;
        this.textAnalyze = textAnalyze;
    }

    private String userName;
    private String textAnalyze;
}
