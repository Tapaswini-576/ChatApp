package com.example.chatapp.domain.model

data class Message(
    val text: String,
    val sender: String,
    val timestamp: Long
)
