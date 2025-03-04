package ru.samsung.itschool.mdev.roomvsfragment;

import static ru.samsung.itschool.mdev.roomvsfragment.fragment.TaskDialogFragment.TAG_DIALOG_TASK_SAVE;
import static ru.samsung.itschool.mdev.roomvsfragment.fragment.EditDeleteFragment.TAG_DIALOG_TASK_EDIT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.samsung.itschool.mdev.roomvsfragment.adapter.TaskAdapter;
import ru.samsung.itschool.mdev.roomvsfragment.db.DBClient;
import ru.samsung.itschool.mdev.roomvsfragment.fragment.EditDeleteFragment;
import ru.samsung.itschool.mdev.roomvsfragment.fragment.TaskDialogFragment;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showDialog());

        adapter = new TaskAdapter(getApplicationContext(), new ArrayList<>()); // создаем адаптер с пустым списком
        adapter.setOnClickListener((taskText, descText, finishbyText, task) -> { // создаем функцию для клика на Item
            ArrayList<String> arrayList = new ArrayList<>(); // передаем строки в фрагмент
            arrayList.add(taskText);
            arrayList.add(descText);
            arrayList.add(finishbyText);

            Bundle arguments = new Bundle();
            arguments.putStringArrayList(EditDeleteFragment.KEY_STRING_ARRAYLIST, arrayList);

            DialogFragment dialogFragment = new EditDeleteFragment(task); // передаем task из списка
            dialogFragment.setArguments(arguments);                       // через конструктор фрагмента
            dialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_TASK_EDIT); // остальное - через Bundle
        });
        Log.d("RRRR","onCreate()");
/*
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Task> tasks = DBClient.getInstance(getApplicationContext()).getTaskDatabase().taskDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new TaskAdapter(getApplicationContext(),tasks));
                    }
                });
            }
        }).start();


*/
        // Подписка на изменение базы данных
        Disposable rrr = DBClient.getInstance(this).getTaskDatabase()
                .taskDao()
                .getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                    Log.d("RRR", "List has been changed!");
                    adapter.updateList(tasks);
                    if (recyclerView.getAdapter() == null) {
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    private void showDialog() {
        DialogFragment dialogFragment = new TaskDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_TASK_SAVE);
    }
}