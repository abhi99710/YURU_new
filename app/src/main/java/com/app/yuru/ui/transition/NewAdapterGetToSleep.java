package com.app.yuru.ui.transition;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yuru.R;

import java.util.List;

public class NewAdapterGetToSleep extends RecyclerView.Adapter<NewAdapterGetToSleep.Myholder> {
    Context context;
    List<String> programId;
    List<String> programName;
    String gender;
    String duration;
    ProgramClickPosition programClickPosition;

    public NewAdapterGetToSleep(Context context, List<String> programId, List<String> programName, String gender, String duration, ProgramClickPosition programClickPosition) {
        this.context = context;
        this.programId = programId;
        this.programName = programName;
        this.gender = gender;
        this.duration = duration;
        this.programClickPosition = programClickPosition;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.gridsubjects, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        holder.cl_grid_adapter.setOnClickListener(v->{
//            Intent intent = new Intent(context, GettoSleepVideos.class);
//            intent.putExtra("name", programId.get(position));
//            intent.putExtra("gender", gender);
//            intent.putExtra("duration", duration);
//            context.startActivity(intent);

            programClickPosition.clickIdForprogram(programId.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class Myholder extends RecyclerView.ViewHolder {
        ConstraintLayout cl_grid_adapter;
        public Myholder(@NonNull View itemView) {
            super(itemView);

            cl_grid_adapter = itemView.findViewById(R.id.cl_grid_adapter);
        }
    }
}
