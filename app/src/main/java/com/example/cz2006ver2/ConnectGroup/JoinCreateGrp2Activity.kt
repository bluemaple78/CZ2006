package com.example.cz2006ver2.ConnectGroup

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.cz2006ver2.Account.AccountMainActivity
import com.example.cz2006ver2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join_create_grp2.*
import kotlin.random.Random

class JoinCreateGrp2Activity : AppCompatActivity() {

    /**
     * Method used to start default activity. Allows users to create a new elderly group while logged in
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_create_grp2)

        var cr1 = findViewById<TextView>(R.id.join_create_grp2_code)
        val elderUIDadd = getRandomString(8).toString()
        val elderUID = intent.getStringExtra("key").toString()
        cr1.text = elderUIDadd

        // back btn
        join_create_grp2_BackText.setOnClickListener {
            val intent = Intent(this, JoinCreateGrpActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
         //enter btn
        join_create_grp2_enter.setOnClickListener{
            val intent = Intent(this, AccountMainActivity::class.java)
            saveElderInfo(join_create_grp2_input.text.toString(),elderUIDadd)
            addElderToUser(elderUIDadd)

            val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
            val userID = currentFirebaseUser!!.uid
            val db = FirebaseFirestore.getInstance()

            val docRef = db.collection("users").document(userID)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        println("persons name " + document.get("name").toString())
                        var personname = document.get("name".toString()).toString()
                        addUserToElderSubCol(elderUIDadd,personname)   //saving the caretaker data to elderly subcollection
                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
    }

    /**
     * Function to generate elderUID
     *
     * @param length which represents how lnog u want the code to be
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

    /**
     * Function to add the elderly's information into Firestore
     *
     * @param elderName which represents the name of the elderly
     * @param elderUID which represets the ID tagged to each elderly
     */

    fun saveElderInfo(elderName: String,elderUID : String) {    //adding elder to database
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        //Toast.makeText(this, "" + currentFirebaseUser!!.uid, Toast.LENGTH_SHORT).show()   just for testing
        val db = FirebaseFirestore.getInstance()

        val data = GroupNamePageActivity.elderInfo(elderName)

        db.collection("careRecipient").document(elderUID).set(data)
            .addOnSuccessListener {
                println("Care recipient added successfully")
            }

            .addOnFailureListener{
                println("Record failed to add")
            }
    }

    /**
     * Function to add the elderly's information into the user's account in Firestore
     *
     * @param elderUID which represents the ID tagged to a specific elderly
     */

    fun addElderToUser(elderUID : String) {      // add to the users array of carerecipient
        //get instance of user
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val data =
            GroupNamePageActivity.elderInfo(elderUID) //check on top to find data that we add in. need to add more eventually
        val userCareRecipientArray = db.collection("users").document(userID)

        userCareRecipientArray.update("careArray", FieldValue.arrayUnion(elderUID))
            .addOnSuccessListener {
                println("Record added successfully")
            }

            .addOnFailureListener{
                println("Record failed to add")
            }
    }

    /**
     * Function to add the elderly's information into the user's account in Firestore
     *
     * @param elderUID which represents the ID tagged to a specific elderly
     * @param userName which represents the name tagged to a caretaker which is added to the elderly's subcollection
     */


    fun addUserToElderSubCol(elderUID : String, userName: String?){
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