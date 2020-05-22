package sk.skauting.odborkovnk;

import java.util.Map;

public class Odborka {

    private String title;
    private Map<String,TaskForOdborka> taskForOdborky;

    public Odborka() {
    }

    public Odborka(String title, Map<String,TaskForOdborka> taskForOdborky) {
        this.title = title;
        this.taskForOdborky = taskForOdborky;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, TaskForOdborka> getTaskForOdborky() {
        return taskForOdborky;
    }

    public void setTaskForOdborky(Map<String, TaskForOdborka> taskForOdborky) {
        this.taskForOdborky = taskForOdborky;
    }
}