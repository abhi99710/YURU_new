package com.app.yuru.ui.transition

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.app.yuru.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class TransitionActivity : AppCompatActivity() {
    lateinit var bottomNavigationView : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition)



        val firstFragment= TransitionToSleep()
        val secondFragment=SleepEnhancer()
        val thirdFragment=SleepEnhancer2()
        val fourthFragment=SubliminalProgram()
        val fifthFragment = WakeUpProgram()
        setCurrentFragment(firstFragment)


        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.home_nv->setCurrentFragment(firstFragment)
               R.id.journal_nv->setCurrentFragment(secondFragment)
                R.id.wakeup_nv->setCurrentFragment(thirdFragment)
                R.id.sleep_nv->setCurrentFragment(fourthFragment)
                R.id.english_nv->setCurrentFragment(fifthFragment)
            }
            true
        }

    }

   private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
           replace(R.id.nav_host_homepage,fragment)
           commit()
        }
}