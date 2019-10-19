package com.example.tododb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static SecondActivity secondActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        secondActivity = this;
        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getTask();
    }

    public static SecondActivity getInstance(){
        return secondActivity;
    }

    private void getTask() {
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {

            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> taskList = DataBase
                        .getInstance(getApplicationContext())
                        .getAppDB()
                        .DBDaoTask()
                        .getDone(true);
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                TaskAdapter adapter = new TaskAdapter(SecondActivity.this, tasks);
                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
