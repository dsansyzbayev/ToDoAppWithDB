package com.example.tododb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ChangeTaskActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDesc, editTextFinishBy;
    private Switch buttonSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_task);


        editTextTask = findViewById(R.id.editTaskTitle);
        editTextDesc = findViewById(R.id.editTaskDescription);
        editTextFinishBy = findViewById(R.id.editDate);

        buttonSwitch = findViewById(R.id.switchDone);


        final Task task = (Task) getIntent().getSerializableExtra("task");

        loadTask(task);

        findViewById(R.id.buttonChangeTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask(task);
            }
        });

        findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeTaskActivity.this);
                builder.setTitle("Delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTask(task);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadTask(Task task) {
        editTextTask.setText(task.getTitle());
        editTextDesc.setText(task.getDescription());
        editTextFinishBy.setText(task.getDate());
        buttonSwitch.setChecked(task.isDone());
    }

    private void updateTask(final Task task) {
        final String sTask = editTextTask.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();
        final String sFinishBy = editTextFinishBy.getText().toString().trim();

        if (sTask.isEmpty()) {
            editTextTask.setError("Task required");
            editTextTask.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            editTextDesc.setError("Desc required");
            editTextDesc.requestFocus();
            return;
        }

        if (sFinishBy.isEmpty()) {
            editTextFinishBy.setError("Finish by required");
            editTextFinishBy.requestFocus();
            return;
        }

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                task.setTitle(sTask);
                task.setDescription(sDesc);
                task.setDate(sFinishBy);
                task.setDone(buttonSwitch.isChecked());
                DataBase.getInstance(getApplicationContext()).getAppDB()
                        .DBDaoTask()
                        .update(task);
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                if (getIntent().getBooleanExtra("completed",false)){
                    SecondActivity.getInstance().finish();
                    startActivity(new Intent(ChangeTaskActivity.this, SecondActivity.class));
                }else {
                    MainActivity.getInstance().finish();
                    startActivity(new Intent(ChangeTaskActivity.this, MainActivity.class));
                }
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }


    private void deleteTask(final Task task) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DataBase.getInstance(getApplicationContext()).getAppDB()
                        .DBDaoTask()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                if (getIntent().getBooleanExtra("completed",false)){
                    SecondActivity.getInstance().finish();
                    startActivity(new Intent(ChangeTaskActivity.this, SecondActivity.class));
                }else {
                    MainActivity.getInstance().finish();
                    startActivity(new Intent(ChangeTaskActivity.this, MainActivity.class));
                }
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}
