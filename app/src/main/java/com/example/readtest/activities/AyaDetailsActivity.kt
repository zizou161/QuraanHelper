package com.example.readtest.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readtest.ApiRequests
import com.example.quranapp.api.Ayah
import com.example.quranapp.api.tafsiir
import com.example.quranapp.api.tafsiirItem
import com.example.quranapp.api2.Ayahenglish
import com.example.readtest.R
import com.example.readtest.adapters.RecyclerViewAdapter
import com.example.readtest.adapters.RecyclerViewAdapter2
import com.example.readtest.models.données
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_aya_detail.*
import kotlinx.android.synthetic.main.alerte_dialogue.view.*
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Runnable

const val BASE_URL = "http://api.quran-tafseer.com"
const val SECOND_API = "http://api.alquran.cloud"

class AyaDetailsActivity : AppCompatActivity() {

    private lateinit var objectayah :Ayah
    private var isplaying = false
    private var totaltime : Int = 0
    private var soura : Int = 0
    private var ayah : Int = 0
    private var arrayList = ArrayList<tafsiirItem>()
    private lateinit var recyclerView :RecyclerView
    private var adapter : RecyclerViewAdapter? = null
    private lateinit var progerssProgressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aya_detail)

        val parentIntent = intent

        soura = parentIntent.getIntExtra("SURAH",0)
        ayah = parentIntent.getIntExtra("AYA",0)

        Picasso.get()
            .load("https://cdn.islamic.network/quran/images/${soura}_${ayah}.png")
            .placeholder(R.drawable.error)
            .error(R.drawable.error)
            .into(imageView)

        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        //progerssProgressDialog.show()

        getAyahEnglish()

        //données.mp= MediaPlayer.create(this,Uri
         //   .parse("http://cdn.alquran.cloud/media/audio/ayah/ar.abdulbasitmurattal/262/low"))
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)

        adapter = RecyclerViewAdapter(arrayList,textView6)
        recyclerView.adapter = adapter
        getCurrentData()

        val arrayList2 = ArrayList<String>()
        arrayList2.add("ar.alafasy")//Adding object in arraylist
        arrayList2.add("ar.abdulbasitmurattal")

        //adding a layoutmanager
        val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerview2)
        recyclerView2.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        val adapter2 = RecyclerViewAdapter2(arrayList2,this)
        recyclerView2.adapter = adapter2

        val playBtn = findViewById<Button>(R.id.playBtn)
        playBtn.setOnClickListener {
            playBtnClick()
        }

    }

    private fun getAyahEnglish()  {

        val api = Retrofit.Builder()
            .baseUrl(SECOND_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            api.getenglishayah(soura,ayah)
                .enqueue(object: Callback<Ayahenglish>{

                    override fun onResponse(call: Call<Ayahenglish>,
                        response: Response<Ayahenglish>) {
                        if (response.isSuccessful) {
                            val donne = response.body()!!
                            english.text=donne.data.text
                        }
                    }

                    override fun onFailure(call: Call<Ayahenglish>, t: Throwable) {}
            })

        }

    }

    private fun getCurrentData()  {


        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            api.gettafasir()
                .enqueue(object: Callback<tafsiir>{
                    override fun onResponse(call: Call<tafsiir>,
                        response: Response<tafsiir>) {
                        if (response.isSuccessful) {
                            val data = response.body()!!

                            runOnUiThread {
                                adapter = RecyclerViewAdapter(data,textView6)
                                recyclerView.adapter = adapter
                            }
                            //progerssProgressDialog.dismiss()
                            //setfirstrecycler()

                        }
                    }

                    override fun onFailure(call: Call<tafsiir>, t: Throwable) {}
            })
        }

    }

    fun setseekbar(){

        totaltime= données.mp.duration
        val seekbar = findViewById<SeekBar>(R.id.seekbar)
        seekbar.max = totaltime
        seekbar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        données.mp.seekTo(progress)
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )

        // Thread
        Thread(Runnable {
            while (données.mp != null) {
                try {
                    var msg = Message()
                    msg.what = données.mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()
    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                var currentPosition = msg.what
                val seekbar = findViewById<SeekBar>(R.id.seekbar)
                // Update positionBar
                seekbar.progress = currentPosition

                // Update Labels
                var elapsedTime = createTimeLabel(currentPosition)
                val text = findViewById<TextView>(R.id.minutes)
                text.text = elapsedTime

                var remainingTime = createTimeLabel(totaltime - currentPosition)
                val text2 = findViewById<TextView>(R.id.minutesrest)
                text2.text = "-$remainingTime"
                if (remainingTime.toString()=="0:00"){
                    val playBtn = findViewById<Button>(R.id.playBtn)

                        playBtn.setBackgroundResource(R.drawable.ic_play)
                }
            }
        }

    fun createTimeLabel(time: Int): String {
            var timeLabel = ""
            var min = time / 1000 / 60
            var sec = time / 1000 % 60

            timeLabel = "$min:"
            if (sec < 10) timeLabel += "0"
            timeLabel += sec

            return timeLabel
        }

    fun playBtnClick(){



            val playBtn = findViewById<Button>(R.id.playBtn)
            if (isplaying){
                isplaying=!isplaying
                données.mp.pause()

                playBtn.setBackgroundResource(R.drawable.ic_play)
            }else{
                isplaying=!isplaying
                val playBtn = findViewById<Button>(R.id.playBtn)
                données.mp.start()

                playBtn.setBackgroundResource(R.drawable.ic_pause)
            }
            setseekbar()
        }

    fun showalertedialogue(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alerte_dialogue,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Entrer un commentaire")

        val mAlertDialog= mBuilder.show()
        mDialogView.enregistrer.setOnClickListener {
            progerssProgressDialog = ProgressDialog(this)
            progerssProgressDialog.setTitle("Loading")
            progerssProgressDialog.setCancelable(false)
            progerssProgressDialog.show()
            mAlertDialog.dismiss()

            savedata(mDialogView.putnote.text.toString())
            progerssProgressDialog.dismiss()


        }
        mDialogView.fermer.setOnClickListener {
            mAlertDialog.dismiss()

        }
    }

    fun savedata(commentaire: String) {
    //    try {
    //        getayahapi()
    //
    //        for (i : Int in 0 until arrayList.size){
    //            gettafasirapi(i)
    //        }
    //    }catch (e:Exception){}finally {
    //        val ayaah =Ayah(ayah,1,"fatiha","text")
    //    }

        getayahapi()
        for (i : Int in 0 until arrayList.size){
            gettafasirapi(i)
        }
        val ayaah =Ayah(ayah,1,"fatiha","text")





    for( i : Int in 0 .. 5 ){
        try {
            gettafasirapi(0)
        }catch (e :Exception){

        }finally {
            println("3andk  iiiii " + i)
        }
        println("3andk  iiiii " + i)

    }


    //    try {
    //        getayahapi()
    //    }catch (e :Exception){}finally {
    //
    //    }


    }

    fun getayahapi(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getayah(soura,ayah).awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!

                    gettafasirapi(0)
                    objectayah=data
                    println("HAYLIK DAAATTAAAA LOWLA" + data)

                }
            }
            catch (e: Exception) {
                print("######///////////////######"+ e)
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

    fun gettafasirapi(position :Int){
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
                    println("CCCC la phase///////// : "+position)

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

    fun setfirstrecycler(){


            recyclerView.adapter!!.notifyDataSetChanged()
            progerssProgressDialog.dismiss()

        }

    //    private fun fetchtafasir(data :ArrayList<tafsiirItem>)  {
    //        val api = Retrofit.Builder()
    //            .baseUrl(BASE_URL)
    //            .addConverterFactory(GsonConverterFactory.create())
    //            .build()
    //            .create(ApiRequests::class.java)
    //
    //
    //        data.forEach {
    //
    //        GlobalScope.launch(Dispatchers.IO) {
    //            try {
    //                val response = api.getletafsir(it.id,soura,ayah).awaitResponse()
    //                if (response.isSuccessful) {
    //
    //                    println("///////////////////////////////////////////////////////////////////////////////////////222")
    //                    val data2 = response.body()!!
    //                    val kari = Kaari(it.id,it.author,data2.text)
    //                    arrayList.add(kari)
    //                    println(arrayList[it.id-1].tafsir)
    //                }
    //            }
    //            catch (e: Exception) {
    //                print("///////////////////////")
    //                withContext(Dispatchers.Main){
    //                    Toast.makeText(
    //                        applicationContext,
    //                        "Seems like something went wrong...",
    //                        Toast.LENGTH_SHORT
    //                    ).show()
    //                }
    //            }
    //        }
    //        }
    //        setfirstrecycler()
    //
    //    }

}