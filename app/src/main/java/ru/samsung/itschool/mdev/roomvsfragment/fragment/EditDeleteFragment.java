package ru.samsung.itschool.mdev.roomvsfragment.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ru.samsung.itschool.mdev.roomvsfragment.R;
import ru.samsung.itschool.mdev.roomvsfragment.db.DBClient;
import ru.samsung.itschool.mdev.roomvsfragment.db.Task;

public class EditDeleteFragment extends DialogFragment {
    public static final String TAG_DIALOG_TASK_EDIT = "task_edit";
    public static final String TAG_DIALOG_EDIT_TEXT_ARRAY = "view_text";
    public static final String TAG_DIALOG_ITEM_ID = "get_id";
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.task_layout, null);
        final EditText taskText = view.findViewById(R.id.task);
        final EditText descText = view.findViewById(R.id.desc);
        final EditText finishByText = view.findViewById(R.id.finishBy);
        assert getArguments() != null;
        taskText.setText(getArguments().getStringArrayList(TAG_DIALOG_EDIT_TEXT_ARRAY).get(0));
        descText.setText(getArguments().getStringArrayList(TAG_DIALOG_EDIT_TEXT_ARRAY).get(1));
        finishByText.setText(getArguments().getStringArrayList(TAG_DIALOG_EDIT_TEXT_ARRAY).get(2));

        Task t;
        try {
            t = getTaskById(getArguments().getInt(TAG_DIALOG_ITEM_ID));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        builder.setView(view)
                .setTitle("Изменить запись")
                .setPositiveButton("Изменить", (dialogInterface, i) -> {
                    t.setTask(taskText.getText().toString());
                    t.setDesc(descText.getText().toString());
                    t.setFinishBy(descText.getText().toString());
                    editTaskById(getArguments().getInt(TAG_DIALOG_ITEM_ID));
                })
                .setNegativeButton("Удалить", (dialogInterface, i) -> {
                    deleteTaskById(getArguments().getInt(TAG_DIALOG_ITEM_ID));
                });
        return builder.create();
    }

    private void deleteTaskById(int id) {
        new Thread(() -> {
            Task task = DBClient.getInstance(context).getTaskDatabase().taskDao().findById(id);
            DBClient.getInstance(context).getTaskDatabase().taskDao().delete(task);
        }).start();
    }

    private void editTaskById(int id) {
        new Thread(() -> {
            Task task = DBClient.getInstance(context).getTaskDatabase().taskDao().findById(id);
            DBClient.getInstance(context).getTaskDatabase().taskDao().update(task);
        }).start();
    }

    private Task getTaskById(int id) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Task> futureTask = executorService.submit(() -> {
            return DBClient.getInstance(context).getTaskDatabase().taskDao().findById(id); // получает объект из Бд по id
        });
        Task task = futureTask.get();
        executorService.shutdown();
        return task;
    }

}
