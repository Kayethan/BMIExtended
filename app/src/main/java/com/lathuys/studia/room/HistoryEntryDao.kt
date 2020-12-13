package com.lathuys.studia.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryEntryDao {
    @Query("SELECT * FROM ${HistoryEntryData.HISTORY_TABLE}")
    fun getAll(): List<HistoryEntryData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entry: HistoryEntryData)
}