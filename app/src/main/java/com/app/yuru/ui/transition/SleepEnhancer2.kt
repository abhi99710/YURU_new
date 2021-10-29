package com.app.yuru.ui.transition

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.corescheduler.player.video.ui.VideoActivity
import com.app.yuru.corescheduler.utils.Constants
import com.app.yuru.utility.apivolley.APIVolley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
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


    private var id45: MutableList<String> = ArrayList()
    private var language_slug45: MutableList<String> = ArrayList()
    private var traint45: MutableList<String> = ArrayList()
    private var gender45: MutableList<String> = ArrayList()
    private var duration45: MutableList<String> = ArrayList()
    private var sleep_id45: MutableList<String> = ArrayList()
    private var url45: MutableList<String> = ArrayList()

    private var id90: MutableList<String> = ArrayList()
    private var language_slug90: MutableList<String> = ArrayList()
    private var traint90: MutableList<String> = ArrayList()
    private var gender90: MutableList<String> = ArrayList()
    private var duration90: MutableList<String> = ArrayList()
    private var sleep_id90: MutableList<String> = ArrayList()
    private var url90 :  MutableList<String> = ArrayList()

    lateinit var progressDialog: ProgressDialog

    private var checkClickedL1 = false
    private var checkClickedR1 = false
    private var checkClickedL2 = false
    private var checkClickedR2 = false

    private lateinit var showAns2: TextView
    private lateinit var showAns: TextView
    private lateinit var showAdd2: TextView
    private lateinit var showAdd1: TextView


    /* private var id1Female: MutableList<String> = ArrayList()
    private var category_nameFemale: MutableList<String> = ArrayList()
    private var language_nameFemale: MutableList<String> = ArrayList()
    private var genderFemale: MutableList<String> = ArrayList()
    private var traintFemale: MutableList<String> = ArrayList()
    private var durationFemale: MutableList<String> = ArrayList()
    private var url1Female: MutableList<String> = ArrayList() */
    private var requestQueue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_sleep_enhancer2, container, false)

        // for finding IDs this method is used
        findIds(view)

        apiVideos()

        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()

        // bottom left side imageview click managed here
        bottom_1.setOnClickListener {
            showDialog("Openness")
        }

        methodForUpperImageClicks()  // upper image click managed my this method


        // save button click listener
        save_sleep_enhancer_2.setOnClickListener {
//            it.findNavController().navigate(R.id.wakeUpProgram)
            if (checkClickedL1) {

                go(answerForLeft)
                go(270)
                go(annserForRight)
                go(360)

            } else if (checkClickedR1) {

                go(answerForLeft)
                go(270)
                go(annserForRight)
                go(360)

            } else if (checkClickedL2) {

                go(answerForLeft)
                go(270)
                go(annserForRight)
                go(360)

            } else if (checkClickedR2) {

                go(answerForLeft)
                go(270)
                go(annserForRight)
                go(360)

            } else {

                go(225)
                go(270)
                go(315)
                go(360)

            }


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
            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_o))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_o))
                countClicked = 0
            }
        }


        e_option.setOnClickListener {
            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_e))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_e))
                countClicked = 0
            }
        }

        c_option.setOnClickListener {
            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_c))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_c))
                countClicked = 0
            }
        }


        a1Option.setOnClickListener {
            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_a))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_a))
                countClicked = 0
            }
        }

        n1option.setOnClickListener {
            if (countClicked == 0) {
                bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_n))
                countClicked = 1
            } else {
                bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_n))
                countClicked = 0
            }
        }
    }

    private fun clickListnerForTransition() {

        // right arrow for first image slide
        arrowRight1.setOnClickListener {
            checkClickedR1 = true
            if (right_1_count < 5) {
                right_1_count++
                answerForLeft = answerForLeft + 1
//                Toast.makeText(context, "+" + right_1_count, Toast.LENGTH_SHORT)
//                    .show()

                showAdd1.setText("(+" + right_1_count + ")")
                if (right_1_count > 0)
                    showAdd1.setTextColor(Color.GREEN)
                else
                    showAdd1.setTextColor(Color.RED)

                toXdelta1 = toXdelta1 + 20.0f
                val animation = TranslateAnimation(0f, toXdelta1, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom_1.startAnimation(animation)

//                go(answerForLeft)


            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()

            }

        }

        // left arrow for first image slide
        arrowLeft1.setOnClickListener {
            checkClickedL1 = true
            if (left_1_count > -5) {
                left_1_count--
                answerForLeft = answerForLeft - 1
//                Toast.makeText(context, "" + left_1_count, Toast.LENGTH_SHORT)
//                    .show()

                if (left_1_count < 0) {
                    showAdd1.setTextColor(Color.RED)
                    showAdd1.setText("(" + left_1_count + ")")
                } else {
                    showAdd1.setTextColor(Color.GREEN)
                    showAdd1.setText("(" + left_1_count + ")")
                }

                negXdelta1 = negXdelta1 - 20
                val animation = TranslateAnimation(0f, negXdelta1, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom_1.startAnimation(animation)

//                go(answerForLeft)

            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        // left arrow for second image slide
        arrowLeft2.setOnClickListener {
            checkClickedL2 = true
            if (left_2_count > -5) {
                left_2_count--
                annserForRight = annserForRight - 1
                negXdelta2 = negXdelta2 - 20
//                Toast.makeText(context, "" + left_2_count, Toast.LENGTH_SHORT)
//                    .show()
                showAdd2.setText("(" + left_2_count + ")")
                if (left_2_count < 0)
                    showAdd2.setTextColor(Color.RED)
                else
                    showAdd2.setTextColor(Color.GREEN)

                val animation = TranslateAnimation(0f, negXdelta2, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom2.startAnimation(animation)

//                go(annserForRight)

            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // right arrow for second image slide
        arrowRight2.setOnClickListener {
            checkClickedR2 = true
            if (right_2_count < 5) {
                right_2_count++
                annserForRight = annserForRight + 1
//                Toast.makeText(context, "+" + right_2_count, Toast.LENGTH_SHORT)
//                    .show()

                showAdd2.setText("(+" + right_2_count + ")")
                if (right_2_count > 0)
                    showAdd2.setTextColor(Color.GREEN)
                else
                    showAdd2.setTextColor(Color.RED)

                toXdelta2 = toXdelta2 + 20.0f
                val animation = TranslateAnimation(0f, toXdelta2, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom2.startAnimation(animation)

//                go(annserForRight)


            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()

            }

        }

        // center icon for first image slide to position 0 ( 45 min )
        center1.setOnClickListener {

            answerForLeft = 45
            right_1_count = 0
            left_1_count = 0
            toXdelta1 = 0.0f
            negXdelta1 = 0.0f
//            go(45)
//            Toast.makeText(context, "$answerForLeft", Toast.LENGTH_SHORT).show()
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom_1.startAnimation(animation)

            showAdd1.setText("  ")

        }

        // center icon for second image slide to position 0 ( 135 min )
        center2.setOnClickListener {
            annserForRight = 135
            right_2_count = 0
            left_2_count = 0
            toXdelta2 = 0.0f
            negXdelta2 = 0.0f
//            go(135)
//            Toast.makeText(context, "$annserForRight", Toast.LENGTH_SHORT).show()

            showAdd2.setText("  ")
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.setDuration(1000)
            animation.setFillAfter(true)
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
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialogtransition)
            dialog.show()

            val recyclerView: RecyclerView = dialog.findViewById(R.id.recyclerNewSleep);

            val dialog_title: TextView = dialog.findViewById(R.id.dialog_title)
            val logo: ImageView = dialog.findViewById(R.id.logo)
            val cl90: ConstraintLayout = dialog.findViewById(R.id.cl90)
            val cl45: ConstraintLayout = dialog.findViewById(R.id.cl45)

            val adapterSleep = AdapterSleep(
                requireActivity(),
                id45,
                language_slug45,
                gender45,
                traint45,
                sleep_id45,
                duration45,
                url45
            )

            cl45.setOnClickListener {

                cl45.setBackgroundColor(Color.parseColor("#eeeeee"))
                cl90.setBackgroundColor(Color.TRANSPARENT)

                val adapterSleep = AdapterSleep(
                    requireActivity(),
                    id45,
                    language_slug45,
                    gender45,
                    traint45,
                    sleep_id45,
                    duration45,
                    url45
                )
            }

            cl90.setOnClickListener {

                cl45.setBackgroundColor(Color.TRANSPARENT)
                cl90.setBackgroundColor(Color.parseColor("#eeeeee"))

                val adapterSleep = AdapterSleep(
                    requireActivity(),
                    id90,
                    language_slug90,
                    gender90,
                    traint90,
                    sleep_id90,
                    duration90,
                    url90
                )
            }

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapterSleep


//            val cv1: CardView = dialog.findViewById(R.id.cv1)

//            cv1.setOnClickListener {
//                val intent = Intent(context, VideoPlay::class.java)
//                intent.putExtra("videoLink", "")
//                context?.startActivity(intent)
//            }


            val closebtndialog = dialog.findViewById<ImageView>(R.id.closebtndialog)
            closebtndialog.setOnClickListener {
                dialog.dismiss()
            }
        }


    }


    private fun go(hike: Int) {

        val SimpleDateFormat = SimpleDateFormat("HH:mm:ss")

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val calendar = Calendar.getInstance()
        val calList: MutableList<Calendar> = ArrayList()

        for (i in 0..4) {
            calList.add(calendar)
        }

        val stringBuilder = ""

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



            Toast.makeText(context, "Alarm has been set : \n " + stringBuilder, Toast.LENGTH_SHORT)
                .show()

        }
    }

    private fun apiVideos() {
        val url = APIVolley.sleep

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                try {
                    progressDialog.dismiss()
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    val jsonArray = jsonObject.getJSONArray("data")

                    id45.clear()
                    language_slug45.clear()
                    gender45.clear()
                    url45.clear()
                    sleep_id45.clear()
                    duration45.clear()
                    traint45.clear()
                    id90.clear()
                    language_slug90.clear()
                    gender90.clear()
                    url90.clear()
                    sleep_id90.clear()
                    duration90.clear()
                    traint90.clear()



                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)

                        if(jsonObject1.getString("duration").equals("45sec")){

                            id45.add(jsonObject1.getString("id"))
                            language_slug45.add(jsonObject1.getString("language_slug"))
                            gender45.add(jsonObject1.getString("gender"))
                            url45.add(jsonObject1.getString("url"))
                            sleep_id45.add(jsonObject1.getString("sleep_id"))
                            duration45.add(jsonObject1.getString("duration"))
                            traint45.add(jsonObject1.getString("traint"))

                        }else{
                            id90.add(jsonObject1.getString("id"))
                            language_slug90.add(jsonObject1.getString("language_slug"))
                            gender90.add(jsonObject1.getString("gender"))
                            url90.add(jsonObject1.getString("url"))
                            sleep_id90.add(jsonObject1.getString("sleep_id"))
                            duration90.add(jsonObject1.getString("duration"))
                            traint90.add(jsonObject1.getString("traint"))
                        }



                    }

//                    adapterConnects()


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

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }


}