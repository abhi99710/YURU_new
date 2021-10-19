package com.app.yuru.ui.transition

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.utility.apivolley.APIVolley
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.ExperimentalTime


class WakeUpProgram : Fragment() {


    var wakeuprecy: GridView? = null
    private var requestQueue: RequestQueue? = null
    val _mcurrentTime = Calendar.getInstance()
    var hours: Int = 0
    val minutes = 0

    private lateinit var o_option: TextView
    private lateinit var c_option: TextView
    private lateinit var e_option: TextView
    private lateinit var a_option: TextView
    private lateinit var n_option: TextView
    private lateinit var female: ImageView
    private lateinit var male: ImageView
    private lateinit var optionAlarm_tv: ImageView
    private lateinit var time_tv: TextView
    private lateinit var am_tv: TextView
    private lateinit var pm_tv: TextView
    private lateinit var save_wakeup : TextView
    private lateinit var viewall : TextView

    private var id1Male: MutableList<String> = ArrayList()
    private var category_nameMale: MutableList<String> = ArrayList()
    private var language_nameMale: MutableList<String> = ArrayList()
    private var genderMale: MutableList<String> = ArrayList()
    private var traintMale: MutableList<String> = ArrayList()
    private var durationMale: MutableList<String> = ArrayList()
    private var url1Male: MutableList<String> = ArrayList()
    private var id1Female: MutableList<String> = ArrayList()
    private var category_nameFemale: MutableList<String> = ArrayList()
    private var language_nameFemale: MutableList<String> = ArrayList()
    private var genderFemale: MutableList<String> = ArrayList()
    private var traintFemale: MutableList<String> = ArrayList()
    private var durationFemale: MutableList<String> = ArrayList()
    private var url1Female: MutableList<String> = ArrayList()

//    private var

    private val alarmTimePicker: TimePicker? = null

    private var clickedGender = ""




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_wake_up_program, container, false)

        findIds(view)

        allClickListner()

//        requireActivity().fragmentManager.beginTransaction().


        apiVideos()

