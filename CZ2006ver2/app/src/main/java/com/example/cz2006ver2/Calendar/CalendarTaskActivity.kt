package com.example.cz2006ver2.Calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_calendar_task.*

class CalendarTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_task)

        //back button to the calendar day page
        back_word_cal3.setOnClickListener{
            val calDayBackBtn = Intent(this, CalendarDayActivity::class.java)
            startActivity(calDayBackBtn)
        }
        // confirm button
        cal_task_btn.setOnClickListener {
            val intent = Intent(this, CalendarDayActivity::class.java)
            startActivity(intent)
        }
    }
}