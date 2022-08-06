package com.example.cz2006ver2.Account

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cz2006ver2.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_calendar_day.*
import kotlinx.android.synthetic.main.activity_group_details.*

/**
 * This class allows users to display the care recipient's group details, caretakers and group code. The user can switch to another care recipient
 */
class GroupDetailsActivity : AppCompatActivity() {
    /**
     * Method used to start default activity
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)

        val elderUID = intent.getStringExtra("key").toString()
        findCareRecipientName(elderUID)
        groupDetails_GroupCode.text = elderUID.toString()
        findCaretakerNames(elderUID)
        // back btn
        groupDetails_BackText.setOnClickListener {
            val intent = Intent(this, AccountMainActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        // switch groups btn
        groupDetails_EditProfileButton.setOnClickListener {
            val intent = Intent(this, SwitchGroupActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
    }

    /**
     * Function to find the name of the care recipients
     *
     * @param elderUID which represents the ID tagged to a specific elderly
     */

    fun findCareRecipientName(elderUID: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("careRecipient").document(elderUID)
            .get()
            .addOnSuccessListener { documents ->
                println(documents.get("name").toString() + " THIS IS THE NAME")
                groupDetails_CareRecipientName.text = documents.get("name").toString()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    /**
     * Function to find the names of care recipients which is placed in a string format
     *
     * @param elderUID which represents the ID tagged to a specific elderly
     */

    fun findCaretakerNames(elderUID : String) {

        var myList: ArrayList<String> = arrayListOf()
        val db = FirebaseFirestore.getInstance()
        db.collection("careRecipient").document(elderUID).collection("caretaker")
            .get()
            .addOnSuccessListener { documents ->
                for (i in documents) {
                    myList.add(i.get("name").toString())
                }
                var nameString = myList.joinToString(separator = ", ")
                println("name string"  + nameString)
                groupDetails_OtherCaretakers.text = nameString
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

}