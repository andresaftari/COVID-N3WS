package com.example.covid_n3ws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    private lateinit var fAuthorization: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        fAuthorization = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        userID = fAuthorization.currentUser?.uid.toString()
        val docReference = fStore.collection("Users").document(userID)

        btn_editConfirmed.setOnClickListener {
            val emailUpdate = edt_emailEdit.text.toString()
            val nameUpdate = edt_fullnameEdit.text.toString().trim()
            val usernameUpdate = edt_usernameEdit.text.toString().trim()

            val userData: MutableMap<String, Any> = HashMap()
            if (!TextUtils.isEmpty(emailUpdate)) {
                userData["email"] = emailUpdate

                docReference.update(userData)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Email update success!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Email update failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                startActivity(Intent(this, MainActivity::class.java))
            }

            if (!TextUtils.isEmpty(nameUpdate)) {
                userData["fullname"] = nameUpdate

                docReference.update(userData)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Name update success!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Name update failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                startActivity(Intent(this, MainActivity::class.java))
            }

            if (!TextUtils.isEmpty(usernameUpdate)) {
                userData["username"] = usernameUpdate

                docReference.update(userData)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Username update success!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Username update failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                startActivity(Intent(this, MainActivity::class.java))
            }

            if (rb_adminEdit.isChecked) {
                userData["role"] = "Administrator"

                docReference.update(userData)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Role update success!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Role update failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                startActivity(Intent(this, MainActivity::class.java))
            }

            if (rb_userEdit.isChecked) {
                userData["role"] = "User"

                docReference.update(userData)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Role update success!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Role update failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}
