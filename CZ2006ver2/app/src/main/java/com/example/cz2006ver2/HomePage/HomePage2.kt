package com.example.cz2006ver2.HomePage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_home_page2.*
import java.util.*


class HomePage2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page2)

        home2_create_button.setOnClickListener {
            val intent = Intent(this, HomePage3::class.java)
            val descview = findViewById<EditText>(R.id.home2_desc_edit)
            val timeview = findViewById<TextView>(R.id.home2_time_text)
            val dateview = findViewById<TextView>(R.id.home2_date_text)
            val spincon = findViewById<Spinner>(R.id.home2_event_spinner)
            intent.putExtra("desc", descview.text.toString())
            intent.putExtra("time", timeview.text)
            intent.putExtra("date", dateview.text)
            intent.putExtra("spin", spincon.selectedItem.toString())
            startActivity(intent)
        }

        home2_back_button_word.setOnClickListener {
            val intent = Intent(this, HomePage1::class.java)
            startActivity(intent)
        }

        spinclick()
        timeclick()
        dateclick()
    }

    private fun spinclick()
    {
        val languages = resources.getStringArray(R.array.yes_no)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.home2_event_spinner)
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
                ) {}

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    private fun timeclick()
    {
        var picker: TimePickerDialog
        val tvw: TextView = findViewById<View>(R.id.home2_time_text) as TextView
        tvw.setOnClickListener(View.OnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val hour: Int = cldr.get(Calendar.HOUR_OF_DAY)
            val minutes: Int = cldr.get(Calendar.MINUTE)
            // time picker dialog
            picker = TimePickerDialog(this@HomePage2,
                { tp, sHour, sMinute -> tvw.text = "$sHour:$sMinute" }, hour, minutes, true
            )
            picker.show()
            tvw.text = "Selected Time: " + tvw.text
        })
    }

    private fun dateclick()
    {
        val tvw = findViewById<TextView>(R.id.home2_date_text)
        tvw.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val yy = calendar[Calendar.YEAR]
            val mm = calendar[Calendar.MONTH]
            val dd = calendar[Calendar.DAY_OF_MONTH]
            val datePicker = DatePickerDialog(this@HomePage2,
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tvw.text = date
                }, yy, mm, dd
            )
            datePicker.show()  })

    }

}