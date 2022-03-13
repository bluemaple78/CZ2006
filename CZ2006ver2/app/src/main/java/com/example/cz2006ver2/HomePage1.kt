package com.example.cz2006ver2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_page1.*

class HomePage1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page1)
        val elderUID = intent.getStringExtra("key")

        displayUserName(home1introtext)

        home1_addbutton.setOnClickListener{
            val intent = Intent(this, HomePage2::class.java)
            startActivity(intent)
        }

        home1_editbutton.setOnClickListener{
            val intent = Intent(this, HomePage2::class.java)
            startActivity(intent)
        }

        home1_deletebutton.setOnClickListener{
            val intent = Intent(this, HomePage4::class.java)
            startActivity(intent)
        }

        homeicon_page1.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            startActivity(intent)
        }

        calendaricon_page1.setOnClickListener{
            val intent = Intent(this, CalendarMainActivity::class.java)
            startActivity(intent)
        }

        transporticon_page1.setOnClickListener{
            val intent = Intent(this, trans1::class.java)
            startActivity(intent)
        }

        accounticon_page1.setOnClickListener{
            val intent = Intent(this, AccountMainActivity::class.java)
            startActivity(intent)
        }
    }

    fun getElderlyName(elderUID: String, setText: TextView) {        //function for getting stuff
        val TAG = "myLogTag"
        val test = " "
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("careRecipient").document(elderUID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val test  = document.get("name").toString()
                    Log.d(TAG,":${test}")
                    if (test != "null"){
                        setText.text = test}
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun displayUserName(setText: TextView) {        //function for getting stuff
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser    //getting the user id value
        val userID = currentFirebaseUser!!.uid
        val TAG = "myLogTag"
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(userID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    setText.text = "Hello, " + document.get("name").toString()
                    Log.d(TAG, "Our data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}