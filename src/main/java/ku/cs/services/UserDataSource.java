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
                String tel = queryOutput.getString(6);
                String workDate = queryOutput.getString(7);
                String password = queryOutput.getString(8);
                String userRole = queryOutput.getString(9);
                String userImage = queryOutput.getString(10);

                list.addUser(new User(userId, userName, birthDate, sex, address,tel, workDate,password, userRole, userImage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insertData(UserList userList) {
        databaseConnection = new DatabaseConnection();
        Connection connectionUser = databaseConnection.getConnection();

        for (User user : userList.getUsers()) {
            try {
                Statement statement = connectionUser.createStatement();

                String checkUserQuery = "SELECT * FROM employee WHERE id = '" + user.getUserId() + "'";
                ResultSet checkUserResult = statement.executeQuery(checkUserQuery);

                if (checkUserResult.next()) {
                    String updateUserQuery = "UPDATE employee SET name = '" + user.getUserName() + "', " +
                            "date_of_birth = '" + user.getBirthDate() + "', " +
                            "sex = '" + user.getSex() + "', " +
                            "address = '" + user.getAddress() + "', " +
                            "phone_number = '" + user.getTel() + "', " +
                            "start_working_date = '" + user.getWorkDate() + "', " +
                            "password = '" + user.getPassword() + "', " +
                            "role = '" + user.getUserRole() + "', " +
                            "image = '" + user.getUserImage() + "' " +
                            "WHERE id = '" + user.getUserId() + "'";


                    statement.executeUpdate(updateUserQuery);
                } else {
                    // If the user doesn't exist, insert a new user
                    String insertUserQuery = "INSERT INTO employee (id, name, date_of_birth, sex, address, phone_number, start_working_date, password, role, image) " +
                            "VALUES ('" + user.getUserId() + "', '" + user.getUserName() + "', '" + user.getBirthDate() + "', '" +
                            user.getSex() + "', '" + user.getAddress() + "', '" + user.getTel() + "', '" +
                            user.getWorkDate() + "', '" + user.getPassword() + "', '" + user.getUserRole() + "', '" + user.getUserImage() + "')";

                    statement.executeUpdate(insertUserQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteData(String itemId) {

    }

}
