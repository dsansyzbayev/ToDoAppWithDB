package com.example.tododb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateTaskActivity extends AppCompatActivity {

    private EditText editTaskTitle, editTaskDescription, editTaskDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        editTaskTitle = findViewById(R.id.editTaskTitle);
        editTaskDescription = findViewById(R.id.editTaskDescription);
        editTaskDate = findViewById(R.id.editDate);

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewTask();
            }
        });
    }

    private void createNewTask(){
        final String taskTitle = editTaskTitle.getText().toString();
        final String taskDescription = editTaskDescription.getText().toString();
        final String taskDate = editTaskDate.getText().toString();

        if(taskTitle.isEmpty()){
            editTaskTitle.setError("Enter title of a task");
            editTaskTitle.requestFocus();
            return;
        }

        if(taskDescription.isEmpty()){
            editTaskDescription.setError("Missing description");
            editTaskDescription.requestFocus();
            return;
        }

        if(taskDate.isEmpty()){
            editTaskDate.setError("Please Enter Date");
            editTaskDate.requestFocus();
            return;
        }

        class NewTask extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                Task task = new Task();
                task.setTitle(taskTitle);
                task.setDescription(taskDescription);
                task.setDate(taskDate);
                task.setDone(false);

                DataBase.getInstance(getApplicationContext()).getAppDB().DBDaoTask().insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                MainActivity.getInstance().finish();
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Task is saved",Toast.LENGTH_LONG).show();
            }
        }
        NewTask newTask = new NewTask();
        newTask.execute();
    }

}
