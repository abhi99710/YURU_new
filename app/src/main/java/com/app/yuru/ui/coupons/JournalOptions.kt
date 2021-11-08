package com.app.yuru.ui.coupons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.yuru.R
import com.app.yuru.ui.transition.TransitionActivity

class JournalOptions : AppCompatActivity() {

    private lateinit var cl_view_entries : ConstraintLayout
    private lateinit var cl_add_entries : ConstraintLayout
    private lateinit var skipToTranlation : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_options)

        cl_add_entries = findViewById(R.id.cl_add_entries)
        cl_view_entries = findViewById(R.id.cl_view_entries)

        cl_add_entries.setOnClickListener {
            startActivity(Intent(this, JournalAdd::class.java))

        }

        cl_view_entries.setOnClickListener {
            startActivity(Intent(this, JournalList::class.java))
        }

        skipToTranlation = findViewById(R.id.skipToTranlation)
        skipToTranlation.setOnClickListener {
            startActivity(Intent(this, TransitionActivity::class.java))
        }

    }
}