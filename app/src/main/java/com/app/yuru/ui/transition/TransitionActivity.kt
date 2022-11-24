package com.app.yuru.ui.transition

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.yuru.R
import com.app.yuru.ui.coupons.JournalOptions
import com.app.yuru.ui.coupons.Journals
import com.app.yuru.ui.discounts.MainRocket
import com.google.android.material.bottomnavigation.BottomNavigationView

class TransitionActivity : AppCompatActivity() {
    lateinit var bottomNavigationView : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition)



        val firstFragment= TransitionToSleep()
        val secondFragment=SleepEnhancer()
        val thirdFragment=SleepEnhancer2()

        val fifthFragment = WakeUpProgram()
        setCurrentFragment(firstFragment)


        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.home_nv->setCurrentFragment(TransitionToSleep())
                R.id.sleep_nv->setCurrentFragment(SleepEnhancer())
                R.id.evening_nv->setCurrentFragment(SleepEnhancer2())
                R.id.wakeup_nv->setCurrentFragment(WakeUpProgram())
               R.id.journal_nv->{
                   val intent = Intent(this, MainRocket::class.java)
                   intent.putExtra("first_rocket","tts")
                   startActivity(intent)
               }



            }
            true
        }

    }

   private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
           replace(R.id.framwQts,fragment)
           commit()
        }
}