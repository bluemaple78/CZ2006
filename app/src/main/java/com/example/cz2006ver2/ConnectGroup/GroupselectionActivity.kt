package com.example.cz2006ver2.ConnectGroup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cz2006ver2.HomePage.HomePage1
import com.example.cz2006ver2.LoginRegister.LoginActivity
import com.example.cz2006ver2.LoginRegister.MainActivity
import com.example.cz2006ver2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_groupselection.*
import kotlinx.android.synthetic.main.activity_login.back_button_word

/**
 * This class allows users to select which care recipients group they want to access. Users can create new groups as well.
 */
class GroupselectionActivity : AppCompatActivity() {
    var s1: String = " "
    var s2: String = " "
    var s3: String = " "
    var s4: String = " "
    var s5: String = " "

    /**
     * Method used to start default activity. Allows acesss to different care recipients
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groupselection)
        getFirestore()
        //back button to the landing page
        back_button_word.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        gs_group1.setOnClickListener{       //for the first name only. need to make for remaining fields
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", s1)
            startActivity(intent)
        }
        gs_group2.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", s2)
            startActivity(intent)
        }
        gs_group3.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", s3)
            startActivity(intent)
        }
        gs_group4.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", s4)
            startActivity(intent)
        }
        gs_group5.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", s5)
            startActivity(intent)
        }

        add_grp_btn.setOnClickListener{
            val intent = Intent(this, ConnectPageActivity::class.java)
            intent.putExtra("temp1", "0")
            startActivity(intent)
        }
    }

    /**
     * Function to get the list of elderlies tagged to the particular user logged in
     */
    fun getFirestore() {
        var cr1 = findViewById<TextView>(R.id.gs_group1)
        val cr2 = findViewById<TextView>(R.id.gs_group2)
        val cr3 = findViewById<TextView>(R.id.gs_group3)
        val cr4 = findViewById<TextView>(R.id.gs_group4)
        val cr5 = findViewById<TextView>(R.id.gs_group5)

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
