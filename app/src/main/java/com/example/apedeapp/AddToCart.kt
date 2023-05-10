package com.example.apedeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddToCart : AppCompatActivity() {

    private lateinit var itemName: TextView
    private lateinit var quantity: TextView
    private lateinit var bottomPrice: TextView
    private lateinit var itemID: TextView

    private lateinit var plusQuantity: ImageButton
    private lateinit var minusQuantity: ImageButton

    private lateinit var addToCart: Button

    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        itemName = findViewById(R.id.textView3)
        quantity = findViewById(R.id.editTextTextPersonName14)
        bottomPrice = findViewById(R.id.textView9)
        itemID = findViewById(R.id.itemIDCart)

        plusQuantity = findViewById(R.id.imageButton4)
        minusQuantity = findViewById(R.id.imageButton3)

        auth = FirebaseAuth.getInstance()

        addToCart = findViewById(R.id.textView12)

        itemName.text = intent.getStringExtra("iName").toString()
        bottomPrice.text = intent.getStringExtra("iPrice").toString()
        itemID.text = intent.getStringExtra("itemID").toString()

        val botPrice: Int = bottomPrice.text.toString().trim().toInt()
        println(botPrice)

        plusQuantity.setOnClickListener{

            val yourInteger: Int = quantity.text.toString().trim().toInt()
            println(yourInteger)

            val yourIntegerPrice: Int = bottomPrice.text.toString().trim().toInt()
            println(yourIntegerPrice)

            quantity.text = (yourInteger + 1).toString()

            bottomPrice.text = (yourIntegerPrice + botPrice).toString()
        }

        minusQuantity.setOnClickListener{

            val yourInteger: Int = quantity.text.toString().trim().toInt()
            println(yourInteger)

            val yourIntegerPrice: Int = bottomPrice.text.toString().trim().toInt()
            println(yourIntegerPrice)

            if (yourInteger == 1 || yourIntegerPrice == botPrice){
                Toast.makeText(this, "Check", Toast.LENGTH_SHORT).show()
            }else{

                quantity.text = (yourInteger -1).toString()

                bottomPrice.text = (yourIntegerPrice - botPrice).toString()
            }
        }
    }
}