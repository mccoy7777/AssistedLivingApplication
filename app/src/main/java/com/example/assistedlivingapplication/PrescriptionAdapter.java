package com.example.assistedlivingapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PrescriptionAdapter extends FirestoreRecyclerAdapter <Prescription, PrescriptionAdapter.NoteHolder> {

    public PrescriptionAdapter (@NonNull FirestoreRecyclerOptions<Prescription> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Prescription model) {

        holder.prescriptionName.setText(model.getPrescriptionName());
        holder.dosage.setText(String.valueOf(model.getDosage()));
    }



    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_item, parent, false);
        return new NoteHolder(v);
    }

    public void deleteTask (int position){

        getSnapshots().getSnapshot(position).getReference().delete();

    }//end of deleteTask method

    class NoteHolder extends RecyclerView.ViewHolder{

        //Declare variables
        TextView prescriptionName, dosage;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variables
            prescriptionName = itemView.findViewById(R.id.tv_PrescriptionName);
            dosage = itemView.findViewById(R.id.tv_dosage);
        }
    }
}