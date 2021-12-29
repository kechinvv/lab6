package com.example.downloadpic

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.net.URL


class MainActivity : AppCompatActivity() {


    private var num = 0
    private lateinit var urlStr: List<String>
    private lateinit var image: ImageView


    private val backgroundThread = Runnable {
        val newUrl = URL(urlStr[num])
        val mIcon = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream())
        runOnUiThread { image.setImageBitmap(mIcon) }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.image)
        urlStr = listOf(
            getString(R.string.link1),
            getString(R.string.link2),
            getString(R.string.link3)
        )


        findViewById<Button>(R.id.button1)?.setOnClickListener {
            num = 0
            (application as MyApplication).executor.submit(backgroundThread)
        }

        findViewById<Button>(R.id.button2)?.setOnClickListener {
            num = 1
            (application as MyApplication).executor.submit(backgroundThread)
        }

        findViewById<Button>(R.id.button3)?.setOnClickListener {
            num = 2
            (application as MyApplication).executor.submit(backgroundThread)
        }

    }

    override fun onResume() {
        super.onResume()
        Log.i("test", "Count of threads: " + Thread.getAllStackTraces().size)
    }
}
