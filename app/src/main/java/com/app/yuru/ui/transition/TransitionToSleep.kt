package com.app.yuru.ui.transition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.yuru.R


class TransitionToSleep : Fragment() {

    lateinit var transition_to_sleep_recy : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_transition_to_sleep, container, false)

        transition_to_sleep_recy = view.findViewById(R.id.transition_to_sleep_recy)

        val transitionToSleepAdapter = TransitionToSleepAdapter(requireContext())
        transition_to_sleep_recy.setHasFixedSize(true)
        transition_to_sleep_recy.layoutManager = LinearLayoutManager(context)
        transition_to_sleep_recy.adapter = transitionToSleepAdapter

        return view

    }

}