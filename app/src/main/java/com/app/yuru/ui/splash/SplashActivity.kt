package com.app.yuru.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.app.yuru.R
import com.app.yuru.ui.IntroNewVideos.IntroductionVideoActivity
import com.app.yuru.ui.login.LoginActivity
import com.app.yuru.ui.transition.TransitionActivity
import java.util.*

class SplashActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private val handler = Handler(Looper.getMainLooper())
    private val langList: MutableList<String> = ArrayList()
    private lateinit var buttonSplashSubmit : Button
    private lateinit var textView33 : TextView
    private lateinit var loginOnSplash : Button

    var apiLangVar = ""
    private val runnable = Runnable {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
//            startActivity(Intent(this, PermissionActivity::class.java))
//        } else {
//        startActivity(Intent(this, Splash2::class.java))
//            startActivity(Intent(this, TransitionActivity::class.java))
//        startActivity(Intent(this, Survey::class.java))
//        startActivity(Intent(this, CalenderV::class.java))

            Log.e("bb ", Locale.getDefault().getDisplayLanguage())

            val sh = applicationContext.getSharedPreferences("share", Context.MODE_PRIVATE)

            val getSelectlanguage = sh.getString("isSelectlanguage", "no")

            val idh1 = sh.getString("id", "")
            if(!idh1.toString().equals("")){
                spinner.visibility = View.INVISIBLE
                buttonSplashSubmit.visibility = View.INVISIBLE
                textView33.visibility = View.INVISIBLE
                startActivity(Intent(this, TransitionActivity::class.java))


//                startActivity(Intent(this, LowvsHigh::class.java))
//               Intent(this, MainRocket::class.java)
//                intent.putExtra("first_rocket", "latest")
//                startActivity(intent)
            }else{
                startActivity(Intent(this, IntroductionVideoActivity::class.java))
            }

        if (getSelectlanguage != null) {
            if(getSelectlanguage.equals("no")){
//                handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(20000))
            }else{
//                handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(1))
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
//        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        spinner = findViewById(R.id.spinnerLanguageSplash)

        textView33 = findViewById(R.id.textView33)

        loginOnSplash = findViewById(R.id.loginOnSplash)
        loginOnSplash.setOnClickListener({
            startActivity(Intent(this, LoginActivity::class.java))
        })

        langList.add("Select")
        langList.add("English") // en

        langList.add("Hindi") // hi

        langList.add("Spanish") // es

        langList.add("Russian") // ru

        langList.add("Chinese") // zh

        langList.add("Japnese") // ja

        langList.add("Korean") // ko

        selectLanguage()

        val sh = applicationContext.getSharedPreferences("share", Context.MODE_PRIVATE)

        val getSelectlanguage = sh.getString("isSelectlanguage", "no")

//        if (getSelectlanguage != null) {
//            if(getSelectlanguage.equals("no")){
//                handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(20000))
//            }else{
//                handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(1))
//            }
//        }

        buttonSplashSubmit = findViewById(R.id.buttonSplashSubmit)
        buttonSplashSubmit.setOnClickListener({
            val sharedPreferences: SharedPreferences = getSharedPreferences("share", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("isSelectlanguage","yes")
            editor.apply()
            editor.commit()
            if(!apiLangVar.equals("")){
                startActivity(Intent(this, IntroductionVideoActivity::class.java))
            }else{
                Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onResume() {
        super.onResume()
//        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(20000))
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    private fun selectLanguage() {
        val dataAdapter =
            ArrayAdapter(this, R.layout.spinner_layout_background, langList)

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter)
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
//                    mediumIdPosition = mediumId.get(position);
//                    classResponse();
                    apiLangVar = langList.get(position)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
//                checkSum = "not selected";
            }
        })
    }


}