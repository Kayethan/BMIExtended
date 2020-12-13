package com.lathuys.studia

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.lathuys.studia.bmi.BmiHelper
import com.lathuys.studia.databinding.ActivityBmiDetailsBinding
import kotlinx.android.synthetic.main.activity_bmi_details.*
import java.lang.Exception
import java.util.*

class BmiDetailsActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityBmiDetailsBinding
    private var bmiValue: Float = -1.0f

    private var sensorManager: SensorManager? = null
    private var isLight = true

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

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager?
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

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_LIGHT) {
            if (event.values[0] < MainActivity.LIGHT_VAL && isLight) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isLight = false
            }
            else if (event.values[0] > MainActivity.LIGHT_VAL && !isLight) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                isLight = true
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}

