package com.example.cz2006ver2.Calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_calendar_day.*

class CalendarDayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_day)

        //back button to the calendar main page
        back_word_cal1.setOnClickListener{
            val calDayBackBtn = Intent(this, CalendarMainActivity::class.java)
            startActivity(calDayBackBtn)
        }
        // edit caretaker btn
        edit_caretaker_btn.setOnClickListener{
            val calEditCaretakerBtn = Intent(this, CalendarCaretakerActivity::class.java)
            startActivity(calEditCaretakerBtn)
        }
        // edit task btn
        edit_tasks_btn.setOnClickListener{
            val calEditTaskBtn = Intent(this, CalendarTaskActivity::class.java)
            startActivity(calEditTaskBtn)
        }
    }
}