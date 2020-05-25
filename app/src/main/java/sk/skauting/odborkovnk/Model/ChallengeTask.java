package sk.skauting.odborkovnk.Model;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("complete", this.complete);
        result.put("task", this.task);
        return result;
    }
}
