package com.example.application.views;

import javax.validation.constraints.NotBlank;

public class Todo {
    private boolean done = false;
    private String task = "";

    public Todo(String task) {
        this.task = task;
    }

    public Todo() {
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return task + ": " + (done ? "done" : "incomplete");
    }
}
