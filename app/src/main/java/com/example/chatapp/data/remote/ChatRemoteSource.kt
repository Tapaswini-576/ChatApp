package com.example.chatapp.data.remote

import com.example.chatapp.data.model.message
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class ChatRemoteSource {

    private val firestore = FirebaseFirestore.getInstance()
    private val messagesRef = firestore.collection("messages")

    //Write a message to the databse
    suspend fun sendMessage(dto: message) {
        messagesRef.add(dto).await()
    }

    fun messagesQuery(): Query = messagesRef.orderBy("timestamp")
}
