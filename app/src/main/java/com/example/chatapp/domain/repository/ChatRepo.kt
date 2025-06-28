package com.example.chatapp.domain.repository



import com.example.chatapp.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepo {
    suspend fun sendMessage(message: Message)
    fun getMessages(): Flow<List<Message>>
}
