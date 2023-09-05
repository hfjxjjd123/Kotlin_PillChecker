package com.example.pill_checker

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.repo.TimeRepo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity1 : AppCompatActivity() {

    override fun getIntent(): Intent {
        return Intent(this, LoginActivity2::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val googleLogin: SignInButton = findViewById(R.id.google_login)
        googleLogin.setOnClickListener {
            val signInIntent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, 1)
        }

        //FOR DEBUG
        val appleLogin: Button = findViewById(R.id.apple_login)
        appleLogin.setOnClickListener {
            //For test
        }
        //
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val account =
                GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
            initTime()
        }

    }

    private fun initTime(){
        val db = MainDatabase.getDatabase(applicationContext)
        val timeRepo = TimeRepo(db)
        CoroutineScope(coroutineContext).launch {
            withContext(Dispatchers.IO) {
                timeRepo.initialTime()
            }
            startActivity(intent)
        }
    }

}