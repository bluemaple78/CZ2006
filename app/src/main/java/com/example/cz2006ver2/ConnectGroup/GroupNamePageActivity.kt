package com.example.cz2006ver2.ConnectGroup

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cz2006ver2.HomePage.HomePage1
import com.example.cz2006ver2.LoginRegister.MainActivity
import com.example.cz2006ver2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_group_name_page.*


class GroupNamePageActivity : AppCompatActivity() {

    private lateinit var elderName: EditText
    data class elderInfo(
        //data that we are passing in, add on to userInfo if want to ask for more
        val name: String? = null,
    )
    data class userNameInfo(        //data that we are passing in, add on to userInfo if want to ask for more
        val name: String? = null,
    )
    /**
     * Method used to start default activity. Allows user to enter in name for the elderly that they are taking care of
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_name_page)

        val elderUID = intent.getStringExtra("key").toString()

        //get user's name and add into subcollection for elderly///////
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("users").document(userID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    println("persons name " + document.get("name").toString())
                    var personname = document.get("name".toString()).toString()
                    addUserToElderSubCol(elderUID,personname)   //saving the caretaker data to elderly subcollection
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        ////////////////////////////.////////////////////////////////////////




        //direct them to homepage after registering
        groupnameNextButton.setOnClickListener{
            elderName = findViewById(R.id.groupNameField)
            val elderNameString = elderName.text.toString()     //convert to string at this stage
            if (elderUID != null && elderNameString.length > 0) {
                saveElderInfo(elderNameString,elderUID)
                addElderToUser(elderUID)
                val intent = Intent(this, HomePage1::class.java)
                intent.putExtra("key", elderUID)

                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_LONG).show()
            }
        }

    }


    /**
     * Function to add the elderly's information into Firestore
     *
     * @param elderUID String which represents the ID the elderly is tagged to
     * @param elderName String which represents the name of the elderly
     */

    fun saveElderInfo(elderName: String,elderUID : String) {    //adding elder to database
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        //Toast.makeText(this, "" + currentFirebaseUser!!.uid, Toast.LENGTH_SHORT).show()   just for testing
        val db = FirebaseFirestore.getInstance()

        val data = elderInfo(elderName)

        db.collection("careRecipient").document(elderUID).set(data)
            .addOnSuccessListener {
                println("Record added successfully")
            }

            .addOnFailureListener{ Toast.makeText(this@GroupNamePageActivity, "record Failed to add ", Toast.LENGTH_SHORT ).show()
                println("Record failed to add")
            }
    }

    /**
     * Function to add the elderly's information into Firestore
     *
     * @param elderUID String which represents the ID the elderly is tagged to
     */
    fun addElderToUser(elderUID : String) {      // add to the users array of carerecipient
        //get instance of user
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val data = elderInfo(elderUID) //check on top to find data that we add in. need to add more eventually
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
     * Function to add the elderly's information into Firestore
     *
     * @param elderUID String which represents the ID the elderly is tagged to
     * @param userName String which represents the name of the user tagged to the elderly
     */
    fun addUserToElderSubCol(elderUID : String, userName: String?){
        //get instance of user
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val data = userNameInfo(userName)

        db.collection("careRecipient").document(elderUID).collection("caretaker").document(userID).set(data)
            .addOnSuccessListener {
                println("Record added successfully")
            }

            .addOnFailureListener{
                println("Record failed to add")
            }
    }

}