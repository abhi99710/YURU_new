package com.app.yuru.ui.transition

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.corescheduler.player.video.ui.VideoActivity
import com.app.yuru.corescheduler.utils.Constants
import com.app.yuru.ui.coupons.Journals
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class WakeUpProgram : Fragment() , TimePickerDialog.OnTimeSetListener{


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
    private var fileURL: MutableList<String> = ArrayList()
    private var thumb: MutableList<String> = ArrayList()
    private var durationL: MutableList<String> = ArrayList()
    private var traits: MutableList<String> = ArrayList()
    private var fileName: MutableList<String> = ArrayList()

    private lateinit var cl_wakeup_female : ConstraintLayout
    private lateinit var cl_wakeup_male : ConstraintLayout

    private lateinit var wake_up_video : VideoView

//    private var

    private val alarmTimePicker: TimePicker? = null

    private var clickedGender = ""

    private lateinit var e_new : ImageView
    private lateinit var a_new : ImageView
    private lateinit var n_new : ImageView
    private lateinit var o_new : ImageView
    private lateinit var c_new : ImageView

    var day = 0
    var month: Int = 0
    var year: Int = 0

    private lateinit var progressDialog2 : ProgressDialog


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


            val dialog =  Dialog(requireContext())
            if (dialog != null) {
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.dialogshow_options)
                dialog.show()

                var back_dialog_wake : ImageView = dialog.findViewById(R.id.back_dialog_wake)
                back_dialog_wake.setOnClickListener {
                    dialog.dismiss()
                }

                val dialog_ok : TextView = dialog.findViewById<TextView>(R.id.dialog_ok)
                dialog_ok.setOnClickListener{
                    dialog.dismiss()
                }

//                val recyclerView: RecyclerView = dialog.findViewById(R.id.recyclerNewSleep);

                var showval = ""

                val friday4: CheckBox = dialog.findViewById(R.id.friCheck)
                val sunday: CheckBox = dialog.findViewById(R.id.sunCheck)
                val monDay: CheckBox = dialog.findViewById(R.id.monCheck)
                val tuesDay: CheckBox = dialog.findViewById(R.id.tueCheck)
                val wedsnesDay: CheckBox = dialog.findViewById(R.id.wedCheck)
                val thursDay: CheckBox = dialog.findViewById(R.id.thuCheck)
                val saturDay: CheckBox = dialog.findViewById(R.id.satCheck)
                val everyDay: CheckBox = dialog.findViewById(R.id.everyCheck)
                val never: CheckBox = dialog.findViewById(R.id.neverCheck)

                checkClickFunctionality(
                    everyDay,
                    monDay,
                    tuesDay,
                    wedsnesDay,
                    thursDay,
                    friday4,
                    saturDay,
                    sunday,
                    never,
                    dayOfAlarm,
                    showval
                )


            }
        }

        time_tv.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)


           val mHour = calendar.get(Calendar.HOUR_OF_DAY);
          val mMinute = calendar.get(Calendar.MINUTE);
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)

                time_tv.text = SimpleDateFormat("HH:mm").format(calendar.time)
            }

            time_tv.setOnClickListener {
                TimePickerDialog.THEME_DEVICE_DEFAULT_DARK
                TimePickerDialog(
                    context, R.style.MyTimePickerDialogTheme, timeSetListener, calendar.get(
                        Calendar.HOUR_OF_DAY
                    ), calendar.get(Calendar.MINUTE), true
                ).show()


            }



        }


        allClickListner()


        videoPlay()

        apiVideos("45sec", "O")

        return view
    }

    private fun checkClickFunctionality(
        everyDay: CheckBox,
        monDay: CheckBox,
        tuesDay: CheckBox,
        wedsnesDay: CheckBox,
        thursDay: CheckBox,
        friday4: CheckBox,
        saturDay: CheckBox,
        sunday: CheckBox,
        never: CheckBox,
        dayOfAlarm: TextView,
        showval: String
    ) {
        var showval1 = showval
        everyDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                monDay.isChecked = true
                tuesDay.isChecked = true
                wedsnesDay.isChecked = true
                thursDay.isChecked = true
                friday4.isChecked = true
                saturDay.isChecked = true
                sunday.isChecked = true
                everyDay.isChecked = true
                never.isChecked = false

                dayOfAlarm.text = "everyday"
            }
        }

        never.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                monDay.isChecked = false
                tuesDay.isChecked = false
                wedsnesDay.isChecked = false
                thursDay.isChecked = false
                friday4.isChecked = false
                saturDay.isChecked = false
                sunday.isChecked = false
                everyDay.isChecked = false
                never.isChecked = true
                dayOfAlarm.text = "never"

            }
        }

        sunday.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sunday.isChecked = true
                never.isChecked = false
                if (showval1.equals("")) {
                    showval1 = "sun  "
                } else {
                    showval1 = showval1 + ",sun "
                }
                dayOfAlarm.text = showval1
            }
        }

        monDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                monDay.isChecked = true
                never.isChecked = false

                if (showval1.equals("")) {
                    showval1 = "mon"
                } else {
                    showval1 = showval1 + ",mon "
                }

                dayOfAlarm.text = showval1
            }
        }

        tuesDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                tuesDay.isChecked = true
                never.isChecked = false

                if (showval1.equals("")) {
                    showval1 = "tue"
                } else {
                    showval1 = showval1 + ",tue "
                }

                dayOfAlarm.text = showval1
            }
        }

        wedsnesDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                wedsnesDay.isChecked = true
                never.isChecked = false

                if (showval1.equals("")) {
                    showval1 = "wed"
                } else {
                    showval1 = showval1 + ",wed "
                }

                dayOfAlarm.text = showval1
            }
        }

        thursDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                thursDay.isChecked = true
                never.isChecked = false

                if (showval1.equals("")) {
                    showval1 = "thu"
                } else {
                    showval1 = showval1 + ",thu "
                }

                dayOfAlarm.text = showval1
            }
        }

        friday4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                friday4.isChecked = true
                never.isChecked = false

                if (showval1.equals("")) {
                    showval1 = "fri"
                } else {
                    showval1 = showval1 + ",fri "
                }

                dayOfAlarm.text = showval1
            }
        }

        saturDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saturDay.isChecked = true
                never.isChecked = false

                if (showval1.equals("")) {
                    showval1 = "sat"
                } else {
                    showval1 = showval1 + ",sat "
                }

                dayOfAlarm.text = showval1
            }
        }
    }

    private fun allClickListner() {

        female.setOnClickListener {

            male.setTextColor(Color.GRAY)
            female.setTextColor(Color.WHITE)

            clickedGender = "90sec"


        }

        male.setOnClickListener {

            female.setTextColor(Color.GRAY)
            male.setTextColor(Color.WHITE)

            clickedGender = "45sec"

        }


        save_wakeup.setOnClickListener {

            val intent = Intent(context, Journals::class.java)
            startActivity(intent)
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
            if (clickedGender.equals("45sec")) {
                apiVideos("45sec", "O")
            } else {
                apiVideos("90sec", "O")
            }
            e_new.setImageResource(R.drawable.setting_o)
            o_new.setImageResource(R.drawable.setting_a)
            c_new.setImageResource(R.drawable.setting_n)
            a_new.setImageResource(R.drawable.setting_c)
            n_new.setImageResource(R.drawable.setting_e)
        })

        c_new.setOnClickListener {
            if(clickedGender.equals("45sec")){
                apiVideos("45sec", "C")
            }else{
                apiVideos("90sec", "C")
            }
            e_new.setImageResource(R.drawable.setting_c)
            o_new.setImageResource(R.drawable.setting_n)
            c_new.setImageResource(R.drawable.setting_o)
            a_new.setImageResource(R.drawable.setting_e)
            n_new.setImageResource(R.drawable.setting_a)
        }

        e_new.setOnClickListener {
            if(clickedGender.equals("45sec")){
                apiVideos("45sec", "E")
            }else{
                apiVideos("90sec", "E")
            }
            e_new.setImageResource(R.drawable.setting_e)
            o_new.setImageResource(R.drawable.setting_o)
            c_new.setImageResource(R.drawable.setting_c)
            a_new.setImageResource(R.drawable.setting_a)
            n_new.setImageResource(R.drawable.setting_n)
        }

        a_new.setOnClickListener {
            if(clickedGender.equals("45sec")){
                apiVideos("45sec", "A")
            }else{
                apiVideos("90sec", "A")
            }
            e_new.setImageResource(R.drawable.setting_a)
            o_new.setImageResource(R.drawable.setting_c)
            c_new.setImageResource(R.drawable.setting_e)
            a_new.setImageResource(R.drawable.setting_n)
            n_new.setImageResource(R.drawable.setting_o)
        }

        n_new.setOnClickListener {
            if(clickedGender.equals("45sec")){
                apiVideos("45sec", "N")
            }else{
                apiVideos("90sec", "N")
            }
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


//    private fun go() {
//
//        val SimpleDateFormat = SimpleDateFormat("HH:mm:ss")
//
//        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
//        val calendar = Calendar.getInstance()
//        val calList: MutableList<Calendar> = ArrayList()
//
//        for (i in 0..4) {
//            calList.add(calendar)
//        }
//
//        val stringBuilder = ""
//
//        for (calItem in calList) {
//            calItem.add(Calendar.SECOND, 10)
//
//            val requestCode = (calendar.timeInMillis / 1000).toInt()
//            val intent = Intent(context, MyReceiver::class.java)
//            intent.putExtra("REQUEST_CODE", requestCode)
//            intent.putExtra("fragment", "wakeup")
//            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
//            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
//
//            val pi = PendingIntent.getBroadcast(context, requestCode, intent, 0)
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                alarmManager?.setExactAndAllowWhileIdle(
//                    AlarmManager.RTC_WAKEUP,
//                    calItem.timeInMillis,
//                    pi
//                )
//            } else {
//                alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calItem.timeInMillis, pi)
//            }
//
//
//
//            Toast.makeText(context, "Alarm has been set : \n " + stringBuilder, Toast.LENGTH_SHORT)
//                .show()
//
//        }
//    }


    private fun apiVideos(duration: String, trait: String) {

        val sh: SharedPreferences = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/getAllWakeUpVideosByFilter"
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
        process.show()

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    process.dismiss()
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    val jsonArray = jsonObject.getJSONArray("data")

                    id1Male.clear()
                    fileName.clear()
                    traits.clear()
                    durationL.clear()
                    thumb.clear()
                    fileURL.clear()

                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)


                        id1Male.add(jsonObject1.getString("id"))
                        fileName.add(jsonObject1.getString("fileName"))
                        traits.add(jsonObject1.getString("traits"))
                        durationL.add(jsonObject1.getString("duration"))
                        thumb.add(jsonObject1.getString("thumb"))
                        fileURL.add(jsonObject1.getString("fileURL"))

                    }

                    adapterConnects()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("duration", duration)
                params.put("trait", trait)
                params.put("userId", ids1.toString());

                return params
            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun adapterConnects() {
        val adapterMain = AdapterMain(
            context,
            id1Male,
            fileName,
            traits,
            durationL,
            thumb,
            fileURL
        )
        wakeuprecy?.adapter = adapterMain

        wakeuprecy?.setOnItemClickListener { parent, view, position, id ->

            apiListVideos(id1Male.get(position), fileURL.get(position))

            progressDialog2 = ProgressDialog(requireContext())
            progressDialog2.setCancelable(false)
            progressDialog2.setMessage("Loading...")
            progressDialog2.show()
//            val intent = Intent(context, VideoActivity::class.java)
//                intent.putExtra(
//                    Constants.VIDEO_LINK,
//                    fileURL.get(position)
//                )
//
//            startActivity(intent)
        }
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val myHour = hourOfDay
        val myMinute = minute
        time_tv.text = "$myHour : $myMinute"
    }


    private fun videoPlay() {
        val ctlr = MediaController(context)
        ctlr.setMediaPlayer(wake_up_video)

        val uri =  Uri.parse("android.resource://" + context?.getPackageName() + "/R.raw/" + R.raw.moonset);
        wake_up_video.setVideoURI(uri);
        wake_up_video.start()

        wake_up_video.setOnPreparedListener({ mp -> mp.isLooping = true })

    }

    fun apiListVideos(idaa: String, fileUrl: String) {
        val url = "https://app.whyuru.com/api/web/getAllSubPartByID"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response: String? ->
                try {
                    val jsonObject = JSONObject(response)
                    progressDialog2.dismiss()
                    val jsonObject1 = jsonObject.getJSONObject("result")
                    val jsonArray = jsonObject1.getJSONArray("data")

                    for (i in 0 until jsonArray.length()) {
                        var jsonObject2 = jsonArray.getJSONObject(i)
                        idCLickDialog(
                            fileUrl, jsonObject2.getString("thumb"), jsonObject2.getString(
                                "sub1URL"
                            ),
                            jsonObject2.getString("sub2URL"), jsonObject2.getString("sub3URL")
                        )
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError? ->
                Toast.makeText(
                    context,
                    "Server Problem",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            override fun getParams(): Map<String, String>? {
                val map: MutableMap<String, String> = HashMap()
                map.put("mainID", idaa)
                return map
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    private fun idCLickDialog(url1: String, thumb: String, sub1: String, sub2: String, sub3: String) {
        val dialog = Dialog(requireContext())
        //                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogwakeupandevening)
        val image1 = dialog.findViewById<ImageView>(R.id.imageView16)
        val image2 = dialog.findViewById<ImageView>(R.id.imageView17)
        val image3 = dialog.findViewById<ImageView>(R.id.imageView18)
        val image4 = dialog.findViewById<ImageView>(R.id.imageView14)

        Picasso.get().load(thumb).centerCrop().noFade().fit().into(image4)

        image1.setOnClickListener {

            val intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(
                Constants.VIDEO_LINK,
                sub1
            )
            requireContext().startActivity(intent)
        }

        image2.setOnClickListener {

            val intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(
                Constants.VIDEO_LINK,
                sub2
            )
            requireContext().startActivity(intent)
        }

        image3.setOnClickListener {

            val intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(
                Constants.VIDEO_LINK,
                sub3
            )
            requireContext().startActivity(intent)
        }

        image4.setOnClickListener {

            val intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(
                Constants.VIDEO_LINK,
                url1
            )
            requireContext().startActivity(intent)
        }

        val dialogButton = dialog.findViewById<ImageView>(R.id.canceldialog)
        dialogButton.setOnClickListener { v1: View? -> dialog.dismiss() }
        dialog.show()
    }

}




