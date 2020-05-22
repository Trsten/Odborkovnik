package sk.skauting.odborkovnk;

public class TaskForOdborka {

    private String task;
    private int number;
    private boolean completed;

    public TaskForOdborka() {
    }

    public TaskForOdborka(String task, Integer number, Boolean completed) {
        this.task = task;
        this.number = number;
        this.completed = completed;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
