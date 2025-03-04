package ru.samsung.itschool.mdev.roomvsfragment.utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.samsung.itschool.mdev.roomvsfragment.db.Task;

public class TaskCallback extends DiffUtil.Callback {
    private List<Task> oldList;
    private List<Task> newList;

    public TaskCallback(List<Task> oldList, List<Task> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Task oldItem = oldList.get(oldItemPosition);
        Task newItem = newList.get(newItemPosition);

        return Objects.equals(oldItem.getTask(), newItem.getTask())
                && Objects.equals(oldItem.getDesc(), newItem.getDesc())
                && Objects.equals(oldItem.getFinishBy(), newItem.getFinishBy())
                && Objects.equals(oldItem.isFinished(), newItem.isFinished())
                && Objects.equals(oldItem.getId(), newItem.getId());
    }
}
