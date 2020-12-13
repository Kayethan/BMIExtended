package com.lathuys.studia.bmi.history

class HistoryEntry(var height: String, var mass: String, var bmi: String, var date: String) {
    companion object {
        fun createFromSerial(serial: String): HistoryEntry {
            val values = serial.split('|')
            val height = values[0]
            val mass = values[1]
            val bmi = values[2]
            val date = values[3]

            return HistoryEntry(height, mass, bmi, date)
        }
    }
}