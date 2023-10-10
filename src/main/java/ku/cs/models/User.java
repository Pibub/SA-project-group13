package ku.cs.models;

public class User {
    private String userId;
    private String userName;
    private String birthDate;
    private String sex;
    private String address;
    private String tel;
    private String workDate;
    private String password;
    private String userRole;
    private String databaseName = "User";

    public User(String userId , String userName , String birthDate , String sex , String address , String tel , String workDate ,String password ,  String userRole){
        this.userId = userId;
        this.userName = userName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.address =address;
        this.tel = tel;
        this.workDate = workDate;
        this.password =password;
        this.userRole = userRole;


    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getTel() {
        return tel;
    }

    public String getWorkDate() {
        return workDate;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUser(String username){
        return this.userName.equals(username);
    }

    public boolean isPassword(String password){
        return this.password.equals(password);
    }
}
