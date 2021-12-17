package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView


    private lateinit var backgroundThread: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        prefs = getPreferences(Context.MODE_PRIVATE)
        Log.i("test", "onCreate")
    }


    override fun onPause() {
        super.onPause()
        Log.i("test", "onPause")
        backgroundThread.cancel()
        prefs.edit().putInt(getString(R.string.time), secondsElapsed).apply()
    }

    override fun onResume() {
        secondsElapsed = prefs.getInt(getString(R.string.time), 0)
        backgroundThread = GlobalScope.launch {
            val del: Long = System.nanoTime() / 1000000 % 1000
            var prev = 0L
            while (this.isActive) {
                val cur: Long = System.nanoTime() / 1000000
                if ((cur - del) % 1000 == 0L && cur != prev ) {
                    textSecondsElapsed.post {
                        textSecondsElapsed.text =
                            String.format(getString(R.string.seconds), secondsElapsed++);
                    }
                    val finish: Long = System.nanoTime() / 1000000
                    Log.i("time","Time passed: " + (finish - prev))
                    prev = cur
                }
            }
        }
        super.onResume()
        Log.i("test", "onResume")
        Log.i("test", "Count of threads: " + Thread.getAllStackTraces().size)
    }

}

