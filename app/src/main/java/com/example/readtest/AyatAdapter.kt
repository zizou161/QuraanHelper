package com.example.readtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AyatAdapter(var ayatList : ArrayList<Verset>):
    RecyclerView.Adapter<AyatAdapter.AyatHolder>(){
    class AyatHolder constructor(v:View) : RecyclerView.ViewHolder(v) {
        val surahNum : TextView = v.findViewById(R.id.surah_num)
        val ayahNum : TextView = v.findViewById(R.id.ayah_num)
        val ayah : TextView = v.findViewById(R.id.ayah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ayah_list_item, parent, false)
        return AyatAdapter.AyatHolder(itemView)
    }

    override fun onBindViewHolder(holder: AyatHolder, position: Int) {
        var ayah = ayatList[position]
        holder.surahNum.text = "Surah number:" + ayah.numSurah.toString()
        holder.ayahNum.text = "Ayah number: " + ayah.ayahNum.toString()
        holder.ayah.text = ayah.versetArabicText
    }

    override fun getItemCount(): Int {
        return ayatList.size
    }

}