package com.app.yuru.ui.transition

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.yuru.R


class SleepEnhancer2 : Fragment() {


    private lateinit var bottom_1 : ImageView
    private lateinit var arrowLeft1 : ImageView
    private lateinit var bottom2 : ImageView
    private lateinit var arrowRight1 : ImageView
    private lateinit var arrowLeft2 : ImageView
    private lateinit var arrowRight2 : ImageView
    private lateinit var center1 : ImageView
    private lateinit var center2 : ImageView
    private lateinit var save_sleep_enhancer_2 : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_sleep_enhancer2, container, false)


        bottom_1  = view.findViewById(R.id.bottom_1_sl2)
        arrowLeft1  = view.findViewById(R.id.arrowLeft1_sl2)
        bottom2  = view.findViewById(R.id.bottom2_sl2)
        arrowRight1  = view.findViewById(R.id.arrowRight1_sl2)
        arrowLeft2  = view.findViewById(R.id.arrowLeft2_sl2
        )
        arrowRight2  = view.findViewById(R.id.arrowRight2_sl2)
        center1  = view.findViewById(R.id.center1_sl2)
        center2  = view.findViewById(R.id.center2_sl2)
        save_sleep_enhancer_2 = view.findViewById(R.id.save_sleep_enhancer_2)



        arrowRight1.setOnClickListener{
//            val animation =
//                AnimationUtils.loadAnimation(context, R.anim.slide)
            val animation = TranslateAnimation(0f,100f,0f,0f )
            animation.setDuration(1000)
            animation.setFillAfter(true)
            bottom_1.startAnimation(animation)
            Toast.makeText(context, "+5", Toast.LENGTH_SHORT).show()

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