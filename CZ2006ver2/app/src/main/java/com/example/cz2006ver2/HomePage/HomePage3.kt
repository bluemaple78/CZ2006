package com.example.cz2006ver2.HomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_home_page3.*

class HomePage3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page3)

        home3_returnbutton.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            startActivity(intent)
        }

        displaytext()


    }

    fun displaytext()
    {
        val desc = intent.getStringExtra("desc")
        val time = intent.getStringExtra("time")
        val date = intent.getStringExtra("date")
        val spin = intent.getStringExtra("spin")

        val tvdesc: TextView = findViewById(R.id.home3_user_desc_text)
        val tvtime: TextView = findViewById(R.id.home3_user_time_text)
        val tvdate: TextView = findViewById(R.id.home3_user_date_text)
        val tvspin: TextView = findViewById(R.id.home3_user_reccuring_text)

        tvdesc.text = desc
        tvtime.text = time
        tvdate.text = date
        tvspin.text = spin
    }
}