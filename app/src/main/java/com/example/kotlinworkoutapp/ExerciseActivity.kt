package com.example.kotlinworkoutapp

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.kotlinworkoutapp.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    var binding: ActivityExerciseBinding? = null
    var restTimer: CountDownTimer? = null //how much time you want to rest
    var restProgress = 0

    var exerTimer: CountDownTimer? = null //how much time you want to rest
    var exerProgress = 0

    private var exerList : ArrayList<ExerciseModel>? = null
    private var currPosition = -1

    private var textToSpeech : TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.exerToolbar)

        if(supportActionBar != null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerList = Constants.defaultExerList()
        
        textToSpeech = TextToSpeech(this, this)

        binding?.exerToolbar?.setNavigationOnClickListener{
            onBackPressed()
        }

        // binding?.progressBar?.visibility = View.GONE
        setUpRestView()
    }

    fun setUpRestView()
    {
        try {
            val soundURI = Uri.parse(
                "android.resource://com.example.kotlinworkoutapp/" + R.raw.press_start)
            mediaPlayer = MediaPlayer.create(applicationContext, soundURI)
            mediaPlayer?.isLooping = false
            mediaPlayer?.start()

        }catch(e:Exception)
        {
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.upcomingExerName?.visibility = View.VISIBLE

        binding?.tvExercise?.visibility = View.INVISIBLE
        binding?.tvImage?.visibility = View.INVISIBLE


        if(restTimer != null)
        {
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.upcomingExerName?.text = exerList!![currPosition + 1].getName()

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
                currPosition++
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

        if(textToSpeech != null)
        {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }

        if(mediaPlayer != null)
        {
            mediaPlayer!!.stop()
        }

        binding = null
    }

    fun setUpExerView()
    {
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.progressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.upcomingExerName?.visibility = View.INVISIBLE

        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.tvExercise?.visibility = View.VISIBLE
        binding?.tvImage?.visibility = View.VISIBLE


        if(exerTimer != null)
        {
            exerTimer?.cancel()
            exerProgress = 0
        }

        speakText(exerList!![currPosition].getName())

        binding?.tvImage?.setImageResource(exerList!![currPosition].getImage())
        binding?.tvExercise?.text = exerList!![currPosition].getName()

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
               if(currPosition < exerList?.size!! - 1)
               {
                   setUpRestView()
               }
                else{
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congrats you're done",
                        Toast.LENGTH_SHORT
                    ).show()
               }
            }
        }.start()
    }

    override fun onInit(status: Int) {
        //check status
        if(status == TextToSpeech.SUCCESS)
        {
            val result = textToSpeech?.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "The language is not supported")
            }
            else
            {
                Log.e("TTS","initialization failure")
            }
        }
    }

    private fun speakText(text: String)
    {
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


}