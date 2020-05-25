package sk.skauting.odborkovnk.Model;

import java.util.HashMap;
import java.util.Map;

public class Challenge {

    private String title;
    private String notes;
    private String imageUrl;
    private Map<String, ChallengeTask> tasks;

    public Challenge() {
    }

    public Challenge(String title, String notes, String imageUrl, Map<String, ChallengeTask> tasks) {
        this.title = title;
        this.notes = notes;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int numberOfCompleted() {
        int completed = 0;
        for (Map.Entry<String, ChallengeTask> task : this.tasks.entrySet()) {
            if (task.getValue().getComplete().equals("true")) {
                completed++;
            }
        }
        return completed;
    }

}
