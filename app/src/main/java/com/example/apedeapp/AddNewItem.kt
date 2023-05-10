package com.example.apedeapp

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddNewItem : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_PICK = 2
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001
    private val CAMERA_REQUEST_CODE = 1002

    private lateinit var itemName: EditText
    private lateinit var colour:EditText
    private lateinit var price:EditText
    private lateinit var description:EditText

    private lateinit var addItemBTN: Button

    private var database = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    private lateinit var pb: ProgressBar

    private lateinit var storageReference: FirebaseStorage

    //private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_item)

        storageReference = FirebaseStorage.getInstance()

        itemName = findViewById(R.id.editTextTextPersonName13)
        colour = findViewById(R.id.editTextTextPersonName14)
        price = findViewById(R.id.editTextTextPersonName15)
        description = findViewById(R.id.editTextTextPersonName16)

        addItemBTN = findViewById(R.id.button13)

        pb = findViewById(R.id.progressBar2)

        auth = FirebaseAuth.getInstance()

        imageView = findViewById(R.id.imageView6)
        //imageView.setOnClickListener { selectImage() }

        pb.visibility = View.INVISIBLE

        addItemBTN.setOnClickListener(){

            pb.visibility = View.VISIBLE

            val randomID = UUID.randomUUID().toString()
            val sellerID = auth.currentUser?.uid.toString()

           /* if(uri != null) {

                storageReference.getReference("Images/$sellerID")
                    .child(System.currentTimeMillis().toString())
                    .putFile(uri!!)
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { uri ->
*/
                                val eItemName = itemName.text.toString().trim()
                                val eColor = colour.text.toString().trim()
                                val ePrice = price.text.toString().trim()
                                val eDescription = description.text.toString().trim()

                                val sellerMap = hashMapOf(
                                    "sellerID" to sellerID,
                                    "randomID" to randomID,
                                    "itemName" to eItemName,
                                    "ItemPrice" to ePrice,
                                    "itemColor" to eColor,
                                    "ItemDescription" to eDescription,
                                )

                                database.collection("sellerItems").document(randomID).set(sellerMap)
                                database.collection("sellerItemsBySellerID").document(sellerID)
                                    .collection("singleSellerItems").document(randomID)
                                    .set(sellerMap)
                                    .addOnSuccessListener {
                                        pb.visibility = View.INVISIBLE
                                        val i = Intent(this, StoreView::class.java)
                                        startActivity(i)
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        pb.visibility = View.INVISIBLE
                                        Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show()
                                    }
                            }
                    }
}