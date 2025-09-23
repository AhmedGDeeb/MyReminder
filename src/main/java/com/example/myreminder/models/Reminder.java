package com.example.myreminder.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Reminder {
    private int id;
    private int taskId;
    private String reminderTime;
    private boolean isTriggered;

    // (Constructors)
    public Reminder() {
    }

    public Reminder(int taskId, String reminderTime) {
        this.taskId = taskId;
        this.reminderTime = reminderTime;
        this.isTriggered = false;
    }

    public Reminder(int id, int taskId, String reminderTime, boolean isTriggered) {
        this.id = id;
        this.taskId = taskId;
        this.reminderTime = reminderTime;
        this.isTriggered = isTriggered;
    }

    // (Getters and Setters)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    public void setTriggered(boolean triggered) {
        isTriggered = triggered;
    }

    // (Helper Methods)
    public String getFormattedReminderTime() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = sdf.parse(reminderTime);
            if (date != null) {
                SimpleDateFormat outputFormat = new SimpleDateFormat("EEE, dd MMM yyyy - HH:mm", Locale.getDefault());
                return outputFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reminderTime;
    }

    public boolean isDue() {
        if (reminderTime == null || reminderTime.isEmpty()) {
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date reminderDate = sdf.parse(reminderTime);
            Date now = new Date();
            return reminderDate != null && reminderDate.before(now) && !isTriggered;
        } catch (Exception e) {
            return false;
        }
    }

    public long getTimeInMillis() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = sdf.parse(reminderTime);
            if (date != null) {
                return date.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getStatusText() {
        return isTriggered ? "تم التنبيه" : "في انتظار التنبيه";
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", reminderTime='" + getFormattedReminderTime() + '\'' +
                ", isTriggered=" + isTriggered +
                '}';
    }
}