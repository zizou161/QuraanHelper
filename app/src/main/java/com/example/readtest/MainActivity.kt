package com.example.readtest

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory


class MainActivity : AppCompatActivity() {

    private lateinit var jidhrAdapter : JoudhourAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PackageManager.PERMISSION_GRANTED)
        //Workbook Initialisation
        val inputStream  = assets.open("Quran_Database.xls")
        var xlWb = WorkbookFactory.create(inputStream)

        val joudhourList : ArrayList<String> = getJoudhourList(xlWb)
        initJoudhourRecyclerView(joudhourList)
    }

    private fun initJoudhourRecyclerView(joudhourList : ArrayList<String>) {
        val recyclerView = findViewById<RecyclerView>(R.id.jidhr_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext,VERTICAL,false)
        recyclerView.setHasFixedSize(false)
        jidhrAdapter = JoudhourAdapter(joudhourList)
        recyclerView.adapter = jidhrAdapter
    }

    private fun getJoudhourList(xlWb: Workbook): ArrayList<String> {
        val joudhourList = ArrayList<String>()
        val xlWs = xlWb.getSheetAt(3)
        val xlRows = xlWs.rowIterator()
        xlRows.forEach { row -> joudhourList.add(row.getCell(1).toString()) }
        return joudhourList
    }

/*
    private fun getQuranWords(xlWb: Workbook,idRacine : Int) : ArrayList<AyahIdentifier>
    {
        val ayatTemp = ArrayList<AyahIdentifier>()
        val wordSheet = xlWb.getSheetAt(2)
        wordSheet.rowIterator().forEach {
            row ->
            if (row.getCell(4).toString().equals(idRacine.toString())) {
                ayatTemp.add(
                    AyahIdentifier(
                        row.getCell(2).toString().toInt(),
                        row.getCell(3).toString().toInt()
                    )
                )
            }
        }

        return ayatTemp
    }
*/

/*    private fun getAyatList(xlWb: Workbook,ayahIdentity : AyahIdentifier) : Verset {
        var verset = Verset()
        val xlWs = xlWb.getSheetAt(1)
        xlWs.rowIterator().forEach {
            row ->
            if (
                row.getCell(1).toString().equals(ayahIdentity.numSurah.toString()) &&
                (row.getCell(2).toString().equals(ayahIdentity.ayahNum.toString())))
            {
                verset.numSurah = row.getCell(1).toString().toInt()
                verset.ayahNum = row.getCell(2).toString().toInt()
            }
        }
        return verset
    }*/

}