package com.example.cz2006ver2

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class Home1Recyclerr : RecyclerView.Adapter<Home1Recyclerr.ViewHolder>() {

    private var taskname = arrayOf("ahma 1", "ahma 2", "dispose of ahma", "donate ahma to the polyclinic", "ahma 5", "ahma 6", "ahma 7", "ahma 8")
    val code = "un5zqQK0"
    private var testlist: MutableList<String> = ArrayList()

    fun displayDocumentID(elderUID: String = code, list: MutableList<String> = testlist) { //func to test if can pull names of doc id
        val db = FirebaseFirestore.getInstance()
        db.collection("careRecipient").document(elderUID).collection("task").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        list.add(document.id)
                    }
                    print("fuck")
                    Log.d(ContentValues.TAG, list.toString())
                    print("you")
                } else {
                    print("fuckfail")
                    Log.d(ContentValues.TAG, "Error getting documents: ", task.exception)
                }
            })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        displayDocumentID()
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home1check, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkbox1.text = testlist[position]
    }

    override fun getItemCount(): Int {
        return testlist.size;
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var checkbox1: CheckBox

        init {
            checkbox1 = itemView.findViewById(R.id.home1check1)

            checkbox1.setOnClickListener{
                //TODO
                //connect to db to set task completed or not
            }
        }
    }
}