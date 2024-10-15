package com.example.androidfirebase.data.helper

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthHelper  {

    companion object{

        var authHelper = AuthHelper()
    }

    var user: FirebaseUser? = null
    var auth = FirebaseAuth.getInstance()

    suspend fun signUpUser(email:String,password:String): String {
        var message: String? = null
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                message = "Success"
            }.addOnFailureListener {
                Log.e("TAG", "signUpUser: $message")
            }.await()
            Log.d("TAG", "signUpUser: =====================$message")
        }catch (e:FirebaseException){
            message = e.message
        }
        return message!!
    }

    suspend fun sigInUser(email: String, password: String) :String?{
        var message:String?=null

        try {
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                message = "Success"
            }.addOnFailureListener{

            }.await()
        }catch (e:FirebaseException)
        {
            message = e.message
        }
        return message
    }

    fun getCurrentUser()
    {
        user = auth.currentUser
    }

    fun logOut()
    {
        auth.signOut()
    }
}