package com.example.cz2006ver2.HomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.Calendar.CalendarDayActivity
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_home_page5.*
import kotlinx.android.synthetic.main.activity_home_page_completed.*

/**
 * Confirmation page when tasks have been marked as completed
 *
 */
class HomePageCompletedActivity : AppCompatActivity() {

    /**
     * Function that contains main functionalities of page and buttons in page
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_completed)
        val elderUID = intent.getStringExtra("key").toString()
        homecomplete_returnbutton.setOnClickListener {

            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
    }
}