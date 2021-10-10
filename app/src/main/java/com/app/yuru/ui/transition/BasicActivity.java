package com.app.yuru.ui.transition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.yuru.R;

public class BasicActivity extends AppCompatActivity {
    RecyclerView recyViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        recyViewAll = findViewById(R.id.recyViewAll);

        TtsAdapter ttsAdapter = new TtsAdapter(this);
        recyViewAll.setHasFixedSize(true);
        recyViewAll.setLayoutManager(new LinearLayoutManager(this));
        recyViewAll.setAdapter(ttsAdapter);


    }
}