package com.teamtreehouse.techdegrees.com.teamtreehouse.techdegrees.model;

import java.util.Objects;

public class Todo {
    private int id;
    private String name;
    private Boolean isCompleted;

    public Todo(int id, String name, Boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;
        return id == todo.id && name.equals(todo.name) && isCompleted.equals(todo.isCompleted);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + isCompleted.hashCode();
        return result;
    }
}
