package com.example.cz2006ver2.HomePage

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_home_page7.*

/**
 * Page to confirm task edition details
 *
 */
class HomePage7 : AppCompatActivity() {

    /**
     * Page main functionality
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page7)
        val elderUID = intent.getStringExtra("key").toString()//get uid from other page

        Log.d(ContentValues.TAG,"home7" + elderUID)
        displaytext()

        home7_returnbutton.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", elderUID) //return back to first page again???
            startActivity(intent)
        }
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

        val tvdesc: TextView = findViewById(R.id.home7_user_desc_text)
        val tvtime: TextView = findViewById(R.id.home7_user_time_text)
        val tvdate: TextView = findViewById(R.id.home7_user_date_text)

        tvdesc.text = desc
        tvtime.text = time
        tvdate.text = date
    }
}