package com.example.unit1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterUser : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register_user)


        val register = findViewById<Button>(R.id.btnRegister)
        val login = findViewById<Button>(R.id.btnLogin)


        auth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            registerUser()
        }
        login.setOnClickListener {
            startActivity(Intent(this, LoginUser::class.java))
            finish()
        }
    }

    private fun registerUser() {
        val email = findViewById<EditText>(R.id.RegEmail).text.toString().trim()
        val password = findViewById<EditText>(R.id.RegPassword).text.toString().trim()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    auth.currentUser?.sendEmailVerification()
                    Toast.makeText(this, "Verification mail sent on your email", Toast.LENGTH_SHORT).show()
                    auth.signOut()
                    startActivity(Intent(this, LoginUser::class.java))
                    finish()
                }else{
                    Toast.makeText(this,task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

    }
}
