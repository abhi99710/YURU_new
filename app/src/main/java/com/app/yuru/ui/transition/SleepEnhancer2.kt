package com.app.yuru.ui.transition

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.utility.apivolley.APIVolley
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class SleepEnhancer2 : Fragment() {


    private lateinit var bottom_1: ImageView
    private lateinit var arrowLeft1: ImageView
    private lateinit var bottom2: ImageView
    private lateinit var arrowRight1: ImageView
    private lateinit var arrowLeft2: ImageView
    private lateinit var arrowRight2: ImageView
    private lateinit var center1: ImageView
    private lateinit var center2: ImageView
    private lateinit var save_sleep_enhancer_2: TextView

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

    private lateinit var resetBtn2 : TextView
    private lateinit var showOptionSelect : ImageView

    private var id45: MutableList<String> = ArrayList()
    private var traint45: MutableList<String> = ArrayList()
    private var duration45: MutableList<String> = ArrayList()
    private var thumb45: MutableList<String> = ArrayList()
    private var url45: MutableList<String> = ArrayList()

    private lateinit var showAns2: TextView
    private lateinit var showAns: TextView
    private lateinit var showAdd2: TextView
    private lateinit var showAdd1: TextView
    private lateinit var sleVideo : VideoView


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

        resetBtn2 = view.findViewById(R.id.resetBtn2)
        resetBtn2.setOnClickListener {
//            showOptions()
            hideOptions()
        }

        showOptionSelect = view.findViewById(R.id.showOptionSelect)
        showOptionSelect.setOnClickListener {
//            hideOptions()
            showOptions()
        }

        apiVideos("45sec", "O")



        // bottom left side imageview click managed here
        bottom_1.setOnClickListener {
//            showDialog("Openness")
        }

        methodForUpperImageClicks()  // upper image click managed my this method


        // save button click listener
        save_sleep_enhancer_2.setOnClickListener {
            go(answerForLeft)
            go(annserForRight)
            val fragment = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.framwQts, WakeUpProgram())
            fragment.addToBackStack(null)
            fragment.commit()
        }



        clickListnerForTransition()  // this method is used for transition of image


        return view
    }

    private fun methodForUpperImageClicks() {
        o_option.setOnClickListener {

//           val o =  o_option.drawable
            showDialog("Openness")

            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_o))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_o))
                countClicked = 0
            }
        }


        e_option.setOnClickListener {

//            val o =  e_option.drawable

            showDialog("Extraversion")

            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_e))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_e))
                countClicked = 0
            }
        }

        c_option.setOnClickListener {

//            val o =  c_option.drawable

            showDialog("Conscientiousness")

            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_c))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_c))
                countClicked = 0
            }
        }


        a1Option.setOnClickListener {

//            val o =  a1Option.drawable

            showDialog("Agreeableness")

            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_a))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_a))
                countClicked = 0
            }
        }

        n1option.setOnClickListener {

            val o =  n1option.drawable

            showDialog("Neuroticism")


            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_n))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_n))
                countClicked = 0
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clickListnerForTransition() {

        // right arrow for first image slide
        arrowRight1.setOnClickListener {
            if (right_1_count < 5) {
                right_1_count++
                answerForLeft += 1
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
            if (left_1_count > -5) {
                left_1_count--
                answerForLeft -= 1
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
            if (left_2_count > -5) {
                left_2_count--
                annserForRight -= 1
                negXdelta2 -= 20
                showAdd2.setText("(" + left_2_count + ")")
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
            if (right_2_count < 5) {
                right_2_count++
                annserForRight += 1
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
        val dialog = context?.let { Dialog(it, android.R.style.Theme_Holo_Light) }
        if (dialog != null) {
            var traits = "O"
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialogtransition)
            dialog.show()

            val recyclerView: RecyclerView = dialog.findViewById(R.id.recyclerNewSleep);

            val dialog_title: TextView = dialog.findViewById(R.id.dialog_title)
            dialog_title.text = title

            val logo: ImageView = dialog.findViewById(R.id.logo)

            when(title){
                "Extraversion"->{
                    traits = "E"
                    logo.setImageResource(R.drawable.setting_e)
                    dialog_title.setTextColor(Color.parseColor("#FF0000"))
                }
                "Agreeableness"->{
                    traits = "A"
                    logo.setImageResource(R.drawable.setting_a)
                    dialog_title.setTextColor(Color.parseColor("#F9CA14"))


                }

                "Neuroticism"->{
                    traits = "N"
                    logo.setImageResource(R.drawable.setting_n)
                    dialog_title.setTextColor(Color.parseColor("#808080"))


                }
                "Openness"->{
                    traits = "O"
                    logo.setImageResource(R.drawable.setting_o)
                    dialog_title.setTextColor(Color.parseColor("#008000"))


                }
                "Conscientiousness"->{
                    traits = "C"
                    logo.setImageResource(R.drawable.setting_c)
                    dialog_title.setTextColor(Color.parseColor("#0000FF"))


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
                url45
            )

            cl45.setOnClickListener {

                apiVideos("45sec",traits)
                tv45.setTextColor(Color.parseColor("#008000"))
                tv90.setTextColor(Color.BLUE)

            }

            cl90.setOnClickListener {

                tv90.setTextColor(Color.parseColor("#008000"))
                tv45.setTextColor(Color.BLUE)
                apiVideos("90sec", traits)

            }

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(context,2)
            recyclerView.adapter = adapterSleep


            val closebtndialog = dialog.findViewById<ImageView>(R.id.closebtndialog)
            closebtndialog.setOnClickListener {
                dialog.dismiss()
            }
        }


    }


    private fun go(hike: Int) {
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
            Toast.makeText(context, "Alarm has been set", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun apiVideos(duration : String, trait : String) {
        val url = "https://promask.com.co/yuru/api/web/getAllEveningProgram"
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
                return params
            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }


    fun hideOptions(){
        showAns.visibility = View.INVISIBLE
        showAns2.visibility = View.INVISIBLE
        bottom_1.visibility = View.INVISIBLE
        bottom2.visibility = View.INVISIBLE
        arrowLeft1.visibility = View.INVISIBLE
        arrowRight1.visibility = View.INVISIBLE
        arrowLeft2.visibility = View.INVISIBLE
        arrowRight2.visibility = View.INVISIBLE
        showAdd1.visibility = View.INVISIBLE
        showAdd2.visibility = View.INVISIBLE
    }

    fun showOptions(){
        showAns.visibility = View.VISIBLE
        showAns2.visibility = View.VISIBLE
        bottom_1.visibility = View.VISIBLE
        bottom2.visibility = View.VISIBLE
        arrowLeft1.visibility = View.VISIBLE
        arrowRight1.visibility = View.VISIBLE
        arrowLeft2.visibility = View.VISIBLE
        arrowRight2.visibility = View.VISIBLE
        showAdd1.visibility = View.VISIBLE
        showAdd2.visibility = View.VISIBLE
    }

    private fun videoPlay() {
        val ctlr = MediaController(context)
        ctlr.setMediaPlayer(sleVideo)

        val uri =
            Uri.parse("android.resource://" + context?.getPackageName() + "/R.raw/" + R.raw.eveningvideo);

        sleVideo.setVideoURI(uri);
        sleVideo.start()

    }

}