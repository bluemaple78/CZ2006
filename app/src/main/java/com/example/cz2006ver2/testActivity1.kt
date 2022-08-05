package com.example.cz2006ver2

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_test1.*


class testActivity1 : AppCompatActivity() {


    private lateinit var testUserName: EditText
    private lateinit var testUserPhoneNum: EditText

    data class userInfo(        //data that we are passing in, add on to userInfo if want to ask for more
        val name: String? = null,
        val num: String? = null,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)

        testSubmitInfo.setOnClickListener{
            testUserName = findViewById(R.id.testEnterName)     //this is an edittext object?? need to convert to string first
            testUserPhoneNum = findViewById(R.id.testEnterPhoneNum)

            val name = testUserName.text.toString()     //convert to string at this stage
            val num = testUserPhoneNum.text.toString()
            saveFireStore(name,num)
            getFireStore()
        }
    }

    fun saveFireStore(name: String, num: String) {      //function for posting stuff
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        //Toast.makeText(this, "" + currentFirebaseUser!!.uid, Toast.LENGTH_SHORT).show()   just for testing
        val db = FirebaseFirestore.getInstance()

        val data = userInfo(name,num)

        db.collection("users").document(userID).set(data)
            .addOnSuccessListener {
                Toast.makeText(this@testActivity1, "record added successfully ", Toast.LENGTH_SHORT ).show()
            }

            .addOnFailureListener{
                Toast.makeText(this@testActivity1, "record Failed to add ", Toast.LENGTH_SHORT ).show()
            }
    }

    fun getFireStore() {        //function for getting stuff
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser    //getting the user id value
        val userID = currentFirebaseUser!!.uid
        val TAG = "myLogTag"
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(userID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
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