package com.app.yuru.ui.lowvshigh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yuru.R;
import com.app.yuru.ui.transition.TransitionActivity;
import com.app.yuru.utility.apivolley.APIVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LowvsHigh extends AppCompatActivity {

    private TextView o_option_, c_option_, e_option_, a_option_, n_option_;
    private TextView title_low, desc_low, tv_percenteage_low, low_low, high_low, desc_min_low;
    private Button btn_low;
    private Guideline guideline15;
    final List<ModelLowHigh> modelLowHighs = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowvs_high);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findIds();

        try {
            fragmentClick();
        } catch (Exception e) {
            e.printStackTrace();
        }

        apiLowHigh();



        low_low.setBackgroundColor(Color.GREEN);
        low_low.setTextColor(Color.WHITE);
//        if(modelLowHighs!=null) {
//            low_low.setText(modelLowHighs.get(0).getLow_data_point());
//        }

        low_low.setOnClickListener(v->{
            low_low.setBackgroundColor(Color.GREEN);
            low_low.setTextColor(Color.WHITE);
            desc_min_low.setText(modelLowHighs.get(0).getLow_data_point());
            high_low.setBackgroundColor(Color.WHITE);
            high_low.setTextColor(Color.BLACK);
        });

        high_low.setOnClickListener(v->{
            high_low.setBackgroundColor(Color.GREEN);
            high_low.setTextColor(Color.WHITE);
            desc_min_low.setText(modelLowHighs.get(0).getHigh_data_point());
            low_low.setBackgroundColor(Color.WHITE);
            low_low.setTextColor(Color.BLACK);
        });

        btn_low.setOnClickListener(v-> startActivity(new Intent(this, TransitionActivity.class)));
    }

    private void fragmentClick() {
        o_option_.setOnClickListener(v->{
//            setFragment(new FirstFragment());
            title_low.setText("Openness");
            desc_low.setText(modelLowHighs.get(0).getQuestion_title());
            guideline15.setGuidelinePercent(.5f);
            tv_percenteage_low.setBackgroundColor(Color.GREEN);
            tv_percenteage_low.setText("50%");
            o_option_.setBackgroundColor(Color.GREEN);
            o_option_.setTextColor(Color.WHITE);
            optionBackdround(e_option_, c_option_, a_option_, n_option_);
        });

        c_option_.setOnClickListener(v->{
//            setFragment(new CFranment());
            title_low.setText("Conscientiousness");
            desc_low.setText(modelLowHighs.get(1).getQuestion_title());
            guideline15.setGuidelinePercent(.4f);
            tv_percenteage_low.setText("40%");
            tv_percenteage_low.setBackgroundColor(Color.BLUE);
            c_option_.setBackgroundColor(Color.BLUE);
            c_option_.setTextColor(Color.WHITE);
            optionBackdround(o_option_, e_option_, a_option_, n_option_);
        });

        e_option_.setOnClickListener(v->{
//            setFragment(new E_Fragment());
            title_low.setText("Extraversion");

            guideline15.setGuidelinePercent(.7f);
            tv_percenteage_low.setText("70%");
            tv_percenteage_low.setBackgroundColor(Color.RED);
            e_option_.setBackgroundColor(Color.RED);
            e_option_.setTextColor(Color.WHITE);
            optionBackdround(o_option_, c_option_, a_option_, n_option_);
            try {
                desc_low.setText(modelLowHighs.get(2).getQuestion_title());
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        a_option_.setOnClickListener(v->{
//            setFragment(new A_Fragment());
            title_low.setText("Agreeableness");

            guideline15.setGuidelinePercent(.4f);
            tv_percenteage_low.setText("40%");
            tv_percenteage_low.setBackgroundColor(Color.YELLOW);
            a_option_.setBackgroundColor(Color.YELLOW);
            a_option_.setTextColor(Color.WHITE);
            optionBackdround(o_option_, c_option_, e_option_, n_option_);
            try {
                desc_low.setText(modelLowHighs.get(3).getQuestion_title());
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        n_option_.setOnClickListener(v->{
//            setFragment(new N_Fragment());
            title_low.setText("Neuroticism");

            guideline15.setGuidelinePercent(.2f);
            tv_percenteage_low.setText("20%");
            tv_percenteage_low.setBackgroundColor(Color.BLACK);
            n_option_.setBackgroundColor(Color.BLACK);
            n_option_.setTextColor(Color.WHITE);
            optionBackdround(o_option_, c_option_, e_option_, a_option_);
            try {
                desc_low.setText(modelLowHighs.get(4).getQuestion_title());
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private void findIds() {
        guideline15 = findViewById(R.id.guideline15);

        o_option_ = findViewById(R.id.o_option_);
        c_option_ = findViewById(R.id.c_option_);
        e_option_ = findViewById(R.id.e_option_);
        a_option_ = findViewById(R.id.a_option_);
        n_option_ = findViewById(R.id.n_option_);

        View stepO_ = findViewById(R.id.stepO_);
        View stepC_ = findViewById(R.id.stepC_);
        View stepE_ = findViewById(R.id.stepE_);
        View stepA_ = findViewById(R.id.stepA_);
        View stepN_ = findViewById(R.id.stepN_);

        btn_low = findViewById(R.id.btn_low);

        title_low = findViewById(R.id.title_low);
        desc_low = findViewById(R.id.desc_low);
        tv_percenteage_low = findViewById(R.id.tv_percenteage_low);
        low_low = findViewById(R.id.low_low);
        high_low = findViewById(R.id.high_low);
        desc_min_low = findViewById(R.id.desc_min_low);
    }

    void apiLowHigh(){


        SharedPreferences sh = getSharedPreferences("share", MODE_PRIVATE);

        String idh1 = sh.getString("id", "");

        String url = APIVolley.Companion.getSummary();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                progressDialog.dismiss();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length() ; i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    modelLowHighs.add(new ModelLowHigh(
                            jsonObject1.getString("id"),
                            jsonObject1.getString("question_text"),
                            jsonObject1.getString("question_title"),
                            jsonObject1.getString("low_data_point"),
                            jsonObject1.getString("high_data_point"),
                            jsonObject1.getString("question_category_id"),
                            jsonObject1.getString("created_at"),
                            jsonObject1.getString("updated_at")
                    ));
                }

                title_low.setText("Openness");
                desc_low.setText(modelLowHighs.get(0).getQuestion_title());
                guideline15.setGuidelinePercent(.5f);
                tv_percenteage_low.setBackgroundColor(Color.GREEN);
                tv_percenteage_low.setText("50%");
                o_option_.setBackgroundColor(Color.GREEN);
                o_option_.setTextColor(Color.WHITE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.lowhighframe, fragment).commit();
    }

    void optionBackdround(TextView tv1, TextView tv2, TextView tv3, TextView tv4){
        tv1.setBackgroundColor(Color.WHITE);
        tv1.setTextColor(Color.BLACK);
        tv2.setBackgroundColor(Color.WHITE);
        tv2.setTextColor(Color.BLACK);
        tv3.setBackgroundColor(Color.WHITE);
        tv3.setTextColor(Color.BLACK);
        tv4.setBackgroundColor(Color.WHITE);
        tv4.setTextColor(Color.BLACK);
    }
}