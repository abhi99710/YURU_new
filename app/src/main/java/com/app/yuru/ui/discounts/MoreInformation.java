package com.app.yuru.ui.discounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.app.yuru.R;
import com.app.yuru.ui.coupons.Journals;

import java.util.ArrayList;
import java.util.List;

public class MoreInformation extends AppCompatActivity {

    private Spinner spinnerLanguage, spinnerAge, spinnerGender;
    private Button ProceedMore;
    List<String> ageList = new ArrayList<>();
    List<String> genderList = new ArrayList<>();
    List<String> langList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);

        spinnerAge = findViewById(R.id.spinnerAge);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        ProceedMore = findViewById(R.id.ProceedMore);

        ProceedMore.setOnClickListener(v -> {
            startActivity(new Intent(this, Journals.class));
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
        langList.add("English");
        langList.add("Hindi");
        langList.add("Spanish");
        langList.add("Russian");
        langList.add("Chinese");
        langList.add("Japnese");

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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
//                checkSum = "not selected";
            }

        });

    }

}