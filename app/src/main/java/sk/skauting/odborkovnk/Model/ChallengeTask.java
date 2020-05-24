package sk.skauting.odborkovnk.Model;

public class ChallengeTask {

    private String complete;
    private String task;

    public ChallengeTask(String complete, String task) {
        this.complete = complete;
        this.task = task;
    }

    public ChallengeTask() {
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
