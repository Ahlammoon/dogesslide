package com.example.dogesslide.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.dogesslide.`object`.ApiAdapter
//import com.example.dogesslide.R
import com.example.dogesslide.R

import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() , CoroutineScope by MainScope() {


    var imgView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgView = findViewById<ImageView>(R.id.random_image)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val btnStop= findViewById<Button>(R.id.btn_stop)
        btnStart.setOnClickListener {

            launch {
                while (true) {

                    delay(3000)
                    startShow()
                }
            }
        }// End Start button

        btnStop.setOnClickListener{
            this.coroutineContext.cancelChildren()
            Toast.makeText(this@MainActivity, "Slideshow paused", Toast.LENGTH_SHORT).show()
        }
    }


     fun startShow(){
         launch(Dispatchers.Main) {
             try {
                 val response = ApiAdapter.apiClient.getRandomAnimalImage()
                 if(response.isSuccessful && response.body() != null){
                     val data = response.body()!!
                     Toast.makeText(this@MainActivity, data.status, Toast.LENGTH_LONG).show()
                     data.message?.let {
//                            imgView.load(data.message)
                         Picasso.with(applicationContext).load(data.message).into(imgView)
                     }
                 }else {
                     Toast.makeText(this@MainActivity, "Error Occured: ${response.message()}", Toast.LENGTH_LONG).show()
                 }
             } catch (error : Error){
                 Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_LONG).show()
             }
         }



    }
}