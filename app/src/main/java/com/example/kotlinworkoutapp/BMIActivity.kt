package com.example.kotlinworkoutapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinworkoutapp.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.bmiToolbar)
        if(supportActionBar != null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding?.bmiToolbar?.setNavigationOnClickListener{
            onBackPressed()
        }
    }
}