package com.example.cz2006ver2.HomePage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.cz2006ver2.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_page8.*
import java.util.*

/**
 * Page to edit tasks in
 *
 */
class HomePage8 : AppCompatActivity() {

    /**
     * Data class to symbolise information in a task
     *
     * @property datetimeTask date and time of a task in string format
     * @property description description of a task
     * @property name name of a task
     */
    data class taskInfo(
        val datetimeTask: String,
        val description: String,
        val name: String
    )

    /**
     * function that contains main functionalities of the page
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page8)
        val elderUID = intent.getStringExtra("key").toString()      //pass UID to this page
        Log.d(ContentValues.TAG, "home page 8 " + elderUID)



        home8_edit_button.setOnClickListener {
            val intent = Intent(this, HomePage3::class.java)
            val descview = findViewById<EditText>(R.id.home8_desc_edit)
            val timeview = findViewById<TextView>(R.id.home8_time_text)
            val dateview = findViewById<TextView>(R.id.home8_date_text)
            val spincon = findViewById<Spinner>(R.id.home8_event_spinner)
            intent.putExtra("desc", descview.text.toString())
            intent.putExtra("time", timeview.text)
            intent.putExtra("date", dateview.text)
            intent.putExtra("spin", spincon.selectedItem.toString())

            //adding data to database//
            val uniqueID = UUID.randomUUID().toString()
            val db = FirebaseFirestore.getInstance()
            val taskUpload = taskInfo(
                dateview.text.toString() + timeview.text.toString(),
                descview.text.toString(),
                descview.text.toString()
            )
            db.collection("careRecipient").document(elderUID).collection("task").document(uniqueID)
                .set(taskUpload)


            intent.putExtra("key", elderUID)   //pass uid to next page
            startActivity(intent)

        }

        home8_back_button_word.setOnClickListener {
            val intent = Intent(this, HomePage1::class.java)
            startActivity(intent)
        }

        spinclick()
        timeclick()
        dateclick()
    }


    /**
     * class to show a spinner prompt
     * items in spinner sourced from recurring string array in string file
     *
     */
    private fun spinclick() {
        val languages = resources.getStringArray(R.array.recurring)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.home8_event_spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, languages
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    /**
     * Function to show TimePickerDialog to select time for task creation
     * Time will be added to text view in Hour:Minute format
     *
     */
    private fun timeclick() {
        var picker: TimePickerDialog
        val tvw: TextView = findViewById<View>(R.id.home8_time_text) as TextView
        tvw.setOnClickListener(View.OnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val hour: Int = cldr.get(Calendar.HOUR_OF_DAY)
            val minutes: Int = cldr.get(Calendar.MINUTE)
            // time picker dialog
            picker = TimePickerDialog(
                this@HomePage8,
                { tp, sHour, sMinute -> tvw.text = "$sHour:$sMinute" }, hour, minutes, true
            )
            picker.show()
            tvw.text = "Selected Time: " + tvw.text
        })
    }

    /**
     * Function to show DatePickerDialog
     * returns Year, Month and Day as separate values
     *
     */
    private fun dateclick() {
        val tvw = findViewById<TextView>(R.id.home8_date_text)
        tvw.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val yy = calendar[Calendar.YEAR]
            val mm = calendar[Calendar.MONTH]
            val dd = calendar[Calendar.DAY_OF_MONTH]

            val datePicker = DatePickerDialog(
                this@HomePage8,
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tvw.text = date
                }, yy, mm, dd
            )
            datePicker.datePicker.minDate = calendar.timeInMillis

            datePicker.show()
        })

    }
}