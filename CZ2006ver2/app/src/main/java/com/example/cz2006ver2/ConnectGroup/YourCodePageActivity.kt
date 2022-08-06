package com.example.cz2006ver2.ConnectGroup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_your_code_page.*
import kotlin.random.Random

class YourCodePageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_code_page)
        val TAG = "myLogTag"
        val elderUID = getRandomString(8)   //pass this to the next page to add to firestore as UID for elderly

        var cr1 = findViewById<TextView>(R.id.yourCodeGeneratedCode)
        cr1.text = elderUID


        yourCodeNextButton.setOnClickListener{
            Log.d(TAG, "Key" + elderUID)    //for testing if match DB UID
            val intent = Intent(this, GroupNamePageActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }


    }
    /**
     * Function to add the elderly's information into Firestore
     *
     * @param length which gives the length of code that we want to generate
     */
    fun getRandomString(length: Int): String? {
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRST1234567890-=!@#$%^&*()_+".toCharArray()
        val sb = StringBuilder()
        val random = Random
        for (i in 0 until length) {
            val c = chars[random.nextInt(chars.size)]
            sb.append(c)
        }
        return sb.toString()
    }
}