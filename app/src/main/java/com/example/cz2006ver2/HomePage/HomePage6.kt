package com.example.cz2006ver2.HomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_home_page6.*

/**
 * Class for Home Page 6
 * Page to select task to edit
 */
class HomePage6 : AppCompatActivity() {

    /**
     * Main Function for HomePage6
     * Includes back button to return to the previous page
     * Includes confirm button to confirm editing of selected task
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page6)

        home6_confirmbutton.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            startActivity(intent)
        }

        home6_back_button_word.setOnClickListener {
            val intent = Intent(this, HomePage1::class.java)
            startActivity(intent)
        }
    }
}