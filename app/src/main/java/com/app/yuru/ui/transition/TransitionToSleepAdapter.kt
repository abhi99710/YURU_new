package com.app.yuru.ui.transition

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.app.yuru.R



class TransitionToSleepAdapter(context : Context) : RecyclerView.Adapter<TransitionToSleepAdapter.ViewHolder>() {

    var context: Context = context
    val fragment : Fragment = Fragment()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_transition_to_sleep, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tts_videoview.setOnClickListener {

                val fragmentManager = fragment.parentFragmentManager
                fragmentManager.beginTransaction().replace(R.id.frame1, SleepEnhancer())

            }
    }

    override fun getItemCount(): Int {
        return 2
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tts_videoview : VideoView = itemView.findViewById(R.id.tts_videoview)

    }


}