package com.example.readtest.adapters

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.readtest.R
import com.example.readtest.models.données
import kotlinx.android.synthetic.main.item_select.view.*

class RecyclerViewAdapter2(private val dataSet: ArrayList<String>,private val context :Context) :
    RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>() {
     val ayah: Int = 256
     val soura :Int = 2
     /**
      * Provide a reference to the type of views that you are using
      * (custom ViewHolder).
      */
     class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val textView: TextView
         var lastched : Int = 0
         val carditem  : CardView

         init {
             // Define click listener for the ViewHolder's View.
             textView = view.findViewById(R.id.nomitem)
             carditem= view.carditem
         }
     }

     // Create new views (invoked by the layout manager)
     override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
         // Create a new view, which defines the UI of the list item
         val view = LayoutInflater.from(viewGroup.context)
             .inflate(R.layout.item_select, viewGroup, false)

         return ViewHolder(view)
     }

     // Replace the contents of a view (invoked by the layout manager)
     override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
         viewHolder.textView.text = dataSet[position]
         // Get element from your dataset at this position and replace the
         // contents of the view with that element


         viewHolder.itemView.setOnClickListener {
             if(position==0 && données.audiochecked){
                 données.audiochecked=false
                 données.mp= MediaPlayer.create(context, Uri.parse("http://cdn.alquran.cloud/media/audio/ayah/ar.abdulbasitmurattal/262/low"))

             }else{
                 if (position!=0 && !données.audiochecked){
                     données.audiochecked=true
                     données.mp= MediaPlayer.create(context, Uri.parse("http://cdn.alquran.cloud/media/audio/ayah/ar.alafasy/262/low"))
                 }

             }
             viewHolder.carditem.setCardBackgroundColor(Color.parseColor("#9AD59C"))


         }
     }

     // Return the size of your dataset (invoked by the layout manager)
     override fun getItemCount() = dataSet.size
    fun setaudio(){

    }



}