package com.app.yuru.ui.discounts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yuru.R;
import com.app.yuru.ui.coupons.Journals;
import com.app.yuru.ui.lowvshigh.LowvsHigh;
import com.app.yuru.ui.transition.TransitionActivity;
import com.app.yuru.utility.apivolley.APIVolley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoreInformation extends AppCompatActivity {

    private Spinner spinnerLanguage, spinnerAge, spinnerGender;
    private Button ProceedMore;
    private List<String> ageList = new ArrayList<>();
    private List<String> genderList = new ArrayList<>();
    private List<String> langList = new ArrayList<>();
    private List<String> langlistCode = new ArrayList<>();

    private ImageView backMoreInformation;

    private String apiAgeVarCeil;
    private String apiAgeVarfloor;
    private String apiLangVar;
    private String apiGenderVar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);

        spinnerAge = findViewById(R.id.spinnerAge);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        ProceedMore = findViewById(R.id.ProceedMore);

        backMoreInformation = findViewById(R.id.backMoreInformation);
        backMoreInformation.setOnClickListener(v->{
            startActivity(new Intent(this, CouponApplied.class));
        });

        ProceedMore.setOnClickListener(v -> {
            apiMoreInformation();
//            startActivity(new Intent(this, Journals.class));
        });

        ageList.add("Select");
        ageList.add("4-9");
        ageList.add("10-15");
        ageList.add("15-18");
        ageList.add("19-25");
        ageList.add("26-35");
        ageList.add("36-40");
        ageList.add("41-55");
        ageList.add("56-80");
        ageList.add("81+");

        genderList.add("Select");
        genderList.add("Male");
        genderList.add("Female");

        langList.add("Select");
        langList.add("English"); // en
        langList.add("Hindi"); // hi
        langList.add("Spanish"); // es
        langList.add("Russian"); // ru
        langList.add("Chinese"); // zh
        langList.add("Japnese"); // ja
        langList.add("Korean");  // ko

        langlistCode.add("Select");
        langlistCode.add("en");
        langlistCode.add("hi");
        langlistCode.add("es");
        langlistCode.add("ru");
        langlistCode.add("zh");
        langlistCode.add("ja");
        langlistCode.add("ko");


        selectGender();
        selectAge();
        selectLanguage();

    }

    private void selectGender() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MoreInformation.this, R.layout.spinner_layout_background, genderList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerGender.setAdapter(dataAdapter);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (position != 0) {
//                    mediumIdPosition = mediumId.get(position);
//                    classResponse();

                    if(genderList.get(position).equalsIgnoreCase("Male")){
                        apiGenderVar = "1";
                    }else {

                        apiGenderVar = "2";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
//                checkSum = "not selected";
            }

        });

    }

    private void selectAge() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MoreInformation.this, R.layout.spinner_layout_background, ageList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerAge.setAdapter(dataAdapter);

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (position != 0) {
//                    mediumIdPosition = mediumId.get(position);
//                    classResponse();

                    apiAgeVarCeil = ageList.get(position).split("-",0)[0];
                    apiAgeVarfloor = ageList.get(position).split("-",0)[1];

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
//                checkSum = "not selected";
            }

        });

    }

    private void selectLanguage() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MoreInformation.this, R.layout.spinner_layout_background, langList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerLanguage.setAdapter(dataAdapter);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (position != 0) {
//                    mediumIdPosition = mediumId.get(position);
//                    classResponse();

                    apiLangVar = langlistCode.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
//                checkSum = "not selected";
            }

        });

    }

    void apiMoreInformation(){

        String url = APIVolley.Companion.getUpdateProfile();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url , response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean str = jsonObject.getBoolean("valid");
                if(str){
                    Intent intent = new Intent(MoreInformation.this, LowvsHigh.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Please select all values", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show()){
            @Nullable
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> map = new HashMap<>();
                map.put("user_id", "1");
                map.put("gender", apiGenderVar);
                map.put("language_code", apiLangVar);
                map.put("age_floor", apiAgeVarfloor);
                map.put("age_ceiling", apiAgeVarCeil);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}