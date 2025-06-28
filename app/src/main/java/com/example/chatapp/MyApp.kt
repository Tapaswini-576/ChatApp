package com.example.chatapp

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.chatapp.worker.GeminiPushWorker
import com.google.firebase.FirebaseApp
import java.util.concurrent.TimeUnit

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        scheduleGeminiPush()
    }

    private fun scheduleGeminiPush() {
        val request = PeriodicWorkRequestBuilder<GeminiPushWorker>(
            6, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "gemini_push",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
