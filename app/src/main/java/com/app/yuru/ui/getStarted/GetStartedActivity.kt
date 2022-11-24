package com.app.yuru.ui.getStarted

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.app.yuru.R
import com.app.yuru.coreandroid.base.BaseActivityBinding
import com.app.yuru.databinding.ActivityGetStartedBinding
import com.app.yuru.ui.test.TestActivity
import java.util.*

const val ARG_NAME = "nameLogin"

class GetStartedActivity : BaseActivityBinding<ActivityGetStartedBinding>() {
    private lateinit var tts_vids: VideoView

    private val spinnerLanguage: Spinner? = null
    private  var spinnerAge:Spinner? = null
    private  var spinnerGender:Spinner? = null
    private val ageList: MutableList<String> = ArrayList()
    private val genderList: MutableList<String> = ArrayList()
    var selectedAge = ""
    var selectedGender = ""

    override val bindingInflater: (LayoutInflater) -> ActivityGetStartedBinding
        get() = ActivityGetStartedBinding::inflate

    override fun setupView(binding: ActivityGetStartedBinding) {
        val name = intent.getStringExtra(ARG_NAME)
//        binding.tvName.text = getString(R.string.hi_s, name)
        binding.btnGetStarted.setOnClickListener {
            if(selectedAge.equals("")){
                Toast.makeText(this, "Please select age", Toast.LENGTH_SHORT).show()
            }else if(selectedGender.equals("")){
                Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(this, TestActivity::class.java))
                finish()
            }

        }

        spinnerAge = findViewById(R.id.spinnerAgeGet)
        spinnerGender = findViewById(R.id.spinnerGenderGet)

        tts_vids = findViewById(R.id.tts_getStarted)
        videoPlay()

        ageList.add("Select")
        ageList.add("18-25")
        ageList.add("26-45")
        ageList.add("46-65")
        ageList.add("66+")


        genderList.add("Select")
        genderList.add("Male")
        genderList.add("Female")

        selectAge()
        selectGender()

    }

    private fun videoPlay() {
        val ctlr = MediaController(this)
        ctlr.setMediaPlayer(tts_vids)
//        tts_vids.setMediaController(ctlr)

        val uri =
            Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.getstarted);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.com/hearts.mp4");

//                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.lop);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.
        //        com/hearts.mp4");
//        tts_vids.setMediaController(ctlr)

        //        videoView.setVideoURI(uri);

        tts_vids.setVideoURI(uri);
//        tts_vids.setVideoPath("https://invoiz-assets.s3.amazonaws.com/hearts.mp4")
        tts_vids.start()

    }

    private fun selectGender() {
        val dataAdapter =
            ArrayAdapter(this, R.layout.spinner_layout_background, genderList)

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // attaching data adapter to spinner
        spinnerGender!!.adapter = dataAdapter
        spinnerGender!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {

                    selectedGender = genderList.get(position)

                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
                //                checkSum = "not selected";
            }
        }
    }

    private fun selectAge() {
        val dataAdapter =
            ArrayAdapter(this, R.layout.spinner_layout_background, ageList)

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // attaching data adapter to spinner
        spinnerAge!!.adapter = dataAdapter
        spinnerAge!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {

                    selectedAge = ageList.get(position)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
                //                checkSum = "not selected";
            }
        }
    }
}