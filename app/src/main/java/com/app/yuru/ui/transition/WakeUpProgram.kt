package com.app.yuru.ui.transition

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.corescheduler.player.video.ui.VideoActivity
import com.app.yuru.corescheduler.utils.Constants
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class WakeUpProgram : Fragment(), TimePickerDialog.OnTimeSetListener, ClickInterface,
    LongPressSleep2 {


    var wakeuprecy: GridView? = null
    private var requestQueue: RequestQueue? = null
    val sec = 0
    var hours = 0
    var minutes = 0

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

    private lateinit var cl_wakeup_female: ConstraintLayout
    private lateinit var cl_wakeup_male: ConstraintLayout

    private lateinit var wake_up_video: VideoView
    private lateinit var dayOfAlarm: TextView

//    private var

    private val alarmTimePicker: TimePicker? = null

    private var clickedGender = "Male"
    private var clickedVideoTime = "45sec"

    private lateinit var e_new: ImageView
    private lateinit var a_new: ImageView
    private lateinit var n_new: ImageView
    private lateinit var o_new: ImageView
    private lateinit var c_new: ImageView

    private lateinit var playerView11: PlayerView
    private lateinit var clExoPlayer: ConstraintLayout
    private lateinit var closebtndialog1: ImageView
    private var newUrl1: String = ""

    private var id45: MutableList<String> = ArrayList()
    private var traint45: MutableList<String> = ArrayList()
    private var duration45: MutableList<String> = ArrayList()
    private var thumb45: MutableList<String> = ArrayList()
    private var url45: MutableList<String> = ArrayList()

    var day = 0
    var month: Int = 0
    var year: Int = 0

    private lateinit var playerView1: PlayerView
    var player: SimpleExoPlayer? = null
    var newUrl: String = "0"
    var isFull: String = ""

    lateinit var closeID: ImageView

    var timeNew: Int = 5
    var choosedTraits = "O"
    private var clickedUrl = "0"
    private var videoType = "sub"
    private var chooseWakeupVideotime = ""
    private var chooseRepeatDays = ""

    private var clickedImageUrl = ""

    lateinit var exo_fullscreen_icon: ImageView

    private lateinit var progressDialog2: ProgressDialog

    private lateinit var recyclerView: RecyclerView

    private var choosedProgram = ""
    private var choosedUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_wake_up_program, container, false)

        cl_wakeup_male = view.findViewById(R.id.cl_wakeup_male)
        cl_wakeup_female = view.findViewById(R.id.cl_wakeup_female)

        findIds(view)
//        showDialog("O","male")
//        apiVideos("male", "O")

        setData()


        exo_fullscreen_icon = view.findViewById(R.id.exo_fullscreen_icon)
        exo_fullscreen_icon.setOnClickListener {
            if (isFull.equals("yes")) {
                player?.pause()
                closeID.visibility = View.INVISIBLE
                playerView1.visibility = View.INVISIBLE
                exo_fullscreen_icon.visibility = View.INVISIBLE

                val intent = Intent(context, VideoActivity::class.java)
                intent.putExtra(
                    Constants.VIDEO_LINK,
                    newUrl
                )
                requireContext().startActivity(intent)
            }
        }

//        apiVideosLast("45sec", "O")

        savewakeUpHits()

        getWakeUpDataSaver()

        getWakeupForProgram()
