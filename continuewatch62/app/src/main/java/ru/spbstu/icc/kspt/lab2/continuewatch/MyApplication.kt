package ru.spbstu.icc.kspt.lab2.continuewatch

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class MyApplication : Application() {

    val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

}
