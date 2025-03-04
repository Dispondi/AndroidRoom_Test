package ru.samsung.itschool.mdev.roomvsfragment.db;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import ru.samsung.itschool.mdev.roomvsfragment.adapter.TaskAdapter;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String task; // заголовок

    @ColumnInfo
    private String desc; // описание таска

    @ColumnInfo
    private String finishBy; // дата окончания таска

    @ColumnInfo
    private boolean finished; // статус таска

    public Task(String task, String desc, String finishBy) {
        this.task = task;
        this.desc = desc;
        this.finishBy = finishBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFinishBy() {
        return finishBy;
    }

    public void setFinishBy(String finishBy) {
        this.finishBy = finishBy;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}