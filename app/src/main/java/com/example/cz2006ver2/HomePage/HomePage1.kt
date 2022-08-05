package com.example.cz2006ver2.HomePage

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cz2006ver2.Account.AccountMainActivity
import com.example.cz2006ver2.Calendar.CalendarMainActivity
import com.example.cz2006ver2.R
import com.example.cz2006ver2.Transport.Transport1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_home_page1.*
import kotlinx.android.synthetic.main.activity_test_transport_api.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
     * main function for HomePage1
     * includes buttons for navigation bar
     * includes buttons for back button
     *
     * @param savedInstanceState
     */

class HomePage1 : AppCompatActivity() { //must tag user to elderly. when we create their profile then we assign user to them?

    /**
     * data class to symbolise a task object
     *
     * @property name name of the task
     * @property date date of the task
     * @property time time of the task
     * @property uid uid of the care recipient to assign the task to
     * @property checked flag for whether the task is checked
     */
    data class taskObject(
            val name: String? = null,
            val date: String? = null,
            val time: String? = null,
            val uid: String? = null,
            var checked: Boolean = false,
        )

//        private var layoutManager: RecyclerView.LayoutManager? = null
//        private var adapter: RecyclerView.Adapter<Home1Recyclerr.ViewHolder>? = null
    /**
     * adapter object for use in recyclerview
     */
    private lateinit var todoAdapter: TodoAdapter

    /**
     * main function that contains necessary code for running homepage1 and its buttons
     *
     * @param savedInstanceState
     */
    @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home_page1)

            val elderUID = intent.getStringExtra("key").toString()
            val LOG = " "
            Log.d(LOG, "this is the elderly key " + elderUID)

            displayUserName(home1introtext)


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
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val todaysDate = current.format(formatter).toString()


            taskInfoFirestoreToObj(elderUID,todaysDate) //to see if i can convert into taskobject type

            /////// Recycler View Adapter initialisation ///////
            todoAdapter = TodoAdapter(arrayListOf())
            rvTodoItems.adapter = todoAdapter
            rvTodoItems.layoutManager = LinearLayoutManager(this)
            ////////////////////////////////////////////////////


            home1_addbutton.setOnClickListener {
                val intent = Intent(this, HomePage2::class.java)
                intent.putExtra("key", elderUID)
                startActivity(intent)
            }

            home1_deletebutton.setOnClickListener {
                val intent = Intent(this, HomePage4::class.java)
                intent.putExtra("key", elderUID)
                startActivity(intent)
            }

            homeicon_page1.setOnClickListener {
                val intent = Intent(this, HomePage1::class.java)
                intent.putExtra("key", elderUID)
                startActivity(intent)
            }

            calendaricon_page1.setOnClickListener {
                val intent = Intent(this, CalendarMainActivity::class.java)
                intent.putExtra("key", elderUID)
                startActivity(intent)
            }

            transporticon_page1.setOnClickListener {
                val intent = Intent(this, Transport1::class.java)
                intent.putExtra("key", elderUID)
                startActivity(intent)
            }

            accounticon_page1.setOnClickListener {
                val intent = Intent(this, AccountMainActivity::class.java)
                intent.putExtra("key", elderUID)
                startActivity(intent)
            }
            home1_deletebutton.setOnClickListener {
                if(todoAdapter.deleteDoneTodos()) {
                    val intent = Intent(this, HomePage5::class.java)
                    intent.putExtra("key", elderUID)
                    startActivity(intent)
                }
            }
            home1_completedbutton.setOnClickListener{
                var completedTasks: ArrayList<Todo> = ArrayList()
                val intent = Intent(this, HomePageCompletedActivity::class.java)
                intent.putExtra("key", elderUID)
                startActivity(intent)
                
                completedTasks = todoAdapter.checkCompleted()
                updateCheckValue(completedTasks,elderUID)
            }
        }


        /**
         * Function to show the UserName of the caretaker
         *
         * @param setText TextView to output the Username in
         */
        fun displayUserName(setText: TextView) {        //function for getting stuff
            val currentFirebaseUser =
                FirebaseAuth.getInstance().currentUser
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
     * Function to get information from firestore and convert it to a taskInfo object
     *
     * @param elderUID String which represents the ID the elderly is tagged to
     * @param date String of current date of today so we can display under daily task
     */
        fun taskInfoFirestoreToObj(elderUID: String, date: String){
            val db = FirebaseFirestore.getInstance()
            FirebaseFirestore
                .getInstance()
                .collection("careRecipient").document(elderUID).collection("task").whereEqualTo("date", date)
                .addSnapshotListener(this
                ) { querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                    if (querySnapshot != null) {
                        for (document in querySnapshot.documents) {
                            val myObject = document.toObject(taskObject::class.java)
                            if (myObject != null) {
                                val todo =  Todo(myObject.name.toString(), myObject.time.toString() , myObject.uid.toString(), elderUID, myObject.checked)
                                todoAdapter.addTodo(todo)
                            }
                        }
                    }
                }
        }

    /**
     * Function to check whether checkbox is completed
     *
     * @param elderUID which represents the ID the elderly is tagged to
     * @param taskIDList List of tasks by ID
     */
        fun updateCheckValue(taskIDList: ArrayList<Todo>, elderUID: String) {
            val db = FirebaseFirestore.getInstance()

            for (i in 0 until taskIDList.size) {
                println("task + " + taskIDList[i].taskID)

                db.collection("careRecipient").document(elderUID).collection("task").document(taskIDList[i].taskID.toString())
                    .update("checked", true)
            }
        }

}