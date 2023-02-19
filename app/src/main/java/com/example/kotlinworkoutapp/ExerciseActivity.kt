package com.example.kotlinworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.kotlinworkoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    var binding: ActivityExerciseBinding? = null
    var restTimer: CountDownTimer? = null //how much time you want to rest
    var restProgress = 0

    var exerTimer: CountDownTimer? = null //how much time you want to rest
    var exerProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.exerToolbar)

        if(supportActionBar != null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.exerToolbar?.setNavigationOnClickListener{
            onBackPressed()
        }

        // binding?.progressBar?.visibility = View.GONE
        setUpRestView()
    }

    fun setUpRestView()
    {
        if(restTimer != null)
        {
            restTimer?.cancel()
            restProgress = 0
        }

        setRestPB()
    }

    /**
     * Sets the rest progress bar
     */
    fun setRestPB()
    {
        binding?.progressBar?.progress = restProgress

        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.text = "REST NOW"


        restTimer = object: CountDownTimer(10000, 1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.timer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                restProgress = 0;
                setUpExerView()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null)
        {
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerTimer != null)
        {
            exerTimer?.cancel()
            exerProgress = 0
        }

        binding = null
    }

    fun setUpExerView()
    {
        binding?.progressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.text = "Exercise Name"
        binding?.flExerciseView?.visibility = View.VISIBLE


        if(exerTimer != null)
        {
            exerTimer?.cancel()
            exerProgress = 0
        }

        setExerPB()
    }

    /**
     * Sets the rest progress bar
     */
    fun setExerPB()
    {
        binding?.progressBarEXER?.progress = exerProgress

        exerTimer = object: CountDownTimer(30000, 1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                exerProgress++
                binding?.progressBarEXER?.progress = 30 - exerProgress
                binding?.timerEXER?.text = (30 - exerProgress).toString()
            }

            override fun onFinish() {
               setRestPB()
            }
        }.start()
    }
}