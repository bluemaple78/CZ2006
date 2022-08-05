package com.example.cz2006ver2.HomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.Calendar.CalendarDayActivity
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_home_page5.*

/**
 * Class for HomePage5
 * Page to display task deletion success message
 */
class HomePage5 : AppCompatActivity() {

    /**
     * Main Function for HomePage5
     *
     * @param savedInstanceState
     */
    var curr_date : String = "null"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page5)
        val elderUID = intent.getStringExtra("key").toString()
        curr_date = intent.getStringExtra("scheduled_date").toString()
        home5_returnbutton.setOnClickListener {
            if(curr_date != "null"){

                val intent = Intent(this, CalendarDayActivity::class.java)
                intent.putExtra("key", elderUID)
                intent.putExtra("scheduled_date", curr_date)
                startActivity(intent)
            }
            else{
                val intent = Intent(this, HomePage1::class.java)
                intent.putExtra("key", elderUID)
                startActivity(intent)
            }

        }
    }
}