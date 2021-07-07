package com.example.readtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory

class AyateActivity : AppCompatActivity() {

    private lateinit var viewModel: AyatViewModel
    private lateinit var ayatAdapter : AyatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayate)
        val parentIntent = intent
        val jidhr = parentIntent.getStringExtra("JIDHR")
        val inputStream  = assets.open("Quran_Database.xls")
        var xlWb = WorkbookFactory.create(inputStream)

        viewModel = ViewModelProvider(this).get(AyatViewModel::class.java)
       initAyatRecyclerView(xlWb,viewModel.getRacineId(xlWb,jidhr))
       // initAyatRecyclerView(xlWb,5)
    }

    private fun initAyatRecyclerView(xlWb : Workbook,idRacine : Int) {
        val recyclerView = findViewById<RecyclerView>(R.id.ayat_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        ayatAdapter = AyatAdapter(getAllAyatToAdapter(xlWb,idRacine))
        recyclerView.adapter = ayatAdapter
    }

    private fun getAllAyatToAdapter(xlWb : Workbook,idRacine: Int): ArrayList<Verset> {
        return viewModel.getAllAyat(xlWb,viewModel.getQuranWords(xlWb,idRacine))
    }

}