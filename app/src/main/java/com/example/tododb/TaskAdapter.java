package com.example.tododb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;
import androidx.room.Update;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private List<Task> tasks;
    private boolean done;

    public TaskAdapter(Context context, List<Task> tasks){
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_row, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewTitle.setText(task.getTitle());
        holder.textViewDescription.setText(task.getDescription());
        holder.textViewDate.setText(task.getDate());

        if(task.isDone()){
            done = true;
        } else {
            done = false;
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewTitle, textViewDescription, textViewDate;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDate = itemView.findViewById(R.id.textViewDate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Task task = tasks.get(getAdapterPosition());
            Intent intent = new Intent(context, ChangeTaskActivity.class );
            intent.putExtra("task", task);
            intent.putExtra("done", done);
            context.startActivity(intent);
        }
    }
}
