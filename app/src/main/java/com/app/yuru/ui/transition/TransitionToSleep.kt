package com.app.yuru.ui.transition


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.utility.apivolley.APIVolley
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Method
import java.util.ArrayList


class TransitionToSleep : Fragment() {

    lateinit var transition_to_sleep_recy : RecyclerView
    private var requestQueue: RequestQueue? = null
    lateinit var skipToProgram : TextView
    lateinit var skipSleep : TextView

/*    private var id_45: MutableList<String> = ArrayList()
    private var language_slug: MutableList<String> = ArrayList()
    private var traint: MutableList<String> = ArrayList()
    private var gender: MutableList<String> = ArrayList()
    private var duration: MutableList<String> = ArrayList()
    private var id: MutableList<String> = ArrayList()
    private var filename: MutableList<String> = ArrayList()*/

    private var id1Male: MutableList<String> = ArrayList()
    private var category_nameMale: MutableList<String> = ArrayList()
    private var language_nameMale: MutableList<String> = ArrayList()
    private var genderMale: MutableList<String> = ArrayList()
    private var traintMale: MutableList<String> = ArrayList()
    private var durationMale: MutableList<String> = ArrayList()
    private var url1Male: MutableList<String> = ArrayList()
    private var id1Female: MutableList<String> = ArrayList()
    private var category_nameFemale: MutableList<String> = ArrayList()
    private var language_nameFemale: MutableList<String> = ArrayList()
    private var genderFemale: MutableList<String> = ArrayList()
    private var traintFemale: MutableList<String> = ArrayList()
    private var durationFemale: MutableList<String> = ArrayList()
    private var url1Female: MutableList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_transition_to_sleep, container, false)

//        Navigation.createNavigateOnClickListener(R.id.nav_host_homepage, null);




        transition_to_sleep_recy = view.findViewById(R.id.transition_to_sleep_recy)
        skipSleep = view.findViewById(R.id.skipSleep)
        skipToProgram = view.findViewById(R.id.skipToProgram)

        skipSleep.setOnClickListener {
            val fragment = requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framwQts, SleepEnhancer())
            fragment.addToBackStack(null)
            fragment.commit()

        }

        skipToProgram.setOnClickListener {
            val fragment = requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framwQts, WakeUpProgram())
            fragment.addToBackStack(null)
            fragment.commit()
        }


//        apiVideos()
        adapterConnects()


        return view

    }

    private fun apiVideos() {
        val url = APIVolley.videosApi

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    val jsonArray = jsonObject.getJSONArray("data")

                    id1Male.clear()
                    category_nameMale.clear()
                    language_nameMale.clear()
                    genderMale.clear()
                    traintMale.clear()
                    durationMale.clear()
                    url1Male.clear()
                    genderFemale.clear()
                    traintFemale.clear()
                    durationFemale.clear()
                    url1Female.clear()
                    id1Female.clear()
                    category_nameFemale.clear()
                    language_nameFemale.clear()

                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)

                        if(jsonObject1.getString("gender").equals("Male")){
                            id1Male.add(jsonObject1.getString("id"))
                            category_nameMale.add(jsonObject1.getString("category_name"))
                            language_nameMale.add(jsonObject1.getString("language_name"))
                            genderMale.add(jsonObject1.getString("gender"))
                            traintMale.add(jsonObject1.getString("traint"))
                            durationMale.add(jsonObject1.getString("duration"))
                            url1Male.add(jsonObject1.getString("url"))
                        }else{
                            id1Female.add(jsonObject1.getString("id"))
                            category_nameFemale.add(jsonObject1.getString("category_name"))
                            language_nameFemale.add(jsonObject1.getString("language_name"))
                            genderFemale.add(jsonObject1.getString("gender"))
                            traintFemale.add(jsonObject1.getString("traint"))
                            durationFemale.add(jsonObject1.getString("duration"))
                            url1Female.add(jsonObject1.getString("url"))
                        }


                    }

                    adapterConnects()


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

    private fun adapterConnects() {
        val transitionToSleepAdapter = TtsAdapter(requireContext())
        transition_to_sleep_recy.setHasFixedSize(true)
        transition_to_sleep_recy.layoutManager = LinearLayoutManager(context)
        transition_to_sleep_recy.adapter = transitionToSleepAdapter

       /* val adapterMain = AdapterMain(
            context,
            id1Female,
            category_nameFemale,
            language_nameFemale,
            genderFemale,
            traintFemale,
            durationFemale,
            url1Female
        )
        transition_to_sleep_recy?.adapter = adapterMain*/
    }

    /* private fun apiVideos() {
         val url  = "https://promask.com.co/yuru/api/sleep"

         val stringRequest = object : StringRequest(
             Method.GET, url,
             Response.Listener { response ->
                 try {
                     val obj = JSONObject(response)
                     var jsonObject = obj.getJSONObject("result")
                     val jsonArray = jsonObject.getJSONArray("data")
                     for(i in 0 until jsonArray.length()){
                         val jsonObject2 = jsonArray.getJSONObject(i)

                         val transitionToSleepAdapter = TransitionToSleepAdapter(requireContext())
                         transition_to_sleep_recy.setHasFixedSize(true)
                         transition_to_sleep_recy.layoutManager = LinearLayoutManager(context)
                         transition_to_sleep_recy.adapter = transitionToSleepAdapter

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
     }*/

}