package ru.samsung.itschool.mdev.roomvsfragment.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.samsung.itschool.mdev.roomvsfragment.R;
import ru.samsung.itschool.mdev.roomvsfragment.db.DBClient;
import ru.samsung.itschool.mdev.roomvsfragment.db.Task;

public class TaskDialogFragment extends DialogFragment {
     private Context context;
     public static final String TAG_DIALOG_TASK_SAVE = "task_save";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.task_layout, null);
        final EditText task = view.findViewById(R.id.task);
        final EditText desc = view.findViewById(R.id.desc);
        final EditText finishBy = view.findViewById(R.id.finishBy);
        alertDialogBuilder.setView(view)
                .setTitle(getString(R.string.addtasktitle))
                .setPositiveButton(R.string.save, (dialog, which) -> saveTask(task.getText().toString(),desc.getText().toString(),finishBy.getText().toString()))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());

        return alertDialogBuilder.create();
    }

    private void saveTask(String task, String desc, String finishBy) {
        if (TextUtils.isEmpty(task) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(finishBy)) {
            return;
        } else {
            new Thread(() -> {
                Task t = new Task(task, desc, finishBy);
                DBClient.getInstance(context).getTaskDatabase().taskDao().insert(t);
            }).start();
        }
    }
}
