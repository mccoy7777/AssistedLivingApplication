package com.example.assistedlivingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class WednesdayTasks extends AppCompatActivity {

    //Create Variables
    TextView mondayTasks;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    CollectionReference allTasksRef = fStore.collection("Tasks");

    TasksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wednesday_tasks);

        FloatingActionButton btnAddTask = findViewById(R.id.btn_AddTask);

        //Setup onClickListener
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WednesdayTasks.this, CreateNewDailyTask.class));
            }
        });

        setUpRecyclerview();

    }//end of onCreate Method

    private void setUpRecyclerview() {

        Query query = allTasksRef.orderBy("time", Query.Direction.DESCENDING)
                .whereArrayContains("tags", "Wednesday");

        FirestoreRecyclerOptions<Tasks> options = new FirestoreRecyclerOptions.Builder<Tasks>()
                .setQuery(query, Tasks.class)
                .build();

        adapter = new TasksAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteTask(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}//end of WednesdayTasks class