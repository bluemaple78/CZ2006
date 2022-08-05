package com.example.cz2006ver2.Transport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cz2006ver2.R
import com.google.android.material.imageview.ShapeableImageView

/**
 * This class is to set up each component in the recyclerview list under bus information page(Transport 3)
 */
class MyAdapter(private val busList: ArrayList<BusInfo>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_items,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = busList[position]

        //LHS is from this kt file, RHS is from BusInfo kt file
        holder.busNum.text = currentItem.busNum
        holder.timing1.text = currentItem.timing1
        holder.timing2.text = currentItem.timing2
        holder.timing3.text = currentItem.timing3
        holder.crowdStatus1.setImageResource(currentItem.colourStatus1)
        holder.crowdStatus2.setImageResource(currentItem.colourStatus2)
        holder.crowdStatus3.setImageResource(currentItem.colourStatus3)
        holder.wheelchair1.setImageResource(currentItem.wheelStatus1)
        holder.wheelchair2.setImageResource(currentItem.wheelStatus2)
        holder.wheelchair3.setImageResource(currentItem.wheelStatus3)
    }

    override fun getItemCount(): Int {
        return  busList.size
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        val busNum: Button = itemView.findViewById(R.id.busNum)
        val timing1: TextView = itemView.findViewById(R.id.timing1)
        val timing2: TextView = itemView.findViewById(R.id.timing2)
        val timing3: TextView = itemView.findViewById(R.id.timing3)
        val crowdStatus1: ShapeableImageView = itemView.findViewById(R.id.status1)
        val crowdStatus2: ShapeableImageView = itemView.findViewById(R.id.status2)
        val crowdStatus3: ShapeableImageView = itemView.findViewById(R.id.status3)
        val wheelchair1: ShapeableImageView = itemView.findViewById(R.id.wheelchair1)
        val wheelchair2: ShapeableImageView = itemView.findViewById(R.id.wheelchair2)
        val wheelchair3: ShapeableImageView = itemView.findViewById(R.id.wheelchair3)
    }
}