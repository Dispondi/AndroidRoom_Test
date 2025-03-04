package ru.samsung.itschool.mdev.roomvsfragment.db;

import android.content.Context;

import androidx.room.Room;

public class DBClient {
    // Singletone - паттерн
    private Context context;
    private static DBClient client;
    private TaskDB taskDatabase;

    private DBClient(Context context) {
        this.context = context;
        // Builder - паттерн
        this.taskDatabase = Room.databaseBuilder(context,TaskDB.class,"Task2025").build();
    }
    public static DBClient getInstance(Context context) {
        if(client == null) {
            client = new DBClient(context);
        }
        return client;
    }

    public TaskDB getTaskDatabase() {
        return taskDatabase;
    }
}