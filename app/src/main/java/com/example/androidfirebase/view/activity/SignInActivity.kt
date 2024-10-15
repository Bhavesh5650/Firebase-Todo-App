package com.example.androidfirebase.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidfirebase.MainActivity
import com.example.androidfirebase.R
import com.example.androidfirebase.data.helper.AuthHelper.Companion.authHelper
import com.example.androidfirebase.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignInBinding
    private lateinit var googleClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        googleSignIn()

        val registerGoogle = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            val googleId = GoogleSignIn.getSignedInAccountFromIntent(it.data)

            val credential = GoogleAuthProvider.getCredential(googleId.result.idToken,null)

            FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener {
                authHelper.getCurrentUser()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }.addOnFailureListener{
                Log.d("Failure", "Failure ---------- ${it.message} ")
            }
        }

        binding.googleSignInBtn.setOnClickListener {
            val intent = googleClient.signInIntent
            registerGoogle.launch(intent)
        }

        signIn()
        initGoSignUp()
    }

    private fun signIn()
    {
        binding.signInBtn.setOnClickListener {
            val email = binding.edtEmailIn.text.toString()
            val password = binding.edtPasswordIn.text.toString()

            if(email.isEmpty())
            {
                binding.emailLayoutIn.error = "Email Is Required"
            }
            else if(password.isEmpty())
            {
                binding.passwordLayoutIn.error = "Password Is Required"
            }
            else
            {
                GlobalScope.launch {
                    var message = authHelper.sigInUser(email,password)
                    withContext(Dispatchers.Main){
                        if(message=="Success")
                        {
                            startActivity(Intent(this@SignInActivity,MainActivity::class.java))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this@SignInActivity, message , Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun initGoSignUp()
    {
        binding.signUpBtnTxt.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
    }

    private fun googleSignIn()
    {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.id_token))
            .requestEmail()
            .build()

        googleClient = GoogleSignIn.getClient(this,googleSignInOption)

    }
}