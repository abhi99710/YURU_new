package com.app.yuru.ui.transition

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.yuru.R
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.ExperimentalTime


class SleepEnhancer : Fragment() {

    lateinit var bottom_1: ImageView
    lateinit var arrowLeft1: ImageView
    lateinit var bottom2: ImageView
    lateinit var arrowRight1: ImageView
    lateinit var arrowLeft2: ImageView
    lateinit var arrowRight2: ImageView
    lateinit var center1: ImageView
    lateinit var center2: ImageView
    lateinit var save_sleep_enhancer: TextView
    private var left_1_count = 0   //count for left click for image 1
    private var right_1_count = 0  //count for right click for image 1
    private var left_2_count = 0  //count for left click for image 2
    private var right_2_count = 0  //count for right click for image 2
    private var answerForLeft = 1  // real time answer for image 1 button clicks
    private var annserForRight = 2  // real time answer for image 1 button clicks
    private var toXdelta1 = 0.0f  // saves the position to which the first imageview slides right side
    private var toXdelta2 = 0.0f  // saves the position to which the second imageview slides right side
    private var negXdelta1 = 0.0f  // saves the position to which the first imageview slides left side
    private var negXdelta2 = 0.0f  // saves the position to which the second imageview slides left side

    private var alarmAnser = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_sleep_enhancer, container, false)


        findIds(view)

        save_sleep_enhancer.setOnClickListener {

            val fragment = requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framwQts, SleepEnhancer2())
            fragment.addToBackStack(null)
            fragment.commit()


        }

        // this method is used for transition of image
        transitionClickListner()



        return view

    }

    private fun transitionClickListner() {

        // right arrow for first image slide
        arrowRight1.setOnClickListener {

            if (right_1_count < 5) {
                right_1_count++
                answerForLeft = answerForLeft + 1
                alarmAnser = answerForLeft +1
                Toast.makeText(context, "+$right_1_count:$alarmAnser", Toast.LENGTH_SHORT)
                    .show()

                toXdelta1 = toXdelta1 + 20.0f
                val animation = TranslateAnimation(0f, toXdelta1, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom_1.startAnimation(animation)
                go(alarmAnser, 0)



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
                alarmAnser = answerForLeft
                Toast.makeText(context, "" + left_1_count, Toast.LENGTH_SHORT)
                    .show()
                negXdelta1 = negXdelta1 - 20
                val animation = TranslateAnimation(0f, negXdelta1, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom_1.startAnimation(animation)

                go(answerForLeft, 1)

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

                Toast.makeText(context, "" + left_2_count, Toast.LENGTH_SHORT)
                    .show()
                negXdelta2 = negXdelta2 - 20
                val animation = TranslateAnimation(0f, negXdelta2, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom2.startAnimation(animation)

                go(annserForRight, 2)

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

                go(annserForRight, 3)


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
            negXdelta1 = 0.0f
            toXdelta1 = 0.0f
            Toast.makeText(context, "$answerForLeft", Toast.LENGTH_SHORT).show()
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom_1.startAnimation(animation)

            go(45, 4)

        }

        // center icon for second image slide to position 0 ( 135 min )
        center2.setOnClickListener {
            annserForRight = 135
            right_2_count = 0
            left_2_count = 0
            negXdelta2 = 0.0f
            toXdelta2 = 0.0f
            Toast.makeText(context, "$annserForRight", Toast.LENGTH_SHORT).show()
            val animation = TranslateAnimation(0f, 0f, 0f, 0f)
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom2.startAnimation(animation)

            go(135, 5)
        }


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
    }


    private fun go(ans : Int, chechReq : Int) {

        val  SimpleDateFormat = SimpleDateFormat("HH:mm:ss")

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val calendar = Calendar.getInstance()
        val calList : MutableList<Calendar> = ArrayList()

        for(i in 0..4 ){
            calList.add(calendar)
        }

        val stringBuilder = ""

        for( calItem in calList){
            calItem.add(Calendar.MINUTE,ans)

            val requestCode = (calendar.timeInMillis/1000).toInt()
            val intent = Intent(context, MyReceiver::class.java)
            intent.putExtra("REQUEST_CODE",requestCode)
            intent.putExtra("fragment","sleep1")

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

}