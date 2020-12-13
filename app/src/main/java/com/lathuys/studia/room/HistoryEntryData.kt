package com.lathuys.studia.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lathuys.studia.room.HistoryEntryData.Companion.HISTORY_TABLE

@Entity(
    tableName = HISTORY_TABLE
)
data class HistoryEntryData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "height") val height: String,
    @ColumnInfo(name = "mass") val mass: String,
    @ColumnInfo(name = "bmi") val bmi: String,
    @ColumnInfo(name = "date") val date: String
) {
    companion object {
        const val HISTORY_TABLE = "history"
    }
}