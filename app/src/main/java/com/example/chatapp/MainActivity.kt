package com.example.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.chatapp.data.remote.ChatRemoteDataSource
import com.example.chatapp.data.repository.ChatRepositoryImpl
import com.example.chatapp.domain.usecase.GenerateAiReplyUseCase
import com.example.chatapp.domain.usecase.GetMessagesUseCase
import com.example.chatapp.domain.usecase.SendMessageUseCase
import com.example.chatapp.presentation.ChatViewModel
import com.example.chatapp.presentation.screen.ChatScreen
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.worker.GeminiPushWorker
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        val repo = ChatRepositoryImpl(ChatRemoteDataSource())
        val vm = ChatViewModel(
            SendMessageUseCase(repo),
            GetMessagesUseCase(repo),
            GenerateAiReplyUseCase()
        )

        setContent {
            ChatScreen(vm)
        }

        val testWork = OneTimeWorkRequestBuilder<GeminiPushWorker>().build()
        WorkManager.getInstance(this).enqueue(testWork)

    }
}
