package com.example.cz2006ver2.Calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.cz2006ver2.HomePage.HomePage1
import com.example.cz2006ver2.HomePage.HomePage2
import com.example.cz2006ver2.HomePage.Todo
import com.example.cz2006ver2.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_calendar_caretaker.*
import org.w3c.dom.Text

/**
 * This class allows for swapping of caretakers for a particular day
 */
class CalendarCaretakerActivity : AppCompatActivity() {
    /**
     * data class to store a date
     *
     * @property name date in string form
     */
    data class dateName(
        val name: String
    )

    /**
     * main function that contains necessary code for running calendarCaretakerActivity and its buttons
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_caretaker)
        var curr_date = intent.getStringExtra("scheduled_date")
        schedule_date1.setText(curr_date)
        val elderUID = intent.getStringExtra("key").toString()
        val date = intent.getStringExtra("scheduled_date").toString()

        testFirestore(elderUID) // we need to change this to get the elderUID later

        println(elderUID + " " + date)

        cal_caretaker1.setOnClickListener{
            updateName(elderUID,date,cal_caretaker1)
            val intent = Intent(this, CalendarCaretakerActivity2::class.java)
            intent.putExtra("scheduled_date",curr_date)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        cal_caretaker2.setOnClickListener{
            updateName(elderUID,date,cal_caretaker2)
            val intent = Intent(this, CalendarCaretakerActivity2::class.java)
            intent.putExtra("scheduled_date",curr_date)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        cal_caretaker3.setOnClickListener{
            updateName(elderUID,date,cal_caretaker3)
            val intent = Intent(this, CalendarCaretakerActivity2::class.java)
            intent.putExtra("scheduled_date",curr_date)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        cal_caretaker4.setOnClickListener{
            updateName(elderUID,date,cal_caretaker4)
            val intent = Intent(this, CalendarCaretakerActivity2::class.java)
            intent.putExtra("scheduled_date",curr_date)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        cal_caretaker5.setOnClickListener{
            updateName(elderUID,date,cal_caretaker5)
            val intent = Intent(this, CalendarCaretakerActivity2::class.java)
            intent.putExtra("scheduled_date",curr_date)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }

        back_word_cal2.setOnClickListener{
            val cal2BackBtn = Intent(this, CalendarDayActivity::class.java)
            cal2BackBtn.putExtra("key", elderUID)
            cal2BackBtn.putExtra("scheduled_date",curr_date)
            startActivity(cal2BackBtn)
        }

    }

    /**
     * Function to test connection to the firebase
     *
     * @param elderUID unique ID for each care recipient
     */
    fun testFirestore(elderUID: String){
        //define taskObject type
        var nameList: MutableList<String> = ArrayList()

        val db = FirebaseFirestore.getInstance()
        FirebaseFirestore
            .getInstance()
            .collection("careRecipient").document(elderUID).collection("caretaker")
            .addSnapshotListener(this
            ) { querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                if (querySnapshot != null) {
                    for (document in querySnapshot.documents) {
                        nameList.add(document.get("name").toString())
                    }
                    assignToEditTask(nameList)
                }
            }
    }

    /**
     * Function to update name of caretaker based on the care recipient
     *
     * @param elderUID unique ID for each care recipient
     * @param date date that the user is currently on
     * @param pos TextView used to store the caretaker name
     */
    fun updateName(elderUID: String, date: String, pos: TextView) {
        val caretaker = dateName(pos.text.toString())
        val db = FirebaseFirestore.getInstance()
        db.collection("careRecipient").document(elderUID).collection("caretakerDay")
            .document(date).set(caretaker)
    }

    /**
     * Function to store the list of caretakers that can be assigned
     *
     * @param nameList contains names of caretakers
     */
    fun assignToEditTask(nameList: MutableList<String>){
        var cr1 = findViewById<TextView>(R.id.cal_caretaker1)
        val cr2 = findViewById<TextView>(R.id.cal_caretaker2)
        val cr3 = findViewById<TextView>(R.id.cal_caretaker3)
        val cr4 = findViewById<TextView>(R.id.cal_caretaker4)
        val cr5 = findViewById<TextView>(R.id.cal_caretaker5)

        val lsize = nameList.size

        when (lsize) {       //convert this into a function
            1 -> {
                cr1.text = nameList[0]
            }
            2 -> {
                cr1.text = nameList[0]
                cr2.text = nameList[1]
            }
            3 -> {
                cr1.text = nameList[0]
                cr2.text = nameList[1]
                cr3.text = nameList[2]
            }
            4 -> {
                cr1.text = nameList[0]
                cr2.text = nameList[1]
                cr3.text = nameList[2]
                cr4.text = nameList[3]
            }
            5 -> {
                cr1.text = nameList[0]
                cr2.text = nameList[1]
                cr3.text = nameList[2]
                cr4.text = nameList[3]
                cr5.text = nameList[4]
            }
        }
    }
}