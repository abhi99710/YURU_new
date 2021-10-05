package com.app.yuru.ui.transition

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request.Method.POST
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_transition_to_sleep, container, false)

        transition_to_sleep_recy = view.findViewById(R.id.transition_to_sleep_recy)
        skipSleep = view.findViewById(R.id.skipSleep)
        skipToProgram = view.findViewById(R.id.skipToProgram)

        skipToProgram.setOnClickListener {

        }

        skipToProgram.setOnClickListener {

        }


        apiVideos()

        val transitionToSleepAdapter = TransitionToSleepAdapter(requireContext())
        transition_to_sleep_recy.setHasFixedSize(true)
        transition_to_sleep_recy.layoutManager = LinearLayoutManager(context)
        transition_to_sleep_recy.adapter = transitionToSleepAdapter

        return view

    }

    private fun apiVideos() {
        val url  = "https://promask.com.co/yuru/api/videos"

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")




                   /* if(obj.getString("msg").equals("Logged Successfully")) {

//                        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
//                        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
//
//                        editor.putString("email",jsonObject.getString("email"))
//                        editor.apply()
//                        editor.commit()

//                        intent = Intent(applicationContext, Dashboard::class.java)
//                        startActivity(intent)
                    }*/
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("roleType", "admin")

                return params
            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

}