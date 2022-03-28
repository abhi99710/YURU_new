package com.app.yuru.ui.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yuru.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdapterLibrary extends RecyclerView.Adapter<AdapterLibrary.Myholder> implements Serializable {

    private Context context;
    private List<ModelLibrary> list;

    public AdapterLibrary(Context context, List<ModelLibrary> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterLibrary.Myholder(LayoutInflater.from(context).inflate(R.layout.libraries, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

//        Picasso.get().load(list.get(position).getAuthorImg()).fit().noFade().centerCrop()
//                .into(holder.imageLibrary);
        Picasso.get().load(list.get(position).getThumb()).fit().noFade().centerCrop()
                .into(holder.imageLibrary);

        holder.imageLibrary.setOnClickListener(v->{
            Intent intent = new Intent(context, LibraryInfo.class);
//            Bundle bundle = new Bundle();
//            bundle.putParcelable("data", list);
//            intent.putExtras(bundle);
            intent.putExtra("pos",  (Serializable)list);
            intent.putExtra("newpos", position);
            context.startActivity(intent);
        });

//        holder.duration.setText(list.get(position).getDuration());
//
//        holder.videoname.setText(list.get(position).getNameVideo());
//
//        holder.Description.setText(list.get(position).getDescription());
//
//        holder.playBtn.setOnClickListener(v->{
//            Intent intent = new Intent(context, LibraryPlayVideos.class);
//            intent.putExtra("link", list.get(position).getVideoLink());
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        ImageView imageLibrary;
        public Myholder(@NonNull View itemView) {
            super(itemView);

            imageLibrary = itemView.findViewById(R.id.imageLibrary);

        }
    }
}
