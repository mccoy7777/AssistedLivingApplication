package com.example.assistedlivingapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TasksAdapter extends FirestoreRecyclerAdapter <Tasks, TasksAdapter.NoteHolder> {

    public TasksAdapter(@NonNull FirestoreRecyclerOptions<Tasks> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Tasks model) {

        holder.taskName.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.time.setText(String.valueOf(model.getTime()));
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new NoteHolder(v);
    }

    public void deleteTask (int position){

        getSnapshots().getSnapshot(position).getReference().delete();

    }//end of deleteTask method

    class NoteHolder extends RecyclerView.ViewHolder{

        //Declare variables
        TextView taskName, description, time;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variables
            taskName = itemView.findViewById(R.id.tv_TaskName);
            description = itemView.findViewById(R.id.tv_description);
            time = itemView.findViewById(R.id.tv_time);
        }
    }
}
