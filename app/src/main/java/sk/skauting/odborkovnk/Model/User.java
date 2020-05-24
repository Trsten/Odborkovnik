package sk.skauting.odborkovnk.Model;

import java.util.Map;

public class User {

    private String email;
    private String fullname;
    private String scoutNickname;
    private String password;
    private String scoutUnit;
    private Map<String, Challenge> challenges;

    public User() {
    }

    public User(String email, String fullname, String scoutNickname, String password, String scoutUnit, Map<String, Challenge> challenges) {
        this.email = email;
        this.fullname = fullname;
        this.scoutNickname = scoutNickname;
        this.password = password;
        this.scoutUnit = scoutUnit;
        this.challenges = challenges;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getScoutNickname() {
        return scoutNickname;
    }

    public void setScoutNickname(String scoutNickname) {
        this.scoutNickname = scoutNickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScoutUnit() {
        return scoutUnit;
    }

    public void setScoutUnit(String scoutUnit) {
        this.scoutUnit = scoutUnit;
    }

    public Map<String, Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(Map<String, Challenge> challenges) {
        this.challenges = challenges;
    }
}

