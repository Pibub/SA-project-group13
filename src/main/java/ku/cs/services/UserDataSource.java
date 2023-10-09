package ku.cs.services;

import ku.cs.models.User;
import ku.cs.models.UserList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDataSource implements Datasource<UserList> {
    private DatabaseConnection databaseConnection;
    public UserDataSource(){}

    public UserDataSource(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }
    @Override
    public UserList readData(){
        UserList list = new UserList();
        databaseConnection = new DatabaseConnection();
        Connection connectionUser = databaseConnection.getConnection();
        String getUserData = "SELECT * FROM employee";
        try {
            Statement statement = connectionUser.createStatement();
            ResultSet queryOutput = statement.executeQuery(getUserData);
            while(queryOutput != null && queryOutput.next()){
                String userId = queryOutput.getString(1);
                String userName = queryOutput.getString(2);
                String birthDate = queryOutput.getString(3);
                String sex = queryOutput.getString(4);
                String address = queryOutput.getString(5);
                int tel = queryOutput.getInt(6);
                String workDate = queryOutput.getString(7);
                String password = queryOutput.getString(8);
                String userRole = queryOutput.getString(9);

                list.addUser(new User(userId,userName,birthDate,sex,address,tel,workDate,password,userRole));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insertData(UserList userList) {

    }
}
