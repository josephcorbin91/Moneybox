package com.muzzmatch.interview.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muzzmatch.interview.data.entities.ChatMessage

/**
 * The Data Access Object for the ChatMesage class.
 */
@Dao
interface ChatMessagesDao {

    @Query("SELECT * FROM chatMessages WHERE userName = :userName ORDER BY date ASC")
    fun getAllMessages(userName: String): LiveData<List<ChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chatMessages: List<ChatMessage>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(chatMessage: ChatMessage)

    @Query("DELETE FROM chatMessages")
    suspend fun clearChatMessages()
}
