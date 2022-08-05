package com.example.cz2006ver2.HomePage

import android.content.ContentValues.TAG
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cz2006ver2.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_todo.view.*

/**
 * Class to use in recyclerView for HomePage1
 *
 * @property todos list of tasks to be passed into the recyclerView to be displayed to the user
 */
class TodoAdapter(private val todos: ArrayList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    /**
     * View Holder class for todos to use in recyclerview
     *
     * @constructor
     * creates a todoviewholder with an item view
     *
     * @param itemView itemview to be passed into the todoviewholder during construction
     */
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * Main function that runs when viewholder is created
     *
     * @param parent viewgroup that the viewholder belongs to
     * @param viewType type of the view
     * @return TodoViewHolder object
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_todo,
                parent,
                false
            )
        )
    }

    /**
     * add a todos object into arraylist of todos
     *
     * @param todo todos object to be added into list of todos
     */
    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    /**
     * function to delete completed todos
     *
     * @return status of deletion function
     */
    fun deleteDoneTodos(): Boolean{
        var deletedTasks: ArrayList<Todo> = ArrayList()
        for( i in 0 until todos.size)
        {
            if(todos.get(i).isChecked){
                deletedTasks.add(todos.get(i))
            }
        }
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
        return deleteFromDB(deletedTasks)
    }

    /**
     * Function to delete todos from FireBase
     *
     * @param deletedTasks arraylist of todos to be deleted
     * @return true if successfully deleted, false if nothing to be deleted
     */
    private fun deleteFromDB(deletedTasks : ArrayList<Todo>): Boolean{
        if(deletedTasks.size == 0)
            return false
        else{
            val db = FirebaseFirestore.getInstance()
            for( i in 0 until deletedTasks.size){
                var docRef = db.collection("careRecipient").document(deletedTasks.get(i).elderUID)
                    .collection("task").document(deletedTasks.get(i).taskID)
                println("dofref is " + docRef)
                docRef.delete().addOnSuccessListener { task ->
                    Log.w(TAG, "Deleted1")
                }
            }
            return true
        }
    }

    /**
     * Function to add strikethrough to mark todos as completed
     *
     * @param tvTodoTitle Todos represented in a textview to be marked
     * @param isChecked where todos are already checked or not
     */
    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    /**
     * mark and check todos as completed
     *
     * @return arraylist of completed todos
     */
    fun checkCompleted(): ArrayList<Todo>{
        var completedTasks: ArrayList<Todo> = ArrayList()
        for(i in 0 until todos.size) {
            if (todos.get(i).isChecked) {
                completedTasks.add(todos.get(i))
                todos.get(i).completed = true
                notifyItemChanged(i)
                todos.get(i).isChecked = !todos.get(i).isChecked
            }
        }
        return completedTasks
    }

    /**
     * Function to run when viewholder is binded
     *
     * @param holder viewholder to be binded
     * @param position of the current todos object in the viewholder
     */
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        val curTodo = todos[position]
        holder.itemView.apply {
            tvTodoTitle.text = curTodo.title
            tvDeadline.text = curTodo.deadline
            cbDone.isChecked = curTodo.isChecked
            toggleStrikeThrough(tvTodoTitle, curTodo.completed)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
//                toggleStrikeThrough(tvTodoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }

    /**
     * function to return size of todos
     *
     * @return size of todos
     */
    override fun getItemCount(): Int {
        return todos.size
    }

//    fun clear() {
//        val size: Int = todos.size
//        todos.clear()
//        notifyItemRangeRemoved(0, size)
//    }

//    fun setHighLightedText(tv: TextView, textToHighlight: String) {
//            val tvt = tv.text.toString()
//            var ofe = tvt.indexOf(textToHighlight, 0)
//            val wordToSpan: Spannable = SpannableString(tv.text)
//            var ofs = 0
//            while (ofs < tvt.length && ofe != -1) {
//                ofe = tvt.indexOf(textToHighlight, ofs)
//                if (ofe == -1) break else {
//                    // set color here
//                    wordToSpan.setSpan(
//                        BackgroundColorSpan(-0x100),
//                        ofe,
//                        ofe + textToHighlight.length,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                    )
//                    tv.setText(wordToSpan, TextView.BufferType.SPANNABLE)
//                }
//                ofs = ofe + 1
//            }
//
//    }

}