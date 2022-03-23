package com.example.cz2006ver2

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cz2006ver2.HomePage.HomePage1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_group_name_page.*

class GroupNamePageActivity : AppCompatActivity() {

    private lateinit var elderName: EditText
    data class elderInfo(
        //data that we are passing in, add on to userInfo if want to ask for more
        val name: String? = null,
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_name_page)

        val elderUID = intent.getStringExtra("key")

        groupnameNextButton.setOnClickListener{
            elderName = findViewById(R.id.groupNameField)
            val elderNameString = elderName.text.toString()     //convert to string at this stage
            if (elderUID != null) {
                saveElderInfo(elderNameString,elderUID)
                addElderToUser(elderUID)
            }
            val intent = Intent(this, HomePage1::class.java)
            startActivity(intent)
        }
    }




    fun saveElderInfo(elderName: String,elderKey : String) {      //function for posting stuff
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        //Toast.makeText(this, "" + currentFirebaseUser!!.uid, Toast.LENGTH_SHORT).show()   just for testing
        val db = FirebaseFirestore.getInstance()

        val data = elderInfo(elderName) //check on top to find data that we add in. need to add more eventually

        db.collection("careRecipient").document(elderKey).set(data)
            .addOnSuccessListener {
                Toast.makeText(this@GroupNamePageActivity, "record added successfully ", Toast.LENGTH_SHORT ).show()
            }

            .addOnFailureListener{
                Toast.makeText(this@GroupNamePageActivity, "record Failed to add ", Toast.LENGTH_SHORT ).show()
            }
    }


    fun addElderToUser(elderUID : String) {      //function for posting stuff
        //get instance of user
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val data = elderInfo(elderUID) //check on top to find data that we add in. need to add more eventually
        val userCareRecipientArray = db.collection("users").document(userID)

        userCareRecipientArray.update("careArray", FieldValue.arrayUnion(elderUID))
            .addOnSuccessListener {
                Toast.makeText(this@GroupNamePageActivity, "record added successfully ", Toast.LENGTH_SHORT ).show()
            }

            .addOnFailureListener{
                Toast.makeText(this@GroupNamePageActivity, "record Failed to add ", Toast.LENGTH_SHORT ).show()
            }
    }

}