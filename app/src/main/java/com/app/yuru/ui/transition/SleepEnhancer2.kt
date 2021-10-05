package com.app.yuru.ui.transition

import android.app.Dialog
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
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

    private lateinit var o_option : ImageView
    private lateinit var e_option : ImageView
    private lateinit var c_option : ImageView
    private lateinit var a1Option : ImageView
    private lateinit var n1option : ImageView
    private var count : Int = 0
    private var isChecked_O : Boolean = false

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
        arrowLeft2  = view.findViewById(R.id.arrowLeft2_sl2)

        arrowRight2  = view.findViewById(R.id.arrowRight2_sl2)
        center1  = view.findViewById(R.id.center1_sl2)
        center2  = view.findViewById(R.id.center2_sl2)
        save_sleep_enhancer_2 = view.findViewById(R.id.save_sleep_enhancer_2)

        o_option  = view.findViewById(R.id.o_option)
        e_option  = view.findViewById(R.id.e_option)
        c_option  = view.findViewById(R.id.c_option)
        a1Option  = view.findViewById(R.id.a1Option)
        n1option  = view.findViewById(R.id.n1option)

        bottom_1.setOnClickListener {
            showDialog("Openness")
        }

        o_option.setOnClickListener {
            isChecked_O = true
            bottom_1.setImageDrawable(resources.getDrawable(R.drawable.o_icon))
        }


        e_option.setOnClickListener {
            bottom2.setImageDrawable(resources.getDrawable(R.drawable.e_icon))
//            bottom_1.setImageDrawable(resources.getDrawable(R.drawable.e))
        }

        c_option.setOnClickListener {
            bottom_1.setImageDrawable(resources.getDrawable(R.drawable.c_icon))
        }


        a1Option.setOnClickListener {
            bottom_1.setImageDrawable(resources.getDrawable(R.drawable.a_icon))
        }

        n1option.setOnClickListener {
            bottom_1.setImageDrawable(resources.getDrawable(R.drawable.n_icon))
        }


        save_sleep_enhancer_2.setOnClickListener {
//            it.findNavController().navigate(R.id.wakeUpProgram)
        }



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
    private fun showDialog(title: String) {
        val dialog = context?.let { Dialog(it,android.R.style.Theme_Black_NoTitleBar_Fullscreen) }
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

//        val body = dialog.findViewById(R.id.body) as TextView
//        body.text = title
//        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//        val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//        yesBtn.setOnClickListener {
//            dialog.dismiss()
//        }
//        noBtn.setOnClickListener { dialog.dismiss() }


    }

}