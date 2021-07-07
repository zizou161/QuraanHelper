package com.example.readtest.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.readtest.models.History


@Dao
interface HistoryDao {

    @Insert
    fun addHistory(history: History)

    @Delete
    fun deleteHistory(history: History)

    @Query("SELECT * FROM history")
    fun getAllHistory():List<History>
}