package com.example.cz2006ver2.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.HomePage.HomePage1
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Class for main landing page of App
 *
 */
class MainActivity : AppCompatActivity() {

    /**
     * Main Function for App
     * Includes a button to move to Register Page
     * And another button to move to Login Page
     *
     * @param savedInstanceState
     */
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
    }

    override fun onBackPressed() {
    }

}