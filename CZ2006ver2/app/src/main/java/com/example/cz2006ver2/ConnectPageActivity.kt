package com.example.cz2006ver2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_connect_page.*
import kotlinx.android.synthetic.main.activity_register2.*

class ConnectPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_page)

        //this is for when they do not have an account
        createCode.setOnClickListener{
            val intent = Intent(this, YourCodePageActivity::class.java)
            startActivity(intent)
        }

        connectEnterButton.setOnClickListener{
            val groupCode = connectGroupCodeField.text
            enterGroupCode(groupCode.toString())
            val intent = Intent(this, YourCodePageActivity::class.java)
            startActivity(intent)
        }
    }
    
    //As for people that just registered and have a group code as well
    fun enterGroupCode(groupCode: String) {
        //verify if the code exists
        //add it to the user's careRecipient list
        val TAG = "Test "
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("careRecipient").document(groupCode)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    Log.d(TAG, " carerecipient found and adding to user's database")
                    addElderKeyToUser(groupCode)
                } else {
                    Log.d(TAG, "No such document")
                    val intent = Intent(this, ConnectPageActivity::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun addElderKeyToUser(elderKey : String) {      //function for posting stuff
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        //Toast.makeText(this, "" + currentFirebaseUser!!.uid, Toast.LENGTH_SHORT).show()   just for testing
        val db = FirebaseFirestore.getInstance()

        var userRef = db.collection("users").document(userID)

// Atomically add a new region to the care array array field.
        userRef.update("careArray", FieldValue.arrayUnion(elderKey))

    }
}