package com.app.yuru.ui.transition

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class SleepEnhancer : Fragment() {

    lateinit var bottom_1: ImageView
    lateinit var arrowLeft1: ImageView
    lateinit var bottom2: ImageView
    lateinit var arrowRight1: ImageView
    lateinit var arrowLeft2: ImageView
    lateinit var arrowRight2: ImageView
    lateinit var center1: ImageView
    lateinit var center2: ImageView
    lateinit var resetBtn: TextView
    lateinit var save_sleep_enhancer: TextView
    private var left_1_count = 0   //count for left click for image 1
    private var right_1_count = 0  //count for right click for image 1
    private var left_2_count = 0  //count for left click for image 2
    private var right_2_count = 0  //count for right click for image 2
    private var answerForLeft = 45  // real time answer for image 1 button clicks
    private var annserForRight = 135  // real time answer for image 1 button clicks
    private var toXdelta1 =
        0.0f  // saves the position to which the first imageview slides right side
    private var toXdelta2 =
        0.0f  // saves the position to which the second imageview slides right side
    private var negXdelta1 =
        0.0f  // saves the position to which the first imageview slides left side
    private var negXdelta2 =
        0.0f  // saves the position to which the second imageview slides left side

    private lateinit var showAns2: TextView
    private lateinit var showAns: TextView
    private lateinit var showAdd2: TextView
    private lateinit var showAdd1: TextView

    private lateinit var tts_vids: VideoView

    var firstAlarm = ""
    var secondAlarm = ""


    private lateinit var seekBar1: VerticalSeekBar
    private lateinit var showOptionclick: ImageView
    private lateinit var showOptionCLickRight: ImageView
    var showSetAlarmLeft = 0
    var showSetAlarmRight = 0

    private var id45: MutableList<String> = ArrayList()
    private var fileName: MutableList<String> = ArrayList()
    private var fileURL: MutableList<String> = ArrayList()
    private var isActive: MutableList<String> = ArrayList()

    private var requestQueue: RequestQueue? = null
    private var urlL = ""
    private var currentVolume = 0
    private lateinit var audioManager: AudioManager
    private var seekbarPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_sleep_enhancer, container, false)

        findIds(view)


        hideOptions()
        hideOptionRight()

        apiVideos()

        getState()

        saveSleepEnhancerHits()

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
        val newVolume = sharedPreferences.getString("Volume_Sleep_Enhancer", "100")
        currentVolume = Integer.parseInt(newVolume)

        seekBar1.max = 100


        seekBar1.progress = currentVolume

//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)


        resetBtn = view.findViewById(R.id.resetBtn)
        resetBtn.setOnClickListener {
            hideOptions()
            hideOptionRight()

            answerForLeft = 0
            annserForRight = 0
            showOptionCLickRight.visibility = View.VISIBLE
            showOptionclick.visibility = View.VISIBLE
        }

        showOptionclick = view.findViewById(R.id.showOptionclick)
        showOptionclick.setOnClickListener {
            showOptions()
            showOptionclick.visibility = View.INVISIBLE
            showSetAlarmLeft = 1
        }

        showOptionCLickRight = view.findViewById(R.id.showOptionCLickRight)
        showOptionCLickRight.setOnClickListener {
            showOptionRight()
            showOptionCLickRight.visibility = View.INVISIBLE
            showSetAlarmRight = 1
        }


        save_sleep_enhancer.setOnClickListener {
//            answerForLeft = 2
//            annserForRight = 5

            SaveState()




            if (showSetAlarmLeft == 1) {
                val sharedPreferences: SharedPreferences =
                    requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                    Global.ids1 = jsonObject1.getString("id")
                editor.putString("se_time1", "" + answerForLeft)
//                editor.putString("se_time2", "" + annserForRight)
                editor.putString("se_url", "" + urlL)
                editor.apply()
                editor.commit()
//                answerForLeft = 1
                Log.e("515", "Left set" + answerForLeft + "-" + annserForRight)

//                go(answerForLeft)
//                SaveState()
            }

            if (showSetAlarmRight == 1) {

                val sharedPreferences: SharedPreferences =
                    requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                    Global.ids1 = jsonObject1.getString("id")
//                editor.putString("se_time1", "" + answerForLeft)
                editor.putString("se_time2", "" + annserForRight)
                editor.putString("se_url", "" + urlL)
                editor.apply()
                editor.commit()
//                 annserForRight = 4
                Log.e("515", "Right set" + answerForLeft + "-" + annserForRight)

//                go(annserForRight)
//                SaveState()
            }

//            val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
//            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
////                    Global.ids1 = jsonObject1.getString("id")
//            editor.putString("Volume_Sleep_Enhancer", ""+ seekbarPosition)
//            editor.apply()
//            editor.commit()

            val fragment = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.framwQts, SleepEnhancer2())
            fragment.addToBackStack(null)
            fragment.commit()


        }
        // this method is used for transition of image
        transitionClickListner()
        try {
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            seekBar1.max = 100
            seekBar1.setOnSeekBarChangeListener(object  : OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
//                    Toast.makeText(context, "0000000" + 1, Toast.LENGTH_SHORT).show()
                    seekBar1.progress = progress



                    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor =  sharedPreferences.edit()
//                    Global.ids1 = jsonObject1.getString("id")
                    editor.putString("Volume_Sleep_Enhancer", ""+ progress)
                    editor.apply()
                    editor.commit()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })

//            seekBar1.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
//            seekBar1.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
//            seekBar1.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
//                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
//
//                    seekbarPosition = i
//
//                    audioManager =
//                        (context?.getSystemService(requireContext().toString()) as AudioManager?)!!
//                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0)
//
//                    Toast.makeText(context, "0000000"+i, Toast.LENGTH_SHORT).show()
//
//                    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
//                    val editor: SharedPreferences.Editor =  sharedPreferences.edit()
////                    Global.ids1 = jsonObject1.getString("id")
//                    editor.putString("Volume_Sleep_Enhancer", ""+i)
//                    editor.apply()
//                    editor.commit()
//                }
//
//                override fun onStartTrackingTouch(seekBar: SeekBar) {
//
//                    Toast.makeText(context, "0000000", Toast.LENGTH_SHORT).show()
//                }
//                override fun onStopTrackingTouch(seekBar: SeekBar) {}
//            })
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return view

    }


    @SuppressLint("SetTextI18n")
    private fun transitionClickListner() {
        // right arrow for first image slide
        arrowRight1.setOnClickListener {
            showSetAlarmLeft = 1

            left_1_count = 0
            if (right_1_count <= 10) {
                right_1_count+=5
                answerForLeft += 5
                showAdd1.text = "(+$right_1_count)"
                if (right_1_count > 0)
                    showAdd1.setTextColor(Color.GREEN)
                else
                    showAdd1.setTextColor(Color.RED)
                toXdelta1 += 20.0f
                val animation = TranslateAnimation(0f, toXdelta1, 0f, 0f)
                animation.duration = 1000
                animation.fillAfter = true
                bottom_1.startAnimation(animation)
            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // left arrow for first image slide
        arrowLeft1.setOnClickListener {

            right_1_count = 0

            showSetAlarmLeft = 1
            if (left_1_count >= -10) {
                left_1_count-=5
                answerForLeft -= 5
                if (left_1_count < 0) {
                    showAdd1.setTextColor(Color.RED)
                    showAdd1.text = "(" + left_1_count + ")"
                } else {
                    showAdd1.setTextColor(Color.GREEN)
                    showAdd1.text = "(" + left_1_count + ")"
                }
                negXdelta1 -= 20
                val animation = TranslateAnimation(0f, negXdelta1, 0f, 0f)
                animation.duration = 1000
                animation.fillAfter = true
                bottom_1.startAnimation(animation)
            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // left arrow for second image slide
        arrowLeft2.setOnClickListener {

            right_2_count = 0

            showSetAlarmRight = 1
            if (left_2_count >= -10) {
                left_2_count-=5
                annserForRight -= 5
                showAdd2.text = "(" + left_2_count + ")"
                if (left_2_count < 0)
                    showAdd2.setTextColor(Color.RED)
                else
                    showAdd2.setTextColor(Color.GREEN)
                negXdelta2 -= 20
                val animation = TranslateAnimation(0f, negXdelta2, 0f, 0f)
                animation.duration = 1000
                animation.fillAfter = true
                bottom2.startAnimation(animation)
            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // right arrow for second image slide
        arrowRight2.setOnClickListener {

            left_2_count = 0

            showSetAlarmRight = 1
            if (right_2_count <= 10) {
                right_2_count+=5
                annserForRight += 5
                showAdd2.text = "(+$right_2_count)"
                if (right_2_count > 0)
                    showAdd2.setTextColor(Color.GREEN)
                else
                    showAdd2.setTextColor(Color.RED)
                toXdelta2 += 20.0f
                val animation = TranslateAnimation(0f, toXdelta2, 0f, 0f)
                animation.duration = 1000
                animation.fillAfter = true
                bottom2.startAnimation(animation)
            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // center first block
        showAns.setOnClickListener {
            answerForLeft = 45
            right_1_count = 0
            left_1_count = 0
            negXdelta1 = 0.0f
            toXdelta1 = 0.0f
            showAdd1.text = "  "
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.duration = 1000
            animation.fillAfter = true
            bottom_1.startAnimation(animation)
        }

        // center icon for first image slide to position 0 ( 45 min )
        center1.setOnClickListener {
            answerForLeft = 45
            right_1_count = 0
            left_1_count = 0
            negXdelta1 = 0.0f
            toXdelta1 = 0.0f
            showAdd1.text = "  "
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.duration = 1000
            animation.fillAfter = true
            bottom_1.startAnimation(animation)
        }


        // center second block
        showAns2.setOnClickListener {
            annserForRight = 135
            right_2_count = 0
            left_2_count = 0
            negXdelta2 = 0.0f
            toXdelta2 = 0.0f
            showAdd2.text = "  "
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.duration = 1000
            animation.fillAfter = true
            bottom2.startAnimation(animation)
        }

        // center icon for second image slide to position 0 ( 135 min )
        center2.setOnClickListener {
            annserForRight = 135
            right_2_count = 0
            left_2_count = 0
            negXdelta2 = 0.0f
            toXdelta2 = 0.0f
            showAdd2.text = "  "
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.duration = 1000
            animation.fillAfter = true
            bottom2.startAnimation(animation)
        }
        videoPlay()
    }

    private fun findIds(view: View) {
        bottom_1 = view.findViewById(R.id.bottom_1)
        arrowLeft1 = view.findViewById(R.id.arrowLeft1)
        bottom2 = view.findViewById(R.id.bottom2)
        arrowRight1 = view.findViewById(R.id.arrowRight1)
        arrowLeft2 = view.findViewById(R.id.arrowLeft2)
        arrowRight2 = view.findViewById(R.id.arrowRight2)
        center1 = view.findViewById(R.id.center1)
        center2 = view.findViewById(R.id.center2)
        save_sleep_enhancer = view.findViewById(R.id.save_sleep_enhancer)

        seekBar1 = view.findViewById(R.id.seekBar111)

        showAdd1 = view.findViewById(R.id.showAdd1)
        showAdd2 = view.findViewById(R.id.showAdd2)
        showAns = view.findViewById(R.id.showAns)
        showAns2 = view.findViewById(R.id.showAns2)

        tts_vids = view.findViewById(R.id.sleep_video)

    }


//    private fun
//            go(ans: Int) {
//        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
//        val calendar = Calendar.getInstance()
//        val calList: MutableList<Calendar> = ArrayList()
//        calList.add(calendar)
//        for (calItem in calList) {
//            calItem.add(Calendar.MINUTE, ans)
//            val requestCode = (calendar.timeInMillis / 1000).toInt()
//            val intent = Intent(context, MyReceiver::class.java)
//            intent.putExtra("REQUEST_CODE", requestCode)
//            intent.putExtra("fragment", "sleep1")
//            intent.putExtra("url", urlL)
//
//            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
//            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
//            val pi = PendingIntent.getBroadcast(context, requestCode, intent, 0)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                alarmManager?.setExactAndAllowWhileIdle(
//                    AlarmManager.RTC_WAKEUP,
//                    calItem.timeInMillis,
//                    pi
//                )
//
////                alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP,
////                    calItem.timeInMillis,
////                    24*60*60*1000,
////                    pi)
//            } else {
//                alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calItem.timeInMillis, pi)
//                alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP,
//                    calItem.timeInMillis,
//                    24*60*60*1000,
//                    pi)
//            }
//            Toast.makeText(context, "Alarm has been set ", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun videoPlay() {
        val ctlr = MediaController(context)

        val uri =
            Uri.parse("android.resource://" + context?.packageName + "/R.raw/" + R.raw.eveningvideo)
        tts_vids.setVideoURI(uri)
        tts_vids.start()


        tts_vids.setOnPreparedListener({ mp -> mp.isLooping = true })
    }

    fun hideOptions() {
        showAns.visibility = View.INVISIBLE
        bottom_1.visibility = View.INVISIBLE
        arrowLeft1.visibility = View.INVISIBLE
        arrowRight1.visibility = View.INVISIBLE
        showAdd1.visibility = View.INVISIBLE

    }

    fun hideOptionRight() {
        showAns2.visibility = View.INVISIBLE
        bottom2.visibility = View.INVISIBLE

        arrowLeft2.visibility = View.INVISIBLE
        arrowRight2.visibility = View.INVISIBLE
        showAdd2.visibility = View.INVISIBLE
    }

    fun showOptions() {
        showAns.visibility = View.VISIBLE
        bottom_1.visibility = View.VISIBLE
        arrowRight1.visibility = View.VISIBLE
        arrowLeft1.visibility = View.VISIBLE

        showAdd1.visibility = View.VISIBLE

    }

    fun showOptionRight() {
        showAns2.visibility = View.VISIBLE
        bottom2.visibility = View.VISIBLE
        arrowLeft2.visibility = View.VISIBLE
        arrowRight2.visibility = View.VISIBLE
        showAdd2.visibility = View.VISIBLE
    }

    private fun apiVideos() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/getAllSleepEnhancerMaster"
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
                    fileName.clear()
                    fileURL.clear()
                    isActive.clear()


                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)

                        id45.add(jsonObject1.getString("id"))
                        fileName.add(jsonObject1.getString("fileName"))
                        fileURL.add(jsonObject1.getString("fileURL"))
                        isActive.add(jsonObject1.getString("isActive"))

                        if (isActive.equals("1")) {
                            urlL = jsonObject1.getString("isActive")
                        }

                    }
                    urlL = fileURL[0]

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

                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun saveSleepEnhancerHits() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/saveSleepEnhancerHits"
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
                params.put("sleepEnhancerID", "1")
                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun SaveState() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/sleepEnhancerSaver"
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
//                    Toast.makeText(
//                        requireContext(),
//                        jsonObject.getString("message"),
//                        Toast.LENGTH_SHORT
//                    ).show()


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
                params.put("userID", ids1.toString())
                params.put("firstAlarm", "" + answerForLeft)

                params.put("secondAlarm", "" + annserForRight)
                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun getState() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/getSleepEnhancerSaved?userID=" + ids1
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
        process.show()

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                try {
                    process.dismiss()

                    val obj = JSONObject(response)
                    if (!obj.getString("status").equals("NOK")) {
                        var jsonObject = obj.getJSONObject("result")
                        var data = jsonObject.getJSONObject("data")

                        firstAlarm = data.getString("firstAlarm")
                        secondAlarm = data.getString("secondAlarm")

                        showAns.text = firstAlarm
                        showAns2.text = secondAlarm

                        annserForRight = Integer.parseInt(secondAlarm)
                        answerForLeft = Integer.parseInt(firstAlarm)

                        showOptionCLickRight.visibility = View.INVISIBLE
                        showOptionclick.visibility = View.INVISIBLE

                        showOptions()
                        showOptionRight()

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
                params.put("userId", ids1.toString())
                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

}