//        go(timeNew - 45 /*currentTimeToLong().toInt()*/)


        playerView1 = view.findViewById(R.id.playerViewwakeup)
        closeID = view.findViewById(R.id.closeIDwakeup)

        closeID.setOnClickListener {
            player?.pause()
            closeID.visibility = View.INVISIBLE
            playerView1.visibility = View.INVISIBLE
            exo_fullscreen_icon.visibility = View.INVISIBLE
//            playerView11.visibility = View.INVISIBLE
            isFull = "no"
        }

         dayOfAlarm = view.findViewById(R.id.dayOfAlarm)
        dayOfAlarm.setOnClickListener {


            val dialog = Dialog(requireContext())
            if (dialog != null) {
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.dialogshow_options)
                dialog.show()

                var back_dialog_wake: ImageView = dialog.findViewById(R.id.back_dialog_wake)
                back_dialog_wake.setOnClickListener {
                    dialog.dismiss()
                }

                val dialog_ok: TextView = dialog.findViewById<TextView>(R.id.dialog_ok)
                dialog_ok.setOnClickListener {
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


            val mHour = calendar.get(Calendar.HOUR_OF_DAY)
            val mMinute = calendar.get(Calendar.MINUTE)
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)

                time_tv.text = SimpleDateFormat("HH:mm").format(calendar.time)
                var timeX = SimpleDateFormat("HH:mm").format(calendar.time)
//                timeNew = Integer.parseInt(timeX)

                hours = hour
                minutes = minute
                timeNew = toMins(timeX)



//                currentTimeToLong()

//                go(timeNew - currentTimeToLong().toInt())
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



        return view
    }

    private fun setData() {
        id1Male.add("1")
        fileName.add("Program1")
        traits.add("")
        durationL.add("")
        thumb.add("https://app.whyuru.com/assets/uploads/wakeUp/screen_1665407723.png")
        fileURL.add("Program 1")

        id1Male.add("2")
        fileName.add("Program2")
        traits.add("")
        durationL.add("")
        thumb.add("https://app.whyuru.com/assets/uploads/wakeUp/screen_1665407868.png")
        fileURL.add("Program 2")

        id1Male.add("3")
        fileName.add("Program3")
        traits.add("")
        durationL.add("")
        thumb.add("https://app.whyuru.com/assets/uploads/wakeUp/screen_1665407774.png")
        fileURL.add("Program 3")

        id1Male.add("4")
        fileName.add("Program4")
        traits.add("")
        durationL.add("")
        thumb.add("https://app.whyuru.com/assets/uploads/wakeUp/screen_1665407845.png")
        fileURL.add("Program 4")
        adapterConnects()
    }

    private fun toMins(s: String): Int {
        val hourMin: Array<String> = s.split(":").toTypedArray()
        val hour: Int = hourMin[0].toInt()
        val mins: Int = hourMin[1].toInt()
        val hoursInMins = hour * 60
        return hoursInMins + mins
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
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
                chooseRepeatDays = "everyday"
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
                chooseRepeatDays = "never"

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
                chooseRepeatDays = showval
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
                chooseRepeatDays = showval
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
                chooseRepeatDays = showval
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
                chooseRepeatDays = showval
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
                chooseRepeatDays = showval
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
                chooseRepeatDays = showval
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
                chooseRepeatDays = showval
            }
        }
    }

    private fun allClickListner() {

        female.setOnClickListener {

            male.setTextColor(Color.GRAY)
            female.setTextColor(Color.WHITE)

            clickedGender = "female"


        }

        male.setOnClickListener {

            female.setTextColor(Color.GRAY)
            male.setTextColor(Color.WHITE)

            clickedGender = "male"

        }


        save_wakeup.setOnClickListener {

            wakeUpDataSaver()

            var minute_new = ((hours * 60 ) + minutes)*60*1000
//            go( minute_new , clickedUrl)

            // setting first alarm
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hours)
            calendar.set(Calendar.MINUTE, minutes)
            calendar.set(Calendar.SECOND, 0)

            val requestCode = (calendar.timeInMillis / 1000).toInt()
            val intent = Intent(context, MyReceiver::class.java)
            intent.putExtra("REQUEST_CODE", requestCode)
            intent.putExtra("fragment", "wakeup")
            intent.putExtra("url", clickedUrl)
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

            val pi = PendingIntent.getBroadcast(context, requestCode, intent, 0)

            alarmManager?.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pi
            )

            // setting second alarm
            val alarmManager1 = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            val calendar1 = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hours)
            calendar.set(Calendar.MINUTE, minutes - 4)
            calendar.set(Calendar.SECOND, 0)

            val requestCode1 = (calendar.timeInMillis / 1000).toInt()
            val intent1 = Intent(context, MyReceiver::class.java)
            intent1.putExtra("REQUEST_CODE", requestCode1)
            intent1.putExtra("fragment", "wakeup")
            intent1.putExtra("url", choosedUrl)
            intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent1.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

            val pi1 = PendingIntent.getBroadcast(context, requestCode1, intent1, 0)

            alarmManager1?.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pi1
            )


