package com.app.yuru.ui.transition

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.corescheduler.player.video.ui.VideoActivity
import com.app.yuru.corescheduler.utils.Constants
import com.app.yuru.ui.coupons.Journals
import com.app.yuru.utility.apivolley.APIVolley
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


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
    private lateinit var female: TextView
    private lateinit var male: TextView
    private lateinit var optionAlarm_tv: ImageView
    private lateinit var time_tv: TextView
    private lateinit var am_tv: TextView
    private lateinit var pm_tv: TextView
    private lateinit var save_wakeup: TextView
    private lateinit var viewall: TextView

    private var id1Male: MutableList<String> = ArrayList()
    private var category_nameMale: MutableList<String> = ArrayList()
    private var language_nameMale: MutableList<String> = ArrayList()
    private var genderMale: MutableList<String> = ArrayList()
    private var traintMale: MutableList<String> = ArrayList()
    private var durationMale: MutableList<String> = ArrayList()
    private var url1Male: MutableList<String> = ArrayList()
    private var thumbnailMale : MutableList<String> = ArrayList()

    private var id1Female: MutableList<String> = ArrayList()
    private var category_nameFemale: MutableList<String> = ArrayList()
    private var language_nameFemale: MutableList<String> = ArrayList()
    private var genderFemale: MutableList<String> = ArrayList()
    private var traintFemale: MutableList<String> = ArrayList()
    private var durationFemale: MutableList<String> = ArrayList()
    private var url1Female: MutableList<String> = ArrayList()
    private var thumbnailFemale : MutableList<String> = ArrayList()

    private lateinit var cl_wakeup_female : ConstraintLayout
    private lateinit var cl_wakeup_male : ConstraintLayout


    lateinit var progressDialog: ProgressDialog

    private lateinit var wake_up_video : VideoView

//    private var

    private val alarmTimePicker: TimePicker? = null

    private var clickedGender = ""

    private lateinit var e_new : ImageView
    private lateinit var a_new : ImageView
    private lateinit var n_new : ImageView
    private lateinit var o_new : ImageView
    private lateinit var c_new : ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_wake_up_program, container, false)

        cl_wakeup_male = view.findViewById(R.id.cl_wakeup_male)
        cl_wakeup_female = view.findViewById(R.id.cl_wakeup_female)

        findIds(view)

        val dayOfAlarm : TextView = view.findViewById(R.id.dayOfAlarm)
        dayOfAlarm.setOnClickListener {
            val dialog = context?.let { Dialog(it, android.R.style.Theme_Holo_Light) }
            if (dialog != null) {
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.dialogshow_options)
                dialog.show()

                var back_dialog_wake : ImageView = dialog.findViewById(R.id.back_dialog_wake)
                back_dialog_wake.setOnClickListener {
                    dialog.dismiss()
                }

//                val recyclerView: RecyclerView = dialog.findViewById(R.id.recyclerNewSleep);

                val friday4: TextView = dialog.findViewById(R.id.friDay)
                val sunday: TextView = dialog.findViewById(R.id.sunday)
                val monDay: TextView = dialog.findViewById(R.id.monDay)
                val tuesDay: TextView = dialog.findViewById(R.id.tuesDay)
                val wedsnesDay: TextView = dialog.findViewById(R.id.wedsnesDay)
                val thursDay: TextView = dialog.findViewById(R.id.thursDay)
                val saturDay: TextView = dialog.findViewById(R.id.saturDay)
                val everyDay: TextView = dialog.findViewById(R.id.everyDay)
                val never: TextView = dialog.findViewById(R.id.never)

                friday4.setOnClickListener {
                    dialog.dismiss()
                    dayOfAlarm.text = "Friday"
                    Toast.makeText(context, "Alarm set as per selected option", Toast.LENGTH_SHORT)
                }

                never.setOnClickListener {
                    dialog.dismiss()
                    dayOfAlarm.text = "Never"
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT)
                }
                everyDay.setOnClickListener {
                    dialog.dismiss()
                    dayOfAlarm.text = "Everyday"
                    Toast.makeText(context, "Alarm set as per selected option", Toast.LENGTH_SHORT)
                }
                saturDay.setOnClickListener {
                    dialog.dismiss()
                    dayOfAlarm.text = "Saturday"
                    Toast.makeText(context, "Alarm set as per selected option", Toast.LENGTH_SHORT)
                }

                sunday.setOnClickListener {
                    dialog.dismiss()
                    dayOfAlarm.text = "Sunday"
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT)
                }
                monDay.setOnClickListener {
                    dialog.dismiss()
                    dayOfAlarm.text = "Monday"
                    Toast.makeText(context, "Alarm set as per selected option", Toast.LENGTH_SHORT)
                }

                tuesDay.setOnClickListener {
                    dialog.dismiss()
                    dayOfAlarm.text = "Tuesday"
                    Toast.makeText(context, "Alarm set as per selected option", Toast.LENGTH_SHORT)
                }

                wedsnesDay.setOnClickListener {
                    dialog.dismiss()
                    dayOfAlarm.text = "Wednesday"
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT)
                }
                thursDay.setOnClickListener {
                    dialog.dismiss()
                    dayOfAlarm.text = "Thursday"
                    Toast.makeText(context, "Alarm set as per selected option", Toast.LENGTH_SHORT)
                }

