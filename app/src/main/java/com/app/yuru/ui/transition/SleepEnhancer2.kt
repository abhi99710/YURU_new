package com.app.yuru.ui.transition

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.cardview.widget.CardView
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
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class SleepEnhancer2 : Fragment(), ClickInterface, LongPressSleep2 {


    private lateinit var bottom_1: ImageView
    private lateinit var arrowLeft1: ImageView
    private lateinit var bottom2: ImageView
    private lateinit var arrowRight1: ImageView
    private lateinit var arrowLeft2: ImageView
    private lateinit var arrowRight2: ImageView
    private lateinit var center1: ImageView
    private lateinit var center2: ImageView
    private lateinit var save_sleep_enhancer_2: TextView

    var urlToSetAlarm = ""

    var player: SimpleExoPlayer? = null


    private lateinit var o_option: ImageView
    private lateinit var e_option: ImageView
    private lateinit var c_option: ImageView
    private lateinit var a1Option: ImageView
    private lateinit var n1option: ImageView

    lateinit var seekBar1: VerticalSeekBar

    private var left_1_count = 0   //count for left click for image 1
    private var right_1_count = 0  //count for right click for image 1
    private var left_2_count = 0  //count for left click for image 2
    private var right_2_count = 0  //count for right click for image 2
    private var answerForLeft = 225  // real time answer for image 1 button clicks
    private var annserForRight = 315  // real time answer for image 1 button clicks
    private var toXdelta1 =
        0.0f  // saves the position to which the first imageview slides right side
    private var toXdelta2 =
        0.0f  // saves the position to which the second imageview slides right side
    private var negXdelta1 =
        0.0f  // saves the position to which the first imageview slides left side
    private var negXdelta2 =
        0.0f  // saves the position to which the second imageview slides left side
    private var countClicked = 0

    private lateinit var resetBtn2: TextView
    private lateinit var showOptionSelect: ImageView

    private var id45: MutableList<String> = ArrayList()
    private var traint45: MutableList<String> = ArrayList()
    private var duration45: MutableList<String> = ArrayList()
    private var thumb45: MutableList<String> = ArrayList()
    private var url45: MutableList<String> = ArrayList()

    var showSetAlarmLeft = 0
    var showSetAlarmRight = 0

    private lateinit var showAns2: TextView
    private lateinit var showAns: TextView
    private lateinit var showAdd2: TextView
    private lateinit var showAdd1: TextView
    private lateinit var sleVideo: VideoView
    private lateinit var showOptionCLickRight: ImageView

    var selectedUrl1 = ""
    var selectedUrl2 = ""

    var playerView1: PlayerView? = null
    var clExoPlayer: ConstraintLayout? = null
    private lateinit var closebtndialog1: ImageView
    private var newUrl: String = ""

    private var isEngagedO = false
    private var isEngagedC = false

    private var isEngagedE = false

    private var isEngagedA = false

    private var isEngagedN = false

    var traitsSave1 = "1"
    var traitsSave2 = "1"
    var timeSave1 = "1"
    var timeSave2 = "1"
    var urlSave1 = "1"
    var urlSave2 = "1"
    var firstAlarm = "1"
    var secondAlarm = "1"
    var trait1 = ""
    var trait2 = ""
    var currentVolume = 0

    private lateinit var recyclerView: RecyclerView
    private var requestQueue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_sleep_enhancer2, container, false)

        // for finding IDs this method is used
        findIds(view)

        sleVideo = view.findViewById(R.id.sleVideo)
        videoPlay()


        hideOptions()
        hideOptionRight()

        getState()

        saveEveningProgramHits()

        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
        val newVolume = sharedPreferences.getString("Volume_Evening_Program", "100")
        currentVolume = Integer.parseInt(newVolume)

        seekBar1.max = 100


        seekBar1.progress = currentVolume

        resetBtn2 = view.findViewById(R.id.resetBtn2)
        resetBtn2.setOnClickListener {
//            showOptions()
            answerForLeft = 0
            annserForRight = 0
            hideOptions()
            hideOptionRight()
            showOptionCLickRight.visibility = View.VISIBLE
            showOptionSelect.visibility = View.VISIBLE
        }

        showOptionSelect = view.findViewById(R.id.showOptionSelect)
        showOptionSelect.setOnClickListener {
//            hideOptions()
            showOptions()
            showOptionSelect.visibility = View.INVISIBLE

            showSetAlarmLeft = 1
        }

        showOptionCLickRight = view.findViewById(R.id.showOptionCLickRight2)
        showOptionCLickRight.setOnClickListener {
            showOptionRight()
            showOptionCLickRight.visibility = View.INVISIBLE

            showSetAlarmRight = 1
        }


        // bottom left side imageview click managed here
        bottom_1.setOnClickListener {
//            showDialog("Openness")
        }

        methodForUpperImageClicks()  // upper image click managed my this method


        // save button click listener
        save_sleep_enhancer_2.setOnClickListener {




            if (showSetAlarmLeft == 1) {

                val sharedPreferences: SharedPreferences =
                    requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                    Global.ids1 = jsonObject1.getString("id")
                editor.putString("ep_time1", "" + answerForLeft)
//                editor.putString("ep_time2", "" + annserForRight)
                editor.putString("ep1_url", "" + selectedUrl1)
//                editor.putString("ep2_url", "" + newUrl)
                editor.apply()
                editor.commit()

//                answerForLeft = 1
//                timeSave1 = "" + answerForLeft as String
                Log.e("515", "Left set" + answerForLeft + "-" + annserForRight)

//                go(answerForLeft)
            }

            if (showSetAlarmRight == 1) {
                val sharedPreferences: SharedPreferences =
                    requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                    Global.ids1 = jsonObject1.getString("id")
//                editor.putString("ep_time1", "" + answerForLeft)
                editor.putString("ep_time2", "" + annserForRight)
//                editor.putString("ep1_url", "" + newUrl)
                editor.putString("ep2_url", "" + selectedUrl2)
                editor.apply()
                editor.commit()
//                annserForRight = 4
//                timeSave2 = "" + annserForRight as String
                Log.e("515", "Right set" + answerForLeft + "-" + annserForRight)

//                go(annserForRight)
            }

            SaveState()

            val fragment = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.framwQts, WakeUpProgram())
            fragment.addToBackStack(null)
            fragment.commit()
        }



        clickListnerForTransition()  // this method is used for transition of image


        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        seekBar1.max = 100

        seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar1.progress = progress


                val sharedPreferences: SharedPreferences =
                    requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                    Global.ids1 = jsonObject1.getString("id")
                editor.putString("Volume_Evening_Program", "" + progress)
                editor.apply()
                editor.commit()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })


        return view
    }

    private fun methodForUpperImageClicks() {
        o_option.setOnClickListener {

//           val o =  o_option.drawable

//            if (isEngagedO == false) {
            showDialog("Openness")

            isEngagedO = true
            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_o))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_o))
                countClicked = 0
            }
