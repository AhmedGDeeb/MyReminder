// \app\src\main\java\com\example\myreminder\database\DatabaseHelper.java
package com.example.myreminder.database;

import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.myreminder.models.Task;
import com.example.myreminder.models.Note;
import com.example.myreminder.models.Reminder;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_notes.db";
    private static final int DATABASE_VERSION = 1;

    // tables names
    public static final String TABLE_TASKS = "tasks";
    public static final String TABLE_NOTES = "notes";
    public static final String TABLE_REMINDERS = "reminders";

    // columns names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_DUE_DATE = "due_date";
    public static final String COLUMN_IS_DONE = "is_done";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_NOTE_TEXT = "note_text";
    public static final String COLUMN_TAG = "tag";
    public static final String COLUMN_UPDATED_AT = "updated_at";
    public static final String COLUMN_TASK_ID = "task_id";
    public static final String COLUMN_REMINDER_TIME = "reminder_time";
    public static final String COLUMN_IS_TRIGGERED = "is_triggered";

    // creating tables
    private static final String CREATE_TABLE_TASKS =
            "CREATE TABLE " + TABLE_TASKS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT NOT NULL, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_PRIORITY + " INTEGER DEFAULT 1, " +
                    COLUMN_DUE_DATE + " TEXT, " +
                    COLUMN_IS_DONE + " INTEGER DEFAULT 0, " +
                    COLUMN_CREATED_AT + " TEXT DEFAULT (datetime('now'))" +
                    ")";

    private static final String CREATE_TABLE_NOTES =
            "CREATE TABLE " + TABLE_NOTES + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOTE_TEXT + " TEXT NOT NULL, " +
                    COLUMN_TAG + " TEXT, " +
                    COLUMN_CREATED_AT + " TEXT DEFAULT (datetime('now')), " +
                    COLUMN_UPDATED_AT + " TEXT DEFAULT (datetime('now'))" +
                    ")";

    private static final String CREATE_TABLE_REMINDERS =
            "CREATE TABLE " + TABLE_REMINDERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TASK_ID + " INTEGER NOT NULL, " +
                    COLUMN_REMINDER_TIME + " TEXT NOT NULL, " +
                    COLUMN_IS_TRIGGERED + " INTEGER DEFAULT 0, " +
                    "FOREIGN KEY (" + COLUMN_TASK_ID + ") REFERENCES " +
                    TABLE_TASKS + "(" + COLUMN_ID + ") ON DELETE CASCADE" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        db.execSQL(CREATE_TABLE_TASKS);
        db.execSQL(CREATE_TABLE_NOTES);
        db.execSQL(CREATE_TABLE_REMINDERS);

        // inster test data
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // deleting old table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        // recreating tables
        onCreate(db);
    }

    // test data
    private void insertSampleData(SQLiteDatabase db) {
        // tasks test data
        ContentValues task1 = new ContentValues();
        task1.put(COLUMN_TITLE, "إنهاء التقرير");
        task1.put(COLUMN_DESCRIPTION, "كتابة التقرير النهائي للمشروع");
        task1.put(COLUMN_PRIORITY, 3); // عاجل
        task1.put(COLUMN_DUE_DATE, "2025-10-27 15:00:00");
        task1.put(COLUMN_IS_DONE, 0);
        db.insert(TABLE_TASKS, null, task1);

        ContentValues task2 = new ContentValues();
        task2.put(COLUMN_TITLE, "شراء مستلزمات");
        task2.put(COLUMN_DESCRIPTION, "شراء مستلزمات المكتب");
        task2.put(COLUMN_PRIORITY, 2); // مهم
        task2.put(COLUMN_DUE_DATE, "2025-10-26 10:00:00");
        task2.put(COLUMN_IS_DONE, 1);
        db.insert(TABLE_TASKS, null, task2);

        // notes test data
        ContentValues note1 = new ContentValues();
        note1.put(COLUMN_NOTE_TEXT, "الاتصال بالعميل غدًا لتأكيد الموعد");
        note1.put(COLUMN_TAG, "عمل");
        db.insert(TABLE_NOTES, null, note1);

        ContentValues note2 = new ContentValues();
        note2.put(COLUMN_NOTE_TEXT, "فكرة لمشروع جديد: تطبيق لإدارة الوقت");
        note2.put(COLUMN_TAG, "أفكار");
        db.insert(TABLE_NOTES, null, note2);

        // reminders test data
        ContentValues reminder1 = new ContentValues();
        reminder1.put(COLUMN_TASK_ID, 1);
        reminder1.put(COLUMN_REMINDER_TIME, "2025-10-27 14:30:00");
        reminder1.put(COLUMN_IS_TRIGGERED, 0);
        db.insert(TABLE_REMINDERS, null, reminder1);
    }

    // ======== Tasks functions ========

    public long addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, task.getTitle());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_PRIORITY, task.getPriority());
        values.put(COLUMN_DUE_DATE, task.getDueDate());
        values.put(COLUMN_IS_DONE, task.isDone() ? 1 : 0);

        long id = db.insert(TABLE_TASKS, null, values);
        db.close();
        return id;
    }

    public Task getTask(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_PRIORITY,
                        COLUMN_DUE_DATE, COLUMN_IS_DONE, COLUMN_CREATED_AT},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        cursor.moveToFirst();

        Task task = new Task(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1,
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT))
        );

        cursor.close();
        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " ORDER BY " + COLUMN_CREATED_AT + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                task.setPriority(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)));
                task.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)));
                task.setDone(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1);
                task.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, task.getTitle());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_PRIORITY, task.getPriority());
        values.put(COLUMN_DUE_DATE, task.getDueDate());
        values.put(COLUMN_IS_DONE, task.isDone() ? 1 : 0);

        return db.update(TABLE_TASKS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public void deleteTask(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ========== (Notes) ==========

    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TEXT, note.getNoteText());
        values.put(COLUMN_TAG, note.getTag());

        long id = db.insert(TABLE_NOTES, null, values);
        db.close();
        return id;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " ORDER BY " + COLUMN_CREATED_AT + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                note.setNoteText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_TEXT)));
                note.setTag(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAG)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT)));

                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TEXT, note.getNoteText());
        values.put(COLUMN_TAG, note.getTag());
        values.put(COLUMN_UPDATED_AT, "datetime('now')");

        return db.update(TABLE_NOTES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ========== (Reminders) ==========

    public long addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_ID, reminder.getTaskId());
        values.put(COLUMN_REMINDER_TIME, reminder.getReminderTime());
        values.put(COLUMN_IS_TRIGGERED, reminder.isTriggered() ? 1 : 0);

        long id = db.insert(TABLE_REMINDERS, null, values);
        db.close();
        return id;
    }

    public List<Reminder> getRemindersForTask(long taskId) {
        List<Reminder> reminders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REMINDERS,
                new String[]{COLUMN_ID, COLUMN_TASK_ID, COLUMN_REMINDER_TIME, COLUMN_IS_TRIGGERED},
                COLUMN_TASK_ID + "=?",
                new String[]{String.valueOf(taskId)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                reminder.setTaskId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_ID)));
                reminder.setReminderTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_TIME)));
                reminder.setTriggered(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_TRIGGERED)) == 1);

                reminders.add(reminder);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reminders;
    }

    // ========== search and filttering ==========

    public List<Task> searchTasks(String query) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_PRIORITY,
                        COLUMN_DUE_DATE, COLUMN_IS_DONE, COLUMN_CREATED_AT},
                COLUMN_TITLE + " LIKE ? OR " + COLUMN_DESCRIPTION + " LIKE ?",
                new String[]{"%" + query + "%", "%" + query + "%"}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                task.setPriority(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)));
                task.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)));
                task.setDone(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1);
                task.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public List<Note> searchNotes(String query) {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES,
                new String[]{COLUMN_ID, COLUMN_NOTE_TEXT, COLUMN_TAG, COLUMN_CREATED_AT, COLUMN_UPDATED_AT},
                COLUMN_NOTE_TEXT + " LIKE ? OR " + COLUMN_TAG + " LIKE ?",
                new String[]{"%" + query + "%", "%" + query + "%"}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                note.setNoteText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_TEXT)));
                note.setTag(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAG)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT)));

                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    public List<Task> getTasksByPriority(int priority) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_PRIORITY,
                        COLUMN_DUE_DATE, COLUMN_IS_DONE, COLUMN_CREATED_AT},
                COLUMN_PRIORITY + "=?",
                new String[]{String.valueOf(priority)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                task.setPriority(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)));
                task.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)));
                task.setDone(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1);
                task.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public List<Task> getUrgentTasksByDate(String startDate, String endDate) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TASKS +
                " WHERE " + COLUMN_PRIORITY + " = 3" +
                " AND " + COLUMN_DUE_DATE + " BETWEEN ? AND ?" +
                " ORDER BY " + COLUMN_DUE_DATE + " ASC";

        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                task.setPriority(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)));
                task.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)));
                task.setDone(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1);
                task.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public List<Task> getCompletedTasks() {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_PRIORITY,
                        COLUMN_DUE_DATE, COLUMN_IS_DONE, COLUMN_CREATED_AT},
                COLUMN_IS_DONE + "=?",
                new String[]{"1"}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                task.setPriority(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)));
                task.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)));
                task.setDone(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1);
                task.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public List<Task> getPendingTasks() {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_PRIORITY,
                        COLUMN_DUE_DATE, COLUMN_IS_DONE, COLUMN_CREATED_AT},
                COLUMN_IS_DONE + "=?",
                new String[]{"0"}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                task.setPriority(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)));
                task.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)));
                task.setDone(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1);
                task.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public int getTasksCountByStatus(boolean isDone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_TASKS +
                        " WHERE " + COLUMN_IS_DONE + " = ?",
                new String[]{isDone ? "1" : "0"});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
}