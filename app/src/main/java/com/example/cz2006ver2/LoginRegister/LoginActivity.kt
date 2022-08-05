package com.example.cz2006ver2.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cz2006ver2.ConnectGroup.GroupselectionActivity
import com.example.cz2006ver2.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Main Class for LoginPage and associated Functions
 *
 */
class LoginActivity : AppCompatActivity() {

    /**
     * FirebaseAuth Object for firebase authentication
     */
    private lateinit var auth: FirebaseAuth

    /**
     * EditText Field for entering Email address for login
     */
    private lateinit var emailEt: EditText

    /**
     * EditText field for entering password for login
     */
    private lateinit var passwordEt: EditText

    /**
     * Button to initiate login process
     */
    private lateinit var loginBtn: Button

    /**
     * Main Function for Login Process
     * Process includes authentication with Firebase for verification
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEt = findViewById(R.id.enter_email)
        passwordEt = findViewById(R.id.enter_password)

        loginBtn = findViewById(R.id.loginbutton)


        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {
            var email: String = emailEt.text.toString()
            var password: String = passwordEt.text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this@LoginActivity, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, GroupselectionActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        //back button to the landing page
        back_button_word.setOnClickListener{
            val loginBackBtn = Intent(this, MainActivity::class.java)
            startActivity(loginBackBtn)
        }
    }

}