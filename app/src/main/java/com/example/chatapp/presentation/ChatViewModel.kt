package com.example.chatapp.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.usecase.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChatViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val generateAiReplyUseCase: GenerateAiReplyUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    init {
        viewModelScope.launch {
            getMessagesUseCase().collect { _messages.value = it }
        }
    }

    fun sendMessage(userText: String) {
        viewModelScope.launch {
            val userMsg = Message(userText, "user", System.currentTimeMillis())
            sendMessageUseCase(userMsg)

            val botReply = generateAiReplyUseCase(userText)
            val botMsg = Message(botReply, "bot", System.currentTimeMillis())
            sendMessageUseCase(botMsg)
        }
    }
}
