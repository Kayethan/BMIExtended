package com.lathuys.studia.bmi.calc

import java.util.*
import kotlin.math.pow

class MetricalBmiCalc : IBmiCalc {
    private val maxHeight: Float = 300.0f
    private val minHeight: Float = 10.0f
    private val maxMass: Float = 1000.0f
    private val minMass: Float = 1f

    override fun checkHeight(height: Float): Int {
        return when {
            height > maxHeight -> 1
            height < minHeight -> -1
            else -> 0
        }
    }

    override fun checkMass(mass: Float): Int {
        return when {
            mass > maxMass -> 1
            mass < minMass -> -1
            else -> 0
        }
    }

    override fun countBmi(height: Float, mass: Float): Float {
        return mass / (height / 100.0f).pow(2)
    }

    override fun createSerial(height: Float, mass: Float, bmi: Float, date: String): String {
        return "%.2f cm|%.2f kg|%.2f|%s".format(Locale.ENGLISH, height, mass, bmi, date)
    }
}