package com.example.chatapp.data.model

data class message(
    val text: String = "",
    val sender: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
