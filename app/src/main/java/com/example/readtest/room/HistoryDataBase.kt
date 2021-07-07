package com.example.readtest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.readtest.models.History

@Database(entities = [History::class],version = 1)
abstract class HistoryDataBase: RoomDatabase() {

    abstract fun historyDao(): HistoryDao

}