//            } else {
//
//                Toast.makeText(context, "Already used", Toast.LENGTH_SHORT).show()
//            }
        }


        e_option.setOnClickListener {

//            val o =  e_option.drawable
//            if (isEngagedE == false) {
            showDialog("Extraversion")

            isEngagedE = true
            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_e))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_e))
                countClicked = 0
            }
//            } else {
//
//                Toast.makeText(context, "Already used", Toast.LENGTH_SHORT).show()
//            }
        }

        c_option.setOnClickListener {

//            val o =  c_option.drawable


//            if (isEngagedC == false) {
            showDialog("Conscientiousness")

            isEngagedC = true
            if (countClicked == 0) {

                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_c))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_c))
                countClicked = 0
            }
//            } else {
//                Toast.makeText(context, "Already used", Toast.LENGTH_SHORT).show()
//
//            }
        }


        a1Option.setOnClickListener {

//            val o =  a1Option.drawable


//            if (isEngagedA == false) {
            showDialog("Agreeableness")

            isEngagedA = true
            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_a))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_a))
                countClicked = 0
            }
//            } else {
//                Toast.makeText(context, "Already used", Toast.LENGTH_SHORT).show()
//
//            }
        }

        n1option.setOnClickListener {

            val o = n1option.drawable


//            if (isEngagedN == false) {
            showDialog("Neuroticism")
            isEngagedN = true
            if (countClicked == 0) {

                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_n))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_n))
                countClicked = 0
            }
