package com.example.readtest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JoudhourAdapter(val jidhrList : ArrayList<String>) :
    RecyclerView.Adapter<JoudhourAdapter.JoudhourHolder>() {

    class JoudhourHolder constructor(v:View) : RecyclerView.ViewHolder(v){
        val jidhrText = v.findViewById<TextView>(R.id.jidhr_word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoudhourHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.jidhr_list_item, parent, false)
        return JoudhourHolder(itemView)
    }

    override fun onBindViewHolder(holder: JoudhourHolder, position: Int) {
        var jidhr = jidhrList[position]
        holder.jidhrText.text = jidhr
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,AyateActivity::class.java)
            intent.putExtra("JIDHR",jidhr)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = jidhrList.size

}