//                val logo: TextView = dialog.findViewById(R.id.logo)
              /*  val repeat_dialog_tts: Button = dialog.findViewById(R.id.repeat_dialog_tts)
                val sleep_dialog_tts: Button = dialog.findViewById(R.id.sleep_dialog_tts)

                repeat_dialog_tts.setOnClickListener {
                    val intent = Intent(context, VideoActivity::class.java)
                    intent.putExtra(
                        Constants.VIDEO_LINK,
                        filename45.get(0) *//*"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"*//*
                    )
                    startActivity(intent)
                    dialog.dismiss()
                }*/


              /*  sleep_dialog_tts.setOnClickListener {
                    val fragment = requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.framwQts, SleepEnhancer())
                    fragment.addToBackStack(null)
                    fragment.commit()

                    dialog.dismiss()
                }*/

            }
        }


        allClickListner()

        progressDialog = ProgressDialog(context)
        progressDialog.show()
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")

//        requireActivity().fragmentManager.beginTransaction().

        videoPlay()

        apiVideos()

//        go()

        timePicker()
        return view
    }

    private fun allClickListner() {

        female.setOnClickListener {
            Toast.makeText(context, "female", Toast.LENGTH_SHORT).show()

//            cl_wakeup_female.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
//            cl_wakeup_male.setBackgroundColor(Color.TRANSPARENT)

            male.setTextColor(Color.GRAY)
//            male_tts_cl.setBackgroundColor(resources.getColor(R.color.white))

            female.setTextColor(Color.WHITE)

            val typeface: Typeface? = ResourcesCompat.getFont(requireContext(), R.font.raleway_bold)
            female.setTypeface(typeface)

            val typeface1: Typeface? = ResourcesCompat.getFont(requireContext(), R.font.raleway_semibold)
            male.setTypeface(typeface)

            clickedGender = "female"

            val adapterMain = AdapterMain(
                context,
                id1Female,
                category_nameFemale,
                language_nameFemale,
                genderFemale,
                traintFemale,
                durationFemale,
                url1Female,
                thumbnailFemale
            )
            wakeuprecy?.adapter = adapterMain

        }

        male.setOnClickListener {
            Toast.makeText(context, "male", Toast.LENGTH_SHORT).show()

//            cl_wakeup_male.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
//            cl_wakeup_female.setBackgroundColor(Color.TRANSPARENT)

            female.setTextColor(Color.GRAY)
//            male_tts_cl.setBackgroundColor(resources.getColor(R.color.white))

            male.setTextColor(Color.WHITE)


            //or to support all versions use
            //or to support all versions use
            val typeface: Typeface? = ResourcesCompat.getFont(requireContext(), R.font.raleway_bold)
            male.setTypeface(typeface)

            val typeface1: Typeface? = ResourcesCompat.getFont(requireContext(), R.font.raleway_semibold)
            female.setTypeface(typeface)

            clickedGender = "male"

            val adapterMain = AdapterMain(
                context,
                id1Male,
                category_nameMale,
                language_nameMale,
                genderMale,
                traintMale,
                durationMale,
                url1Male,
                thumbnailMale
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
            textColor(e_option, c_option, o_option, a_option, n_option)

            Toast.makeText(context, "E", Toast.LENGTH_SHORT).show()
        }

        a_option.setOnClickListener {
            textColor(a_option, e_option, c_option, o_option, n_option)

            Toast.makeText(context, "A", Toast.LENGTH_SHORT).show()

        }
        n_option.setOnClickListener {
            textColor(n_option, a_option, e_option, c_option, o_option)

            Toast.makeText(context, "N", Toast.LENGTH_SHORT).show()

        }

        save_wakeup.setOnClickListener {
//            val fragment = requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.framwQts, EveningProgram())
//            fragment.addToBackStack(null)
//            fragment.commit()
            val intent = Intent(context, Journals::class.java)
            startActivity(intent)
        }

        viewall.setOnClickListener {
//            val intent = Intent(context, BasicActivity::class.java)
//            context?.startActivity(intent)
        }

        optionAlarm_tv.setOnClickListener {

        }

        pm_tv.setOnClickListener {
            pm_tv.setBackgroundResource(R.drawable.wakeup_selected)
            am_tv.setBackgroundColor(Color.TRANSPARENT)
        }

        am_tv.setOnClickListener {
            am_tv.setBackgroundResource(R.drawable.wakeup_selected)
            pm_tv.setBackgroundColor(Color.TRANSPARENT)
        }


        /*all gear new setup click listner*/

        o_new.setOnClickListener({
            e_new.setImageResource(R.drawable.setting_o)
            o_new.setImageResource(R.drawable.setting_a)
            c_new.setImageResource(R.drawable.setting_n)
            a_new.setImageResource(R.drawable.setting_c)
            n_new.setImageResource(R.drawable.setting_e)
        })

        c_new.setOnClickListener {
            e_new.setImageResource(R.drawable.setting_c)
            o_new.setImageResource(R.drawable.setting_n)
            c_new.setImageResource(R.drawable.setting_o)
            a_new.setImageResource(R.drawable.setting_e)
            n_new.setImageResource(R.drawable.setting_a)
        }

        e_new.setOnClickListener {
            e_new.setImageResource(R.drawable.setting_e)
            o_new.setImageResource(R.drawable.setting_o)
            c_new.setImageResource(R.drawable.setting_c)
            a_new.setImageResource(R.drawable.setting_a)
            n_new.setImageResource(R.drawable.setting_n)
        }

        a_new.setOnClickListener {
            e_new.setImageResource(R.drawable.setting_a)
            o_new.setImageResource(R.drawable.setting_c)
            c_new.setImageResource(R.drawable.setting_e)
            a_new.setImageResource(R.drawable.setting_n)
            n_new.setImageResource(R.drawable.setting_o)
        }

        n_new.setOnClickListener {
            e_new.setImageResource(R.drawable.setting_n)
            o_new.setImageResource(R.drawable.setting_e)
            c_new.setImageResource(R.drawable.setting_a)
            a_new.setImageResource(R.drawable.setting_o)
            n_new.setImageResource(R.drawable.setting_c)
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

        wake_up_video = view.findViewById(R.id.wake_up_video)

        c_new = view.findViewById(R.id.c_new)
        o_new = view.findViewById(R.id.o_new)
        e_new = view.findViewById(R.id.e_new)
        a_new = view.findViewById(R.id.a_new)
        n_new = view.findViewById(R.id.n_new)
    }


    private fun go() {

        val SimpleDateFormat = SimpleDateFormat("HH:mm:ss")

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val calendar = Calendar.getInstance()
        val calList: MutableList<Calendar> = ArrayList()

        for (i in 0..4) {
            calList.add(calendar)
        }

        val stringBuilder = ""

        for (calItem in calList) {
            calItem.add(Calendar.SECOND, 10)

            val requestCode = (calendar.timeInMillis / 1000).toInt()
            val intent = Intent(context, MyReceiver::class.java)
            intent.putExtra("REQUEST_CODE", requestCode)
            intent.putExtra("fragment", "wakeup")
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

            val pi = PendingIntent.getBroadcast(context, requestCode, intent, 0)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager?.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calItem.timeInMillis,
                    pi
                )
            } else {
                alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calItem.timeInMillis, pi)
            }



            Toast.makeText(context, "Alarm has been set : \n " + stringBuilder, Toast.LENGTH_SHORT)
                .show()

        }
    }


    private fun apiVideos() {
        val url = APIVolley.videosApi

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                try {
                    progressDialog.dismiss()
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
                    thumbnailFemale.clear()
                    thumbnailMale.clear()

                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)

                        if (jsonObject1.getString("gender").equals("Male")) {
                            id1Male.add(jsonObject1.getString("id"))
                            category_nameMale.add(jsonObject1.getString("category_name"))
                            language_nameMale.add(jsonObject1.getString("language_name"))
                            genderMale.add(jsonObject1.getString("gender"))
                            traintMale.add(jsonObject1.getString("traint"))
                            durationMale.add(jsonObject1.getString("duration"))
                            url1Male.add(jsonObject1.getString("url"))
                            thumbnailMale.add(jsonObject1.getString("thumb"))
                        } else {
                            id1Female.add(jsonObject1.getString("id"))
                            category_nameFemale.add(jsonObject1.getString("category_name"))
                            language_nameFemale.add(jsonObject1.getString("language_name"))
                            genderFemale.add(jsonObject1.getString("gender"))
                            traintFemale.add(jsonObject1.getString("traint"))
                            durationFemale.add(jsonObject1.getString("duration"))
                            url1Female.add(jsonObject1.getString("url"))
                            thumbnailFemale.add(jsonObject1.getString("thumb"))

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
            url1Female,
            thumbnailFemale
        )
        wakeuprecy?.adapter = adapterMain

        wakeuprecy?.setOnItemClickListener { parent, view, position, id ->

//            Toast.makeText(
//                context,
//                "" + category_nameFemale.get(position),
//                Toast.LENGTH_SHORT
//            ).show()
            val intent = Intent(context, VideoActivity::class.java)
            if (clickedGender.equals("male")) {
                intent.putExtra(
                    Constants.VIDEO_LINK, /*genderMale.get(position)*/
                    url1Male.get(position)
                )
            } else {
                intent.putExtra(
                    Constants.VIDEO_LINK, /*genderFemale.get(position)*/
                    url1Female.get(position)
                )
            }
            startActivity(intent)
        }
    }


    fun timePicker() {

        time_tv.setOnClickListener {
            val strin: String = showDialog("Time Picker")
            time_tv.setText("" + strin)
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

    private fun showDialog(title: String): String {
        val dialog = context?.let { Dialog(it) }
        var txt = ""
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

                txt = "" + Calendar.HOUR_OF_DAY + " : " + Calendar.MINUTE
                dialog.dismiss()

            }

        }

        return txt
    }


    fun textColor(tv1: TextView, tv2: TextView, tv3: TextView, tv4: TextView, tv5: TextView) {

        tv1.setBackgroundColor(Color.parseColor("#FFC107")) // Yellow

        // Black
        tv2.setBackgroundColor(Color.parseColor("#000000"))
        tv3.setBackgroundColor(Color.parseColor("#000000"))
        tv4.setBackgroundColor(Color.parseColor("#000000"))
        tv5.setBackgroundColor(Color.parseColor("#000000"))

    }

    private fun videoPlay() {
        val ctlr = MediaController(context)
        ctlr.setMediaPlayer(wake_up_video)
//        wake_up_video.setMediaController(ctlr)

        val uri =  Uri.parse("android.resource://" + context?.getPackageName() + "/R.raw/" + R.raw.moonset);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.com/hearts.mp4");

//                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.lop);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.com/hearts.mp4");
//        wake_up_video.setMediaController(ctlr)

        //        videoView.setVideoURI(uri);

        wake_up_video.setVideoURI(uri);
//        tts_vids.setVideoPath("https://invoiz-assets.s3.amazonaws.com/hearts.mp4")
        wake_up_video.start()

    }

    private fun setGear(
        image1: ImageView,
        image2: ImageView,
        image3: ImageView,
        image4: ImageView,
        image5: ImageView
    ){


    }
}




