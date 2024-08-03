package model;
public class ToDo {
    private int id;
    private String task;
    private boolean isCompleted;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}
