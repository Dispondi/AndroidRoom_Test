package ru.samsung.itschool.mdev.roomvsfragment.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task t);

    @Delete
    void delete(Task t);

    @Update
    void update(Task t);

    @Query("select * from task")
    Flowable<List<Task>> getAll();

    @Query("select * from task where id == :id")
    Task findById(int id);
}