//        go()

        timePicker()
        return view
    }

    private fun allClickListner() {

        female.setOnClickListener {
            Toast.makeText(context, "female", Toast.LENGTH_SHORT).show()

            clickedGender = "female"

            val adapterMain = AdapterMain(
                context,
                id1Female,
                category_nameFemale,
                language_nameFemale,
                genderFemale,
                traintFemale,
                durationFemale,
                url1Female
            )
            wakeuprecy?.adapter = adapterMain

        }

        male.setOnClickListener {
            Toast.makeText(context, "male", Toast.LENGTH_SHORT).show()

            clickedGender = "male"

            val adapterMain = AdapterMain(
                context,
                id1Male,
                category_nameMale,
                language_nameMale,
                genderMale,
                traintMale,
                durationMale,
                url1Male
            )
            wakeuprecy?.adapter = adapterMain
        }

        o_option.setOnClickListener {

            textColor(o_option, c_option, e_option, a_option, n_option)

            Toast.makeText(context, "O", Toast.LENGTH_SHORT).show()
        }

        c_option.setOnClickListener {
            textColor(c_option, o_option, e_option, a_option, n_option)
            Toast.makeText(context, "C", Toast.LENGTH_SHORT).show()

        }
        e_option.setOnClickListener {
            textColor(e_option,c_option, o_option, a_option, n_option)

            Toast.makeText(context, "E", Toast.LENGTH_SHORT).show()
        }

        a_option.setOnClickListener {
            textColor(a_option,e_option,c_option, o_option, n_option)

            Toast.makeText(context, "A", Toast.LENGTH_SHORT).show()

        }
        n_option.setOnClickListener {
            textColor(n_option,a_option,e_option,c_option, o_option)

            Toast.makeText(context, "N", Toast.LENGTH_SHORT).show()

        }

        save_wakeup.setOnClickListener {
            val fragment = requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framwQts, TransitionToSleep())
            fragment.addToBackStack(null)
            fragment.commit()
        }

        viewall.setOnClickListener {
            val intent = Intent(context, BasicActivity::class.java)
            context?.startActivity(intent)
        }

        optionAlarm_tv.setOnClickListener {

        }
    }

    private fun findIds(view: View) {
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
        save_wakeup = view.findViewById(R.id.save_wakeup)
        viewall = view.findViewById(R.id.viewall)
    }


    private fun go() {

        val  SimpleDateFormat = SimpleDateFormat("HH:mm:ss")

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val calendar = Calendar.getInstance()
        val calList : MutableList<Calendar> = ArrayList()

        for(i in 0..4 ){
            calList.add(calendar)
        }

        val stringBuilder = ""

        for( calItem in calList){
            calItem.add(Calendar.SECOND,10)

            val requestCode = (calendar.timeInMillis/1000).toInt()
            val intent = Intent(context, MyReceiver::class.java)
            intent.putExtra("REQUEST_CODE",requestCode)
            intent.putExtra("fragment","wakeup")
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

            val pi = PendingIntent.getBroadcast(context, requestCode, intent, 0)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    alarmManager?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calItem.timeInMillis, pi)
                }else{
                    alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calItem.timeInMillis, pi)
                }



            Toast.makeText(context, "Alarm has been set : \n "+stringBuilder , Toast.LENGTH_SHORT).show()

        }
    }


    private fun apiVideos() {
        val url = APIVolley.videosApi

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    val jsonArray = jsonObject.getJSONArray("data")

                    id1Male.clear()
                    category_nameMale.clear()
                    language_nameMale.clear()
                    genderMale.clear()
                    traintMale.clear()
                    durationMale.clear()
                    url1Male.clear()
                    genderFemale.clear()
                    traintFemale.clear()
                    durationFemale.clear()
                    url1Female.clear()
                    id1Female.clear()
                    category_nameFemale.clear()
                    language_nameFemale.clear()

                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)

                        if(jsonObject1.getString("gender").equals("Male")){
                            id1Male.add(jsonObject1.getString("id"))
                            category_nameMale.add(jsonObject1.getString("category_name"))
                            language_nameMale.add(jsonObject1.getString("language_name"))
                            genderMale.add(jsonObject1.getString("gender"))
                            traintMale.add(jsonObject1.getString("traint"))
                            durationMale.add(jsonObject1.getString("duration"))
                            url1Male.add(jsonObject1.getString("url"))
                        }else{
                            id1Female.add(jsonObject1.getString("id"))
                            category_nameFemale.add(jsonObject1.getString("category_name"))
                            language_nameFemale.add(jsonObject1.getString("language_name"))
                            genderFemale.add(jsonObject1.getString("gender"))
                            traintFemale.add(jsonObject1.getString("traint"))
                            durationFemale.add(jsonObject1.getString("duration"))
                            url1Female.add(jsonObject1.getString("url"))
                        }


                    }

                    adapterConnects()


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
            }) {
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

    private fun adapterConnects() {
        val adapterMain = AdapterMain(
            context,
            id1Female,
            category_nameFemale,
            language_nameFemale,
            genderFemale,
            traintFemale,
            durationFemale,
            url1Female
        )
        wakeuprecy?.adapter = adapterMain

        wakeuprecy?.setOnItemClickListener { parent, view, position, id ->

//            Toast.makeText(
//                context,
//                "" + category_nameFemale.get(position),
//                Toast.LENGTH_SHORT
//            ).show()
            val intent = Intent(context, VideoPlay::class.java)
            if (clickedGender.equals("male")) {
                intent.putExtra(
                    "videoLink", /*genderMale.get(position)*/
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"
                )
            } else {
                intent.putExtra(
                    "videoLink", /*genderFemale.get(position)*/
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"
                )
            }
            startActivity(intent)
        }
    }


    fun timePicker() {

        time_tv.setOnClickListener {
          val strin : String =  showDialog("Time Picker")
            time_tv.setText(strin)
        }


//        time_tv.setOnClickListener {
//
//            val mTimePicker: TimePickerDialog
//            mTimePicker = TimePickerDialog(
//                context,
//                { timePicker, selectedHour, selectedMinute -> time_tv.setText("$selectedHour:$selectedMinute") },
//                hours,
//                minutes,
//                true
//            ) //Yes 24 hour time
//
//            mTimePicker.setTitle("Select Time")
//             mTimePicker.updateTime(11,45)
//            mTimePicker.show()
//

//        }

    }

    private fun showDialog(title: String) : String{
        val dialog = context?.let { Dialog(it) }
        var txt  = ""
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_time_picker)
            dialog.show()

            val closebtndialog = dialog.findViewById<TextView>(R.id.alarmToggle)
            closebtndialog.setOnClickListener {

                val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(
                    context,
                { timePicker, selectedHour, selectedMinute -> time_tv.setText("$selectedHour:$selectedMinute") },
                hours,
                minutes,
                true
                )

                 txt  = ""+ Calendar.HOUR_OF_DAY + " " + Calendar.MINUTE
                dialog.dismiss()

            }

            }

        return txt
        }


    fun textColor(tv1 : TextView,tv2 : TextView,tv3 : TextView, tv4 : TextView,tv5 : TextView ){

        tv1.setBackgroundColor(Color.parseColor("#FFC107")) // Yellow

        // Black
        tv2.setBackgroundColor(Color.parseColor("#000000"))
        tv3.setBackgroundColor(Color.parseColor("#000000"))
        tv4.setBackgroundColor(Color.parseColor("#000000"))
        tv5.setBackgroundColor(Color.parseColor("#000000"))

    }


}




