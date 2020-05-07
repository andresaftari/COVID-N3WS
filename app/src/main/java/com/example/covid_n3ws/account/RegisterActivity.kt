package com.example.covid_n3ws.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.covid_n3ws.MainActivity
import com.example.covid_n3ws.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private lateinit var fAuthorization: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var userID: String

    companion object {
        const val TAG = "tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(main_toolbar)
        supportActionBar?.title = null

        fAuthorization = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        if (fAuthorization.currentUser != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        tv_goToLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        btn_register.setOnClickListener {
            val nameData = edt_fullname.text.toString().trim()
            val usernameData = edt_username.text.toString().trim()
            val emailData = edt_email.text.toString()
            val passwordData = edt_password.text.toString()
            val passwordCheck = edt_passwordVerif.text.toString()

            if (TextUtils.isEmpty(nameData)) {
                edt_fullname.error = "Fullname is required!"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(usernameData)) {
                edt_username.error = "Username is required!"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(emailData)) {
                edt_email.error = "Email is required!"
                return@setOnClickListener
            }

            if (passwordData != passwordCheck
                || TextUtils.isEmpty(passwordData)
                || TextUtils.isEmpty(passwordCheck)
            ) {
                edt_password.error = "Password does't match!"
                edt_passwordVerif.error = "Password does't match!"
                return@setOnClickListener
            }

            progressBarRegister.visibility = View.VISIBLE

            fAuthorization.createUserWithEmailAndPassword(emailData, passwordData)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Registration success!",
                            Toast.LENGTH_SHORT
                        ).show()

                        userID = fAuthorization.currentUser!!.uid

                        val docReference =
                            fStore.collection("Users").document(userID)

                        val userData: MutableMap<String, Any> = HashMap()

                        userData["fullname"] = nameData
                        userData["username"] = usernameData
                        userData["email"] = emailData

                        if (rb_admin.isChecked) userData["role"] = "Administrator"
                        else if (rb_user.isChecked) userData["role"] = "User"

                        docReference.set(userData)
                            .addOnSuccessListener {
                                Log.d(TAG, "onSuccess : Successfully created user with ID $userID")
                            }
                            .addOnFailureListener { e: Exception ->
                                Log.d(TAG, "onFailure : ${e.message}")
                            }
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            this,
                            "Registration failed! ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBarRegister.visibility = View.GONE
                    }
                }

        }
    }
}
