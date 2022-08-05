package com.example.cz2006ver2.HomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_home_page4.*

/**
 * Class for HomePage4
 * Page to delete tasks
 */
class HomePage4 : AppCompatActivity() {

    /**
     * Main Function of HomePage4
     * Includes Back Button to return to previous page
     * And confirm button to confirm task deletion
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page4)
        val elderUID = intent.getStringExtra("key").toString()

        home4_confirmbutton.setOnClickListener{
            val intent = Intent(this, HomePage5::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }

        home4_back_button_word.setOnClickListener {
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
    }
}