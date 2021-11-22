package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
<<<<<<< HEAD
    private val executorService: ScheduledExecutorService =
        Executors.newSingleThreadScheduledExecutor()
    private lateinit var future: ScheduledFuture<*>

    private var backgroundThread = Runnable {
        Log.i("test", Thread.currentThread().name + " working")
        textSecondsElapsed.post {
            textSecondsElapsed.text =
                String.format(getString(R.string.seconds), secondsElapsed++)
=======
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
>>>>>>> origin/master
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
        future.cancel(true);
        super.onPause()
        Log.i("test", "onPause")
        prefs.edit().putInt(getString(R.string.time), secondsElapsed).apply()
    }

    override fun onResume() {
        secondsElapsed = prefs.getInt(getString(R.string.time), 0)
        future = executorService.scheduleAtFixedRate(backgroundThread, 1, 1, TimeUnit.SECONDS)
        super.onResume()
        Log.i("test", "onResume")
        Log.i("test", "Count of threads: " + Thread.getAllStackTraces().size)
    }

<<<<<<< HEAD

    override fun onDestroy() {
        executorService.shutdown()
        super.onDestroy()
        Log.i("test", "onDestroy")
    }
=======
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
    }

>>>>>>> origin/master
}
