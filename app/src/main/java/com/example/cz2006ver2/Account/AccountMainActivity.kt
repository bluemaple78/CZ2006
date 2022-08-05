package com.example.cz2006ver2.Account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cz2006ver2.*
import com.example.cz2006ver2.Calendar.CalendarMainActivity
import com.example.cz2006ver2.ConnectGroup.JoinCreateGrpActivity
import com.example.cz2006ver2.HomePage.HomePage1
import com.example.cz2006ver2.LoginRegister.LogoutActivity
import com.example.cz2006ver2.Transport.Transport1
import kotlinx.android.synthetic.main.activity_account_main.*

/**
 * This class allows user to View Profile, View Group Details, Create Group and Logout
 */

class AccountMainActivity : AppCompatActivity() {
    /**
     * Method allows users to View Profile, View Group Details, Create Group and Logout
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_main)
        val elderUID = intent.getStringExtra("key").toString()
        println("accmain pageID:  " +  elderUID)
        //nav bar
        homeicon_page3.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        calendaricon_page3.setOnClickListener{
            val intent = Intent(this, CalendarMainActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        transporticon_page3.setOnClickListener{
            val intent = Intent(this, Transport1::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        accounticon_page3.setOnClickListener{
            val intent = Intent(this, AccountMainActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        view_profile_btn.setOnClickListener{
            val intent = Intent(this, ViewProfileActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        grp_details_btn.setOnClickListener{
            val intent = Intent(this, GroupDetailsActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        create_grp_btn.setOnClickListener{
            val intent = Intent(this, JoinCreateGrpActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }
        logout_btn.setOnClickListener{
            val intent = Intent(this, LogoutActivity::class.java)
            intent.putExtra("key", elderUID)
            startActivity(intent)
        }

    }

}