package com.example.chatapp.domain.usecase


import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.ChatRepo

class SendMessageUseCase(private val repo: ChatRepo) {
    suspend operator fun invoke(message: Message) {
        repo.sendMessage(message)
    }
}
