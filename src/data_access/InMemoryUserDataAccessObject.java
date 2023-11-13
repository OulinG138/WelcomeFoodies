package data_access;

import entity.User;
import use_case.register.RegisterUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDataAccessObject implements RegisterUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, User> usersID = new HashMap<>();

    /**
     * @param identifier the user's username
     * @return whether the user exists
     */
    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public boolean existsByID(int identifier) {
        return users.containsValue(identifier);
    }

    @Override
    public boolean duplicatedID(int userID) {
        return usersID.containsKey(userID);
    }

    /**
     * @param user the data to save
     */
    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    private void clear() {
        users.clear();
    }

    public User get(String username){
        return users.get(username);
    }
}
