package com.hacktiv8.finpro1todolist;

public class ToDoList {

    int id;
    String title;

    public ToDoList(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public ToDoList() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
