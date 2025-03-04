package ru.samsung.itschool.mdev.roomvsfragment.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDB extends RoomDatabase {
    // инициализация таблицы в БД
    public abstract TaskDao taskDao();
}