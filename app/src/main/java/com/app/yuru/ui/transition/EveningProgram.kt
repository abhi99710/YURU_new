package com.app.yuru.ui.transition

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.corescheduler.player.video.ui.VideoActivity
import com.app.yuru.corescheduler.utils.Constants
import com.app.yuru.utility.apivolley.APIVolley
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class EveningProgram : Fragment(), View.OnClickListener {

    private lateinit var o_option: TextView
    private lateinit var c_option: TextView
    private lateinit var e_option: TextView
    private lateinit var a_option: TextView
    private lateinit var n_option: TextView
    private lateinit var save_evening: TextView

    private lateinit var gridEvening: GridView

    private lateinit var fiveEvening: TextView
    private lateinit var nineEvening: TextView
    private lateinit var female_evening: ImageView
    private lateinit var male_evening: ImageView
    private lateinit var viewAll_eve: TextView

    private var requestQueue: RequestQueue? = null

    // 5 min var male and female
    private var id1Male5: MutableList<String> = ArrayList()
    private var category_nameMale5: MutableList<String> = ArrayList()
    private var language_nameMale5: MutableList<String> = ArrayList()
    private var genderMale5: MutableList<String> = ArrayList()
    private var traintMale5: MutableList<String> = ArrayList()
    private var durationMale5: MutableList<String> = ArrayList()
    private var url1Male5: MutableList<String> = ArrayList()
    private var url1Male5Short: MutableList<String> = ArrayList()
    private var url1Male5Medium: MutableList<String> = ArrayList()
    private var url1Male5Large: MutableList<String> = ArrayList()
    private var thumbnail5Male : MutableList<String> = ArrayList()

    private var id1Female5: MutableList<String> = ArrayList()
    private var category_nameFemale5: MutableList<String> = ArrayList()
    private var language_nameFemale5: MutableList<String> = ArrayList()
    private var genderFemale5: MutableList<String> = ArrayList()
    private var traintFemale5: MutableList<String> = ArrayList()
    private var durationFemale5: MutableList<String> = ArrayList()
    private var url1Female5: MutableList<String> = ArrayList()
    private var url1Female5Short: MutableList<String> = ArrayList()
    private var url1Female5Medium: MutableList<String> = ArrayList()
    private var url1Female5Large: MutableList<String> = ArrayList()
    private var thumbnail5Female : MutableList<String> = ArrayList()


    lateinit var progressDialog: ProgressDialog // progress dialog


    // 9 min var male and female
    private var id1Male9: MutableList<String> = ArrayList()
    private var category_nameMale9: MutableList<String> = ArrayList()
    private var language_nameMale9: MutableList<String> = ArrayList()
    private var genderMale9: MutableList<String> = ArrayList()
    private var traintMale9: MutableList<String> = ArrayList()
    private var durationMale9: MutableList<String> = ArrayList()
    private var url1Male9: MutableList<String> = ArrayList()
    private var url1Male9Short: MutableList<String> = ArrayList()
    private var url1Male9Medium: MutableList<String> = ArrayList()
    private var url1Male9Large: MutableList<String> = ArrayList()
    private var thumbnail9Male : MutableList<String> = ArrayList()


    private var id1Female9: MutableList<String> = ArrayList()
    private var category_nameFemale9: MutableList<String> = ArrayList()
    private var language_nameFemale9: MutableList<String> = ArrayList()
    private var genderFemale9: MutableList<String> = ArrayList()
    private var traintFemale9: MutableList<String> = ArrayList()
    private var durationFemale9: MutableList<String> = ArrayList()
    private var url1Female9: MutableList<String> = ArrayList()
    private var url1Female9Short: MutableList<String> = ArrayList()
    private var url1Female9Medium: MutableList<String> = ArrayList()
    private var url1Female9Large: MutableList<String> = ArrayList()
    private var thumbnail9Female : MutableList<String> = ArrayList()


    private var clickedGender = ""
    private var clickedTime = ""

    private lateinit var full_video : ImageView
    private lateinit var half_video : ImageView
    private lateinit var short_video : ImageView

    private var positionNew = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_evening_program, container, false)

        findIds(view)  // find ids

        apiVideos()  // api calling

        allClick()  // click listner

        save_evening.setOnClickListener {
            val fragment = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.framwQts, TransitionToSleep())
            fragment.addToBackStack(null)
            fragment.commit()
        }

        return view
    }

    private fun allClick() {
        // short video click
        short_video.setOnClickListener {
            val intent = Intent(context, VideoActivity::class.java)
            if (clickedGender.equals("male")) {
                if (clickedTime.equals("5min")) {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Male5Short.get(positionNew)
                    )
                } else {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Male9Short.get(positionNew)
                    )
                }
            } else {
                if (clickedTime.equals("5min")) {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Female5Short.get(positionNew)
                    )
                } else {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Female9Short.get(positionNew)
                    )
                }
            }

            startActivity(intent)
        }

        // half video click
        half_video.setOnClickListener {
            val intent = Intent(context, VideoActivity::class.java)
            if (clickedGender.equals("male")) {
                if (clickedTime.equals("5min")) {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Male5Medium.get(positionNew)
                    )
                } else {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Male9Medium.get(positionNew)
                    )
                }
            } else {
                if (clickedTime.equals("5min")) {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Female5Medium.get(positionNew)
                    )
                } else {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Female9Medium.get(positionNew)
                    )
                }
            }

            startActivity(intent)
        }

        // full video click
        full_video.setOnClickListener {
            val intent = Intent(context, VideoActivity::class.java)
            if (clickedGender.equals("male")) {
                if (clickedTime.equals("5min")) {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Male5Large.get(positionNew)
                    )
                } else {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Male9Large.get(positionNew)
                    )
                }
            } else {
                if (clickedTime.equals("5min")) {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Female5Large.get(positionNew)
                    )
                } else {
                    intent.putExtra(
                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
                        url1Female9Large.get(positionNew)
                    )
                }
            }

            startActivity(intent)
        }

        // male click
        male_evening.setOnClickListener {
            context
        }

        female_evening.setOnClickListener {
            context
        }

        fiveEvening.setOnClickListener {
            context
        }

        nineEvening.setOnClickListener {
            context
        }


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
        if (view != null) {
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
            short_video = view.findViewById(R.id.short_video)
            full_video = view.findViewById(R.id.full_video)
            half_video = view.findViewById(R.id.half_video)
        }

    }

    private fun apiVideos() {
        val url = APIVolley.videosApi

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                try {
//                    progressDialog.dismiss()
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    val jsonArray = jsonObject.getJSONArray("data")

                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)

                        if (jsonObject1.getString("gender").equals("Male")) {

                            if (jsonObject1.getString("duration").equals("5 Min")) {
                                id1Male5.add(jsonObject1.getString("id"))
                                category_nameMale5.add(jsonObject1.getString("category_name"))
                                language_nameMale5.add(jsonObject1.getString("language_name"))
                                genderMale5.add(jsonObject1.getString("gender"))
                                traintMale5.add(jsonObject1.getString("traint"))
                                durationMale5.add(jsonObject1.getString("duration"))
                                url1Male5.add(jsonObject1.getString("url"))
                                url1Male5Short.add(jsonObject1.getString("short_video"))
                                url1Male5Medium.add(jsonObject1.getString("half_video"))
                                url1Male5Large.add(jsonObject1.getString("full_video"))
                                thumbnail5Male.add(jsonObject1.getString("thumb"))


                            } else {

                                id1Male9.add(jsonObject1.getString("id"))
                                category_nameMale9.add(jsonObject1.getString("category_name"))
                                language_nameMale9.add(jsonObject1.getString("language_name"))
                                genderMale9.add(jsonObject1.getString("gender"))
                                traintMale9.add(jsonObject1.getString("traint"))
                                durationMale9.add(jsonObject1.getString("duration"))
                                url1Male9.add(jsonObject1.getString("url"))
                                url1Male9Short.add(jsonObject1.getString("short_video"))
                                url1Male9Medium.add(jsonObject1.getString("half_video"))
                                url1Male9Large.add(jsonObject1.getString("full_video"))
                                thumbnail9Male.add(jsonObject1.getString("thumb"))

                            }

                        } else {
                            if (jsonObject1.getString("duration").equals("5 Min")) {
                                id1Female5.add(jsonObject1.getString("id"))
                                category_nameFemale5.add(jsonObject1.getString("category_name"))
                                language_nameFemale5.add(jsonObject1.getString("language_name"))
                                genderFemale5.add(jsonObject1.getString("gender"))
                                traintFemale5.add(jsonObject1.getString("traint"))
                                durationFemale5.add(jsonObject1.getString("duration"))
                                url1Female5.add(jsonObject1.getString("url"))
                                url1Female5Short.add(jsonObject1.getString("short_video"))
                                url1Female5Medium.add(jsonObject1.getString("half_video"))
                                url1Female5Large.add(jsonObject1.getString("full_video"))
                                thumbnail5Female.add(jsonObject1.getString("thumb"))


                            } else {
                                id1Female9.add(jsonObject1.getString("id"))
                                category_nameFemale9.add(jsonObject1.getString("category_name"))
                                language_nameFemale9.add(jsonObject1.getString("language_name"))
                                genderFemale9.add(jsonObject1.getString("gender"))
                                traintFemale9.add(jsonObject1.getString("traint"))
                                durationFemale9.add(jsonObject1.getString("duration"))
                                url1Female9.add(jsonObject1.getString("url"))
                                url1Female9Short.add(jsonObject1.getString("short_video"))
                                url1Female9Medium.add(jsonObject1.getString("half_video"))
                                url1Female9Large.add(jsonObject1.getString("full_video"))
                                thumbnail9Female.add(jsonObject1.getString("thumb"))

                            }

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
        val adapterMain = AdapterEvening(
            context,
            id1Female5,
            category_nameFemale5,
            language_nameFemale5,
            genderFemale5,
            traintFemale5,
            durationFemale5,
            url1Female5,
            thumbnail5Female
        )
        gridEvening?.adapter = adapterMain

        gridEvening?.setOnItemClickListener { parent, view, position, id ->

//            Toast.makeText(
//                context,
//                "" + category_nameFemale.get(position),
//                Toast.LENGTH_SHORT
//            ).show()

            short_video.visibility = View.VISIBLE
            half_video.visibility = View.VISIBLE
            full_video.visibility = View.VISIBLE

             val view = gridEvening.getChildAt(position);


            val intent = Intent(context, VideoActivity::class.java)
//            intent.putExtra(
//                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
//                        url1Female5.get(position)
//                    )
            if (clickedGender.equals("male")) {
                if (clickedTime.equals("5min")) {

                    positionNew = position


//                    intent.putExtra(
//                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
//                        url1Male5.get(position)
//                    )
                } else {

                    positionNew = position
//                    intent.putExtra(
//                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
//                        url1Male9.get(position)
//                    )
                }

            } else {
                if (clickedTime.equals("5min")) {

                    positionNew = position
//                    intent.putExtra(
//                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
//                        url1Female5.get(position)
//                    )
                } else {

                    positionNew = position
//                    intent.putExtra(
//                        Constants.VIDEO_LINK, /*genderMale.get(position)*/
//                        url1Female9.get(position)
//                    )
                }
            }
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        when (view?.id) {

            R.id.male_evening ->{
                clickedGender = "male"
                val adapterMain = AdapterEvening(
                    context,
                    id1Male5,
                    category_nameMale5,
                    language_nameMale5,
                    genderMale5,
                    traintMale5,
                    durationMale5,
                    url1Male5,
                    thumbnail5Male
                )
                gridEvening?.adapter = adapterMain
            }
            R.id.female_evening -> {
                clickedGender = "female"

                val adapterMain = AdapterEvening(
                    context,
                   url1Female5,
                    category_nameFemale5,
                    language_nameFemale5,
                    genderFemale5,
                    traintFemale5,
                    durationFemale5,
                    url1Female5,
                    thumbnail5Female
                )
                gridEvening?.adapter = adapterMain
            }
            R.id.fiveEvening -> {
                clickedTime = "5min"
                if (clickedGender.equals("male")){
                    val adapterMain = AdapterEvening(
                        context,
                        id1Male5,
                        category_nameMale5,
                        language_nameMale5,
                        genderMale5,
                        traintMale5,
                        durationMale5,
                        url1Male5,
                        thumbnail5Male
                    )
                    gridEvening?.adapter = adapterMain
                }else{
                    val adapterMain = AdapterEvening(
                        context,
                        url1Female5,
                        category_nameFemale5,
                        language_nameFemale5,
                        genderFemale5,
                        traintFemale5,
                        durationFemale5,
                        url1Female5,
                        thumbnail5Female
                    )
                    gridEvening?.adapter = adapterMain
                }


            }
            R.id.nineEvening -> {
                clickedTime = "9min"
                if (clickedGender.equals("male")){
                    val adapterMain = AdapterEvening(
                        context,
                        id1Male9,
                        category_nameMale9,
                        language_nameMale9,
                        genderMale9,
                        traintMale9,
                        durationMale9,
                        url1Male9,
                        thumbnail9Male
                    )
                    gridEvening?.adapter = adapterMain
                }else{
                    val adapterMain = AdapterEvening(
                        context,
                        url1Female9,
                        category_nameFemale9,
                        language_nameFemale9,
                        genderFemale9,
                        traintFemale9,
                        durationFemale9,
                        url1Female9,
                        thumbnail9Female
                    )
                    gridEvening?.adapter = adapterMain
                }
            }
        }
    }


}