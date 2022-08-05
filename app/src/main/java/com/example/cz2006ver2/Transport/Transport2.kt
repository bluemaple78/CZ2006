package com.example.cz2006ver2.Transport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cz2006ver2.R
import kotlinx.android.synthetic.main.activity_transport2.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * Class to search for bus stop and bus number information.
 */
class Transport2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * Method used to start default activity. Link back to main Transport Page.
         * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
         */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport2)
        val elderUID = intent.getStringExtra("key").toString()
        var busStopCodeInput: EditText = findViewById(R.id.editBusStop)
        var listView: ListView = findViewById(R.id.busList)
        val baseURL = "http://datamall2.mytransport.sg/ltaodataservice/BusArrivalv2"
        searchButton.setOnClickListener{
            var busStopCode: String = busStopCodeInput.text.toString()
            if(TextUtils.isEmpty(busStopCode)){
                Toast.makeText(this@Transport2, "Please enter a valid Bus Stop Code", Toast.LENGTH_LONG).show()
            }else {
                //do something
                val queue = Volley.newRequestQueue(this)
                val busStopURL = "$baseURL?BusStopCode=$busStopCode"
                val jsonObjectRequest = object: JsonObjectRequest(
                    Method.GET, busStopURL, null,  Response.Listener<JSONObject>
                    { response ->

                        val services: JSONArray = response.getJSONArray("Services")
                        val list: MutableList<String> = ArrayList()

                        //this dropdown is for the bus numbers on that particular busstop
                        //if dropdown is to show the available bus stop codes instead, then need another for loop
                        for (i in 0 until services.length()) {
                            val busNum = services.getJSONObject(i).getString("ServiceNo")
                            list.add(busNum)
                        }
                        if (list.isEmpty()){
                            Toast.makeText(this@Transport2, "Bus Stop not in service. Please enter another code", Toast.LENGTH_LONG).show()
                        }

                        val arrayadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
                        listView.adapter = arrayadapter

                        listView.setOnItemClickListener { parent, view, position, id ->
                            val element = parent.getItemAtPosition(position) // The item that was clicked
                            val intent = Intent(this, Transport3::class.java)
//                            Toast.makeText(this@transport2, element.toString() + position, Toast.LENGTH_LONG).show()
                            intent.putExtra("key", elderUID)
                            intent.putExtra("BusStopCode", busStopCode)
                            intent.putExtra("BusJSONObjectNum", position.toString())
//                            intent.putExtra("BusStopDesc", something here)
                            startActivity(intent)
                        }

//                        val test3: JSONObject = services.getJSONObject(0)
//                        val test4: String = test3.getString("ServiceNo")

//                        Log.d("TESTINGBUS", services.toString())
//                        println(services.toString())


                    }, Response.ErrorListener { error ->
                        // TODO: Handle error
                        println(error.message)
                        Toast.makeText(this, "Error: No such Bus Stop exist. Please try again", Toast.LENGTH_SHORT).show();

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
            }
        }

        back_btn_cal2.setOnClickListener {
            val back1 = Intent(this, Transport1::class.java)
            back1.putExtra("key", elderUID)
            startActivity(back1)
        }
        back_word_cal2.setOnClickListener {
            val back2 = Intent(this, Transport1::class.java)
            back2.putExtra("key", elderUID)
            startActivity(back2)
        }


    }
}