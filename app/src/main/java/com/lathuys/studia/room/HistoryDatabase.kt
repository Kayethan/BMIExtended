package com.lathuys.studia.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [HistoryEntryData::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyEntryDao(): HistoryEntryDao

    companion object {
        private var INSTANCE: HistoryDatabase? = null
        private const val DB_NAME = "HistoryDB"

        fun getDatabase(context: Context): HistoryDatabase {
            if (INSTANCE == null) {
                synchronized(HistoryDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, HistoryDatabase::class.java, DB_NAME)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return INSTANCE!!
        }
    }
}