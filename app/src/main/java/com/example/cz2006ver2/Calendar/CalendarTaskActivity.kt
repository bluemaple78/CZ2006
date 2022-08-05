package com.example.cz2006ver2.Calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_calendar_task.*

/**
 * This class allows creating, completing and deleting of tasks for the day.
 */
class CalendarTaskActivity : AppCompatActivity() {
    /**
     * Method used to modify tasks.
     *
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_task)

        var curr_date = intent.getStringExtra("scheduled_date")
        schedule_date3.setText(curr_date)

        //back button to the calendar day page
        back_word_cal3.setOnClickListener{
            val calDayBackBtn = Intent(this, CalendarDayActivity::class.java)
            calDayBackBtn.putExtra("scheduled_date",curr_date)
            startActivity(calDayBackBtn)
        }
    }
}