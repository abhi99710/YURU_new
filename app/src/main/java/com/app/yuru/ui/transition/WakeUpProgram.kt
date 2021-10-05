package com.app.yuru.ui.transition

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class WakeUpProgram : Fragment() {

    var wakeuprecy: GridView? = null
    private var requestQueue: RequestQueue? = null
    val _mcurrentTime = Calendar.getInstance()
    val hour: Int = _mcurrentTime.get(Calendar.HOUR_OF_DAY)
    val minute: Int = _mcurrentTime.get(Calendar.MINUTE)

    private lateinit var o_option: TextView
    private lateinit var c_option: TextView
    private lateinit var e_option: TextView
    private lateinit var a_option: TextView
    private lateinit var n_option: TextView
    private lateinit var female: ImageView
    private lateinit var male: ImageView
    private lateinit var optionAlarm_tv : ImageView
    private lateinit var time_tv: TextView
    private lateinit var am_tv: TextView
    private lateinit var pm_tv: TextView

    private var id1: MutableList<String> = ArrayList()
    private var category_name: MutableList<String> = ArrayList()
    private var language_name: MutableList<String> = ArrayList()
    private var gender: MutableList<String> = ArrayList()
    private var traint: MutableList<String> = ArrayList()
    private var duration: MutableList<String> = ArrayList()
    private var url1 : MutableList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_wake_up_program, container, false)

        o_option = view.findViewById(R.id.o_option)
        c_option = view.findViewById(R.id.c_option)
        e_option = view.findViewById(R.id.e_option)
        a_option = view.findViewById(R.id.a_option)
        n_option = view.findViewById(R.id.n_option)
        female = view.findViewById(R.id.female)
        male = view.findViewById(R.id.male)
        optionAlarm_tv = view.findViewById(R.id.optionAlarm_tv)
        time_tv = view.findViewById(R.id.time_tv)
        am_tv = view.findViewById(R.id.am_tv)
        pm_tv = view.findViewById(R.id.pm_tv)
        wakeuprecy = view.findViewById(R.id.wakeuprecy)

        apiVideos()

        timePicker()
        return view
    }

    private fun go() {
        //GET TIME IN SECONDS AND INITIALIZE INTENT
        val time: Long = time_tv.getText().toString().toLong()
        val i = Intent(context, MyReceiver::class.java)



        val dat = Date()
        val cal_alarm = Calendar.getInstance()
        val cal_now = Calendar.getInstance()
        cal_now.time = dat
        cal_alarm.time = dat
        cal_alarm[Calendar.HOUR_OF_DAY] = hour
        cal_alarm[Calendar.MINUTE] = minute
        cal_alarm[Calendar.SECOND] = 0



        //PASS CONTEXT,YOUR PRIVATE REQUEST CODE,INTENT OBJECT AND FLAG
        val pi = PendingIntent.getBroadcast(context, 0, i, 0)

        //INITIALIZE ALARM MANAGER
        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager?

        //SET THE ALARM
        alarmManager!![AlarmManager.RTC_WAKEUP, /*System.currentTimeMillis() + time * 1000*/  (((hour*60) + minute)*60 *1000).toLong()  - System.currentTimeMillis()] =
            pi
        Toast.makeText(context, "Alarm set in $time seconds", Toast.LENGTH_SHORT).show()
    }


    private fun apiVideos() {
        val url  = "https://promask.com.co/yuru/api/videos"

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    val jsonArray = jsonObject.getJSONArray("data")

                    id1.clear()
                    category_name.clear()
                    language_name.clear()
                    gender.clear()
                    traint.clear()
                    duration.clear()
                    url1.clear()
                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)

                        id1.add(jsonObject1.getString("id"))
                        category_name.add(jsonObject1.getString("category_name"))
                        language_name.add(jsonObject1.getString("language_name"))
                        gender.add(jsonObject1.getString("gender"))
                        traint.add(jsonObject1.getString("traint"))
                        duration.add(jsonObject1.getString("duration"))
                        url1.add(jsonObject1.getString("url"))

                    }

                    val adapterMain = AdapterMain(
                        context,
                        id1,
                        category_name,
                        language_name,
                        gender,
                        traint,
                        duration,
                        url1
                    )
                    wakeuprecy?.adapter = adapterMain



                    /* if(obj.getString("msg").equals("Logged Successfully")) {

 //                        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
 //                        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
 //
 //                        editor.putString("email",jsonObject.getString("email"))
 //                        editor.apply()
 //                        editor.commit()

 //                        intent = Intent(applicationContext, Dashboard::class.java)
 //                        startActivity(intent)
                     }*/
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            })
        {
//            @Throws(AuthFailureError::class)
//            override fun getParams(): Map<String, String> {
//                val params = HashMap<String, String>()
//                params.put("roleType", "admin")
//
//                return params
//            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    fun timePicker(){

        time_tv.setOnClickListener {

            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(
                context,
                { timePicker, selectedHour, selectedMinute -> time_tv.setText("$selectedHour:$selectedMinute") },
                hour,
                minute,
                true
            ) //Yes 24 hour time

            mTimePicker.setTitle("Select Time")
            mTimePicker.show()

            go()
        }

    }

}