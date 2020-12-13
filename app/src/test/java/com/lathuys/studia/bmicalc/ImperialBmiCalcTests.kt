package com.lathuys.studia.bmicalc

import com.lathuys.studia.bmi.calc.ImperialBmiCalc
import org.junit.Assert
import org.junit.Test

class ImperialBmiCalcTests {
    private val ibc = ImperialBmiCalc()

    @Test
    fun checkHeight_Correct() {
        Assert.assertEquals(0, ibc.checkHeight(4.0f))
        Assert.assertEquals(0, ibc.checkHeight(20.0f))
        Assert.assertEquals(0, ibc.checkHeight(90.0f))
        Assert.assertEquals(0, ibc.checkHeight(118.0f))
    }

    @Test
    fun checkHeight_TooLittle() {
        Assert.assertEquals(-1, ibc.checkHeight(3.99f))
        Assert.assertEquals(-1, ibc.checkHeight(-100.0f))
    }

    @Test
    fun checkHeight_TooMuch() {
        Assert.assertEquals(1, ibc.checkHeight(118.01f))
        Assert.assertEquals(1, ibc.checkHeight(1000.0f))
    }

    @Test
    fun checkMass_Correct() {
        Assert.assertEquals(0, ibc.checkMass(2.2f))
        Assert.assertEquals(0, ibc.checkMass(124.2f))
        Assert.assertEquals(0, ibc.checkMass(1202.4f))
        Assert.assertEquals(0, ibc.checkMass(2200.0f))
    }

    @Test
    fun checkMass_TooLittle() {
        Assert.assertEquals(-1, ibc.checkMass(2.19f))
        Assert.assertEquals(-1, ibc.checkMass(-100.0f))
    }

    @Test
    fun checkMass_TooMuch() {
        Assert.assertEquals(1, ibc.checkMass(2200.01f))
        Assert.assertEquals(1, ibc.checkMass(10120.0f))
    }

    @Test
    fun countBmi_Correct() {
        Assert.assertEquals(27.50f, ibc.countBmi(75.0f, 220.0f), 0.01f)
        Assert.assertEquals(19.94f, ibc.countBmi(71.0f, 143.0f), 0.01f)
        Assert.assertEquals(35.54f, ibc.countBmi(59.0f, 176.0f), 0.01f)
        Assert.assertEquals(13.78f, ibc.countBmi(67.0f, 88.0f), 0.01f)
    }
}