package com.example.quranapp.api2

import java.io.Serializable

data class Data(
    val edition: Edition,
    val hizbQuarter: Int,
    val juz: Int,
    val manzil: Int,
    val number: Int,
    val numberInSurah: Int,
    val page: Int,
    val ruku: Int,
    val sajda: Boolean,
    val surah: Surah,
    val text: String
):Serializable