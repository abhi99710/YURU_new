package com.app.yuru.ui.discounts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yuru.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Survey extends AppCompatActivity {

    private List<String> question = new ArrayList<>();
    private List<String> ques_id = new ArrayList<>();
    private Button btn_survey_submit;
    private TextView ques1, ques2, ques3, ques4, ques5;
    private ProgressDialog progressDialog;
    private RadioGroup radioGroup, radioGroup2, radioGroup3, radioGroup4, radioGroup5;
    private RadioButton rb1Q1, rb2Q1, rb3Q1, rb4Q1, rb5Q1;
    private RadioButton rb1Q2, rb2Q2, rb3Q2, rb4Q2, rb5Q2;
    private RadioButton rb1Q3, rb2Q3, rb3Q3, rb4Q3, rb5Q3;
    private RadioButton rb1Q4, rb2Q4, rb3Q4, rb4Q4, rb5Q4;
    private RadioButton rb1Q5, rb2Q5, rb3Q5, rb4Q5, rb5Q5;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        findIDs();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();



        btn_survey_submit = findViewById(R.id.btn_survey_submit);
        btn_survey_submit.setOnClickListener(v->{

            int selectedId1 = radioGroup.getCheckedRadioButtonId();
            radioButton1 = findViewById(selectedId1);

            int selectedId2 = radioGroup2.getCheckedRadioButtonId();
            radioButton2 = findViewById(selectedId2);

            int selectedId3 = radioGroup3.getCheckedRadioButtonId();
            radioButton3 = findViewById(selectedId3);

            apiSaveQuestions(/*""+radioButton1.getId(), ""+radioButton2.getId(), ""+radioButton3.getId()*/);
        });

        apiGetQuestions();


    }



    private void apiGetQuestions() {

        String url = "https://promask.com.co/yuru/api/web/showSurveyquestions";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean status = jsonObject.getBoolean("valid");
                    progressDialog.dismiss();
                if (status){
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        ques_id.add(jsonObject1.getString("id"));
                        question.add(jsonObject1.getString("questions"));

                    }

                    ques1.setText(question.get(0));
                    ques2.setText(question.get(1));
                    ques3.setText(question.get(2));

                }else {
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void apiSaveQuestions(/*String selectedId1, String selectedId2, String selectedId3*/) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user = sharedPreferences.getString("id", "1");

        String url = "https://promask.com.co/yuru/api/web/addRating";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.getBoolean("valid")){
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");

                    Intent intent = new Intent(this, RocketTake.class);
                    intent.putExtra("videoURL", jsonObject1.getString("videoURL"));
                    startActivity(intent);
                    
                }else {
                    Toast.makeText(this, "No response from server", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show();
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", "12");
                map.put("q1ID", ques_id.get(0));
                map.put("q2ID", ques_id.get(1));
                map.put("q3ID", ques_id.get(2));
                map.put("q4ID", ques_id.get(3));
                map.put("q5ID", ques_id.get(4));
                map.put("q1RT", "5");
                map.put("q2RT", "4");
                map.put("q3RT", "5");
                map.put("q4RT","3");
                map.put("q5RT","5");

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void findIDs() {
        ques1 = findViewById(R.id.ques1);
        ques2 = findViewById(R.id.ques2);
        ques3 = findViewById(R.id.ques3);
//        ques4 = findViewById(R.id.ques5);
//        ques5 = findViewById(R.id.ques5);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
//        radioGroup4 = findViewById(R.id.radioGroup4);
//        radioGroup5 = findViewById(R.id.radioGroup5);

        rb1Q1 = findViewById(R.id.rb1Q1);
        rb2Q1 = findViewById(R.id.rb2Q1);
        rb3Q1 = findViewById(R.id.rb3Q1);
        rb4Q1 = findViewById(R.id.rb4Q1);
        rb5Q1 = findViewById(R.id.rb5Q1);

        rb1Q2 = findViewById(R.id.rb1Q2);
        rb2Q2 = findViewById(R.id.rb2Q2);
        rb3Q2 = findViewById(R.id.rb3Q2);
        rb4Q2 = findViewById(R.id.rb4Q2);
        rb5Q2 = findViewById(R.id.rb5Q2);

        rb1Q3 = findViewById(R.id.rb1Q3);
        rb2Q3 = findViewById(R.id.rb2Q3);
        rb3Q3 = findViewById(R.id.rb3Q3);
        rb4Q3 = findViewById(R.id.rb4Q3);
        rb5Q3 = findViewById(R.id.rb5Q3);

//        rb1Q1 = findViewById(R.id.rb1Q1);
//        rb2Q1 = findViewById(R.id.rb2Q1);
//        rb3Q1 = findViewById(R.id.rb3Q1);
//        rb4Q1 = findViewById(R.id.rb4Q1);
//        rb5Q1 = findViewById(R.id.rb5Q1);
//
//        rb1Q1 = findViewById(R.id.rb1Q1);
//        rb2Q1 = findViewById(R.id.rb2Q1);
//        rb3Q1 = findViewById(R.id.rb3Q1);
//        rb4Q1 = findViewById(R.id.rb4Q1);
//        rb5Q1 = findViewById(R.id.rb5Q1);

    }
}