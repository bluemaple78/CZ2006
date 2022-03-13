package com.example.cz2006ver2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginbutton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        regbutton.setOnClickListener{
            val regIntent = Intent(this, RegisterActivity::class.java)
            startActivity(regIntent)
        }

        //delete this portion after the API thing
        button4.setOnClickListener{
            val testIntent = Intent(this, TestTransportAPI::class.java)
            startActivity(testIntent)
        }

        button5.setOnClickListener{
            val testIntent = Intent(this, GoogleMapsTest::class.java)
            startActivity(testIntent)
        }
    }
}