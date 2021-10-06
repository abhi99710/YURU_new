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
    var left_1_count = 0
    var right_1_count = 0
    var left_2_count = 0
    var right_2_count = 0




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view : View = inflater.inflate(R.layout.fragment_sleep_enhancer, container, false)


        bottom_1  = view.findViewById(R.id.bottom_1)
        arrowLeft1  = view.findViewById(R.id.arrowLeft1)
        bottom2  = view.findViewById(R.id.bottom2)
        arrowRight1  = view.findViewById(R.id.arrowRight1)
        arrowLeft2  = view.findViewById(R.id.arrowLeft2)
        arrowRight2  = view.findViewById(R.id.arrowRight2)
        center1  = view.findViewById(R.id.center1)
        center2  = view.findViewById(R.id.center2)
        save_sleep_enhancer = view.findViewById(R.id.save_sleep_enhancer)

        save_sleep_enhancer.setOnClickListener {

//            it.findNavController().navigate(R.id.sleepEnhancer2)

        }


        arrowRight1.setOnClickListener{
//            val animation =
//                AnimationUtils.loadAnimation(context, R.anim.slide)
            right_1_count ++
                val animation = TranslateAnimation(0f,100f,0f,0f )
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom_1.startAnimation(animation)

            Toast.makeText(context, "+ {$right_1_count}", Toast.LENGTH_SHORT).show()


        }


        arrowLeft1.setOnClickListener{
//            val animation =
//                AnimationUtils.loadAnimation(context, R.anim.slide)
            val animation = TranslateAnimation(0f,-100f,0f,0f )
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom_1.startAnimation(animation)
            Toast.makeText(context, "-5", Toast.LENGTH_SHORT).show()

        }

        arrowLeft2.setOnClickListener{
//            val animation =
//                AnimationUtils.loadAnimation(context, R.anim.slide)
            val animation = TranslateAnimation(0f,-100f,0f,0f )
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom2.startAnimation(animation)

            Toast.makeText(context, "-5", Toast.LENGTH_SHORT).show()
        }

        arrowRight2.setOnClickListener{
//            val animation =
//                AnimationUtils.loadAnimation(context, R.anim.slide)
            val animation = TranslateAnimation(0f,100f,0f,0f )
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom2.startAnimation(animation)
            Toast.makeText(context, "+5", Toast.LENGTH_SHORT).show()

        }

        center1.setOnClickListener{
            val animation = TranslateAnimation(0f,0f,0f,0f )
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom_1.startAnimation(animation)
            Toast.makeText(context, "45", Toast.LENGTH_SHORT).show()
        }

        center2.setOnClickListener{
            val animation = TranslateAnimation(0f,0f,0f,0f )
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom2.startAnimation(animation)
            Toast.makeText(context, "135", Toast.LENGTH_SHORT).show()
        }

        return view

    }

}