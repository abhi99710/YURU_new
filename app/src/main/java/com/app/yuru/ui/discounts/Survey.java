package com.app.yuru.ui.discounts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private final List<String> question = new ArrayList<>();
    private final List<String> ques_id = new ArrayList<>();
    private TextView ques1, ques2, ques3, ques4, ques5;
    private ProgressDialog progressDialog;
    private Guideline guideline25, guideline24, guideline23, guideline21, guideline2;
    int seek1 = 0;
    int seek2 = 0;
    private Button buttonSubmitSurvey;
    int seek3 = 0;
    int seek4 = 0;
    int seek5 = 0;
    //    private RadioGroup radioGroup, radioGroup2, radioGroup3, radioGroup4, radioGroup5;
//    private RadioButton rb1Q4, rb2Q4, rb3Q4, rb4Q4, rb5Q4;
//    private RadioButton rb1Q5, rb2Q5, rb3Q5, rb4Q5, rb5Q5;
//    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    SeekBar seekBar, seekBar1, seekBar2, seekBar3, seekBar4;
    private ImageView img1, img2, img3, img4, img5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findIDs();

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        apiGetQuestions();

        clickable();

        buttonSubmitSurvey.setOnClickListener(v->{

                    if(seek1 == 0 && seek2 == 0 && seek3 == 0 && seek4 == 0 && seek5 == 0){
                        Toast.makeText(this, "Please select range for questions", Toast.LENGTH_SHORT).show();
                    }else {
                        finalCall();
//                        startActivity(new Intent(Survey.this, CalenderV.class));
                        Log.e("error", ""+ seek1 + " ," + seek2 + ", "+ seek3 + ", "+ seek4 + ", "+ seek5);
                    }

        });


    }

    private void finalCall() {
        SharedPreferences sh = getSharedPreferences("share", MODE_PRIVATE);

        String idh1 = sh.getString("id", "");


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user = sharedPreferences.getString("id", "1");

        String url = "https://app.whyuru.com/api/web/addRating";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("valid")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    JSONObject jsonObject11 = jsonObject1.getJSONObject("ratings");
                    int a = Integer.parseInt(jsonObject11.getString("rt1"));
                    int b = Integer.parseInt(jsonObject11.getString("rt2"));
                    int c = Integer.parseInt(jsonObject11.getString("rt3"));
                    int d = Integer.parseInt(jsonObject11.getString("rt4"));
                    int e1 = Integer.parseInt(jsonObject11.getString("rt5"));
                    guideline2.setGuidelinePercent(a);
                    guideline21.setGuidelinePercent(b);
                    guideline23.setGuidelinePercent(c);
                    guideline24.setGuidelinePercent(d);
                    guideline25.setGuidelinePercent(e1);

                    new Handler().postDelayed(()->{
                        Intent intent = new Intent(this, CalenderV.class);
                        try {
                            intent.putExtra("videoURL", jsonObject1.getString("videoURL"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                    },3000);

                } else {
                    Toast.makeText(this, "No response from server", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", idh1);
                map.put("q1ID", ques_id.get(0));
                map.put("q2ID", ques_id.get(1));
                map.put("q3ID", ques_id.get(2));
                map.put("q4ID", ques_id.get(3));
                map.put("q5ID", ques_id.get(4));
                map.put("q1RT", String.valueOf(seek1));
                map.put("q2RT", String.valueOf(seek2));
                map.put("q3RT", String.valueOf(seek3));
                map.put("q4RT", String.valueOf(seek4));
                map.put("q5RT", String.valueOf(seek5));

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void clickable() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i <= 20) {
                    seek1 = 1;
                    img1.setImageResource(R.drawable.verysad);
                } else if (i > 20 && i <= 40) {
                    seek1 = 2;
                    img1.setImageResource(R.drawable.lowsad);

                } else if (i > 40 && i <= 60) {
                    seek1 = 3;
                    img1.setImageResource(R.drawable.average);

                } else if (i > 60 && i <= 80) {
                    seek1 = 4;
                    img1.setImageResource(R.drawable.lowsat);

                } else {
                    seek1 = 5;
                    img1.setImageResource(R.drawable.verysat);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i <= 20) {
                    seek2 = 1;
                    img3.setImageResource(R.drawable.verysad);
                } else if (i > 20 && i <= 40) {
                    seek2 = 2;
                    img3.setImageResource(R.drawable.lowsad);

                } else if (i > 40 && i <= 60) {
                    seek2 = 3;
                    img3.setImageResource(R.drawable.average);

                } else if (i > 60 && i <= 80) {
                    seek2 = 4;
                    img3.setImageResource(R.drawable.lowsat);

                } else {
                    seek2 = 5;
                    img3.setImageResource(R.drawable.verysat);

                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i <= 20) {
                    seek3 = 1;
                    img2.setImageResource(R.drawable.verysad);

                } else if (i > 20 && i <= 40) {
                    seek3 = 2;
                    img2.setImageResource(R.drawable.lowsad);

                } else if (i > 40 && i <= 60) {
                    seek3 = 3;
                    img2.setImageResource(R.drawable.average);

                } else if (i > 60 && i <= 80) {
                    seek3 = 4;
                    img2.setImageResource(R.drawable.lowsat);

                } else {
                    seek3 = 5;
                    img2.setImageResource(R.drawable.verysat);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i <= 20) {
                    seek4 = 1;
                    img4.setImageResource(R.drawable.verysad);
                } else if (i > 20 && i <= 40) {
                    seek4 = 2;
                    img4.setImageResource(R.drawable.lowsad);
                } else if (i > 40 && i <= 60) {
                    seek4 = 3;
                    img4.setImageResource(R.drawable.average);
                } else if (i > 60 && i <= 80) {
                    seek4 = 4;
                    img4.setImageResource(R.drawable.lowsat);
                } else {
                    seek4 = 5;
                    img4.setImageResource(R.drawable.verysat);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i <= 20) {
                    seek5 = 1;
                    img5.setImageResource(R.drawable.verysad);
                } else if (i > 20 && i <= 40) {
                    seek5 = 2;
                    img5.setImageResource(R.drawable.lowsad);
                } else if (i > 40 && i <= 60) {
                    seek5 = 3;

                    img5.setImageResource(R.drawable.average);
                } else if (i > 60 && i <= 80) {
                    seek5 = 4;
                    img5.setImageResource(R.drawable.lowsat);
                } else {
                    seek5 = 5;
                    img5.setImageResource(R.drawable.verysat);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void apiGetQuestions() {
        String url = "https://app.whyuru.com/api/web/showSurveyquestions";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean status = jsonObject.getBoolean("valid");
                progressDialog.dismiss();
                if (status) {
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        ques_id.add(jsonObject1.getString("id"));
                        question.add(jsonObject1.getString("questions"));

                    }

                    ques1.setText(question.get(0));
                    ques2.setText(question.get(1));
                    ques3.setText(question.get(2));
                    ques4.setText(question.get(3));
                    ques5.setText(question.get(4));

                } else {
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void findIDs() {
        seekBar = findViewById(R.id.seekBar);
        seekBar1 = findViewById(R.id.seekBar11);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);
        seekBar4 = findViewById(R.id.seekBar4);
        ques1 = findViewById(R.id.ques1);
        ques2 = findViewById(R.id.ques2);
        ques3 = findViewById(R.id.ques3);
        ques4 = findViewById(R.id.ques4);
        ques5 = findViewById(R.id.ques5);
        buttonSubmitSurvey = findViewById(R.id.buttonSubmitSurvey);

        guideline2 = findViewById(R.id.guideline2);
        guideline21 = findViewById(R.id.guideline21);
        guideline23 = findViewById(R.id.guideline23);
        guideline24 = findViewById(R.id.guideline24);
        guideline25 = findViewById(R.id.guideline25);
    }


    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_survey);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//
//        findIDs();
//
//        ques4 = findViewById(R.id.ques4);
//
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//
//        Button btn_survey_submit = findViewById(R.id.btn_survey_submit);
//        btn_survey_submit.setOnClickListener(v->{
//
//            int selectedId1 = radioGroup.getCheckedRadioButtonId();
//            radioButton1 = findViewById(selectedId1);
//
//            int selectedId2 = radioGroup2.getCheckedRadioButtonId();
//            radioButton2 = findViewById(selectedId2);
//
//            int selectedId3 = radioGroup3.getCheckedRadioButtonId();
//            radioButton3 = findViewById(selectedId3);
//
//            apiSaveQuestions(/*""+radioButton1.getId(), ""+radioButton2.getId(), ""+radioButton3.getId()*/);
//        });
//
//        apiGetQuestions();
//    }
//
//
//
//    private void apiGetQuestions() {
//
//        String url = "https://app.whyuru.com/api/web/showSurveyquestions";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                boolean status = jsonObject.getBoolean("valid");
//                    progressDialog.dismiss();
//                if (status){
//                    JSONArray jsonArray = jsonObject.getJSONArray("result");
//                    for (int i = 0; i < jsonArray.length() ; i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//
//                        ques_id.add(jsonObject1.getString("id"));
//                        question.add(jsonObject1.getString("questions"));
//
//                    }
//
//                    ques1.setText(question.get(0));
//                    ques2.setText(question.get(1));
//                    ques3.setText(question.get(2));
//                    ques4.setText(question.get(3));
//
//                }else {
//                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show());
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
//
//    private void apiSaveQuestions(/*String selectedId1, String selectedId2, String selectedId3*/) {
//
//        SharedPreferences sh = getSharedPreferences("share", MODE_PRIVATE);
//
//        String idh1 = sh.getString("id", "");
//
//
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String user = sharedPreferences.getString("id", "1");
//
//        String url = "https://app.whyuru.com/api/web/addRating";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                if(jsonObject.getBoolean("valid")){
//                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
//
//                    Intent intent = new Intent(this, CalenderV.class);
//                    intent.putExtra("videoURL", jsonObject1.getString("videoURL"));
//                    startActivity(intent);
//
//                }else {
//                    Toast.makeText(this, "No response from server", Toast.LENGTH_SHORT).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show())
//        {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> map = new HashMap<>();
//                map.put("userId", idh1);
//                map.put("q1ID", ques_id.get(0));
//                map.put("q2ID", ques_id.get(1));
//                map.put("q3ID", ques_id.get(2));
//                map.put("q4ID", ques_id.get(3));
//                map.put("q5ID", ques_id.get(4));
//                map.put("q1RT", "5");
//                map.put("q2RT", "4");
//                map.put("q3RT", "5");
//                map.put("q4RT","3");
//                map.put("q5RT","5");
//
//                return map;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
//
//    private void findIDs() {
//        ques1 = findViewById(R.id.ques1);
//        ques2 = findViewById(R.id.ques2);
//        ques3 = findViewById(R.id.ques3);
//
//
//        radioGroup = findViewById(R.id.radioGroup);
//        radioGroup2 = findViewById(R.id.radioGroup2);
//        radioGroup3 = findViewById(R.id.radioGroup3);
//
//        RadioButton rb1Q1 = findViewById(R.id.rb1Q1);
//        RadioButton rb2Q1 = findViewById(R.id.rb2Q1);
//        RadioButton rb3Q1 = findViewById(R.id.rb3Q1);
//        RadioButton rb4Q1 = findViewById(R.id.rb4Q1);
//        RadioButton rb5Q1 = findViewById(R.id.rb5Q1);
//
//        RadioButton rb1Q2 = findViewById(R.id.rb1Q2);
//        RadioButton rb2Q2 = findViewById(R.id.rb2Q2);
//        RadioButton rb3Q2 = findViewById(R.id.rb3Q2);
//        RadioButton rb4Q2 = findViewById(R.id.rb4Q2);
//        RadioButton rb5Q2 = findViewById(R.id.rb5Q2);
//
//        RadioButton rb1Q3 = findViewById(R.id.rb1Q3);
//        RadioButton rb2Q3 = findViewById(R.id.rb2Q3);
//        RadioButton rb3Q3 = findViewById(R.id.rb3Q3);
//        RadioButton rb4Q3 = findViewById(R.id.rb4Q3);
//        RadioButton rb5Q3 = findViewById(R.id.rb5Q3);
//
//    }
}