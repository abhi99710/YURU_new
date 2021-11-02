package com.app.yuru.ui.coupons;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JournalAdd extends AppCompatActivity {

    private TextView jour_title;
    private ImageView jour_save;
    private TextView jour_date;
    private EditText jour_content;
    private ImageView jour_font;
    Date currentTime;
    private ImageView jour_idea;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_add);

        findIds();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);


        currentTime = Calendar.getInstance().getTime();
        jour_date.setText(""+currentTime);

        jour_save.setOnClickListener(v->{
            progressDialog.show();
            apiAddJournal();
        });



    }

    private void apiAddJournal() {


        String url = APIVolley.Companion.getAddJournal();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                progressDialog.dismiss();
                if(jsonObject.getBoolean("valid")){
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("data");

                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, JournalOptions.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
        }){
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

        /*    @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type","application/x-www-form-urlencoded");
                map.put("Content-Length","<calculated when request is sent>");

                return map;
            }*/
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void findIds() {
        jour_idea = findViewById(R.id.jour_idea);
        jour_font = findViewById(R.id.jour_font);
        jour_content = findViewById(R.id.jour_content);
        jour_date = findViewById(R.id.jour_date);
        jour_save = findViewById(R.id.jour_save);
        jour_title = findViewById(R.id.jour_title);

    }
}