//            var sleep2alarm = (timeNew) - (  currentTimeToLong().toInt()/(1000*60))
//            Log.e("time", ""+ timeNew + "  **  " + (TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())).toInt() + " ** " + sleep2alarm)
//                        Toast.makeText(context, " "+ sleep2alarm + " " + timeNew +  "--" + , Toast.LENGTH_SHORT).show()

//            go((((hours * 60 ) + minutes)-1)*60*1000, newUrl)

//            val intent = Intent(context, MainRocket::class.java)
//            intent.putExtra("first_rocket", "wakeup");
//            startActivity(intent)
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

        clickOnO()  // click on icon O

        cliclOnC()  // click on icon C

        cliclOnE()  // click on icon E

        clickOnA()  // click on icon A

        cliclOnN()  // click on icon N

    }

    private fun cliclOnN() {
        n_new.setOnClickListener {

            choosedTraits = "N"

            if (clickedGender.equals("male")) {
    //                apiVideos("male", "N")
    //                apiVideosLast("45sec", "N")
                showDialog("N", "male")

            } else {
    //                apiVideos("female", "N")
                showDialog("N", "female")
    //                apiVideosLast("90sec", "N")

            }
            chnageDimension(   n_new, o_new, c_new ,  e_new, a_new)
//            e_new.setImageResource(R.drawable.setting_n)
//            o_new.setImageResource(R.drawable.setting_e)
//            c_new.setImageResource(R.drawable.setting_a)
//            a_new.setImageResource(R.drawable.setting_o)
//            n_new.setImageResource(R.drawable.setting_c)
        }
    }

    private fun clickOnA() {
        a_new.setOnClickListener {
            choosedTraits = "A"
            if (clickedGender.equals("male")) {
    //                apiVideos("male", "A")
                showDialog("A", "male")

            } else {
    //                apiVideos("female", "A")
                showDialog("A", "female")

            }
            chnageDimension(  a_new, n_new, o_new, c_new ,  e_new)
//            e_new.setImageResource(R.drawable.setting_a)
//            o_new.setImageResource(R.drawable.setting_c)
//            c_new.setImageResource(R.drawable.setting_e)
//            a_new.setImageResource(R.drawable.setting_n)
//            n_new.setImageResource(R.drawable.setting_o)
        }
    }

    private fun cliclOnE() {
        e_new.setOnClickListener {
            choosedTraits = "E"
            if (clickedGender.equals("male")) {
    //                apiVideos("male", "E")
                showDialog("E", "male")

            } else {
    //                apiVideos("female", "E")
                showDialog("E", "female")

            }
            chnageDimension(  e_new, a_new, n_new, o_new, c_new )
//            e_new.setImageResource(R.drawable.setting_e)
//            o_new.setImageResource(R.drawable.setting_o)
//            c_new.setImageResource(R.drawable.setting_c)
//            a_new.setImageResource(R.drawable.setting_a)
//            n_new.setImageResource(R.drawable.setting_n)
        }
    }

    private fun cliclOnC() {
        c_new.setOnClickListener {

            choosedTraits = "C"

            if (clickedGender.equals("male")) {
    //                apiVideos("male", "C")
                showDialog("C", "male")

            } else {
    //                apiVideos("female", "C")
                showDialog("C", "female")

            }

            chnageDimension( c_new, e_new, a_new, n_new, o_new )
//            e_new.setImageResource(R.drawable.setting_c)
//            o_new.setImageResource(R.drawable.setting_n)
//            c_new.setImageResource(R.drawable.setting_o)
//            a_new.setImageResource(R.drawable.setting_e)
//            n_new.setImageResource(R.drawable.setting_a)
        }
    }

    private fun clickOnO() {
        o_new.setOnClickListener({

            choosedTraits = "O"
            if (clickedGender.equals("male")) {
                //                apiVideos("male", "O")
                showDialog("O", "male")
            } else {
                //                apiVideos("female", "O")
                showDialog("O", "female")

            }

            chnageDimension(o_new, c_new, e_new, a_new, n_new)

            //            e_new.setImageResource(R.drawable.setting_o)
//            o_new.setImageResource(R.drawable.setting_a)
//            c_new.setImageResource(R.drawable.setting_n)
//            a_new.setImageResource(R.drawable.setting_c)
//            n_new.setImageResource(R.drawable.setting_e)
        })
    }

    private fun chnageDimension(imageView : ImageView, imageView1 : ImageView, imageView2 : ImageView, imageView3 : ImageView, imageView4 : ImageView){
        val layoutParams = LinearLayout.LayoutParams(200, 200)
        layoutParams.marginStart = 40
        imageView.setLayoutParams(layoutParams)

        val layoutParams1 = LinearLayout.LayoutParams(150, 150)
        layoutParams1.marginStart = 40
        imageView1.setLayoutParams(layoutParams1)
        imageView2.setLayoutParams(layoutParams1)
        imageView3.setLayoutParams(layoutParams1)
        imageView4.setLayoutParams(layoutParams1)

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

    private fun go(min: Int, url: String) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val calendar = Calendar.getInstance()
        val calList: MutableList<Calendar> = ArrayList()
        calList.add(calendar)
        for (calItem in calList) {

            calItem.add(Calendar.MINUTE, min)


            val requestCode = (calendar.timeInMillis / 1000).toInt()
            val intent = Intent(context, MyReceiver::class.java)
            intent.putExtra("REQUEST_CODE", requestCode)
            intent.putExtra("fragment", "wakeup")
            intent.putExtra("url", url)
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

            val pi = PendingIntent.getBroadcast(context, requestCode, intent, 0)

            Log.e("time1", "" + calItem.timeInMillis)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager?.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calItem.timeInMillis,
                    pi
                )

//                alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP,
//                    calItem.timeInMillis,
//                    24*60*60*1000,
//                    pi)
            } else {
                alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calItem.timeInMillis, pi)
