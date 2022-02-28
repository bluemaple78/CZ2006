package com.example.cz2006ver2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private companion object {
        private const val TAG = "LoginActivity"
        private const val RC_GOOGLE_SIGN_IN = 4926
    }
    private lateinit var auth: FirebaseAuth


    private lateinit var btnSignIn: SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnSignIn = findViewById(R.id.btnSignIn)
//        SignInButton btnSignIn = (SignInButton) findViewById(R.id.btnSignIn)

        auth = Firebase.auth

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("532184677401-h7rq8qmjm50s7opth8ljdhi3gravnbbq.apps.googleusercontent.com")
            //^hardcorded, use (getString(R.string.default_web_client_id)) instead
            .requestEmail()
            .build()

        //    private lateinit var googleSignInClient: GoogleSignInClient
        val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(this, gso)
        btnSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)

//            registerForActivityResult(StartActivityForResult()) { result ->
//                onActivityResult(RC_GOOGLE_SIGN_IN, result)
//            }.launch(signInIntent)

//            val launcher =
//                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                    if (result.resultCode == Activity.RESULT_OK) {
//                        // handle the response in result.data
//                        val data: Intent? = result.data
//                        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//                        try {
//                            // Google Sign In was successful, authenticate with Firebase
//                            val account = task.getResult(ApiException::class.java)!!
//                            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//                            firebaseAuthWithGoogle(account.idToken!!)
//                        } catch (e: ApiException) {
//                            // Google Sign In failed, update UI appropriately
//                            Log.w(TAG, "Google sign in failed", e)
//                        }
//                    }
//                }
//            launcher.launch(signInIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        //Navigate to MainActivity????
        if (user == null){
            Log.w(TAG, "User is null, not going to navigate")
            return
        }

        startActivity(Intent(this, DUMMY::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
//    private fun onActivityResult(requestCode: Int, result: ActivityResult) {
//        if(result.resultCode == Activity.RESULT_OK) {
//            val intent = result.data
//            when (requestCode) {
//                Constants.MY_CODE_REQUEST -> {
//                    ...

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_LONG).show()
                    updateUI(null)
                }
            }
    }

}