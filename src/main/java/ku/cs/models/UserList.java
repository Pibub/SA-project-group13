package ku.cs.models;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;
    public UserList(){
        users = new ArrayList<>();
    }
    public void addUser(User user){
        users.add(user);
    }
    public boolean isCorrectPair(String userName, String password){
        for (User user : users){
            if (user.getUserName().equals(userName)&&user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public User findUser(String username, String password) {
        for (User user : users) {
            if (user.isUser(username) && user.isPassword(password)) {
                return user;
            }
        }
        return null;
    }

    public User findUserByUsername(String username) {
        for(User user : users)
        {
            if(user.isUser(username)){
                return user;
            }
        }
        return null;
    }
    public User findUserByIdAndUsername(String id, String username) {
        for(User user : users)
        {
            if(user.isId(id) && user.isUser(username)){
                return user;
            }
        }
        return null;
    }
    public void addNewUser(String userId, String userName, String birthDate , String sex, String address, String tel, String workDate, String password, String userRole, String userImage) {
        userId = userId.trim();
        userName = userName.trim();
        birthDate = birthDate.trim();
        sex = sex.trim();
        address = address.trim();
        tel = tel.trim();
        workDate = workDate.trim();
        userRole = userRole.trim();
        userImage = userImage.trim();
        if (!userId.equals("") && !userName.equals("")) {
            User exist = findUserByIdAndUsername(userId, userName);
            if (exist == null) {
                users.add(new User(userId, userName, birthDate , sex, address, tel, workDate, password, userRole, userImage));
            }
        }
    }

    public ArrayList<User> getUsers(){
        return users;
    }
}
