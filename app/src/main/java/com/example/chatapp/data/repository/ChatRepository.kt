package com.example.chatapp.data.repository


import com.example.chatapp.data.model.message
import com.example.chatapp.data.model.message
import com.example.chatapp.data.remote.ChatRemoteSource
import com.example.chatapp.data.remote.ChatRemoteSource
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.ChatRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChatRepository(
    private val remote: ChatRemoteSource
) : ChatRepository {

    override suspend fun sendMessage(message: Message) {
        // map domain ➜ DTO
        remote.sendMessage(
            message(message.text, message.sender, message.timestamp)
        )
    }

    /** Firestore ➜ callbackFlow ➜ domain objects */
    override fun getMessages() = callbackFlow<List<message>> {

        val registration = remote.messagesQuery()
            .addSnapshotListener { snapshot, error ->
                when {
                    error != null -> close(error)           // propagate Firestore error
                    snapshot != null -> {
                        val list = snapshot.toObjects(message::class.java)
                            .map { dto ->
                                message(dto.text, dto.sender, dto.timestamp)
                            }
                        trySend(list).isSuccess             // push to the Flow
                    }
                }
            }

        awaitClose { registration.remove() }                 // cancel listener when Flow closes
    }
}