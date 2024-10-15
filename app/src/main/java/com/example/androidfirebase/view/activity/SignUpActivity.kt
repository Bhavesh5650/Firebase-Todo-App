package com.example.androidfirebase.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidfirebase.R
import com.example.androidfirebase.data.helper.AuthHelper.Companion.authHelper
import com.example.androidfirebase.databinding.ActivitySignUpBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        signUp()
        initGoSignIn()

    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun signUp()
    {
        binding.signUpBtn.setOnClickListener {

            val email = binding.edtEmailUp.text.toString()
            val password = binding.edtPasswordUp.text.toString()

            if(email.isEmpty())
            {
                binding.emailLayoutUp.error = "Email Is Required"
            }
            else if(password.isEmpty())
            {
                binding.passwordLayoutUp.error = "Password Is Required"
            }
            else
            {
                GlobalScope.launch {
                    val message = authHelper.signUpUser(email = email, password = password)
                    withContext(Dispatchers.Main)
                    {
                        if(message=="Success")
                        {
                            startActivity(Intent(this@SignUpActivity,SignInActivity::class.java))
                            finish()
                            Log.i("TAG", "signUp: ========== $email $password")
                        }
                        else
                        {
                            //Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_SHORT).show()
                            Log.d("TAG", "signUp: =================================================$message")
                        }
                    }
                }
            }
        }
    }

    private fun initGoSignIn()
    {
        binding.signInBtnTxt.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        }
    }



}