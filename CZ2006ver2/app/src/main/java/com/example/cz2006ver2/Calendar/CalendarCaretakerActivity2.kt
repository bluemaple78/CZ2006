package com.example.cz2006ver2.Calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_calendar_caretaker2.*

class CalendarCaretakerActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_caretaker2)

        calpage_caretaker2_btn.setOnClickListener {
            val intent = Intent(this, CalendarDayActivity::class.java)
            startActivity(intent)
        }
    }
}