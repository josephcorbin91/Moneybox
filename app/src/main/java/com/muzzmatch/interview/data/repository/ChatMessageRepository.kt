package com.muzzmatch.interview.data.repository

import androidx.lifecycle.LiveData
import com.muzzmatch.interview.data.dao.ChatMessagesDao
import com.muzzmatch.interview.data.entities.ChatMessage
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class ChatMessageRepository @Inject constructor(private val chatMessagesDao: ChatMessagesDao) {

    // Hardcoded to Sarah
    /** Live data of most recent chat messages stored in the database */
    val mostRecentMessages: LiveData<List<ChatMessage>> = chatMessagesDao.getAllMessages("Sarah")

    suspend fun addMessage(chatMessage: ChatMessage) {
        chatMessagesDao.insertChatMessage(chatMessage)
    }
}