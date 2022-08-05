package com.example.cz2006ver2.Calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_calendar_caretaker2.*

/**
 * This class displays the confirmation of caretaker changes and the current date the user is on.
 */
class CalendarCaretakerActivity2 : AppCompatActivity() {
    /**
     * Method obtains the date in order to display in the page and back button functionality
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_caretaker2)
        val elderUID = intent.getStringExtra("key")
        var curr_date = intent.getStringExtra("scheduled_date")
        schedule_date2.setText(curr_date)

        calpage_caretaker2_btn.setOnClickListener {
            val intent = Intent(this, CalendarDayActivity::class.java)
            intent.putExtra("scheduled_date",curr_date)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
    }
}