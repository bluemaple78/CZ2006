package com.example.cz2006ver2.Transport

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.cz2006ver2.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_transport2.*

/**
 * Class used for user's favourite bus stop and bus numbers
 */
class Transport4 : AppCompatActivity() {
    /**
     * Method used to start default activity. Link back to main Transport Page.
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
//    private lateinit var busStopCodeList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {        /**we need to pass the elderUID into this page as intent**/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport4)
        val elderUID = intent.getStringExtra("key").toString()
        println(elderUID)
//        var busStopCodeList: MutableList<String> = ArrayList()
//        val list: MutableList<String> = ArrayList()
//        val busStopCode:String = intent.getStringExtra("BusStopCode").toString()
//        if (busStopCode != null) {
//            list.add(busStopCode)
//        }
        //this dropdown is for the bus numbers on that particular busstop
        //if dropdown is to show the available bus stop codes instead, then need another for loop
//        for (i in 0 until services.length()) {
//            val busNum = services.getJSONObject(i).getString("ServiceNo")
//            list.add(busNum)
//        }
//
        getFavouriteBusStopCode(elderUID)
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
    /**
     * Method used to get favourite bus stop code from FireStore
     * @param elderKey Current care recipient's group code
     */
    private fun getFavouriteBusStopCode(elderUID : String) {
        var busStopCodeList: MutableList<String> = ArrayList()
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        //Toast.makeText(this, "" + currentFirebaseUser!!.uid, Toast.LENGTH_SHORT).show()   just for testing
        val db = FirebaseFirestore.getInstance()

        db.collection("careRecipient").document(elderUID).collection("favBusStop").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        busStopCodeList.add(document.id + " (" + document["busStopLocation"] + ")")    /**adding each document to our list as a string**/
                    }
                    Log.d(ContentValues.TAG, "favourite bus stops in this list " + busStopCodeList.toString())
                    for (i in busStopCodeList){/** for testing**/
                        println("Here " + i)
                    }
                    /**start coding here**/
                    var busStopListView: ListView = findViewById(R.id.busStopList)
                    val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, busStopCodeList)
                    busStopListView.adapter = arrayAdapter
                } else {
                    Log.d(ContentValues.TAG, "Error getting documents: ", task.exception)
                }
            })
    }
}