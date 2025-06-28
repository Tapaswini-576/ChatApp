package com.example.chatapp.domain.usecase

import androidx.compose.ui.semantics.text
import com.google.firebase.Firebase

import com.google.firebase.ai.ai

import com.google.firebase.ai.type.GenerativeBackend

class GenerateAiReplyUseCase {

    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")


    suspend operator fun invoke(prompt: String): String {
        val response = model.generateContent(prompt)
        return response.text ?: "I couldn’t generate a reply."
    }
}

