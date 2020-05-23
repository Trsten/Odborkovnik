package sk.skauting.odborkovnk;

import java.util.Map;

public class Challenge {

    private String title;
    private Map<String, TaskChallenge> taskForOdborky;

    public Challenge() {
    }

    public Challenge(String title, Map<String, TaskChallenge> taskForOdborky) {
        this.title = title;
        this.taskForOdborky = taskForOdborky;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, TaskChallenge> getTaskForOdborky() {
        return taskForOdborky;
    }

    public void setTaskForOdborky(Map<String, TaskChallenge> taskForOdborky) {
        this.taskForOdborky = taskForOdborky;
    }
}