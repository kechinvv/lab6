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
<<<<<<< HEAD
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView


    private lateinit var backgroundThread: Job
=======
    private var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private var stopped = false
    private var destroy = false
    private var threadNum = 0


    private var backgroundThread = Thread {
        val num = threadNum
        threadNum++
        Log.i("test", "Thread $num start")
        while (!destroy) {
            if (!stopped) {
                val start = System.currentTimeMillis()
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text =
                        String.format(getString(R.string.seconds), secondsElapsed++)
                }
                val finish = System.currentTimeMillis()
                Log.i("time","Time passed: " + (finish - start))
            }
        }
        Log.i("test", "Thread $num stop")
    }
>>>>>>> origin/master

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
<<<<<<< HEAD
        Log.i("test", "Count of threads: " + Thread.getAllStackTraces().size)
=======
        stopped = false
    }

    override fun onStart() {
        secondsElapsed = prefs.getInt(getString(R.string.time), 0)
        threadNum = prefs.getInt(getString(R.string.thread), 0)
        destroy = false
        backgroundThread.start()
        super.onStart()
        Log.i("test", "onStart")
    }

    override fun onStop() {
        destroy = true
        prefs.edit().putInt(getString(R.string.time), secondsElapsed).apply()
        prefs.edit().putInt(getString(R.string.thread), threadNum).apply()
        super.onStop()
        Log.i("test", "onStop")
>>>>>>> origin/master
    }

}
