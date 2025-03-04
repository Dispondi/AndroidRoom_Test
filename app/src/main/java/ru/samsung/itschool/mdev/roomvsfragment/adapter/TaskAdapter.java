package ru.samsung.itschool.mdev.roomvsfragment.adapter;

import static ru.samsung.itschool.mdev.roomvsfragment.fragment.TaskDialogFragment.TAG_DIALOG_TASK_SAVE;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

import ru.samsung.itschool.mdev.roomvsfragment.R;
import ru.samsung.itschool.mdev.roomvsfragment.db.Task;
import ru.samsung.itschool.mdev.roomvsfragment.fragment.TaskDialogFragment;
import ru.samsung.itschool.mdev.roomvsfragment.utils.TaskCallback;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TasksViewHolder> {

    public interface onClickItemListener {
        void onClick(String taskText, String descText, String finishbyText, Task task);
    }

    private final Context context;
    private List<Task> taskList;
    private onClickItemListener onClickListener;

    public TaskAdapter(Context mCtx, List<Task> taskList) {
        this.context = mCtx;
        this.taskList = taskList;
    }

    public void setOnClickListener(onClickItemListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Task taskItem = taskList.get(position);
        holder.textViewTask.setText(taskItem.getTask());
        holder.textViewDesc.setText(taskItem.getDesc());
        holder.textViewFinishBy.setText(taskItem.getFinishBy());

        holder.itemView.setOnClickListener(view -> onClickListener.onClick(
                holder.textViewTask.getText().toString(),
                holder.textViewDesc.getText().toString(),
                holder.textViewFinishBy.getText().toString(),
                taskItem)
        );
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateList(List<Task> taskList) {
        DiffUtil.Callback diffCallback = new TaskCallback(this.taskList, taskList);
        DiffUtil.DiffResult diffTasks = DiffUtil.calculateDiff(diffCallback);
        this.taskList = taskList;
        diffTasks.dispatchUpdatesTo(this);
    }

    static class TasksViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTask, textViewDesc, textViewFinishBy;

        public TasksViewHolder(View itemView) {
            super(itemView);

            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy);
        }
    }
}