package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.*


class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView


    private lateinit var future: ScheduledFuture<*>

    private var backgroundThread = Runnable {
        Log.i("test",  Thread.currentThread().name + " working")
       runOnUiThread {
            textSecondsElapsed.text =
                String.format(getString(R.string.seconds), secondsElapsed++)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        prefs = getPreferences(Context.MODE_PRIVATE)
        Log.i("test", "onCreate")
    }


    override fun onPause() {
        future.cancel(true);
        super.onPause()
        Log.i("test", "onPause")
        prefs.edit().putInt(getString(R.string.time), secondsElapsed).apply()
    }

    override fun onResume() {
        secondsElapsed = prefs.getInt(getString(R.string.time), 0)
        future = (application as MyApplication).executor.scheduleAtFixedRate(backgroundThread, 1, 1, TimeUnit.SECONDS)
        super.onResume()
        Log.i("test", "onResume")
        Log.i("test", "Count of threads: "+ Thread.getAllStackTraces().size)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("test", "onDestroy")
    }
}
