package com.app.yuru.ui.discounts;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yuru.R;
import com.app.yuru.ui.transition.TransitionActivity;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class CalenderV extends AppCompatActivity {

    private final List<String> dated_list = new ArrayList<>();
//    List<Calendar> highlighted = new ArrayList<>();
//    private CalendarView calendarView;
    private CalendarPickerView calendar;
    private Button button;
//    private ProgressDialog progressDialog;
    private final List<String> mm = new ArrayList<>();


    private ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_v);
        
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Button thanks = findViewById(R.id.Thanks);
        thanks.setOnClickListener(v->{
//            getSupportFragmentManager().beginTransaction().replace(R.id.framwQts, new TransitionToSleep()).commit();
            startActivity(new Intent(this, TransitionActivity.class));
        });


        mm.add("31-12-2021");
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, - 10);

        calendar = findViewById(R.id.calendar_view);
        button = findViewById(R.id.get_selected_dates);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);

        calendar.deactivateDates(list);
        ArrayList<Date> arrayList = new ArrayList<>();
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

            String strdate = "22-12-2021";
            String strdate2 = "26-12-2021";

            Date newdate = dateformat.parse(strdate);
            Date newdate2 = null;
            try {
                newdate2 = dateformat.parse(strdate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            arrayList.add(newdate);
            arrayList.add(newdate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
                .withDeactivateDates(list)
                .withSubTitles(getSubTitles())
                .withHighlightedDates(arrayList);

        calendar.scrollToDate(new Date());

//        calendarSet(mm);

        getDateList();

    }

    private ArrayList<SubTitle> getSubTitles() {
        final ArrayList<SubTitle> subTitles = new ArrayList<>();
        final Calendar tmrw = Calendar.getInstance();
        tmrw.add(Calendar.DAY_OF_MONTH, 1);
        subTitles.add(new SubTitle(tmrw.getTime(), ""));
        return subTitles;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void getDateList(){

        SharedPreferences sh = getSharedPreferences("share", MODE_PRIVATE);

        String ids1 = sh.getString("id", "1");

        String url = "https://app.whyuru.com/api/getSavedDateListByUser";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                progressDialog.dismiss();
                JSONObject jsonObject = new JSONObject(response);
                JSONObject result = jsonObject.getJSONObject("result");
                JSONArray jsonArray = result.getJSONArray("dateArr");

                for (int i = 0; i < jsonArray.length() ; i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String s = jsonObject1.getString("updatedDate");

                    String k = s.split(" ", 0)[0];
                    int year = Integer.parseInt(k.split("-",0)[0]);
                    int month = Integer.parseInt(k.split("-",0)[1]);
                    int day = Integer.parseInt(k.split("-",0)[2]);

                    dated_list.add(day+"-"+month+"-"+year);
//                    Calendar cal = Calendar.getInstance();

                    calendarSet(dated_list);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show()){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", ids1);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public static Integer toJulianDate(Date pDate) {
        if (pDate == null) {
            return null;
        }
        Calendar lCal = Calendar.getInstance();
        lCal.setTime(pDate);
        int lYear = lCal.get(Calendar.YEAR);
        int lMonth = lCal.get(Calendar.MONTH) + 1;
        int lDay = lCal.get(Calendar.DATE);
        int a = (14 - lMonth) / 12;
        int y = lYear + 4800 - a;
        int m = lMonth + 12 * a - 3;
        return lDay + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
    }

    void calendarSet(List<String> list1){


        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, - 10);

        calendar = findViewById(R.id.calendar_view);
        button = findViewById(R.id.get_selected_dates);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);

        calendar.deactivateDates(list);
        ArrayList<Date> arrayList = new ArrayList<>();
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

            String strdate = "22-12-2021";
            String strdate2 = "26-12-2021";

            Date newdate = dateformat.parse(strdate);
            Date newdate2 = null;
            try {
                newdate2 = dateformat.parse(strdate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for (int i = 0; i <list1.size() ; i++) {
                Date newdate1 = dateformat.parse(list1.get(i));
                arrayList.add(newdate1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
                .withDeactivateDates(list)
                .withSubTitles(getSubTitles())
                .withHighlightedDates(arrayList);

        calendar.scrollToDate(new Date());


        button.setOnClickListener(view -> Toast.makeText(CalenderV.this, "list " + calendar.getSelectedDates().toString(), Toast.LENGTH_LONG).show());
    }
}