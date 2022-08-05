package com.example.cz2006ver2.LoginRegister

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cz2006ver2.Account.AccountMainActivity
import com.example.cz2006ver2.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_logout.*

/**
 * This class allows users to safely logout of their account.
 */
class LogoutActivity : AppCompatActivity() {
    /**
     * Method used to logout after saving any changes made.
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_logout)
        val elderUID = intent.getStringExtra("key").toString()
        println(elderUID + " LOGOUT PAGE")

        logout_BackButton.setOnClickListener {
            val intent = Intent(this, AccountMainActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }

        logout_ConfirmButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Successfully Logged Out", Toast.LENGTH_LONG).show()
            val logoutConfirm = Intent(this, MainActivity::class.java)
            startActivity(logoutConfirm)
        }
    }
}