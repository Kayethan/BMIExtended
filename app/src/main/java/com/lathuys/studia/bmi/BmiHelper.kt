package com.lathuys.studia.bmi

import android.content.Context
import androidx.core.content.ContextCompat.getColor
import com.lathuys.studia.R

class BmiHelper {
    companion object {
        const val BMI_VALUE: String = "com.lathuys.studia.BmiValue"

        const val HISTORY_PREF: String = "history-entries"
        const val HISTORY_PREFIX: String = "ENTRY_"
        const val HISTORY_MAX: Int = 10

        private const val vsUnderweight: Float = 16.0f
        private const val sUnderweight: Float = 17.0f
        private const val underweight: Float = 18.5f
        private const val normal: Float = 25.0f
        private const val overweight: Float = 30.0f
        private const val iOverweight: Float = 35.0f
        private const val iiOverweight: Float = 40.0f

        fun getBmiColor(context: Context, bmiValue: Float): Int {
            return when {
                bmiValue < vsUnderweight -> getColor(context, R.color.vsUnderWeight)
                bmiValue < sUnderweight -> getColor(context, R.color.sUnderWeight)
                bmiValue < underweight -> getColor(context, R.color.underWeight)
                bmiValue < normal -> getColor(context, R.color.normalWeight)
                bmiValue < overweight -> getColor(context, R.color.overWeight)
                bmiValue < iOverweight -> getColor(context, R.color.iOverWeight)
                bmiValue < iiOverweight -> getColor(context, R.color.iiOverWeight)
                else -> getColor(context, R.color.iiiOverWeight)
            }
        }

        fun getBmiDesc(context: Context, bmiValue: Float): String {
            return when {
                bmiValue < vsUnderweight -> context.getString(R.string.vsUnderWeight)
                bmiValue < sUnderweight -> context.getString(R.string.sUnderWeight)
                bmiValue < underweight -> context.getString(R.string.underWeight)
                bmiValue < normal -> context.getString(R.string.normalWeight)
                bmiValue < overweight -> context.getString(R.string.overWeight)
                bmiValue < iOverweight -> context.getString(R.string.iOverWeight)
                bmiValue < iiOverweight -> context.getString(R.string.iiOverWeight)
                else -> context.getString(R.string.iiiOverWeight)
            }
        }

        fun getBmiMessage(context: Context, bmiValue: Float): String {
            return when {
                bmiValue < underweight -> context.getString(R.string.tooLittleMessage)
                bmiValue < normal -> context.getString(R.string.normalMessage)
                else -> context.getString(R.string.tooMuchMessage)
            }
        }
    }
}