//            } else {
//                Toast.makeText(context, "Already used", Toast.LENGTH_SHORT).show()
//
//            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clickListnerForTransition() {

        // right arrow for first image slide
        arrowRight1.setOnClickListener {

            left_1_count = 0

            showSetAlarmLeft = 1
            if (right_1_count <= 10) {
                right_1_count += 5
                answerForLeft += 5
                showAdd1.text = "(+" + right_1_count + ")"
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
                left_1_count -= 5
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
                left_2_count -= 5
                annserForRight -= 5
                negXdelta2 -= 20
                showAdd2.text = "(" + left_2_count + ")"
                if (left_2_count < 0)
                    showAdd2.setTextColor(Color.RED)
                else
                    showAdd2.setTextColor(Color.GREEN)
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
                right_2_count += 5
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

        showAns.setOnClickListener {
            answerForLeft = 225
            right_1_count = 0
            left_1_count = 0
            toXdelta1 = 0.0f
            negXdelta1 = 0.0f
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.duration = 1000
            animation.fillAfter = true
            bottom_1.startAnimation(animation)
            showAdd1.text = "  "
        }

        showAns2.setOnClickListener {
            annserForRight = 315
            right_2_count = 0
            left_2_count = 0
            toXdelta2 = 0.0f
            negXdelta2 = 0.0f
            showAdd2.text = "  "
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.duration = 1000
            animation.fillAfter = true
            bottom2.startAnimation(animation)
        }

        // center icon for first image slide to position 0 ( 45 min )
        center1.setOnClickListener {
            answerForLeft = 225
            right_1_count = 0
            left_1_count = 0
            toXdelta1 = 0.0f
            negXdelta1 = 0.0f
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.duration = 1000
            animation.fillAfter = true
            bottom_1.startAnimation(animation)
            showAdd1.text = "  "
        }

        // center icon for second image slide to position 0 ( 135 min )
        center2.setOnClickListener {
            annserForRight = 315
            right_2_count = 0
            left_2_count = 0
            toXdelta2 = 0.0f
            negXdelta2 = 0.0f
            showAdd2.text = "  "
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.duration = 1000
            animation.fillAfter = true
            bottom2.startAnimation(animation)
        }
    }

    private fun findIds(view: View) {
        bottom_1 = view.findViewById(R.id.bottom_1_sl2)
        arrowLeft1 = view.findViewById(R.id.arrowLeft1_sl2)
        bottom2 = view.findViewById(R.id.bottom2_sl2)
        arrowRight1 = view.findViewById(R.id.arrowRight1_sl2)
        arrowLeft2 = view.findViewById(R.id.arrowLeft2_sl2)

        arrowRight2 = view.findViewById(R.id.arrowRight2_sl2)
        center1 = view.findViewById(R.id.center1_sl2)
        center2 = view.findViewById(R.id.center2_sl2)
        save_sleep_enhancer_2 = view.findViewById(R.id.save_sleep_enhancer_2)

        o_option = view.findViewById(R.id.o_option1)
        e_option = view.findViewById(R.id.e_option1)
        c_option = view.findViewById(R.id.c_option1)
        a1Option = view.findViewById(R.id.a1Option)
        n1option = view.findViewById(R.id.n1option)

        seekBar1 = view.findViewById(R.id.seekBar2)

        showAdd1 = view.findViewById(R.id.showAdd1S2)
        showAdd2 = view.findViewById(R.id.showAdd2S2)
        showAns = view.findViewById(R.id.showAnsS2)
        showAns2 = view.findViewById(R.id.showAns2S2)
    }


    private fun showDialog(title: String) {
        val dialog = context?.let { Dialog(it, android.R.style.Theme_Black_NoTitleBar_Fullscreen) }
        if (dialog != null) {
            var traits = "O"
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialogtransition)
            dialog.show()

            val widthOfWindow = getScreenWidth()
            val heightOfWindow = widthOfWindow

            playerView1?.minimumHeight = heightOfWindow


            recyclerView = dialog.findViewById(R.id.recyclerNewSleep)

            val dialog_title: TextView = dialog.findViewById(R.id.dialog_title)
            dialog_title.text = title

            val cardFocus: CardView = dialog.findViewById(R.id.cardFocus)

            playerView1 = dialog.findViewById(R.id.playerView1)


            clExoPlayer = dialog.findViewById(R.id.clExoPlayer)
            closebtndialog1 = dialog.findViewById(R.id.closebtndialog1)
            closebtndialog1.setOnClickListener {
                clExoPlayer!!.visibility = View.INVISIBLE
                playerView1!!.onPause()
                playerView1!!.setKeepContentOnPlayerReset(true)
                player?.pause()

                cardFocus.alpha = 1.0f

            }


            click()
            cardFocus.alpha = 0.7f


            val logo: ImageView = dialog.findViewById(R.id.logo)

            when (title) {
                "Extraversion" -> {
                    traits = "E"
                    logo.setImageResource(R.drawable.setting_e)
                    dialog_title.setTextColor(Color.parseColor("#FF0000"))
                    apiVideos("45sec", "E")
                }
                "Agreeableness" -> {
                    traits = "A"
                    logo.setImageResource(R.drawable.setting_a)
                    dialog_title.setTextColor(Color.parseColor("#F9CA14"))
                    apiVideos("45sec", "A")

                }

                "Neuroticism" -> {
                    traits = "N"
                    logo.setImageResource(R.drawable.setting_n)
                    dialog_title.setTextColor(Color.parseColor("#808080"))
                    apiVideos("45sec", "N")

                }
                "Openness" -> {
                    traits = "O"
                    logo.setImageResource(R.drawable.setting_o)
                    dialog_title.setTextColor(Color.parseColor("#008000"))

                    apiVideos("45sec", "O")
                }
                "Conscientiousness" -> {
                    traits = "C"
                    logo.setImageResource(R.drawable.setting_c)
                    dialog_title.setTextColor(Color.parseColor("#0000FF"))
                    apiVideos("45sec", "C")

                }
            }
//            logo.setImageDrawable(iview)
            val cl90: ConstraintLayout = dialog.findViewById(R.id.cl90)
            val cl45: ConstraintLayout = dialog.findViewById(R.id.cl45)

            val tv45: TextView = dialog.findViewById(R.id.textView7)
            val tv90: TextView = dialog.findViewById(R.id.program)

//            recyclerView.setHasFixedSize(true)
//            recyclerView.layoutManager = GridLayoutManager(context, 2)
//            recyclerView.adapter = adapterSleep

            cl45.setOnClickListener {

                apiVideos("45sec", traits)
                tv45.setTextColor(Color.parseColor("#008000"))
                tv90.setTextColor(Color.BLUE)
//                recyclerView.setHasFixedSize(true)
//                recyclerView.layoutManager = GridLayoutManager(context, 2)
//                recyclerView.adapter = adapterSleep

            }

            cl90.setOnClickListener {

                tv90.setTextColor(Color.parseColor("#008000"))
                tv45.setTextColor(Color.BLUE)
                apiVideos("90sec", traits)


            }

//            recyclerView.setHasFixedSize(true)
//            recyclerView.layoutManager = GridLayoutManager(context, 2)
//            recyclerView.adapter = adapterSleep


            val closebtndialog = dialog.findViewById<ImageView>(R.id.closebtndialog)
            closebtndialog.setOnClickListener {
                dialog.dismiss()
                player?.pause()
            }
        }


    }

    private fun click() {

        if (!newUrl.equals("")) {
            clExoPlayer?.visibility = View.VISIBLE
        }

        val mediaItem: MediaItem = newUrl.let { MediaItem.fromUri(it) }
        player = SimpleExoPlayer.Builder(requireContext()).build().also {
            playerView1?.player = it
            // Set the media item to be played.
            it.setMediaItem(mediaItem)
            // Prepare the player.
            it.prepare()
            // Start the playback.
            it.play()


        }

    }

   /* private fun go(hike: Int) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val calendar = Calendar.getInstance()
        val calList: MutableList<Calendar> = ArrayList()
        calList.add(calendar)
        for (calItem in calList) {
            calItem.add(Calendar.MINUTE, hike)

            val requestCode = (calendar.timeInMillis / 1000).toInt()
            val intent = Intent(context, MyReceiver::class.java)
            intent.putExtra("REQUEST_CODE", requestCode)
            intent.putExtra("fragment", "sleep_enhancer_2")
            intent.putExtra("url", urlToSetAlarm)
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

            val pi = PendingIntent.getBroadcast(context, requestCode, intent, 0)

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
                alarmManager?.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calItem.timeInMillis,
                    24 * 60 * 60 * 1000,
                    pi
                )
            }
            Toast.makeText(context, "Alarm has been set", Toast.LENGTH_SHORT)
                .show()
        }
    }*/

    private fun apiVideos(duration: String, trait: String) {

//        traitsSave1 = trait
//        timeSave1 = duration

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

                    val adapterSleep = AdapterSleep(
                        requireActivity(),
                        id45,
                        traint45,
                        thumb45,
                        duration45,
                        url45,
                        this,
                        this,
                        trait
                    )
                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = GridLayoutManager(context, 2)
                    recyclerView.adapter = adapterSleep

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
                params.put("userId", ids1.toString())

                return params
            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
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
        arrowLeft1.visibility = View.VISIBLE
        arrowRight1.visibility = View.VISIBLE
        showAdd1.visibility = View.VISIBLE

    }

    fun showOptionRight() {
        showAns2.visibility = View.VISIBLE
        bottom2.visibility = View.VISIBLE
        arrowLeft2.visibility = View.VISIBLE
        arrowRight2.visibility = View.VISIBLE
        showAdd2.visibility = View.VISIBLE
    }

    private fun videoPlay() {
        val ctlr = MediaController(context)
        ctlr.setMediaPlayer(sleVideo)

        val uri =
            Uri.parse("android.resource://" + context?.packageName + "/R.raw/" + R.raw.sl2)

        sleVideo.setVideoURI(uri)
        sleVideo.start()

        sleVideo.setOnPreparedListener({ mp -> mp.isLooping = true })

    }

    override fun urlGet(url: String, cli: String) {
        newUrl = url
        click()

        urlSave1 = newUrl
    }
