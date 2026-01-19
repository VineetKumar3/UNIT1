package com.example.unit1

import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ShoppingCart : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance().getReference("User Cart")
    lateinit var cb1: CheckBox
    lateinit var cb2: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shopping_cart)

        cb1 = findViewById(R.id.checkboxLaptop)
        cb2 = findViewById(R.id.checkboxPhone)

        cb1.setOnCheckedChangeListener { _, isChecked ->
            saveItem("Laptop", 3300, isChecked)

        }

        cb2.setOnCheckedChangeListener { _, isChecked ->
            saveItem("phone", 2200, isChecked)
        }
    }

    fun saveItem(name: String, price: Int, isSelected: Boolean) {
        val data = mapOf("name" to name, "price" to price, "isSelected" to isSelected)
        db.child(auth.currentUser?.uid.toString()).setValue(data)
        Toast.makeText(
            this,
            "Shopping Cart Updated", Toast.LENGTH_SHORT
        )
            .show()
    }

}

