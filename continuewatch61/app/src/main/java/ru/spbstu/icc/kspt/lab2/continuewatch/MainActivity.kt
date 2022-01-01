package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    @Volatile
    private var destroy = false
    private var threadNum = 0


    private var backgroundThread = Runnable {
        val num = threadNum
        threadNum++
        Log.i("test", "Thread $num start")
        while (!destroy) {
            val start = System.currentTimeMillis()
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text =
                    String.format(getString(R.string.seconds), secondsElapsed++)
            }
            val finish = System.currentTimeMillis()
            Log.i("time", "Time passed: " + (finish - start))

        }
        Log.i("test", "Thread $num stop")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        prefs = getPreferences(Context.MODE_PRIVATE)
        Log.i("test", "onCreate")
    }


    override fun onPause() {
        super.onPause()
        destroy = true
        prefs.edit().putInt(getString(R.string.time), secondsElapsed).apply()
        prefs.edit().putInt(getString(R.string.thread), threadNum).apply()
        Log.i("test", "onPause")
    }

    override fun onResume() {
        secondsElapsed = prefs.getInt(getString(R.string.time), 0)
        threadNum = prefs.getInt(getString(R.string.thread), 0)
        destroy = false
        Thread(backgroundThread).start()
        super.onResume()
        Log.i("test", "onResume")
    }


}
