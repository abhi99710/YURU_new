package com.app.yuru.ui.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.yuru.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class LibraryInfo extends AppCompatActivity implements Serializable {

    private TextView videoname, duration, Description;
    private ImageView imageViewVideo, playBtn, circleImageView;
    List<ModelLibrary> list;
    private ImageView backlibraryInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_info);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoname = findViewById(R.id.videoname);
        duration = findViewById(R.id.duration);
        Description = findViewById(R.id.Description);
        imageViewVideo = findViewById(R.id.imageViewVideo);
        playBtn = findViewById(R.id.playBtn);
        circleImageView = findViewById(R.id.circleImageView);
        backlibraryInfo = findViewById(R.id.backlibraryInfo);

        backlibraryInfo.setOnClickListener(v -> {
            super.onBackPressed();
        });

        list = (List<ModelLibrary>) getIntent().getSerializableExtra("pos");
        int position = getIntent().getIntExtra("newpos", 1);

        Picasso.get().load(list.get(position).getThumb()).fit().noFade().centerCrop().into(imageViewVideo);
        Picasso.get().load(list.get(position).getAuthorImg()).fit().noFade().centerCrop().into(circleImageView);

        videoname.setText(list.get(position).getNameVideo());
        duration.setText("05:30");
        Description.setText(list.get(position).getDescription());

        playBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LibraryPlayVideos.class);
            intent.putExtra("link", list.get(position).getVideoLink());
            startActivity(intent);
        });


    }
}