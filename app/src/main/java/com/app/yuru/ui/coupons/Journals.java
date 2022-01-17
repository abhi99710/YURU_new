package com.app.yuru.ui.coupons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.app.yuru.R;

public class Journals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journals);

        Button btn_journal_continue = findViewById(R.id.btn_journal_continue);
        btn_journal_continue.setOnClickListener(v->{
            Intent intent = new Intent(this, JournalOptions.class);
            startActivity(intent);
        });


    }
}