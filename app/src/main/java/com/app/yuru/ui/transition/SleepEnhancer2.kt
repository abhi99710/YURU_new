package com.app.yuru.ui.transition

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.yuru.R


class SleepEnhancer2 : Fragment() {

    lateinit var cl_se2 : ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_sleep_enhancer2, container, false)

        cl_se2 = view.findViewById(R.id.cl_se2)
        cl_se2.setOnClickListener({
            startActivity(Intent(context, SubliminalProgram::class.java))
        })


        return view
    }


}