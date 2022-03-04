package com.app.yuru.ui.coupons;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.app.yuru.ui.discounts.MainRocket;
import com.app.yuru.ui.discounts.Survey;
import com.app.yuru.utility.apivolley.APIVolley;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JournalAdd extends AppCompatActivity {

    private TextView jour_title;
    private ImageView jour_save;
    private TextView jour_date;
    private EditText jour_content;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_add);

        findIds();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);


        Date currentTime = Calendar.getInstance().getTime();
        jour_date.setText(""+ currentTime);

        jour_save.setOnClickListener(v->{
            progressDialog.show();
            apiAddJournal();
        });



    }

    private void apiAddJournal() {

        SharedPreferences sh = getSharedPreferences("share", MODE_PRIVATE);

        String ids1 = sh.getString("id", "1");

        String url = APIVolley.Companion.getAddJournal();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                progressDialog.dismiss();
                if(jsonObject.getBoolean("valid")){
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("data");

                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, MainRocket.class);
                    intent.putExtra("first_rocket", "journal");
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show()){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("user_id",ids1);
                map.put("title",jour_title.getText().toString());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now1 = LocalDateTime.now();
                String s1 = now1.toString();
                String s2 = s1.replace("T", " ");
                String[] s = s2.split("\\.",2);

                map.put("date_time",""+s[0]);
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
        ImageView jour_idea = findViewById(R.id.jour_idea);
        ImageView jour_font = findViewById(R.id.jour_font);
        jour_content = findViewById(R.id.jour_content);
        jour_date = findViewById(R.id.jour_date);
        jour_save = findViewById(R.id.jour_save);
        jour_title = findViewById(R.id.jour_title);

    }
}