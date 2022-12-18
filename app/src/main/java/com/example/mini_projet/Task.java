package com.example.mini_projet;

public class Task {
    private String label_task;
    private String description_task;
    private String time_task;
    private String priority_task;

    public Task(String label_task, String description_task, String time_task, String priority_task) {
        this.label_task = label_task;
        this.description_task = description_task;
        this.time_task = time_task;
        this.priority_task = priority_task;
    }

    public String getPriority_task() {
        return priority_task;
    }

    public void setPriority_task(String priority_task) {
        this.priority_task = priority_task;
    }

    public String getTime_task() {
        return time_task;
    }

    public void setTime_task(String time_task) {
        this.time_task = time_task;
    }

    public String getDescription_task() {
        return description_task;
    }

    public void setDescription_task(String description_task) {
        this.description_task = description_task;
    }

    public String getLabel_task() {
        return label_task;
    }

    public void setLabel_task(String label_task) {
        this.label_task = label_task;
    }

}
