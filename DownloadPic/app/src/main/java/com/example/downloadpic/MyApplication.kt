package com.example.downloadpic

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyApplication : Application() {

    val executor: ExecutorService = Executors.newSingleThreadExecutor()

}