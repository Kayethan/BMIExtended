package com.lathuys.studia.bmi.calc

interface IBmiCalc {
    fun checkHeight(height: Float): Int
    fun checkMass(mass: Float): Int
    fun countBmi(height: Float, mass: Float): Float
    fun createSerial(height: Float, mass: Float, bmi: Float, date: String): String
}