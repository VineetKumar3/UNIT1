package com.example.unit1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Homepage : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var database : DatabaseReference
    lateinit var notesedittext : EditText
    lateinit var notesbtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homepage)

        notesedittext = findViewById(R.id.notesedittext)
        notesbtn = findViewById(R.id.notesbtn)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Notes")


        notesbtn.setOnClickListener {
            realtimedatabase()
        }

        val cart = findViewById<Button>(R.id.btncart)
        cart.setOnClickListener {
            val intent = Intent(this, ShoppingCart::class.java)
            startActivity(intent)
        }

    }
    private fun realtimedatabase(){
        val notetext = notesedittext.text.toString().trim()
        if(notetext.isEmpty()){
            Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUser = auth.currentUser
        if(currentUser == null){
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show()
            return
        }
        val noteDB = database.child(currentUser.uid)
        val notekey = noteDB.push().key

        if(notekey ==  null){
            Toast.makeText(this, "Error creating note", Toast.LENGTH_SHORT).show()
            return
        }

        val note = hashMapOf(
            "notetext" to notetext,
            "timestamp" to System.currentTimeMillis()
        )

        noteDB.child(notekey).setValue(note)
            .addOnCompleteListener {
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                notesedittext.text.clear()
            }
            .addOnFailureListener {e ->
                Toast.makeText(this, "Error adding note ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}