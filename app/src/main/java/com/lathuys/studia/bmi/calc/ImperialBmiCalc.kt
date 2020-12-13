package com.lathuys.studia.bmi.calc

import java.util.*
import kotlin.math.pow

class ImperialBmiCalc : IBmiCalc {
    private val maxHeight: Float = 118.0f
    private val minHeight: Float = 4.0f
    private val maxMass: Float = 2200.0f
    private val minMass: Float = 2.2f

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
        return mass / height.pow(2) * 703
    }

    override fun createSerial(height: Float, mass: Float, bmi: Float, date: String): String {
        return "%.2f in|%.2f lb|%.2f|%s".format(Locale.ENGLISH, height, mass, bmi, date)
    }
}