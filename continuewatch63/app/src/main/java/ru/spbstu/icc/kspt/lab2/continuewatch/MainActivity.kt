package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView


    private lateinit var backgroundThread: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        prefs = getPreferences(Context.MODE_PRIVATE)
        Log.i("test", "onCreate")

        backgroundThread = lifecycleScope.launch(Dispatchers.Default) {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                var prev = System.nanoTime() / 1000000
                while (this.isActive) {
                    val cur: Long = System.nanoTime() / 1000000
                    if (cur < prev + 100) {
                        delay(900)
                        continue
                    }
                    if (cur < prev + 999) continue
                    withContext(Dispatchers.Main) {
                        textSecondsElapsed.text =
                            String.format(getString(R.string.seconds), secondsElapsed++)
                    }
                    val finish: Long = System.nanoTime() / 1000000
                    Log.i("time", "Time passed: " + (finish - prev))
                    prev += 1000
                }
            }
        }


    }


    override fun onPause() {
        super.onPause()
        Log.i("test", "onPause")
        prefs.edit().putInt(getString(R.string.time), secondsElapsed).apply()
    }

    override fun onResume() {
        secondsElapsed = prefs.getInt(getString(R.string.time), 0)
        super.onResume()
        Log.i("test", "onResume")
        Log.i("test", "Count of threads: " + Thread.getAllStackTraces().size)
    }


}

