package com.example.kotlinworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var flStart : FrameLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flStart = findViewById(R.id.flStart)
        flStart?.setOnClickListener{
            Toast.makeText(
                this@MainActivity,
                "Click",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}