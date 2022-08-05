package com.example.cz2006ver2.Account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.cz2006ver2.HomePage.HomePage1
import com.example.cz2006ver2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_groupselection.*
import kotlinx.android.synthetic.main.activity_switch_group.*

/**
 * This class allows for change of care recipient of the user
 */
class SwitchGroupActivity : AppCompatActivity() {
    /**
     * Method used to start default activity. Link back to main Transport Page.
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
    var s1: String = " "
    var s2: String = " "
    var s3: String = " "
    var s4: String = " "
    var s5: String = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_group)
        val elderUID = intent.getStringExtra("key").toString()
        // back btn
        switchGroup_BackText.setOnClickListener {
            val intent = Intent(this, GroupDetailsActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }

        getFirestore()

        sg_group1.setOnClickListener{       //for the first name only. need to make for remaining fields
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", s1)
            startActivity(intent)
        }
        sg_group2.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", s2)
            startActivity(intent)
        }
        sg_group3.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", s3)
            startActivity(intent)
        }
        sg_group4.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", s4)
            startActivity(intent)
        }
        sg_group5.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", s5)
            startActivity(intent)
        }

    }


    /**
     * Function to get the elderUID
     *
     */

    fun getFirestore() {
        var cr1 = findViewById<TextView>(R.id.sg_group1)
        val cr2 = findViewById<TextView>(R.id.sg_group2)
        val cr3 = findViewById<TextView>(R.id.sg_group3)
        val cr4 = findViewById<TextView>(R.id.sg_group4)
        val cr5 = findViewById<TextView>(R.id.sg_group5)

        val currentFirebaseUser =
            FirebaseAuth.getInstance().currentUser    //getting the user id value
        val userID = currentFirebaseUser!!.uid
        val TAG = "myLogTag"
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("users").document(userID)
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null) {
                    val list = documentSnapshot.get("careArray") as ArrayList<String>


                    val size = list.size
                    when (size) {       //convert this into a function
                        1 -> {
                            s1 = list.get(0)
                        }
                        2 -> {
                            s1 = list.get(0)
                            s2 = list.get(1)
                        }
                        3 -> {
                            s1 = list.get(0)
                            s2 = list.get(1)
                            s3 = list.get(2)
                        }
                        4 -> {
                            s1 = list.get(0)
                            s2 = list.get(1)
                            s3 = list.get(2)
                            s4 = list.get(3)
                        }
                        5 -> {
                            s1 = list.get(0)
                            s2 = list.get(1)
                            s3 = list.get(2)
                            s4 = list.get(3)
                            s5 = list.get(4)
                        }
                    }

                    getElderlyName(s1,cr1)
                    getElderlyName(s2,cr2)
                    getElderlyName(s3,cr3)
                    getElderlyName(s4,cr4)
                    getElderlyName(s5,cr5)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    /**
     * Gets the elderly name from the firebase database for display on the page.
     * @param elderUID Unique ID assigned to the elderly in firebase cloud
     * @param setText A placeholder to display elderly's name
     */
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
}