package com.app.yuru.ui.transition

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.app.yuru.R

class EveningProgram : Fragment() {

    private lateinit var o_option : TextView
    private lateinit var c_option : TextView
    private lateinit var e_option : TextView
    private lateinit var a_option : TextView
    private lateinit var n_option : TextView
    private lateinit var save_evening : TextView
    private lateinit var gridEvening : GridView
    private lateinit var fiveEvening : TextView
    private lateinit var nineEvening : TextView
    private lateinit var female_evening : ImageView
    private lateinit var male_evening : ImageView
    private lateinit var viewAll_eve : ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_evening_program, container, false)

        findIds(view )

        o_option.setOnClickListener {

            textColor(o_option, c_option, e_option, a_option, n_option)

            Toast.makeText(context, "O", Toast.LENGTH_SHORT).show()
        }

        c_option.setOnClickListener {
            textColor(c_option, o_option, e_option, a_option, n_option)
            Toast.makeText(context, "C", Toast.LENGTH_SHORT).show()

        }
        e_option.setOnClickListener {
            textColor(e_option, c_option, o_option, a_option, n_option)

            Toast.makeText(context, "E", Toast.LENGTH_SHORT).show()
        }

        a_option.setOnClickListener {
            textColor(a_option, e_option, c_option, o_option, n_option)

            Toast.makeText(context, "A", Toast.LENGTH_SHORT).show()

        }
        n_option.setOnClickListener {
            textColor(n_option, a_option, e_option, c_option, o_option)

            Toast.makeText(context, "N", Toast.LENGTH_SHORT).show()

        }

        return view
    }

    fun textColor(tv1: TextView, tv2: TextView, tv3: TextView, tv4: TextView, tv5: TextView) {

        tv1.setBackgroundColor(Color.parseColor("#FFC107")) // Yellow

        // Black
        tv2.setBackgroundColor(Color.parseColor("#000000"))
        tv3.setBackgroundColor(Color.parseColor("#000000"))
        tv4.setBackgroundColor(Color.parseColor("#000000"))
        tv5.setBackgroundColor(Color.parseColor("#000000"))

    }

    private fun findIds(view: View?) {
        if (view!= null) {
            o_option = view.findViewById(R.id.o_option_eve)
            c_option = view.findViewById(R.id.c_option_eve)
            e_option = view.findViewById(R.id.e_option_eve)
            a_option = view.findViewById(R.id.a_option_eve)
            n_option = view.findViewById(R.id.n_option_eve)
            save_evening = view.findViewById(R.id.save_evening)
            gridEvening = view.findViewById(R.id.gridEvening)
            fiveEvening = view.findViewById(R.id.fiveEvening)
            nineEvening = view.findViewById(R.id.nineEvening)
            female_evening = view.findViewById(R.id.female_evening)
            male_evening = view.findViewById(R.id.male_evening)
            viewAll_eve = view.findViewById(R.id.viewAll_eve)
        }

    }

}