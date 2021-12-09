package com.app.yuru.ui.transition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.yuru.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterEvening extends BaseAdapter {

    final Context context;
    final List<String> id;
    final List<String> category_name;
    final  List<String> language_name;
    final List<String> gender;
    final  List<String> traint;
    final List<String> duration;
    final  List<String> url;
    final List<String> thumbnail;

    public AdapterEvening(Context context, List<String> id, List<String> category_name, List<String> language_name,
                          List<String> gender, List<String> traint, List<String> duration, List<String> url, List<String> thumbnail) {
        this.context = context;
        this.id = id;
        this.category_name = category_name;
        this.language_name = language_name;
        this.gender = gender;
        this.traint = traint;
        this.duration = duration;
        this.url = url;
        this.thumbnail = thumbnail;
    }

    @Override
    public int getCount() {
        return  id.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.gridevening_layout,parent,false);
        }


        ConstraintLayout cardsub = convertView.findViewById(R.id.clEve);
        ImageView videoView = convertView.findViewById(R.id.grid_evening_iv);



//        Picasso.get().load(thumbnail.get(position)).into(videoView);
        Picasso.get().load("https://i.pinimg.com/originals/e9/37/ec/e937ece4a014308c3e3685ff2dc4f751.jpg")
                .centerCrop().noFade().fit().into(videoView);

        return convertView;
    }
}
