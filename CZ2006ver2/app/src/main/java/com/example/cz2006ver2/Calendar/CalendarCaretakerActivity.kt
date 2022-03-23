package com.example.cz2006ver2.Calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_calendar_caretaker.*

class CalendarCaretakerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_caretaker)

        //back button to the calendar day page
        back_word_cal2.setOnClickListener{
            val cal2BackBtn = Intent(this, CalendarDayActivity::class.java)
            startActivity(cal2BackBtn)
        }
        // confirm btn
        cal_caretaker_changebtn.setOnClickListener{
            val calConfirmBtn = Intent(this, CalendarCaretakerActivity2::class.java)
            startActivity(calConfirmBtn)
        }
    }
}