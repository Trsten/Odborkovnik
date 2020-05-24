package sk.skauting.odborkovnk.Model;

import java.util.Map;

public class Challenge {

    private String title;
    private String notes;
    private String ImgUrl;
    private Map<String, ChallengeTask> tasks;

    public Challenge() {
    }

    public Challenge(String title, String notes, String imgUrl, Map<String, ChallengeTask> tasks) {
        this.title = title;
        this.notes = notes;
        ImgUrl = imgUrl;
        this.tasks = tasks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Map<String, ChallengeTask> getTasks() {
        return tasks;
    }

    public void setTasks(Map<String, ChallengeTask> tasks) {
        this.tasks = tasks;
    }

    public int ngetNumerOfTask() {
        return this.tasks.size();
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
