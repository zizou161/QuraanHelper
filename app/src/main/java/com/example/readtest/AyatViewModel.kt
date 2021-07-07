package com.example.readtest

import android.util.Log
import androidx.lifecycle.ViewModel
import org.apache.poi.ss.usermodel.Workbook
import kotlin.math.roundToInt


class AyatViewModel:ViewModel() {

     fun getRacineId(xlWb: Workbook,Jidhr : String) : Int{
         var racineId : Int = 0
        val xlWs = xlWb.getSheetAt(3)
         xlWs.rowIterator().forEach {
             row -> if (row.getCell(1).toString().equals(Jidhr))
         {
                 val str1 = row.getCell(0).toString()
           racineId =  str1.substring(0,str1.lastIndexOf('.')).toInt()
         }

         }
         return racineId
     }
     fun getQuranWords(xlWb: Workbook, idRacine : Int) : ArrayList<AyahIdentifier>
    {
        val ayatTemp = ArrayList<AyahIdentifier>()
        val wordSheet = xlWb.getSheetAt(2)
     wordSheet.rowIterator().forEach {
                row ->
            if (row.getCell(4).toString()==idRacine.toDouble().toString()) {
                val str1 =  row.getCell(2).toString()
                val str2 = row.getCell(3).toString()
                ayatTemp.add(
                    AyahIdentifier(
                       str1.substring(0,str1.lastIndexOf('.')).toInt(),
                        str2.substring(0,str2.lastIndexOf('.')).toInt()
                    )
                )
            }
        }
        Log.d("QuranWords",idRacine.toString())
        Log.d("QuranWords",ayatTemp.toString())

        return ayatTemp
    }

     fun getAllAyat(xlWb: Workbook,ayahIdentities : ArrayList<AyahIdentifier>) : ArrayList<Verset>
    {
       // Log.d("getAllAyatVerify",ayahIdentities.toString()) !!!!!!!!!Fargha
        val versets = ArrayList<Verset>()
        for (i in 0 until ayahIdentities.size) {
            Log.d("getAllIdentity", ayahIdentities[i].toString())
            versets.add(getAyat(xlWb, ayahIdentities[i]))
        }
        Log.d("getAllAyat",versets.toString())
        return versets
    }

     fun getAyat(xlWb: Workbook,ayahIdentity : AyahIdentifier) : Verset {
        var verset = Verset()
        val xlWs = xlWb.getSheetAt(1)
        xlWs.rowIterator().forEach {
                row ->
            if (
                row.getCell(1).toString() == ayahIdentity.numSurah.toDouble().toString() &&
                (row.getCell(2).toString() == ayahIdentity.ayahNum.toDouble().toString()))
            {
                val str1 = row.getCell(1).toString()
                val str2 = row.getCell(2).toString()
              verset.numSurah = str1.substring(0,str1.lastIndexOf('.')).toInt()
                verset.ayahNum = str2.substring(0,str2.lastIndexOf('.')).toInt()
                verset.versetArabicText = row.getCell(3).toString()
            }
        }
         Log.d("getAya",verset.toString())
        return verset
    }


}