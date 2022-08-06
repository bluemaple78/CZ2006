package com.example.cz2006ver2.HomePage

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.cz2006ver2.Calendar.CalendarDayActivity
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_home_page3.*

/**
 * Class for HomePage3
 *
 */
class HomePage3 : AppCompatActivity() {

    /**
     * Variable to represent the current date saved as a string
     * Initialised as "null"
     */
    var curr_date : String = "null"

    /**
     * Main function for HomePage3 Logic
     * Page to display and confirm inputs from HomePage2
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page3)
        val elderUID = intent.getStringExtra("key").toString()//get uid from other page
        curr_date = intent.getStringExtra("scheduled_date").toString()
        Log.d(ContentValues.TAG,"home3" + elderUID)

        home3_returnbutton.setOnClickListener{
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


        displaytext()


    }

    /**
     * Function to display text added to intent from HomePage2
     * Text strings will be updated in respective TextViews
     *
     */
    fun displaytext()
    {
        val desc = intent.getStringExtra("desc")
        val time = intent.getStringExtra("time")
        val date = intent.getStringExtra("date")

        val tvdesc: TextView = findViewById(R.id.home3_user_desc_text)
        val tvtime: TextView = findViewById(R.id.home3_user_time_text)
        val tvdate: TextView = findViewById(R.id.home3_user_date_text)

        tvdesc.text = desc
        tvtime.text = time
        tvdate.text = date
     }

}