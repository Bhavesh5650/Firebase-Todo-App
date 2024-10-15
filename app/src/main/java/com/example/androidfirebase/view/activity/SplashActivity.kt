package com.example.androidfirebase.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidfirebase.MainActivity
import com.example.androidfirebase.R
import com.example.androidfirebase.data.helper.AuthHelper.Companion.authHelper
import com.example.androidfirebase.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Handler(Looper.getMainLooper()).postDelayed({
            authHelper.getCurrentUser()
            if(authHelper.user == null)
            {
                startActivity(Intent(this,SignInActivity::class.java))
                finish()
            }
            else
            {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        },3000)

    }
}