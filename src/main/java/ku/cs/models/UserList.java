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

    public ArrayList<User> getUsers(){
        return users;
    }
}
