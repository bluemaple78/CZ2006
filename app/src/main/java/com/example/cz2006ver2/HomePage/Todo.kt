package com.example.cz2006ver2.HomePage

/**
 * Class to represent a task
 * Contains essential information to be displayed or manipulated
 *
 * @property title title of the task
 * @property deadline deadline that the task has to be completed by
 * @property taskID unique ID of the task
 * @property elderUID ID of the care recipient the task is linked to
 * @property completed flag for whether the task is completed or not
 * @property isChecked flag for whether the task is selected by the user
 */
data class Todo(
    val title: String,
    val deadline: String,
    val taskID: String,
    val elderUID: String,
    var completed: Boolean,
    var isChecked: Boolean = false
)