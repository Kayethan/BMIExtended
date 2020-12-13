package com.lathuys.studia

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.lathuys.studia.bmi.BmiHelper
import com.lathuys.studia.bmi.calc.IBmiCalc
import com.lathuys.studia.bmi.calc.ImperialBmiCalc
import com.lathuys.studia.bmi.calc.MetricalBmiCalc
import com.lathuys.studia.bmi.history.HistoryEntry
import com.lathuys.studia.databinding.ActivityMainBinding
import com.lathuys.studia.room.HistoryDatabase
import com.lathuys.studia.room.HistoryEntryDao
import com.lathuys.studia.room.HistoryEntryData
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private var currentCalc: IBmiCalc = MetricalBmiCalc()
    private var bmiValue = -1.0f

    private lateinit var historyDao: HistoryEntryDao
    private lateinit var db: HistoryDatabase

    private var sensorManager: SensorManager? = null
    private var isLight = true

    companion object {
        const val CALC: String = "BmiCalcName"
        const val CALC_METRIC: String = "Metric"
        const val CALC_IMPERIAL: String = "Imperial"

        const val BMI_VALUE: String = "BmiValue"
        const val BMI_TV_CONTENT: String = "BmiTVContent"
        const val BMI_TV_COLOR: String = "BmiTVColor"

        const val LIGHT_VAL: Int = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = HistoryDatabase.getDatabase(this)
        historyDao = db.historyEntryDao()

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager?
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        when (currentCalc) {
            is MetricalBmiCalc -> outState.putString(CALC, CALC_METRIC)
            is ImperialBmiCalc -> outState.putString(CALC, CALC_IMPERIAL)
        }

        outState.putFloat(BMI_VALUE, bmiValue)

        outState.putString(BMI_TV_CONTENT, bmiTV.text.toString())
        outState.putInt(BMI_TV_COLOR, bmiTV.currentTextColor)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        when (savedInstanceState.getString(CALC)) {
            CALC_METRIC -> changeToMetrical()
            CALC_IMPERIAL -> changeToImperial()
        }

        bmiValue = savedInstanceState.getFloat(BMI_VALUE)

        bmiTV.text = savedInstanceState.getString(BMI_TV_CONTENT)
        bmiTV.setTextColor(savedInstanceState.getInt(BMI_TV_COLOR))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeUnits -> {
                when (currentCalc) {
                    is MetricalBmiCalc -> changeToImperial()
                    is ImperialBmiCalc -> changeToMetrical()
                }
            }
            R.id.showHistory -> {
                val intent = Intent(this, HistoryActivity::class.java).apply {

                }
                startActivityForResult(intent, 0)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun changeToMetrical() {
        Log.i("test", "Change to Metric")
        currentCalc = MetricalBmiCalc()
        heightTV.text = getString(R.string.height_cm)
        massTV.text = getString(R.string.mass_kg)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // jasny
    }

    private fun changeToImperial() {
        Log.i("test", "Change to Imperial")
        currentCalc = ImperialBmiCalc()
        heightTV.text = getString(R.string.height_in)
        massTV.text = getString(R.string.mass_lb)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) // ciemny
    }

    fun count(view: View) {
        Log.d("test", "test")
        binding.apply {
            var problem = false

            var height = 0.0f
            var mass = 0.0f

            when {
                heightET.text.isBlank() -> {
                    heightET.error = getString(R.string.height_is_empty)
                    problem = true
                }
                else -> {
                    height = heightET.text.toString().toFloat()
                    when (currentCalc.checkHeight(height)) {
                        1 -> {
                            heightET.error = getString(R.string.height_is_too_big)
                            problem = true
                        }
                        -1 -> {
                            heightET.error = getString(R.string.height_is_too_little)
                            problem = true
                        }
                    }
                }
            }

            when {
                massET.text.isBlank() -> {
                    massET.error = getString(R.string.mass_is_empty)
                    problem = true
                }
                else -> {
                    mass = massET.text.toString().toFloat()
                    when (currentCalc.checkMass(mass)) {
                        1 -> {
                            massET.error = getString(R.string.mass_is_too_big)
                            problem = true
                        }
                        -1 -> {
                            massET.error = getString(R.string.mass_is_too_little)
                            problem = true
                        }
                    }
                }
            }

            when {
                !problem -> {
                    bmiValue = currentCalc.countBmi(height, mass)
                    bmiTV.text = "%.2f".format(Locale.ENGLISH, bmiValue)

                    bmiTV.setTextColor(BmiHelper.getBmiColor(this@MainActivity, bmiValue))

                    addResultToRoom(
                        HistoryEntry.createFromSerial(
                            currentCalc.createSerial(
                                height,
                                mass,
                                bmiValue,
                                LocalDate.now().toString()
                            )
                        )
                    )
                }
                else -> {
                    bmiTV.text = getString(R.string.empty_value)
                    bmiTV.setTextColor(getColor(R.color.textDefault))

                    bmiValue = -1.0f
                }
            }
        }
    }

    private fun addResultToRoom(entry: HistoryEntry) {
        val historyEntryData = HistoryEntryData(
            height = entry.height,
            mass = entry.mass,
            bmi = entry.bmi,
            date = entry.date
        )
        historyDao.insert(historyEntryData)
    }

    fun showDetails(view: View) {
        if (bmiValue < 0)
            return

        val bmi = bmiValue
        val intent = Intent(this, BmiDetailsActivity::class.java).apply {
            putExtra(BmiHelper.BMI_VALUE, bmi)
        }
        startActivityForResult(intent, 0)
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
            if (event.values[0] < LIGHT_VAL && isLight) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isLight = false
            }
            else if (event.values[0] > LIGHT_VAL && !isLight) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                isLight = true
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}