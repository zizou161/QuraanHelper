package com.example.readtest.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.readtest.ApiRequests
import com.example.quranapp.api.tafsiirItem
import com.example.readtest.R
import com.example.readtest.activities.BASE_URL
import kotlinx.android.synthetic.main.item_select.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class RecyclerViewAdapter(private val dataSet: ArrayList<tafsiirItem>, val tafsir : TextView) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
val ayah: Int = 256
    val soura :Int = 2


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        var selecteditem :Int = 1
        var lastselecteitem :Int
        val carditem  :CardView

        init {
            lastselecteitem  = 999
            selecteditem  = 1
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

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataSet[position].author
        viewHolder.carditem.setCardBackgroundColor(Color.TRANSPARENT)
        if (viewHolder.selecteditem==position){
            viewHolder.carditem.setCardBackgroundColor(Color.parseColor("#9AD59C"))
        }
        viewHolder.itemView.setOnClickListener {
            viewHolder.carditem.setCardBackgroundColor(Color.parseColor("#9AD59C"))
            viewHolder.lastselecteitem=viewHolder.selecteditem
            println("HELLO Before: "+viewHolder.selecteditem)
            viewHolder.selecteditem=position
            println("HELLO: "+viewHolder.lastselecteitem)
            println("HELLO: "+viewHolder.selecteditem)
            notifyItemChanged(0)
            notifyItemChanged(1)
            if(dataSet.size>3){
                fetchtafasir(position)
            }
            println("HELLO: "+viewHolder.selecteditem)
           // tafsir.text=dataSet[position].name
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    private fun fetchtafasir(position: Int)  {

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)




        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getletafsir(position+1,soura,ayah).awaitResponse()
                if (response.isSuccessful) {

                    val data2 = response.body()!!
                    tafsir.text = data2.text

                }
            }
            catch (e: Exception) {
//                print("///////////////////////")
//                withContext(Dispatchers.Main){
//                    Toast.makeText(
//                        applicationContext,
//                        "Seems like something went wrong...",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            }
        }



    }
}