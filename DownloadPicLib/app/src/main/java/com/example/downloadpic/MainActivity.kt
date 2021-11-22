package com.example.downloadpic

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {

    private lateinit var urlStr: List<String>
    private lateinit var image: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.image)

        urlStr = listOf(
            getString(R.string.link1),
            getString(R.string.link2),
            getString(R.string.link3),
        )

        findViewById<Button>(R.id.button1)?.setOnClickListener {
            Glide.with(this).load(urlStr[0]).into(image)
        }

        findViewById<Button>(R.id.button2)?.setOnClickListener {
            Glide.with(this).load(urlStr[1]).into(image)
        }

        findViewById<Button>(R.id.button3)?.setOnClickListener {
            Glide.with(this).load(urlStr[2]).into(image)
        }

    }
    override fun onResume() {
        super.onResume()
        Log.i("test", "Count of threads: " + Thread.getAllStackTraces().size)
    }

}
