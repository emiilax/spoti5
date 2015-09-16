package Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public class User implements IUser {

    private String email;
    private String username;
    private String password;
    private String name;
    private double distance;

    /**
     * Creates a user with username, email and password. This class does not check
     * username and password
     * @param username Has to be checked before entered here
     * @param email Has to cbe checked before entered here
     * @param password Has to be checked before entered here
     */
    public User(String username, String email, String password){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String email, String password, String name){
        this(username, email, password);
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean checkPassword(String password) {
        return this.password.equals((String)password);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getDistance() {
        return distance;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void incDistance(double addedDistance) {
        distance = distance + addedDistance;
    }

    @Override
    public void decDistance(double reducedDistance) {
        distance = distance + reducedDistance;
    }
}
