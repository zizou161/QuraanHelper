package com.example.readtest.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.readtest.R
import com.example.readtest.activities.AyateActivity
import com.example.readtest.models.History
import com.example.readtest.room.HistoryDataBase
import java.util.*
import kotlin.collections.ArrayList

class JoudhourAdapter(val jidhrList : ArrayList<String>, val db: HistoryDataBase) :
    RecyclerView.Adapter<JoudhourAdapter.JoudhourHolder>(), Filterable {

    val historydao = db.historyDao()
    var jidhrs = jidhrList

    class JoudhourHolder(v:View) : RecyclerView.ViewHolder(v){
        val jidhrText = v.findViewById<TextView>(R.id.jidhr_word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoudhourHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.jidhr_list_item, parent, false)
        return JoudhourHolder(itemView)
    }

    override fun onBindViewHolder(holder: JoudhourHolder, position: Int) {
        var jidhr = jidhrs[position]
        holder.jidhrText.text = jidhr
        holder.itemView.setOnClickListener {
            historydao.addHistory(History(jidhr))
            val intent = Intent(holder.itemView.context, AyateActivity::class.java)
            intent.putExtra("JIDHR",jidhr)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = jidhrs.size

    override fun getFilter(): Filter = object : Filter(){

        override fun performFiltering(filterSequence: CharSequence?): FilterResults {
            val result = FilterResults()

            if(jidhrList != null){
                jidhrs = if(filterSequence.isNullOrEmpty()){
                    jidhrList!!
                }else{
                    val filteredList = ArrayList<String>()
                    val constraint = filterSequence.toString().lowercase(Locale.getDefault())
                    for(Item in jidhrList!!){
                        if(Item.contains(constraint)){
                            filteredList.add(Item)
                        }
                    }
                    filteredList
                }
            }

            //publish the results that you got from the list
            result.values =  jidhrs
            return result
        }


        override fun publishResults(filterSequence: CharSequence?, result: FilterResults?) {
            @Suppress("UNCHECKED_CAST")
            jidhrs = result?.values as ArrayList<String>
            notifyDataSetChanged()
        }
    }

}