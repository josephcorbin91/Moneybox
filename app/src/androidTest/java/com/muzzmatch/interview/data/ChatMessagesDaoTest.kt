package com.muzzmatch.interview.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.muzzmatch.interview.data.dao.ChatMessagesDao
import com.muzzmatch.interview.data.db.AppDatabase
import com.muzzmatch.interview.data.entities.ChatMessage
import com.muzzmatch.interview.utilities.getValue
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class  ChatMessagesDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var chatMessagesDao: ChatMessagesDao
    val date1 = LocalDate.parse("2021-12-20").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant()
    val date2 = LocalDate.parse("2021-12-21").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant()
    val date3 = LocalDate.parse("2021-12-22").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant()
    private val messageA = ChatMessage(isMe = true, message = "Message A", date = Date.from(date1), userName = "Sarah")
    private val messageB = ChatMessage(isMe = false, message = "Message B", date = Date.from(date2), userName = "Sarah")
    private val messageC = ChatMessage(isMe = true, message = "Message C", date = Date.from(date3), userName = "Sarah")
    private val messageD = ChatMessage(isMe = true, message = "Message A", date = Date.from(date1), userName = "Dan")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        chatMessagesDao = database.chatDao()
    }

    @After
    fun closeDb() = runBlocking {
        chatMessagesDao.clearChatMessages()
        database.close()
    }

    @Test
    fun testGetAllMessagesFromDifferentUsers() = runBlocking {
        chatMessagesDao.insertAll(listOf(messageA, messageB, messageC, messageD))
        val sarahChatMessages = getValue(chatMessagesDao.getAllMessages("Sarah"))
        assertThat(sarahChatMessages.size, equalTo(3))
        assertThat(sarahChatMessages[0], equalTo(messageA))
        assertThat(sarahChatMessages[1], equalTo(messageB))
        assertThat(sarahChatMessages[2], equalTo(messageC))
        val danChatMessages = getValue(chatMessagesDao.getAllMessages("Dan"))
        assertThat(danChatMessages.size, equalTo(1))
        assertThat(danChatMessages[0], equalTo(messageD))
    }

    @Test
    fun testGetAllMessagesReturnsCorrectOrder() = runBlocking {
        chatMessagesDao.insertAll(listOf(messageC, messageB, messageA, messageD))
        val sarahChatMessages = getValue(chatMessagesDao.getAllMessages("Sarah"))
        assertThat(sarahChatMessages.size, equalTo(3))
        assertThat(sarahChatMessages[0], equalTo(messageA))
        assertThat(sarahChatMessages[1], equalTo(messageB))
        assertThat(sarahChatMessages[2], equalTo(messageC))
    }

    @Test
    fun testInsertMessage() = runBlocking {
        chatMessagesDao.insertAll(listOf(messageC, messageB))
        chatMessagesDao.insertChatMessage(messageA)
        val sarahChatMessages = getValue(chatMessagesDao.getAllMessages("Sarah"))
        assertThat(sarahChatMessages.size, equalTo(3))
        assertThat(sarahChatMessages[0], equalTo(messageA))
        assertThat(sarahChatMessages[1], equalTo(messageB))
        assertThat(sarahChatMessages[2], equalTo(messageC))
    }

    @Test
    fun testClearMessages() = runBlocking {
        chatMessagesDao.insertAll(listOf(messageC, messageB, messageA, messageD))
        assertThat(getValue(chatMessagesDao.getAllMessages("Sarah")).size, equalTo(3))
        chatMessagesDao.clearChatMessages()
        assertThat(getValue(chatMessagesDao.getAllMessages("Sarah")).size, equalTo(0))
    }
}
