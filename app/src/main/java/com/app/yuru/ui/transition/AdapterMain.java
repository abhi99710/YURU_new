package com.app.yuru.ui.transition;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.yuru.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMain extends BaseAdapter {

    final Context context;
    final List<String> id;
    // --Commented out by Inspection (7/23/2021 11:13 AM):final List<String> name;
    final List<String> category_name;
    final  List<String> language_name;
    final List<String> gender;
    final  List<String> traint;
    final List<String> duration;
    final  List<String> url;


    public AdapterMain(Context context, List<String> id,
                       List<String> category_name, List<String> language_name, List<String> gender,
                       List<String> traint, List<String> duration, List<String> url) {
        this.context = context;
        this.id = id;
        this.category_name = category_name;
        this.language_name = language_name;
        this.gender = gender;
        this.traint = traint;
        this.duration = duration;
        this.url = url;
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
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.gridsubjects,parent,false);
        }


        ConstraintLayout cardsub = convertView.findViewById(R.id.cardsub);
        ImageView videoView = convertView.findViewById(R.id.gridIMageView);

        Picasso.get().load(url.get(position)).into(videoView);


        return convertView;
    }

}
