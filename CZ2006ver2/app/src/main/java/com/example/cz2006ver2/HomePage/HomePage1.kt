package com.example.cz2006ver2.HomePage

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cz2006ver2.Account.AccountMainActivity
import com.example.cz2006ver2.Calendar.CalendarMainActivity
import com.example.cz2006ver2.Home1Recyclerr
import com.example.cz2006ver2.R
import com.example.cz2006ver2.Transport.trans1
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_home_page1.*


/**
     * main function for HomePage1
     * includes buttons for navigation bar
     * includes buttons for back button
     *
     * @param savedInstanceState
     */

class HomePage1 : AppCompatActivity() { //must tag user to elderly. when we create their profile then we assign user to them?

        private var layoutManager: RecyclerView.LayoutManager? = null
        private var adapter: RecyclerView.Adapter<Home1Recyclerr.ViewHolder>? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home_page1)

            val elderUID = intent.getStringExtra("key")
            var testList: MutableList<String> = ArrayList()
            val LOG = " "
            Log.d(LOG, "this is the elderly key " + elderUID)

            displayUserName(home1introtext)
            displayTaskList(elderUID.toString())

            //////////////////get specific task based on ---//////////////////////////////////
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("careRecipient").document(elderUID.toString()).collection("task")
                .whereEqualTo("date", "2022-2-31")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
            //////////////////////////////////////////////////////////////////////////////////////

            ///////////////////testing///////////
            displayDocumentID(elderUID.toString(),testList)
            println(testList)
            print("hahahaha")
            println(testList.toString())
            ////////////////////////////////////


            ///////////////recycler////////////////////
            layoutManager = LinearLayoutManager(this)
            home1scroll.layoutManager = layoutManager
            adapter = Home1Recyclerr()
            home1scroll.adapter = adapter
            //////////////////////////////////////////


            home1_addbutton.setOnClickListener {
                val intent = Intent(this, HomePage2::class.java)
                intent.putExtra("key", elderUID)
                startActivity(intent)
            }

            home1_editbutton.setOnClickListener {
                val intent = Intent(this, HomePage2::class.java)
                startActivity(intent)
            }

            home1_deletebutton.setOnClickListener {
                val intent = Intent(this, HomePage4::class.java)
                startActivity(intent)
            }

            homeicon_page1.setOnClickListener {
                val intent = Intent(this, HomePage1::class.java)
                startActivity(intent)
            }

            calendaricon_page1.setOnClickListener {
                val intent = Intent(this, CalendarMainActivity::class.java)
                startActivity(intent)
            }

            transporticon_page1.setOnClickListener {
                val intent = Intent(this, trans1::class.java)
                startActivity(intent)
            }

            accounticon_page1.setOnClickListener {
                val intent = Intent(this, AccountMainActivity::class.java)
                startActivity(intent)
            }
        }

        /**
         * Function to return name of caretakee from Firebase
         *
         * @param elderUID ID of the caretakee
         * @param setText TextView to output the name in
         * @return A string representing the Caretakee's Name
         */
        fun getElderlyName(
            elderUID: String,
            setText: TextView
        ) {        //function for getting stuff
            val TAG = "myLogTag"
            val test = " "
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("careRecipient").document(elderUID)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val test = document.get("name").toString()
                        Log.d(TAG, ":${test}")
                        if (test != "null") {
                            setText.text = test
                        }
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }

        /**
         * Function to show the UserName of the caretaker
         *
         * @param setText TextView to output the Username in
         */
        fun displayUserName(setText: TextView) {        //function for getting stuff
            val currentFirebaseUser =
                FirebaseAuth.getInstance().currentUser    //getting the user id value
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


        /**
         * Displays the List of tasks tagged to a specific caretakee
         *
         * @param elderUID ID of the specific caretakee
         */
        fun displayTaskList(elderUID: String) {
            val db = FirebaseFirestore.getInstance()
            db.collection("careRecipient").document(elderUID).collection("task")
                .whereEqualTo("name", "hotomi1")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }

        fun displayDocumentID(elderUID: String, list: MutableList<String>) { //func to test if can pull names of doc id
            val db = FirebaseFirestore.getInstance()
            db.collection("careRecipient").document(elderUID).collection("task").get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            list.add(document.id)
                        }
                        Log.d(TAG, list.toString())
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.exception)
                    }
                })
            
        }


        //func for deleting of ddocuments by ID under task (to be done aft recyclerview finish)



}