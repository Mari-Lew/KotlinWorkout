package com.example.kotlinworkoutapp

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

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

        binding?.buttonDisplayResults?.setOnClickListener {
            if(validateMetricUnits())
            {
                val heightVal : Float = binding?.editTextMetricUnitHeight?.text.toString().toFloat() / 100
                val weightVal : Float = binding?.editTextMetricUnitWeight?.text.toString().toFloat()

                val endBMI = weightVal / (heightVal*heightVal)
                //display the results
                displayBMIResults(endBMI)
            }
            else
            {
                Toast.makeText(this@BMIActivity,
                    "Please enter valid values.",
                Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Checks if the user has entered anything into the textFields
     */
    private fun validateMetricUnits() : Boolean
    {
        var isValid = true

        if(binding?.editTextMetricUnitWeight?.text.toString().isEmpty())
        {
            isValid = false
        }
        else if(binding?.editTextMetricUnitHeight?.text.toString().isEmpty())
        {
            isValid = false
        }

        return isValid
    }

    /**
     * Checks if the user has entered anything into the textFields
     */
    private fun displayBMIResults(bmi: Float)
    {
        val bmiLabel : String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0)
        {
            bmiLabel = "VERY Severely underweight"
            bmiDescription = "Oops! You need to take care of yourself; eat more!"
        }
        else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0)
        {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You need to take care of yourself; eat more!"
        }
        else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0)
        {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You need to take care of yourself; eat more!"
        }
        else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0)
        {
            bmiLabel = "Normal BMI"
            bmiDescription = "Objectively, you are in good shape!"
        }
        else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0)
        {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You need to take care of yourself; Workout more!"
        }
        else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0)
        {
            bmiLabel = "Obese Class (Moderately obese)"
            bmiDescription = "Oops! You need to take care of yourself; Workout more!"
        }
        else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0)
        {
            bmiLabel = "Obese Class (Severely obese)"
            bmiDescription = "This level of BMI is dangerous! Please act now!"
        }
        else
        {
            bmiLabel = "Obese Class (VERY Severely obese)"
            bmiDescription = "This level of BMI is dangerous! Please act now!"
        }

        //take float then display after 2 values
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.displayBMIResults?.visibility = View.VISIBLE
        binding?.totalBMIValue?.text = bmiValue
        binding?.yourBMIType?.text = bmiLabel
        binding?.yourBMIDescription?.text = bmiDescription


        //set values accordingly

    }

}