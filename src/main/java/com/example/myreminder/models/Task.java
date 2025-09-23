package com.example.myreminder.models;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Task {
    private int id;
    private String title;
    private String description;
    private int priority; // 1: عادي, 2: مهم, 3: عاجل
    private String dueDate;
    private boolean isDone;
    private String createdAt;

    // (Constructors)
    public Task() {
        this.createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public Task(String title, String description, int priority, String dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isDone = false;
        this.createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public Task(int id, String title, String description, int priority, String dueDate, boolean isDone, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isDone = isDone;
        this.createdAt = createdAt;
    }

    // دوال الوصول (Getters and Setters)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // (Helper Methods)
    public String getPriorityText() {
        switch (priority) {
            case 1:
                return "عادي";
            case 2:
                return "مهم";
            case 3:
                return "عاجل";
            default:
                return "غير محدد";
        }
    }

    public int getPriorityColor() {
        switch (priority) {
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 1;
        }
    }

    public boolean isOverdue() {
        if (dueDate == null || dueDate.isEmpty()) {
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date due = sdf.parse(dueDate);
            Date now = new Date();
            return due != null && due.before(now) && !isDone;
        } catch (Exception e) {
            return false;
        }
    }

    public String getStatusText() {
        return isDone ? "منجزة" : "قيد الانتظار";
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", priority=" + getPriorityText() +
                ", isDone=" + isDone +
                '}';
    }
}