//    override fun onStart() {
//        super.onStart()
//        player?.play()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        player?.pause()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        player?.release()
//        player = null
//    }

    private fun saveEveningProgramHits() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/saveEveningProgramHits"
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
                params.put("eveningProgramID", "1")
                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    override fun longPressId(urlLong: String?, tr: String) {
        urlToSetAlarm = urlLong.toString()
        urlSave1 = urlToSetAlarm

        if (trait1.equals("")) {
            if (trait2.equals("")) {
                trait1 = tr as String
                selectedUrl1 = urlLong as String
            }

        } else {
            if (trait2.equals("")) {
                trait2 = tr as String
                Toast.makeText(context, ""+tr, Toast.LENGTH_SHORT).show()
                selectedUrl2 = urlLong as String
            } else {
                trait1 = tr as String
                selectedUrl1 = urlLong as String
            }

        }

    }

    private fun setImageToDashboard(imageView_3: ImageView, trait1: String) {

        if (trait1.equals("O")) {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.setting_o))
        } else if (trait1.equals("C")) {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.setting_c))
        } else if (trait1.equals("E")) {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.setting_e))
        } else if (trait1.equals("A")) {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.setting_a))
        } else if (trait1.equals("N")) {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.setting_n))
        } else {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.logo_toolbar))
        }

    }

    private fun SaveState() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

