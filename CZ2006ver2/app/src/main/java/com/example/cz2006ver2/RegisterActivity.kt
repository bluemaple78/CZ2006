package com.example.cz2006ver2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register2.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var nameEt: EditText
    private lateinit var emailEt: EditText

    data class userInfo(        //data that we are passing in, add on to userInfo if want to ask for more
        val name: String? = null,
        val email: String? = null,
        val careArray: Array<String>? = null,
    )

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
            emailEt = findViewById(R.id.emailfield)



            registerbutton.setOnClickListener{
                var name: String = nameEt.text.toString()
                var email: String = emailEt.text.toString()

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
                } else{
                    auth.createUserWithEmailAndPassword(email, name).addOnCompleteListener(this, OnCompleteListener{ task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, ConnectPageActivity::class.java)
                            saveFireStore(name,email)
                            startActivity(intent)
                            finish()
                        }else {
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
        }
    }

    fun saveFireStore(name: String, email: String) {      //function for posting stuff
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userID = currentFirebaseUser!!.uid
        //Toast.makeText(this, "" + currentFirebaseUser!!.uid, Toast.LENGTH_SHORT).show()   just for testing
        val db = FirebaseFirestore.getInstance()

        val data = userInfo(name, email)

        db.collection("users").document(userID).set(data)
            .addOnSuccessListener {
                Toast.makeText(this@RegisterActivity, "record added successfully ", Toast.LENGTH_SHORT ).show()
            }

            .addOnFailureListener{
                Toast.makeText(this@RegisterActivity, "record Failed to add ", Toast.LENGTH_SHORT ).show()
            }
    }

}