package com.lathuys.studia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lathuys.studia.bmi.BmiHelper
import com.lathuys.studia.databinding.ActivityBmiDetailsBinding
import kotlinx.android.synthetic.main.activity_bmi_details.*
import java.lang.Exception
import java.util.*

class BmiDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiDetailsBinding
    private var bmiValue: Float = -1.0f

    companion object {
        const val BMI_VALUE: String = "BmiValue"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.detailsTitle)

        binding = ActivityBmiDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bmi = intent.getFloatExtra(BmiHelper.BMI_VALUE, -1.0f)
        if (bmi < 0) {
            Log.wtf("BMI", "BMI value wasn't passed!")
            throw Exception("BMI value wasn't passed!")
        }

        bmiValue = bmi
    }

    override fun onStart() {
        super.onStart()

        setData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putFloat(BMI_VALUE, bmiValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        bmiValue = savedInstanceState.getFloat(BMI_VALUE)
        setData()
    }

    private fun setData() {
        bmiTV.text = "%.2f".format(Locale.ENGLISH, bmiValue)
        bmiTV.setTextColor(BmiHelper.getBmiColor(this@BmiDetailsActivity, bmiValue))
        bmiDescTV.text = BmiHelper.getBmiDesc(this@BmiDetailsActivity, bmiValue)
        bmiMessageTV.text = BmiHelper.getBmiMessage(this@BmiDetailsActivity, bmiValue)
    }
}