//                alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP,
//                    calItem.timeInMillis,
//                    24*60*60*1000,
//                    pi)
            }
            Toast.makeText(context, "Alarm has been set", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun apiVideos(duration: String, trait: String) {

        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

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
                        durationL.add(jsonObject1.getString("gender"))
                        thumb.add(jsonObject1.getString("thumb"))
                        fileURL.add(jsonObject1.getString("fileURL"))

                        /*               id1Male.add(jsonObject1.getString("id"))
                                       fileName.add(jsonObject1.getString("fileName"))
                                       traits.add(jsonObject1.getString("traits"))
                                       durationL.add(jsonObject1.getString("gender"))
                                       thumb.add(jsonObject1.getString("thumb"))
                                       fileURL.add(jsonObject1.getString("fileURL"))*/

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
                params.put("gender", duration)
                params.put("trait", trait)
                params.put("userId", ids1.toString())

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
            fileURL,
            this,
            choosedProgram
        )
        wakeuprecy?.adapter = adapterMain

        wakeuprecy?.setOnItemClickListener { parent, view, position, id ->

            apiListVideos(id1Male.get(position), fileURL.get(position))

            progressDialog2 = ProgressDialog(requireContext())
            progressDialog2.setCancelable(false)
            progressDialog2.setMessage("Loading...")
            progressDialog2.show()

            choosedProgram = fileName.get(position)
            val sharedPreferences: SharedPreferences =
                requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                    Global.ids1 = jsonObject1.getString("id")
            editor.putString("wakeup_choosedProgram", "" + choosedProgram)
//                editor.putString("se_time2", "" + annserForRight)

            editor.apply()
            editor.commit()

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

        val uri =
            Uri.parse("android.resource://" + context?.packageName + "/R.raw/" + R.raw.moonset)
        wake_up_video.setVideoURI(uri)
        wake_up_video.start()

        wake_up_video.setOnPreparedListener({ mp -> mp.isLooping = true })

    }

    fun apiListVideos(idaa: String, fileUrl: String) {
//        val url = "https://app.whyuru.com/api/web/getAllSubPartByID"

        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/getwakeVideos"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response: String? ->
                try {
                    val jsonObject = JSONObject(response)
                    savewakeUp_subpartHits()
                    progressDialog2.dismiss()
                    val jsonObject1 = jsonObject.getJSONObject("result")
                    val jsonArray = jsonObject1.getJSONArray("data")



                    for (i in 0 until jsonArray.length()) {
                        if (i == 0) {
                            var jsonObject2 = jsonArray.getJSONObject(i)

                            // open dialog on main videos on wakeup
                            idCLickDialog(
                                fileUrl, jsonObject2.getString("thumb1"), jsonObject2.getString(
                                    "sub1URL"
                                ),
                                jsonObject2.getString("sub2URL"), jsonObject2.getString("sub3URL")
                            )
                        }
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
                map.put("program", fileUrl)  // name of file
                map.put("gender", clickedGender)
                map.put("userID", ids1.toString())
                return map
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    private fun idCLickDialog(
        url1: String,
        thumb: String,
        sub1: String,
        sub2: String,
        sub3: String
    ) {
        val dialog = Dialog(requireContext())
        //                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogwakeupandevening)
        val image1 = dialog.findViewById<ImageView>(R.id.imageView16)
        val image2 = dialog.findViewById<ImageView>(R.id.imageView17)
        val image3 = dialog.findViewById<ImageView>(R.id.imageView18)
        val image4 = dialog.findViewById<ImageView>(R.id.imageView14)

        Picasso.get().load(thumb).centerCrop().noFade().fit().into(image4)
        Picasso.get().load(thumb).centerCrop().noFade().fit().into(image1)
        Picasso.get().load(thumb).centerCrop().noFade().fit().into(image2)
        Picasso.get().load(thumb).centerCrop().noFade().fit().into(image3)

        clickedImageUrl = thumb

        image1.setOnClickListener {

            clickedUrl = sub1
            videoType = "sub"
            newUrl = sub1
            playerView1.visibility = View.VISIBLE
            closeID.visibility = View.VISIBLE
            exo_fullscreen_icon.visibility = View.VISIBLE

            click()
            dialog.dismiss()
//            if(isFull.equals("yes")) {
//                val intent = Intent(context, VideoActivity::class.java)
//                intent.putExtra(
//                    Constants.VIDEO_LINK,
//                    sub1
//                )
//                requireContext().startActivity(intent)
//            }
        }

        image2.setOnClickListener {
            clickedUrl = sub2
            videoType = "sub"

            newUrl = sub2
            playerView1.visibility = View.VISIBLE
            closeID.visibility = View.VISIBLE
            exo_fullscreen_icon.visibility = View.VISIBLE
            click()
            dialog.dismiss()
//            if(isFull.equals("yes")) {
//                val intent = Intent(context, VideoActivity::class.java)
//                intent.putExtra(
//                    Constants.VIDEO_LINK,
//                    sub2
//                )
//                requireContext().startActivity(intent)
//            }
        }

        image3.setOnClickListener {

            clickedUrl = sub3
            videoType = "sub"

            newUrl = sub3
            playerView1.visibility = View.VISIBLE
            closeID.visibility = View.VISIBLE
            exo_fullscreen_icon.visibility = View.VISIBLE

            dialog.dismiss()
            click()

        }

        image4.setOnClickListener {
            clickedUrl = url1
            videoType = "main"

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

    override fun urlGet(url: String?, imageUrl: String) {
        newUrl = url.toString()
        newUrl1 = url.toString()
        playerView1.visibility = View.VISIBLE
        closeID.visibility = View.VISIBLE
        exo_fullscreen_icon.visibility = View.VISIBLE
        isFull = "yes"
        clExoPlayer.visibility = View.VISIBLE
        click()

//        clickedImageUrl = imageUrl
    }

    private fun click() {

        val mediaItem: MediaItem = newUrl.let { MediaItem.fromUri(it) }
        player = SimpleExoPlayer.Builder(requireContext()).build().also {
            playerView1.player = it

//            playerView1.hideController()
//            playerView1.setControllerVisibilityListener {
//                if(it == View.VISIBLE){
//                    playerView1.hideController()
//                }
//            }

//            playerView1.setF
            it.setMediaItem(mediaItem)
            // Prepare the player.
            it.prepare()
            // Start the playback.
            it.play()

            player?.volume = 10f

        }


    }


    private fun apiVideosLast(duration: String, trait: String) {

        val sh: SharedPreferences = requireActivity().getSharedPreferences(
            "share",
            Context.MODE_PRIVATE
        )

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/getAllEveningProgram"
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

                    id45.clear()
                    url45.clear()
                    duration45.clear()
                    traint45.clear()
                    thumb45.clear()

                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)

                        id45.add(jsonObject1.getString("id"))
                        url45.add(jsonObject1.getString("fileURL"))
                        thumb45.add(jsonObject1.getString("thumb"))
                        duration45.add(jsonObject1.getString("duration"))
                        traint45.add(jsonObject1.getString("traits"))

                    }

                    val adapterSleep1 = AdapterSleep(
                        requireActivity(),
                        id45,
                        traint45,
                        thumb45,
                        duration45,
                        url45,
                        this,
                        this,
                        "o"
                    )
                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = GridLayoutManager(context, 2)
                    recyclerView.adapter = adapterSleep1

//                    showDialog("O", "male")

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    process.dismiss()
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("duration", duration)
                params.put("trait", trait)
                params.put("userId", ids1.toString())

                return params
            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun showDialog(title: String, gender: String) {
        val dialog = context?.let { Dialog(it, android.R.style.Theme_Holo_Light) }
        if (dialog != null) {
            var traits = "O"
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialogtransition)
            dialog.show()

             recyclerView = dialog.findViewById(R.id.recyclerNewSleep)

            apiVideosLast("45sec", title)
            val adapterSleep1 = AdapterSleep(
                requireActivity(),
                id45,
                traint45,
                thumb45,
                duration45,
                url45,
                this,
                this,
                "o"
            )
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.adapter = adapterSleep1


            val dialog_title: TextView = dialog.findViewById(R.id.dialog_title)
            dialog_title.text = title

            playerView1 = dialog.findViewById(R.id.playerView1)


            clExoPlayer = dialog.findViewById(R.id.clExoPlayer)
            closebtndialog1 = dialog.findViewById(R.id.closebtndialog1)
            closebtndialog1.setOnClickListener {
                clExoPlayer.visibility = View.INVISIBLE
                playerView1.onPause()
                playerView1.setKeepContentOnPlayerReset(true)
                player?.pause()
//                playerView11.onPause()
//                playerView11.visibility = View.INVISIBLE
                exo_fullscreen_icon.visibility = View.INVISIBLE
                closeID.visibility = View.INVISIBLE


            }


            click()


            val logo: ImageView = dialog.findViewById(R.id.logo)

            when (title) {
                "E" -> {
                    traits = "E"
                    logo.setImageResource(R.drawable.setting_e)
                    dialog_title.setTextColor(Color.parseColor("#FF0000"))
                    apiVideosLast("45dec", "E")

                }
                "A" -> {
                    traits = "A"
                    logo.setImageResource(R.drawable.setting_a)
                    dialog_title.setTextColor(Color.parseColor("#F9CA14"))

                    apiVideosLast("45dec", "A")
                }

                "N" -> {
                    traits = "N"
                    logo.setImageResource(R.drawable.setting_n)
                    dialog_title.setTextColor(Color.parseColor("#808080"))
                    apiVideosLast("45dec", "N")

                }
                "O" -> {
                    traits = "O"
                    logo.setImageResource(R.drawable.setting_o)
                    dialog_title.setTextColor(Color.parseColor("#008000"))

                    apiVideosLast("45dec", "O")
                }
                "C" -> {
                    traits = "C"
                    logo.setImageResource(R.drawable.setting_c)
                    dialog_title.setTextColor(Color.parseColor("#0000FF"))
                    apiVideosLast("45dec", "C")

                }
            }
//            logo.setImageDrawable(iview)

            val cl90: ConstraintLayout = dialog.findViewById(R.id.cl90)
            val cl45: ConstraintLayout = dialog.findViewById(R.id.cl45)

            val tv45: TextView = dialog.findViewById(R.id.textView7)
            val tv90: TextView = dialog.findViewById(R.id.program)

            val adapterSleep = AdapterSleep(
                requireActivity(),
                id45,
                traint45,
                thumb45,
                duration45,
                url45,
                this,
                this,
                "o"
            )

            cl45.setOnClickListener {

                clickedVideoTime = "45sec"
                apiVideosLast("45dec", traits)
                tv45.setTextColor(Color.parseColor("#008000"))
                tv90.setTextColor(Color.BLUE)

            }

            cl90.setOnClickListener {

                clickedVideoTime = "90sec"

                tv90.setTextColor(Color.parseColor("#008000"))
                tv45.setTextColor(Color.BLUE)
                apiVideosLast("90sec", traits)

            }

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.adapter = adapterSleep


            val closebtndialog = dialog.findViewById<ImageView>(R.id.closebtndialog)
            closebtndialog.setOnClickListener {
                dialog.dismiss()
                player?.pause()
            }
        }
    }

    private fun savewakeUpHits() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/savewakeUpHits"
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
//                    var jsonObject = obj.getJSONObject("result")
//                    val jsonArray = jsonObject.getJSONArray("data")


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
                params.put("userId", ids1.toString())
                params.put("wakeUpID", "1")
                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun savewakeUp_subpartHits() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/savewakeUp_subpartHits"
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
//                    var jsonObject = obj.getJSONObject("result")
//                    val jsonArray = jsonObject.getJSONArray("data")


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
                params.put("userId", ids1.toString())
                params.put("wu_subPartID", "1")
                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun wakeUpDataSaver() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/wakeUpDataSaver"
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
        process.show()

        var checkTest = time_tv.text as String

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    process.dismiss()

                    val obj = JSONObject(response)
                    if (obj.getBoolean("valid")) {
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Not 1 Saved", Toast.LENGTH_SHORT).show()

                    }
//                    var jsonObject = obj.getJSONObject("result")
//                    val jsonArray = jsonObject.getJSONArray("data")


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
                params.put("userId", ids1.toString())
                params.put("traits", choosedTraits)
                params.put("gender", clickedGender)
                params.put("repeatDay", dayOfAlarm.text as String)
                params.put("videoId", newUrl)
                params.put("videoURL", clickedImageUrl)
                params.put("time1", clickedVideoTime)
                params.put("videoType", videoType)
                params.put("time2", checkTest)

                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun getWakeUpDataSaver() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/getWakeUpSaved?userID="+ids1
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
//        process.show()

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                try {
//                    process.dismiss()

                    val obj = JSONObject(response)
                    if (obj.getBoolean("valid")) {
//                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        val result = obj.getJSONObject("result")
                        val data = result.getJSONObject("data")
                        time_tv.text = data.getString("time2")
                        dayOfAlarm.text = data.getString("repeatDay")

                        val traitClickedLast = data.getString("traits")
                        when (traitClickedLast) {
                            "O" -> {
                                chnageDimension( o_new, c_new,  e_new, a_new, n_new)
//                                e_new.setImageResource(R.drawable.setting_o)
//                                o_new.setImageResource(R.drawable.setting_a)
//                                c_new.setImageResource(R.drawable.setting_n)
//                                a_new.setImageResource(R.drawable.setting_c)
//                                n_new.setImageResource(R.drawable.setting_e)
                            }
                            "C" -> {
                                chnageDimension(c_new,  e_new, a_new, n_new, o_new)
//                                e_new.setImageResource(R.drawable.setting_c)
//                                o_new.setImageResource(R.drawable.setting_n)
//                                c_new.setImageResource(R.drawable.setting_o)
//                                a_new.setImageResource(R.drawable.setting_e)
//                                n_new.setImageResource(R.drawable.setting_a)
                            }
                            "E" -> {
                                chnageDimension( e_new, a_new, n_new, o_new, c_new)
//                                e_new.setImageResource(R.drawable.setting_e)
//                                o_new.setImageResource(R.drawable.setting_o)
//                                c_new.setImageResource(R.drawable.setting_c)
//                                a_new.setImageResource(R.drawable.setting_a)
//                                n_new.setImageResource(R.drawable.setting_n)
                            }
                            "A" -> {
                                chnageDimension(  a_new, n_new, o_new, c_new ,  e_new)
//                                e_new.setImageResource(R.drawable.setting_a)
//                                o_new.setImageResource(R.drawable.setting_c)
//                                c_new.setImageResource(R.drawable.setting_e)
//                                a_new.setImageResource(R.drawable.setting_n)
//                                n_new.setImageResource(R.drawable.setting_o)
                            }
                            "N" -> {
                                chnageDimension(   n_new, o_new, c_new ,  e_new, a_new)
//                                e_new.setImageResource(R.drawable.setting_n)
//                                o_new.setImageResource(R.drawable.setting_e)
//                                c_new.setImageResource(R.drawable.setting_a)
//                                a_new.setImageResource(R.drawable.setting_o)
//                                n_new.setImageResource(R.drawable.setting_c)
                            }
                        }
                    } else {
                        Toast.makeText(context, "Not Saved", Toast.LENGTH_SHORT).show()

                    }


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
                params.put("userID", ids1.toString());
                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    override fun longPressId(urlLong: String?, tr: String) {
        newUrl = urlLong as String
        choosedUrl = urlLong as String
    }

    private fun getWakeupForProgram() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/wakeUp_program"
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
//        process.show()

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                try {
//                    process.dismiss()


                    val obj = JSONObject(response)
                    if (obj.getBoolean("valid")) {
//                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        val result = obj.getJSONObject("result")
                        val url_images = result.getString("url")
                        val data = result.getJSONArray("data")

                        thumb.clear()
                        for (i in 0 until data.length()) {
                            val jsonObject = data.getJSONObject(i)
//                            thumb.add(url_images + "screen_1665215396.png"/*jsonObject.getString("imgName")*/)

                            thumb.add(
                                "https://app.whyuru.com/assets/uploads/wakeUp/" + jsonObject.getString(
                                    "imgName"
                                )
                            )
//                            Toast.makeText(context, url_images + jsonObject.getString("imgName"), Toast.LENGTH_SHORT).show()
                        }
                        adapterConnects()
//                        dayOfAlarm.text = data.getString("repeatDay")
                    } else {
                        Toast.makeText(context, "Not Saved", Toast.LENGTH_SHORT).show()

                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(
                    context,
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }
}




