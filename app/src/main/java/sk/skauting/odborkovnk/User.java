package sk.skauting.odborkovnk;

public class User {

    private String email;
    private String fullname;
    private String scoutNickname;
    private String scoutUnit;
    private String password;


    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public User(String email, String fullname, String scoutNickname, String scoutUnit, String password) {
        this.email = email;
        this.fullname = fullname;
        this.scoutNickname = scoutNickname;
        this.scoutUnit = scoutUnit;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getScoutNickname() {
        return scoutNickname;
    }

    public String getScoutUnit() {
        return scoutUnit;
    }

    public String getPassword() {
        return password;
    }
}

