package com.example.cz2006ver2.Calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cz2006ver2.HomePage.*
import com.example.cz2006ver2.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_calendar_day.*
import kotlinx.android.synthetic.main.activity_home_page1.*

/**
 * This class displays the tasks and the caretaker in charge for the a certain day. It also provides options to change the caretaker and edit tasks.
 */
class CalendarDayActivity : AppCompatActivity() {

    private lateinit var CalenderTodoAdapter: CalenderTodoAdapter

    /**
     * Method used to start default activity. Edit caretaker button and Edit tasks button are for users to edit accordingly
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_day)
        val elderUID = intent.getStringExtra("key")
        // calendar date
        val curr_date = intent.getStringExtra("scheduled_date").toString()
        //// RecyclerView initialisation ////
        CalenderTodoAdapter = CalenderTodoAdapter(arrayListOf())
        rvCalander.adapter = CalenderTodoAdapter
        rvCalander.layoutManager = LinearLayoutManager(this)
        ///////////////////////////////////////////

        schedule_date.setText(curr_date)

        // fetch tasks from database
        testFirestore(elderUID.toString(), curr_date.toString())

        //back button to the calendar main page
        back_word_cal1.setOnClickListener{
            val intent = Intent(this, CalendarMainActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        // edit caretaker btn
        edit_caretaker_btn.setOnClickListener{
            val intent = Intent(this, CalendarCaretakerActivity::class.java)
            intent.putExtra("scheduled_date",curr_date)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        calenderday_deletebutton.setOnClickListener {
            if(CalenderTodoAdapter.deleteDoneTodos()) {
                val intent = Intent(this, HomePage5::class.java)
                intent.putExtra("key", elderUID)
                intent.putExtra("scheduled_date" , curr_date)
                startActivity(intent)
            }
        }
        calenderday_addbutton.setOnClickListener(){
            val intent = Intent(this, HomePage2::class.java)
            intent.putExtra("key", elderUID)
            intent.putExtra("scheduled_date" , curr_date)
            startActivity(intent)
        }
    }

    fun testFirestore(elderUID: String, date: String){
        //define taskObject type
        var testList: MutableList<HomePage1.taskObject> = ArrayList()

        val db = FirebaseFirestore.getInstance()
        FirebaseFirestore
            .getInstance()
            .collection("careRecipient").document(elderUID).collection("task").whereEqualTo("date", date)
            .addSnapshotListener(this
            ) { querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                if (querySnapshot != null) {
                    for (document in querySnapshot.documents) {
                        val myObject = document.toObject(HomePage1.taskObject::class.java)
                        if (myObject != null) {
                            testList.add(myObject)
                            println("calender myObject taskID is " + myObject.uid)
                            println("calender myObject desciption is " + myObject.name)
                            println("calender myObject time is" + myObject.time)
                            println("calender myObject elderID is " + elderUID)
                            val calenderTodo =  CalenderTodo(myObject.name.toString(), myObject.time.toString() , myObject.uid.toString(), elderUID)
                            CalenderTodoAdapter.addTodo(calenderTodo)
                        }
                    }
                }
            }
    }
}