package com.example.covid_n3ws.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.covid_n3ws.MainActivity
import com.example.covid_n3ws.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private var fAuthorization: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(main_toolbar)

        tv_goToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btn_login.setOnClickListener {
            val emailLogin = edt_LoginEmail.text.toString().trim()
            val usernameLogin = edt_Loginusername.text.toString().trim()
            val passwordLogin = edt_Loginpassword.text.toString()

            if (TextUtils.isEmpty(emailLogin)) {
                edt_LoginEmail.error = "Email is required!"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(usernameLogin)) {
                edt_Loginusername.error = "Username is required!"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(passwordLogin)) {
                edt_Loginpassword.error = "Password is required!"
                return@setOnClickListener
            }

            pb_login.visibility = View.VISIBLE

            fAuthorization.signInWithEmailAndPassword(emailLogin, passwordLogin)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Login success! Welcome back $usernameLogin !",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            this,
                            "Login Failed! ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        pb_login.visibility = View.INVISIBLE
                    }
                }
        }

        tv_reset?.setOnClickListener {v: View ->
            val resetDialog: AlertDialog.Builder = AlertDialog.Builder(v.context)
            val resetEmail = EditText(v.context)

            resetDialog.setTitle("Reset Password")
            resetDialog.setMessage("Enter your email to receive the password reset link")
            resetDialog.setView(resetEmail)

            resetDialog.setPositiveButton("Yes") { _, _ ->
                val email: String = resetEmail.text.toString()

                fAuthorization.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Reset link has been sent to your email, check your Inbox and Spam",
                            Toast.LENGTH_LONG
                        ).show()
                    }.addOnFailureListener { e: Exception ->
                        Toast.makeText(
                            this,
                            "Error while sending reset link ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }

            }
            resetDialog.setNegativeButton("No") { _, _ -> }
            resetDialog.create().show()
        }
    }
}
