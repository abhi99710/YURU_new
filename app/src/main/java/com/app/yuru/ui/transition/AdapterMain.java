package com.app.yuru.ui.transition;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yuru.R;
import com.app.yuru.corescheduler.player.video.ui.VideoActivity;
import com.app.yuru.corescheduler.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterMain extends BaseAdapter {

    final Context context;
    final List<String> id;
    final List<String> fileName;
    final List<String> traits;
    final List<String> duration;
    final List<String> thumb;
    final List<String> fileURL;
    ClickInterface clickInterface;
    final String choosedProgram;

    public AdapterMain(Context context, List<String> id, List<String> fileName, List<String> traits, List<String> duration, List<String> thumb,
                       List<String> fileURL, ClickInterface clickInterface, String choosedProgram) {
        this.context = context;
        this.id = id;
        this.fileName = fileName;
        this.traits = traits;
        this.duration = duration;
        this.thumb = thumb;
        this.fileURL = fileURL;
        this.clickInterface = clickInterface;
        this.choosedProgram = choosedProgram;
    }

    @Override
    public int getCount() {
        return id.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridsubjects, parent, false);
        }


        ConstraintLayout cardsub = convertView.findViewById(R.id.cardsub);
        ImageView videoView = convertView.findViewById(R.id.gridIMageView);


        ImageView forground_check = convertView.findViewById(R.id.forground_check);

         SharedPreferences sharedPreferences =
                context.getSharedPreferences("share", Context.MODE_PRIVATE);
//                    Global.ids1 = jsonObject1.getString("id")
        String x =  sharedPreferences.getString("wakeup_choosedProgram", "");
        if( fileName.get(position).equalsIgnoreCase(x)){
            forground_check.setVisibility(View.VISIBLE);

        }
//        else {
//            forground_check.setVisibility(View.INVISIBLE);
//        }
//        cardsub.setOnClickListener(v -> {
//
//            clickInterface.urlGet(fileURL.get(position), thumb.get(position));
//
//                }
//
//        );

        try {
            Picasso.get().load(thumb.get(position)).fit().noFade().centerCrop().into(videoView);
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }

}
