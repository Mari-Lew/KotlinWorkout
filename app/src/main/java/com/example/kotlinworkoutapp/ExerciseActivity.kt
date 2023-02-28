package com.example.kotlinworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinworkoutapp.databinding.ActivityExerciseBinding
import com.example.kotlinworkoutapp.databinding.BackButtonDialogBinding
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

    private var adapter : ExerciseAdapter? = null

    private var restTimerDuration: Long = 1
    private var exerTimerDuration: Long = 1

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
            customBackDialog()
        }

        exerList = Constants.defaultExerList()
        textToSpeech = TextToSpeech(this, this)

        // binding?.progressBar?.visibility = View.GONE
        setUpRestView()
        setUpRV()
    }

    private fun customBackDialog() {
       val customDialog = Dialog(this )
        val dialogBinding = BackButtonDialogBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.YesButton.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.NoButton.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

    override fun onBackPressed() {
        customBackDialog()

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
        binding?.flExerciseView?.visibility = View.INVISIBLE
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

        restTimer = object: CountDownTimer(restTimerDuration * 1000, 1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.timer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                // restProgress = 0;
                currPosition++

                exerList!![currPosition].setIsSelected(true)
                adapter!!.notifyDataSetChanged()

                setUpExerView()
            }
        }.start()
    }

    fun setUpExerView()
    {
        binding?.flRestView?.visibility = View.INVISIBLE
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

    fun setExerPB()
    {
        binding?.progressBarEXER?.progress = exerProgress

        exerTimer = object: CountDownTimer(exerTimerDuration*1000, 1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                exerProgress++
                binding?.progressBarEXER?.progress = exerTimerDuration.toInt() - exerProgress
                binding?.timerEXER?.text = (exerTimerDuration.toInt() - exerProgress).toString()
            }

            override fun onFinish() {

                if(currPosition < exerList?.size!! - 1)
                {
                    exerList!![currPosition].setIsSelected(false)
                    exerList!![currPosition].setIsCompleted(true)
                    adapter!!.notifyDataSetChanged()

                    setUpRestView()
                } else {
                    //this is not going to be exerciseActivity, but instead the countdown timer
                    finish() //this alone takes you back to the 7 min workout page

                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    override fun onDestroy() {
        if(restTimer != null)
        {
            restTimer?.cancel()
            restProgress = 0
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

        super.onDestroy()
        binding = null
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

    fun setUpRV()
    {
        binding?.rvExerStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter = ExerciseAdapter(exerList!!)
        binding?.rvExerStatus?.adapter = adapter
    }
}