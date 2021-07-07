package com.example.quranapp.api

data class TextTafsir(
    val ayah_number: Int,
    val ayah_url: String,
    val tafseer_id: Int,
    val tafseer_name: String,
    val text: String
)