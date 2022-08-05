package com.example.cz2006ver2.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.cz2006ver2.ConnectGroup.ConnectPageActivity
import com.example.cz2006ver2.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register2.*

/**
 * This class allows new users to register an account and provide their particulars.
 * Includes functionalities to verify and create new account to firebase server
 */
class RegisterActivity : AppCompatActivity() {

    /**
     * FirebaseAuth object for authentication with firebase server
     */
    private lateinit var auth: FirebaseAuth

    /**
     * EditText Field for input of name attribute for new account
     */
    private lateinit var nameEt: EditText

    /**
     * EditText Field for input of email attribute for new account
     */
    private lateinit var emailEt: EditText

    /**
     * EditText Field for input of password attribute for new account
     */
    private lateinit var passEt: EditText

    /**
     * EditText Field for input of email attribute for new account
     */
    private lateinit var passET2: EditText


    /**
     * Object to store attributes of an account for registration
     *
     * @property name for registering new account
     * @property email for registering of new account
     * @property careArray Array of care recipients that a caretaker provides care to limited to 5 per caretaker
     */
    data class userInfo(        //data that we are passing in, add on to userInfo if want to ask for more
        val name: String? = null,
        val email: String? = null,
        val careArray: Array<String>? = null,
    )

    /**
     * Main Function for registration page and activities
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        regPageBackBtn.setOnClickListener{
            val backBtnIntent = Intent(this, MainActivity::class.java)
            startActivity(backBtnIntent)
        }

        registerbutton.setOnClickListener {auth = FirebaseAuth.getInstance()
            nameEt = findViewById(R.id.namefield)
            passEt = findViewById(R.id.passwordfield1)
            emailEt = findViewById(R.id.emailfield)
            passET2 = findViewById(R.id.passwordfield2)

            var password: String = passEt.text.toString()
            var email: String = emailEt.text.toString()
            var name: String = nameEt.text.toString()
            var password2: String = passET2.text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2) || TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            }
            else if ((password != password2)&& ((password.length != 0) && password2.length != 0)) {
                println(password.length)
                println("doesnt create")
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
            }
            else if (password.length <= 7){
                Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_LONG).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, ConnectPageActivity::class.java)
                        saveFireStore(name,email)
                        intent.putExtra("temp1", "1")
                        startActivity(intent)
                        finish()
                    }else {
                        println("doesnt create")
                        Toast.makeText(this, "Email has already been registered", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

    }

    /**
     * Function to save attributes of newly registered account to firestore
     *
     * @param name name attribute of new account
     * @param email email attribute of new account
     */
    fun saveFireStore(name: String, email: String) {      //function for posting stuff
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        //Toast.makeText(this, "" + currentFirebaseUser!!.uid, Toast.LENGTH_SHORT).show()   just for testing
        val db = FirebaseFirestore.getInstance()

        val data = userInfo(name, email)

        db.collection("users").document(userID).set(data)
            .addOnSuccessListener {
                println("Record added successfully")
            }

            .addOnFailureListener{
                println("Record failed to add")
            }
    }

}