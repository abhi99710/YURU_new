package com.app.yuru.ui.coupons;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yuru.R;
import com.app.yuru.utility.apivolley.APIVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JournalList extends AppCompatActivity implements ListInterface {

    private ImageView ivSearch, delete, print;
    private EditText searchEdit;
    private ConstraintLayout cl_add_list;
    private RecyclerView recyclerViewJournals;
    private TextView totalEntries, date_journl, name_jornal;
    ProgressDialog progressDialog;
    private ImageView checkAll;

    private List<String> ids = new ArrayList<>();
    private List<String> user_id = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private List<String> date_time = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> created_at = new ArrayList<>();
    private List<String> updated_at = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        apiShowList();

        searchEdit = findViewById(R.id.searchEdit);
        ivSearch = findViewById(R.id.ivSearch);
        cl_add_list = findViewById(R.id.cl_add_list);
        recyclerViewJournals = findViewById(R.id.recyclerViewJournals);
        checkAll = findViewById(R.id.checkAll);
        totalEntries = findViewById(R.id.totalEntries);
        date_journl = findViewById(R.id.date_journl);

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

    private void apiShowList() {
        String url = "https://promask.com.co/yuru/api/activity";

        @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                progressDialog.dismiss();
                if (jsonObject.getBoolean("valid")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    JSONArray jsonArray = jsonObject1.getJSONArray("data");

                    ids.clear();
                    date_time.clear();
                    created_at.clear();
                    title.clear();
                    description.clear();
                    updated_at.clear();
                    user_id.clear();
                    for (int i = 0; i < jsonArray.length() ; i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        ids.add(jsonObject2.getString("id"));
                        date_time.add(jsonObject2.getString("date_time"));
                        created_at.add(jsonObject2.getString("created_at"));
                        title.add(jsonObject2.getString("title"));
                        description.add(jsonObject2.getString("description"));
                        updated_at.add(jsonObject2.getString("updated_at"));
                        user_id.add(jsonObject2.getString("user_id"));


                    }
                    totalEntries.setText(ids.size()+" total entries");
                    date_journl.setText("Created on " + date_time.get(date_time.size() -1));


                    AdapterJornals adapterJornals = new AdapterJornals(this, ids, user_id, title, date_time, description, created_at, updated_at,
                            this);
                    recyclerViewJournals.setHasFixedSize(true);
                    recyclerViewJournals.setLayoutManager(new LinearLayoutManager(this));
                    recyclerViewJournals.setAdapter(adapterJornals);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
        })/*{
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user_id","1");
                map.put("title",jour_title.getText().toString());



                map.put("date_time",""+currentTime);
                map.put("description",jour_content.getText().toString());


                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type","application/x-www-form-urlencoded");
                map.put("Content-Length","<calculated when request is sent>");

                return map;
            }
            }*/;



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void deleteEntry(){

        String url = "https://promask.com.co/yuru/api/activity/delete?id="+"";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("code").equalsIgnoreCase("200")){
                    Toast.makeText(this, "Deleted successfully.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    public void selected(JSONObject jsonObject) {

        new Handler().postDelayed(() -> {


            JSONObject jsonObject1 = jsonObject;

            deleteEntry();

        }, 2);


    }
}