package in.shantanupatil.notificationmanager.Model;

/**
 * Created by Shantanu on 12/20/2017.
 */

public class User {

    private String token;
    private String email;
    private String name;
    private String branch;


    public User(String email, String name, String branch, String token) {
        this.email = email;
        this.name = name;
        this.branch = branch;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;

    }

    public String getBranch() {
        return branch;
    }

    public String getToken() {
        return token;
    }
}
