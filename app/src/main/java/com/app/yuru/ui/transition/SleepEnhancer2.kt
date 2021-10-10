package com.app.yuru.ui.transition

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.app.yuru.R


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

    private var left_1_count = 0   //count for left click for image 1
    private var right_1_count = 0  //count for right click for image 1
    private var left_2_count = 0  //count for left click for image 2
    private var right_2_count = 0  //count for right click for image 2
    private var answerForLeft = 45  // real time answer for image 1 button clicks
    private var annserForRight = 135  // real time answer for image 1 button clicks
    private var toXdelta1 = 0.0f  // saves the position to which the first imageview slides right side
    private var toXdelta2 = 0.0f  // saves the position to which the second imageview slides right side
    private var negXdelta1 = 0.0f  // saves the position to which the first imageview slides left side
    private var negXdelta2 = 0.0f  // saves the position to which the second imageview slides left side

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_sleep_enhancer2, container, false)

        // for finding IDs this method is used
        findIds(view)


        // bottom left side imageview click managed here
        bottom_1.setOnClickListener {
            showDialog("Openness")
        }

        methodForUpperImageClicks()  // upper image click managed my this method


        // save button click listener
        save_sleep_enhancer_2.setOnClickListener {
//            it.findNavController().navigate(R.id.wakeUpProgram)

            val fragment = requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framwQts, WakeUpProgram())
            fragment.addToBackStack(null)
            fragment.commit()
        }



        clickListnerForTransition()  // this method is used for transition of image


        return view
    }

    private fun methodForUpperImageClicks() {
        o_option.setOnClickListener {

            bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_o))
        }


        e_option.setOnClickListener {
            bottom2.setImageDrawable(resources.getDrawable(R.drawable.setting_e))
            //            bottom_1.setImageDrawable(resources.getDrawable(R.drawable.e))
        }

        c_option.setOnClickListener {
            bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_c))
        }


        a1Option.setOnClickListener {
            bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_a))
        }

        n1option.setOnClickListener {
            bottom_1.setImageDrawable(resources.getDrawable(R.drawable.setting_n))
        }
    }

    private fun clickListnerForTransition() {

        // right arrow for first image slide
        arrowRight1.setOnClickListener {

            if (right_1_count < 5) {
                right_1_count++
                answerForLeft = answerForLeft + 1
                Toast.makeText(context, "+" + right_1_count, Toast.LENGTH_SHORT)
                    .show()

                toXdelta1 = toXdelta1 + 20.0f
                val animation = TranslateAnimation(0f, toXdelta1, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom_1.startAnimation(animation)

                go(answerForLeft)


            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()

            }

        }

        // left arrow for first image slide
        arrowLeft1.setOnClickListener {

            if (left_1_count > -5) {
                left_1_count--
                answerForLeft = answerForLeft - 1
                Toast.makeText(context, "" + left_1_count, Toast.LENGTH_SHORT)
                    .show()
                negXdelta1 = negXdelta1 - 20
                val animation = TranslateAnimation(0f, negXdelta1, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom_1.startAnimation(animation)

                go(answerForLeft)

            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        // left arrow for second image slide
        arrowLeft2.setOnClickListener {

            if (left_2_count > -5) {
                left_2_count--
                annserForRight = annserForRight - 1
                negXdelta2 = negXdelta2 - 20
                Toast.makeText(context, "" + left_2_count, Toast.LENGTH_SHORT)
                    .show()
                val animation = TranslateAnimation(0f, negXdelta2, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom2.startAnimation(animation)

                go(annserForRight)

            } else {
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // right arrow for second image slide
        arrowRight2.setOnClickListener {

            if (right_2_count < 5) {
                right_2_count++
                annserForRight = annserForRight + 1
                Toast.makeText(context, "+" + right_2_count, Toast.LENGTH_SHORT)
                    .show()

                toXdelta2 = toXdelta2 + 20.0f
                val animation = TranslateAnimation(0f, toXdelta2, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom2.startAnimation(animation)

                go(annserForRight)


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
            go(45)
            Toast.makeText(context, "$answerForLeft", Toast.LENGTH_SHORT).show()
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom_1.startAnimation(animation)

        }

        // center icon for second image slide to position 0 ( 135 min )
        center2.setOnClickListener {
            annserForRight = 135
            right_2_count = 0
            left_2_count = 0
            toXdelta2 = 0.0f
            negXdelta2 = 0.0f
            go(135)
            Toast.makeText(context, "$annserForRight", Toast.LENGTH_SHORT).show()
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

        o_option = view.findViewById(R.id.o_option)
        e_option = view.findViewById(R.id.e_option)
        c_option = view.findViewById(R.id.c_option)
        a1Option = view.findViewById(R.id.a1Option)
        n1option = view.findViewById(R.id.n1option)
    }

    private fun showDialog(title: String) {
        val dialog = context?.let { Dialog(it, android.R.style.Theme_Holo_Light) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialogtransition)
            dialog.show()

            val closebtndialog = dialog.findViewById<ImageView>(R.id.closebtndialog)
            closebtndialog.setOnClickListener {
                dialog.dismiss()
            }
        }


    }


    private fun go(ans : Int) {
        //GET TIME IN SECONDS AND INITIALIZE INTENT
//        val time: Long =
        val i = Intent(context, MyReceiver::class.java)

//        val calendar = Calendar.getInstance()
//        if (alarmTimePicker != null) {
//            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour())
//        }
//        if (alarmTimePicker != null) {
//            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute())
//        }
//        val dat = Date()
//        val cal_alarm = Calendar.getInstance()
//        val cal_now = Calendar.getInstance()
//        cal_now.time = dat
//        cal_alarm.time = dat
////        cal_alarm[Calendar.HOUR_OF_DAY] = hours
////        cal_alarm[Calendar.MINUTE] = minutes
//        cal_alarm[Calendar.SECOND] = 0
//
//        val p1 = Calendar.HOUR_OF_DAY
//        val p2 = Calendar.MINUTE


        //PASS CONTEXT,YOUR PRIVATE REQUEST CODE,INTENT OBJECT AND FLAG
        val pi = PendingIntent.getBroadcast(context, 0, i, 0)

        //INITIALIZE ALARM MANAGER
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

        //SET THE ALARM
        alarmManager!![AlarmManager.RTC_WAKEUP, /*System.currentTimeMillis() + time * 1000*/  (  System.currentTimeMillis() + ans)] =
            pi
//        Toast.makeText(context, "Alarm set in $time seconds", Toast.LENGTH_SHORT).show()
    }

}