package ru.samsung.itschool.mdev.roomvsfragment.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ru.samsung.itschool.mdev.roomvsfragment.R;
import ru.samsung.itschool.mdev.roomvsfragment.adapter.TaskAdapter;
import ru.samsung.itschool.mdev.roomvsfragment.db.DBClient;
import ru.samsung.itschool.mdev.roomvsfragment.db.Task;

public class EditDeleteFragment extends DialogFragment {
    public static final String TAG_DIALOG_TASK_EDIT = "task_edit";
    public static final String KEY_STRING_ARRAYLIST = "view_text";
    private Context context;
    private Task t;

    public EditDeleteFragment(Task t) {
        this.t = t;
    }

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
        taskText.setText(getArguments().getStringArrayList(KEY_STRING_ARRAYLIST).get(0));
        descText.setText(getArguments().getStringArrayList(KEY_STRING_ARRAYLIST).get(1));
        finishByText.setText(getArguments().getStringArrayList(KEY_STRING_ARRAYLIST).get(2));
        builder.setView(view)
                .setTitle("Изменить запись")
                .setPositiveButton("Изменить", (dialogInterface, i) -> {
                    t.setTask(taskText.getText().toString());
                    t.setDesc(descText.getText().toString());
                    t.setFinishBy(descText.getText().toString());
                    editTask(t);
                })
                .setNegativeButton("Удалить", (dialogInterface, i) -> {
                    deleteTask(t);
                });
        return builder.create();
    }

    private void deleteTask(@NonNull Task task) {
        new Thread(() -> {
            DBClient.getInstance(context).getTaskDatabase().taskDao().delete(task);
        }).start();
    }

    private void editTask(@NonNull Task task) {
        new Thread(() -> {
            DBClient.getInstance(context).getTaskDatabase().taskDao().update(task);
        }).start();
    }
}
