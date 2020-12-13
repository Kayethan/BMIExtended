package com.lathuys.studia.bmicalc

import com.lathuys.studia.bmi.calc.MetricalBmiCalc
import org.junit.Test

import org.junit.Assert.*

class MetricalBmiCalcTests {
    private val mbc = MetricalBmiCalc()

    @Test
    fun checkHeight_Correct() {
        assertEquals(0, mbc.checkHeight(10.0f))
        assertEquals(0, mbc.checkHeight(100.0f))
        assertEquals(0, mbc.checkHeight(200.0f))
        assertEquals(0, mbc.checkHeight(300.0f))
    }

    @Test
    fun checkHeight_TooLittle() {
        assertEquals(-1, mbc.checkHeight(9.99f))
        assertEquals(-1, mbc.checkHeight(-100.0f))
    }

    @Test
    fun checkHeight_TooMuch() {
        assertEquals(1, mbc.checkHeight(300.01f))
        assertEquals(1, mbc.checkHeight(1000.0f))
    }

    @Test
    fun checkMass_Correct() {
        assertEquals(0, mbc.checkMass(10.0f))
        assertEquals(0, mbc.checkMass(124.0f))
        assertEquals(0, mbc.checkMass(523.0f))
        assertEquals(0, mbc.checkMass(1000.0f))
    }

    @Test
    fun checkMass_TooLittle() {
        assertEquals(-1, mbc.checkMass(0.425f))
        assertEquals(-1, mbc.checkMass(-100.0f))
    }

    @Test
    fun checkMass_TooMuch() {
        assertEquals(1, mbc.checkMass(1000.01f))
        assertEquals(1, mbc.checkMass(10120.0f))
    }

    @Test
    fun countBmi_Correct() {
        assertEquals(20.06f, mbc.countBmi(180.0f, 65.0f), 0.01f)
        assertEquals(27.70f, mbc.countBmi(190.0f, 100.0f), 0.01f)
        assertEquals(35.56f, mbc.countBmi(150.0f, 80.0f), 0.01f)
        assertEquals(13.84f, mbc.countBmi(170.0f, 40.0f), 0.01f)
    }
}