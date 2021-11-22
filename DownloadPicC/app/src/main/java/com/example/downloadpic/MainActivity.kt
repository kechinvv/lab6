package com.example.downloadpic

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private var num = 0
    private lateinit var urlStr: List<String>
    private lateinit var image: ImageView
    private lateinit var mIcon: Bitmap

    private fun backgroundThread() {
        val newUrl = URL(urlStr[num])
        mIcon = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream())
        runOnUiThread(setPic)
    }

    private var setPic = Runnable { image.setImageBitmap(mIcon) }


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
            GlobalScope.launch { backgroundThread() }
        }

        findViewById<Button>(R.id.button2)?.setOnClickListener {
            num = 1
            GlobalScope.launch { backgroundThread() }
        }

        findViewById<Button>(R.id.button3)?.setOnClickListener {
            num = 2
            GlobalScope.launch { backgroundThread() }
        }

    }

    override fun onResume() {
        super.onResume()
        Log.i("test", "Count of threads: " + Thread.getAllStackTraces().size)
    }

}
