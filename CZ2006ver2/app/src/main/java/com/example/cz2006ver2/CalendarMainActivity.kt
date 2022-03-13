package com.example.cz2006ver2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_calendar_main.*
import kotlinx.android.synthetic.main.activity_home_page1.*
import kotlinx.android.synthetic.main.activity_login.*

class CalendarMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_main)

        //nav bar
        homeicon_page2.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            startActivity(intent)
        }
        calendaricon_page2.setOnClickListener{
            val intent = Intent(this, CalendarMainActivity::class.java)
            startActivity(intent)
        }
        transporticon_page2.setOnClickListener{
            val intent = Intent(this, trans1::class.java)
            startActivity(intent)
        }
        accounticon_page2.setOnClickListener{
            val intent = Intent(this, AccountMainActivity::class.java)
            startActivity(intent)
        }
    }
}