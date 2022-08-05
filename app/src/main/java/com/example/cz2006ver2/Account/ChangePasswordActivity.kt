package com.example.cz2006ver2.Account

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.cz2006ver2.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_logout.*

/**
 * This activity allows users to change their passwords.
 */
class ChangePasswordActivity : AppCompatActivity() {

    /**
     * FirebaseAuthenticator Object with current user information
     * To be used for re-authentication and password updating
     */
    var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    /**
     * elderUID value to receive from previous page
     * Used to pass to the next page
     */
    val elderUID = intent.getStringExtra("key").toString()


    /**
     * Method used to start default activity
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        changePW_BackText.setOnClickListener {
            val passwordChange = Intent(this, AccountMainActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(passwordChange)
        }

        changePW_ConfirmButton.setOnClickListener {
            if(changePass()) {
                val passwordChange = Intent(this, AccountMainActivity::class.java)
                intent.putExtra("key", elderUID)
                startActivity(passwordChange)
            }
        }
    }

    /**
     * Function to change password for current user
     * Authenticates the user with email and current password
     * Then checks if the new password entered matches with confirmed password field
     * Finally updates the password if above conditions are met
     *
     * @return True if successful, false otherwise
     */
    fun changePass():Boolean {
        var newPassword = ""
        var checky = false
        val emailadd: EditText = findViewById(R.id.changePW_CurEmail)
        val curpass: EditText = findViewById(R.id.changePW_CurPW)
        val newPass: EditText = findViewById(R.id.changePW_NewPW)
        val newPassConfirm: EditText = findViewById(R.id.changePW_ReEnterNewPW)
        val creds = EmailAuthProvider.getCredential(emailadd.text.toString(), curpass.text.toString())
        user?.reauthenticate(creds)?.addOnCompleteListener {task1 ->
            if(task1.isSuccessful){
                if(newPass.text.toString() == newPassConfirm.text.toString()) {
                    newPassword = newPass.text.toString()
                    if(newPassword != "") {
                        user!!.updatePassword(newPassword)
                        .addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                Log.d(ContentValues.TAG, "User password updated.")
                                checky = true
                                finish()
                            }
                            else {
                                Toast.makeText(this, "Passwords update fail", Toast.LENGTH_LONG).show()
                                checky = false
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                    checky = false
                }
            }
            else{
                Toast.makeText(this, "Email or Current Password is incorrect", Toast.LENGTH_LONG).show()
            }

        }
        return checky
    }
}