//        traitsSave1 = bottom_1.tag as String
//        traitsSave2 = bottom2.tag as String

//        bottom2.get

        val url = "https://app.whyuru.com/api/web/eveningProgramSaver"
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
        process.show()

        Toast.makeText(context, trait1 + "-" + trait2, Toast.LENGTH_SHORT).show()

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    process.dismiss()

                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    Toast.makeText(
                        requireContext(),
                        jsonObject.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()


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
                params.put("thirdAlarm", "" + answerForLeft)
                params.put("fourthAlarm", "" + annserForRight)
                params.put("time1", "" + timeSave1)
                params.put("time2", "" + timeSave1)
                params.put("trait1", "" + trait1)
                params.put("trait2", "" + trait2)
                params.put("videoURL1", "" + urlSave1)
                params.put("videoURL2", "" + urlSave1)
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

        val url = "https://app.whyuru.com/api/web/getSavedEveningProgram?userID=" + ids1
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

                        firstAlarm = data.getString("thirdAlarm")
                        secondAlarm = data.getString("fourthAlarm")

//                        firstAlarm = data.getString("thirdAlarm")
//                        secondAlarm = data.getString("fourthAlarm")

//                        trait1 = data.getString("trait1")
//                        trait2 = data.getString("trait2")
//                        trait1 = data.getString("videoURL1")
//                        trait2 = data.getString("videoURL2")
//                        trait1 = data.getString("time1")
//                        trait2 = data.getString("time2")

                        var newTrait1 = data.getString("trait1")
                        var newTrait2 = data.getString("trait2")

                        setImageToDashboard(bottom_1, newTrait1)
                        setImageToDashboard(bottom2, newTrait2)

                        showAns.text = firstAlarm
                        showAns2.text = secondAlarm

                        annserForRight = Integer.parseInt(secondAlarm)
                        answerForLeft = Integer.parseInt(firstAlarm)

                        showOptionCLickRight.visibility = View.INVISIBLE
                        showOptionSelect.visibility = View.INVISIBLE

                        showOptions()
                        showOptionRight()

                    }


//                    Toast.makeText(requireContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show()


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

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}