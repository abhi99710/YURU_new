package com.app.yuru.ui.transition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.app.yuru.R



class SleepEnhancer : Fragment() {

    lateinit var bottom_1 : ImageView
    lateinit var arrowLeft1 : ImageView
    lateinit var bottom2 : ImageView
    lateinit var arrowRight1 : ImageView
    lateinit var arrowLeft2 : ImageView
    lateinit var arrowRight2 : ImageView
    lateinit var center1 : ImageView
    lateinit var center2 : ImageView
    lateinit var save_sleep_enhancer : TextView
    var left_1_count = 0   //count for left click for image 1
    var right_1_count = 0  //count for right click for image 1
    var left_2_count = 0  //count for left click for image 2
    var right_2_count = 0  //count for right click for image 2
    var answerForLeft = 45  // real time answer for image 1 button clicks
    var annserForRight = 135  // real time answer for image 1 button clicks
    var toXdelta1 = 0.0f
    var toXdelta2 = 0.0f




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view : View = inflater.inflate(R.layout.fragment_sleep_enhancer, container, false)


        findIds(view)

        save_sleep_enhancer.setOnClickListener {

//        findNavController().navigate()

        }

        // right arrow for first image slide
        arrowRight1.setOnClickListener{
//            val animation =
//                AnimationUtils.loadAnimation(context, R.anim.slide)
            if(right_1_count < 5) {
                right_1_count++
                answerForLeft = answerForLeft + 1
                Toast.makeText(context, "+" + right_1_count , Toast.LENGTH_SHORT)
                    .show()

                toXdelta1 = toXdelta1 + 20.0f
                val animation = TranslateAnimation(0f, toXdelta1, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom_1.startAnimation(animation)


            }else{
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()

            }

        }

        // left arrow for first image slide
        arrowLeft1.setOnClickListener{
//            val animation =
//                AnimationUtils.loadAnimation(context, R.anim.slide)

            if(left_1_count > -5) {
                left_1_count--
                answerForLeft = answerForLeft - 1
                Toast.makeText(context, "" + left_1_count , Toast.LENGTH_SHORT)
                    .show()
                val animation = TranslateAnimation(0f, -100f, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom_1.startAnimation(animation)

            }else{
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        // left arrow for second image slide
        arrowLeft2.setOnClickListener{
//            val animation =
//                AnimationUtils.loadAnimation(context, R.anim.slide)
            if(left_2_count > -5) {
                left_2_count--
                annserForRight = annserForRight - 1
                Toast.makeText(context, "" + left_2_count , Toast.LENGTH_SHORT)
                    .show()
                val animation = TranslateAnimation(0f, -100f, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom2.startAnimation(animation)

            }else{
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // right arrow for second image slide
        arrowRight2.setOnClickListener{
//            val animation =
//                AnimationUtils.loadAnimation(context, R.anim.slide)
            if(right_2_count < 5) {
                right_2_count++
                annserForRight = annserForRight + 1
                Toast.makeText(context, "+" + right_2_count , Toast.LENGTH_SHORT)
                    .show()

                toXdelta2 = toXdelta2 + 20.0f
                val animation = TranslateAnimation(0f, toXdelta2, 0f, 0f)
                animation.setDuration(1000)
                animation.setFillAfter(true)
                bottom2.startAnimation(animation)


            }else{
                Toast.makeText(context, "not allowed", Toast.LENGTH_SHORT)
                    .show()

            }

        }

        // center icon for first image slide to position 0 ( 45 min )
        center1.setOnClickListener{

            answerForLeft = 45
            right_1_count = 0
            left_1_count = 0
            Toast.makeText(context, "$answerForLeft", Toast.LENGTH_SHORT).show()
            val animation = TranslateAnimation(0f,0f,0f,0f )
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom_1.startAnimation(animation)

        }

        // center icon for second image slide to position 0 ( 135 min )
        center2.setOnClickListener{
            annserForRight = 135
            right_2_count = 0
            left_2_count = 0
            Toast.makeText(context, "$annserForRight", Toast.LENGTH_SHORT).show()
            val animation = TranslateAnimation(0f,0f,0f,0f )
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom2.startAnimation(animation)
        }

        return view

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

}