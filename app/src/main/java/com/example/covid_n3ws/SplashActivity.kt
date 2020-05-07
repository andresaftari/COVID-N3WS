package com.example.covid_n3ws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.Exception

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val doInBackground = object : Thread() {
            override fun run() {
                try {
                    sleep(3500)
                    startActivity(Intent(baseContext, MainActivity::class.java))
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        doInBackground.start()
    }
}
