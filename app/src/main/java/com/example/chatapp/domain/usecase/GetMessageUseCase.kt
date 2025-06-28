package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.ChatRepo

class GetMessagesUseCase(private val repo: ChatRepo) {
    operator fun invoke() = repo.getMessages()
}
