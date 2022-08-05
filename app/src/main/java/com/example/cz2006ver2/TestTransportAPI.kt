package com.example.cz2006ver2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
//import khttp.get


class TestTransportAPI : AppCompatActivity() {

//    private lateinit var btn1: Button
//    private lateinit var btn2: Button
//    private lateinit var btn3: Button
//    private lateinit var et: EditText
//    private lateinit var lv: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_transport_api)

        var btn1: Button = findViewById(R.id.button)
        var btn2: Button = findViewById(R.id.button2)
        var btn3: Button = findViewById(R.id.button3)
        var et: EditText = findViewById(R.id.testInput)
        var lv: ListView = findViewById(R.id.testlv)

        btn1.setOnClickListener(View.OnClickListener { view ->
            // Do some work here
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = "https://api.data.gov.sg/v1/transport/carpark-availability"
//                    val url = "https://www.metaweather.com/api/api/location/search/?query=london"
// Request a string response from the provided URL.
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener<JSONObject>
            { response ->
                //displays the first the carpark json objects
                val test: JSONObject = response.getJSONArray("items").getJSONObject(0).getJSONArray("carpark_data").getJSONObject(0)
                val carplate: String = test.getString("carpark_number")

                Toast.makeText(this, "CityID = " + carplate.toString(), Toast.LENGTH_SHORT).show();

            },Response.ErrorListener { error ->
                // TODO: Handle error
                Toast.makeText(this, "WRONG LA", Toast.LENGTH_SHORT).show();

            })
//

// Add the request to the RequestQueue.
            queue.add(jsonObjectRequest)
//                    Toast.makeText(this, "You clicked on 1.", Toast.LENGTH_SHORT).show();

        })


        btn2.setOnClickListener{
//            Toast.makeText(this, "You clicked on 2.", Toast.LENGTH_SHORT).show()
            // Do some work here
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = "http://datamall2.mytransport.sg/ltaodataservice/BusArrivalv2?BusStopCode=10101"

            val jsonObjectRequest = object: JsonObjectRequest(
                Method.GET, url, null,  Response.Listener<JSONObject>
                { response ->

                    val test2: JSONArray = response.getJSONArray("Services")
                    val test3: JSONObject = test2.getJSONObject(0)
                    val test4: String = test3.getString("ServiceNo")

                    Log.d("TESTINGBUS", test2.toString())
                    println(test2.toString())

                },Response.ErrorListener { error ->
                    // TODO: Handle error
                    println(error.message)
                    Toast.makeText(this, "WRONG LA", Toast.LENGTH_SHORT).show();

                })
            {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("accept", "application/json")
                    headers.put("AccountKey", "M8EyGPshTCOa1WvqEjEPQg==")
                    return headers
                }}


// Add the request to the RequestQueue.
            queue.add(jsonObjectRequest)
//                    Toast.makeText(this, "You clicked on 1.", Toast.LENGTH_SHORT).show();

        }
        btn3.setOnClickListener{
            Toast.makeText(this, "You clicked on 3.", Toast.LENGTH_SHORT).show()
        }
    }



//    val url = "http://datamall2.mytransport.sg/ltaodataservice/BusArrivalv2"

}