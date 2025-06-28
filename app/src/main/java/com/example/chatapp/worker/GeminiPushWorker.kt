package com.example.chatapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.chatapp.domain.usecase.GenerateAiReplyUseCase
import com.example.chatapp.util.NotificationHelper

class GeminiPushWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val aiUseCase = GenerateAiReplyUseCase()

    override suspend fun doWork(): Result {
        val prompt = "Send a fun quote or motivational line"
        val reply  = aiUseCase(prompt)

        NotificationHelper.show(
            context = applicationContext,
            title   = "Gemini says:",
            body    = reply
        )
        return Result.success()
    }
}
