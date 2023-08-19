package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton


class LoginActivity1:AppCompatActivity() {

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
        googleLogin.setOnClickListener{
            val signInIntent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, 1)
        }
    }




}