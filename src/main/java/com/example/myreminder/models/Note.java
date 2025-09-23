package com.example.myreminder.models;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note {
    private int id;
    private String noteText;
    private String tag;
    private String createdAt;
    private String updatedAt;

    //  (Constructors)
    public Note() {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
    }

    public Note(String noteText, String tag) {
        this.noteText = noteText;
        this.tag = tag;
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
    }

    public Note(int id, String noteText, String tag, String createdAt, String updatedAt) {
        this.id = id;
        this.noteText = noteText;
        this.tag = tag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // (Getters and Setters)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
        this.updatedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
        this.updatedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    // (Helper Methods)
    public String getFormattedCreatedAt() {
        return formatDate(createdAt);
    }

    public String getFormattedUpdatedAt() {
        return formatDate(updatedAt);
    }

    private String formatDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = sdf.parse(dateString);
            if (date != null) {
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                return outputFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public boolean hasTag() {
        return tag != null && !tag.trim().isEmpty();
    }

    public String getPreviewText() {
        if (noteText == null) return "";
        if (noteText.length() <= 100) {
            return noteText;
        }
        return noteText.substring(0, 100) + "...";
    }

    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", noteText='" + getPreviewText() + '\'' +
                ", tag='" + tag + '\'' +
                ", updatedAt='" + getFormattedUpdatedAt() + '\'' +
                '}';
    }
}