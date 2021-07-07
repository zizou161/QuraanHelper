package com.example.readtest

import com.example.quranapp.api.Ayah
import com.example.quranapp.api2.Ayahenglish
import com.example.quranapp.api.TextTafsir
import com.example.quranapp.api.tafsiir
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequests {

    @GET("/tafseer")
    fun gettafasir(): Call<tafsiir>

    @GET("/tafseer/{tafseer_id}/{sura_number}/{ayah_number}")
    fun getletafsir(@Path("tafseer_id") tafseer_id:Int ,@Path("sura_number") sura_number:Int ,@Path("ayah_number") ayah_number:Int  ) : Call<TextTafsir>

    @GET("/v1/ayah/{sura_number}:{ayah_number}/en.asad")
    fun getenglishayah(@Path("sura_number") souraNumber:Int,@Path("ayah_number") ayahNumber:Int): Call<Ayahenglish>

    @GET("/quran/{sura_number}/{ayah_number}")
    fun getayah(@Path("sura_number") sura_number:Int,@Path("ayah_number") ayah_number:Int): Call<Ayah>

}