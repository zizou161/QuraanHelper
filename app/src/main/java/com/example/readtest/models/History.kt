package com.example.readtest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class History(
    @ColumnInfo(name = "history")
    val history: String = ""
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_history")
    var idHistory: Int = 0
}
