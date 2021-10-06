package com.app.yuru.ui.transition


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Method
import java.util.ArrayList


class TransitionToSleep : Fragment() {

    lateinit var transition_to_sleep_recy : RecyclerView
    private var requestQueue: RequestQueue? = null
    lateinit var skipToProgram : TextView
    lateinit var skipSleep : TextView

    private var id_45: MutableList<String> = ArrayList()
    private var language_slug: MutableList<String> = ArrayList()
    private var traint: MutableList<String> = ArrayList()
    private var gender: MutableList<String> = ArrayList()
    private var duration: MutableList<String> = ArrayList()
    private var id: MutableList<String> = ArrayList()
    private var filename: MutableList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_transition_to_sleep, container, false)

        transition_to_sleep_recy = view.findViewById(R.id.transition_to_sleep_recy)
        skipSleep = view.findViewById(R.id.skipSleep)
        skipToProgram = view.findViewById(R.id.skipToProgram)

        skipSleep.setOnClickListener {
           findNavController().navigate(R.id.sleepEnhancer)
        }

        skipToProgram.setOnClickListener {
//          findNavController().navigate(R.id.wakeUpProgram)
        }


        apiVideos()

        val transitionToSleepAdapter = TransitionToSleepAdapter(requireContext())
        transition_to_sleep_recy.setHasFixedSize(true)
        transition_to_sleep_recy.layoutManager = LinearLayoutManager(context)
        transition_to_sleep_recy.adapter = transitionToSleepAdapter

        return view

    }

    private fun apiVideos() {
        val url  = "https://promask.com.co/yuru/api/sleep"

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    val jsonArray = jsonObject.getJSONArray("data")
                    for(i in 0 until jsonArray.length()){
                        val jsonObject2 = jsonArray.getJSONObject(i)


                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
//            @Throws(AuthFailureError::class)
//            override fun getParams(): Map<String, String> {
//                val params = HashMap<String, String>()
//                params.put("roleType", "admin")
//
//                return params
//            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

}