package com.example.readtest.models

import com.example.quranapp.api.tafsiirItem

class Favori(val Ayah :Int , val Sourah :String,val Nbayah :Int,val TypeShourah :String,val TextAyah :String,val EnglishVersion :String,val moufasirin :ArrayList<tafsiirItem> ,val tafasir: ArrayList<String>, val commentaire :String) {
}