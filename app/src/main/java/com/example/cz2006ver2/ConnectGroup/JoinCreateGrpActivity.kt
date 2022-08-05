package com.example.cz2006ver2.ConnectGroup

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cz2006ver2.Account.AccountMainActivity
import com.example.cz2006ver2.HomePage.HomePage1
import com.example.cz2006ver2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join_create_grp.*
import kotlinx.android.synthetic.main.activity_join_create_grp2.*

class JoinCreateGrpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_create_grp)
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        val db = FirebaseFirestore.getInstance()
        val elderUID = intent.getStringExtra("key").toString()
        //once we enter a code, check its validity, otherwise throw an error
        //1. add the elderuid to the user's array (carearray)
        //2. add the userid to the elder's subcollection
        //3 steps

        join_create_grp_enterButton.setOnClickListener{
            val elderUID = join_create_grp_input.text.toString()
            println(elderUID)

            enterGroupCode(elderUID)// this function checks for validity. within the func, we also add to the user's array

            val docRef = db.collection("users").document(userID)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        println("persons name " + document.get("name").toString())
                        var personname = document.get("name".toString()).toString()
                        addUserToElderSubCol(elderUID.toString(),personname)   //saving the caretaker data to elderly subcollection
                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
            val intent = Intent(this, AccountMainActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }


        // back btn
        join_create_grp_BackText.setOnClickListener {
            val intent = Intent(this, AccountMainActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        // create code btn
        join_createCode.setOnClickListener{
            val intent = Intent(this, JoinCreateGrp2Activity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
    }

    /**
     * Function to add the elderly's information into Firestore
     *
     * @param length which gives the
     */

    fun enterGroupCode(elderUID: String) {
        //verify if the code exists
        //add it to the user's careRecipient list
        val TAG = "Test "
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("careRecipient").document(elderUID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    Log.d(TAG, " carerecipient found and adding to user's database")        //if it is successful, add elderKey to user's array
                    addElderKeyToUser(elderUID)
                } else {
                    Log.d(TAG, "No such document")                                      //if unsuccessful, just start intent at this page again
                    val intent = Intent(this, ConnectPageActivity::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun addElderKeyToUser(elderUID : String) {      //function for posting stuff
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        //Toast.makeText(this, "" + currentFirebaseUser!!.uid, Toast.LENGTH_SHORT).show()   just for testing
        val db = FirebaseFirestore.getInstance()

        var userRef = db.collection("users").document(userID)

// Atomically add a new region to the care array array field.
        userRef.update("careArray", FieldValue.arrayUnion(elderUID))

    }

    fun addUserToElderSubCol(elderUID : String, userName: String?){  //not done yet!!!!
        //get instance of user
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val data = GroupNamePageActivity.userNameInfo(userName)

        db.collection("careRecipient").document(elderUID).collection("caretaker").document(userID).set(data)
            .addOnSuccessListener {
                println("Record added successfully")
            }

            .addOnFailureListener{
                println("Record failed to add")
            }
    }
}