package com.muzzmatch.interview.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

/**
 * Represents the chat message objects in the conversation
 */
@Entity(tableName = "chatMessages")
data class ChatMessage(
        @PrimaryKey
        val id: Long = UUID.randomUUID().mostSignificantBits,
        val isMe: Boolean,
        val message: String,
        val date: Date = Date.from(Instant.now()),
        val userName: String,
        val messageReadStatus: MessageReadStatus? = MessageReadStatus.Sent
)

enum class MessageReadStatus {
    /** Demarcated by one gray check mark indicating the message is successfully sent to the server */
    Sent,

    /** Demarcated by two gray check mark indicating the message has been received by the other recipient */
    Received,

    /** Demarcated by one gray check mark indicating the message has been read by the other receipient */
    Read
}