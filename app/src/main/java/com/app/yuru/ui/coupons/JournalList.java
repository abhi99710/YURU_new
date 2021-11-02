package com.app.yuru.ui.coupons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.yuru.R;

public class JournalList extends AppCompatActivity {

    private ImageView ivSearch, delete, print;
    private EditText searchEdit;
    private ConstraintLayout cl_add_list;
    private RecyclerView recyclerViewJournals;
    private TextView totalEntries, date_journl, name_jornal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);


        searchEdit = findViewById(R.id.searchEdit);
        ivSearch = findViewById(R.id.ivSearch);
        cl_add_list = findViewById(R.id.cl_add_list);
        recyclerViewJournals = findViewById(R.id.recyclerViewJournals);

        cl_add_list.setOnClickListener(v->{
            startActivity(new Intent(this, JournalAdd.class));
        });


        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    searchEdit.clearFocus();
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);

//                    progressDialog1.show();
//                    progressDialog1.setMessage("loading...");
//                    if (!searchEdit.getText().toString().isEmpty()) {
//                        String query = searchEdit.getText().toString();
//                        apiSearch(query);
//                    } else {
//                        progressDialog1.dismiss();
//                    }

                    ivSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            progressDialog1.show();
//                            progressDialog1.setMessage("loading...");
//                            if (!searchEdit.getText().toString().isEmpty()) {
//                                String query = searchEdit.getText().toString();
//                                apiSearch(query);
//                            } else {
//                                progressDialog1.dismiss();
//                            }

                        }
                    });

                    return true;
                }
                return false;
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                progressDialog1.show();
//                progressDialog1.setMessage("loading...");
//                if (!searchEdit.getText().toString().isEmpty()) {
//                    String query = searchEdit.getText().toString();
//                    apiSearch(query);
//                } else {
//                    progressDialog1.dismiss();
//                }

            }
        });
    }
}