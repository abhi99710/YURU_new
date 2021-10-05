package com.app.yuru.ui.transition

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.yuru.R


class SubliminalProgram : Fragment() {

    lateinit var cl_subliminal : ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_subliminal_program, container, false)

//        cl_subliminal = view.findViewById(R.id.cl_subliminal)
//        cl_subliminal.setOnClickListener {
//            startActivity(Intent(context, WakeUpProgram::class.java))
//        }

        return view
    }

  
}