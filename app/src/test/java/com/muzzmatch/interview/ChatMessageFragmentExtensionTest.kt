package com.muzzmatch.interview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.muzzmatch.interview.data.entities.ChatMessage
import com.muzzmatch.interview.data.entities.UiModel
import com.muzzmatch.interview.ui.fragments.convertToUIModelList
import com.muzzmatch.interview.ui.fragments.updateTailState
import com.muzzmatch.interview.ui.fragments.updateTimestamp
import io.mockk.MockKAnnotations
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.time.ExperimentalTime


@ExperimentalCoroutinesApi
class ChatMessageFragmentExtensionTest {

    val date = LocalDate.parse("2021-12-20").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant()
    val dateOneHourEarlier = LocalDate.parse("2021-12-20").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant().minus(Duration.ofHours(1L))
    val dateTwoHoursEarlier = LocalDate.parse("2021-12-20").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant().minus(Duration.ofHours(2L))
    val dateTwoHoursEarlierTwentySeconds = LocalDate.parse("2021-12-20").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant().minus(Duration.ofHours(2L)).minus(Duration.ofSeconds(20L))
    val dateTwoHoursEarlierFiftySeconds = LocalDate.parse("2021-12-20").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant().minus(Duration.ofHours(2L)).minus(Duration.ofSeconds(50L))
    val dateThirtyMinutesEarlier = LocalDate.parse("2021-12-20").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant().minus(Duration.ofMinutes(30L))
    val dateTwentySecondsEarlier = LocalDate.parse("2021-12-20").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant().minus(Duration.ofSeconds(20L))
    val dateTwentyOneSecondsEarlier = LocalDate.parse("2021-12-20").atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant().minus(Duration.ofSeconds(21L))


    val dispatcher = TestCoroutineDispatcher()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        MockKAnnotations.init(this)
    }

    @ExperimentalTime
    @Test
    fun `test hasTimestamp null previous chat message`() {
        val messageA = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message A", date = Date.from(date), userName = "Sarah"))
        assert(!messageA.hasTimeStamp)
        assert(messageA.updateTimestamp(null).hasTimeStamp)
    }

    @ExperimentalTime
    @Test
    fun `test hasTimestamp previous chat message older by exactly one hour`() {
        val message = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message A", date = Date.from(date), userName = "Sarah"))
        val previousMessage = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message B", date = Date.from(dateOneHourEarlier), userName = "Sarah"))
        assert(!message.hasTimeStamp)
        assert(!message.updateTimestamp(previousMessage).hasTimeStamp)
    }

    @ExperimentalTime
    @Test
    fun `test hasTimestamp previous chat message earlier than one hour`() {
        val message = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message A", date = Date.from(date), userName = "Sarah"))
        val previousMessage = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message B", date = Date.from(dateThirtyMinutesEarlier), userName = "Sarah"))
        assert(!message.hasTimeStamp)
        assert(!message.updateTimestamp(previousMessage).hasTimeStamp)
    }

    @ExperimentalTime
    @Test
    fun `test hasTimestamp previous chat message older by more than two hour`() {
        val message = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message A", date = Date.from(date), userName = "Sarah"))
        val previousMessage = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message B", date = Date.from(dateTwoHoursEarlier), userName = "Sarah"))
        assert(!message.hasTimeStamp)
        assert(message.updateTimestamp(previousMessage).hasTimeStamp)
    }

    @ExperimentalTime
    @Test
    fun `test hasTail subsequent is null`() {
        val message = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message A", date = Date.from(date), userName = "Sarah"))
        assert(!message.hasTail)
        assert(message.updateTailState(null).hasTail)
    }


    @ExperimentalTime
    @Test
    fun `test hasTail subsequentMessageHasDifferentUserName`() {
        val message = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message A", date = Date.from(date), userName = "Sarah"))
        val subsequentMessage = UiModel.ChatMessageUIItem(ChatMessage(isMe = false, message = "Message B", date = Date.from(date), userName = "Dan"))
        assert(!message.hasTail)
        assert(message.updateTailState(subsequentMessage).hasTail)
    }

    @ExperimentalTime
    @Test
    fun `test hasTail subsequentMessageHasIs20SecondsOlder`() {
        val message = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message A", date = Date.from(dateTwentySecondsEarlier), userName = "Sarah"))
        val subsequentMessage = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message B", date = Date.from(date), userName = "Sarah"))
        assert(!message.hasTail)
        assert(!message.updateTailState(subsequentMessage).hasTail)
    }

    @ExperimentalTime
    @Test
    fun `test hasTail subsequentMessageHasIs21SecondsOlder`() {
        val message = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message A", date = Date.from(dateTwentyOneSecondsEarlier), userName = "Sarah"))
        val subsequentMessage = UiModel.ChatMessageUIItem(ChatMessage(isMe = true, message = "Message B", date = Date.from(date), userName = "Sarah"))
        assert(!message.hasTail)
        assert(message.updateTailState(subsequentMessage).hasTail)
    }

    @ExperimentalTime
    @Test
    fun `test convertToUIModelList `() {
        val message1 = ChatMessage(isMe = true, message = "Message A", date = Date.from(date), userName = "Dan")
        val message2 = ChatMessage(isMe = true, message = "Message C", date = Date.from(dateTwentySecondsEarlier), userName = "Dan")
        val message3 = ChatMessage(isMe = true, message = "Message B", date = Date.from(dateThirtyMinutesEarlier), userName = "Sarah")
        val message4 = ChatMessage(isMe = true, message = "Message D", date = Date.from(dateTwoHoursEarlier), userName = "Sarah")
        val message5 = ChatMessage(isMe = true, message = "Message A", date = Date.from(dateTwoHoursEarlierTwentySeconds), userName = "Sarah")
        val message6 = ChatMessage(isMe = true, message = "Message C", date = Date.from(dateTwoHoursEarlierFiftySeconds), userName = "Sarah")

        val messageList = listOf(message6, message5, message4, message3, message2, message1)
        val convertedList = messageList.convertToUIModelList()

        assert((convertedList[0] as UiModel.ChatMessageUIItem).hasTail)
        assert(!(convertedList[1] as UiModel.ChatMessageUIItem).hasTail)
        assert((convertedList[2] as UiModel.ChatMessageUIItem).hasTail)
        assert((convertedList[3] as UiModel.ChatMessageUIItem).hasTail)
        assert(!(convertedList[4] as UiModel.ChatMessageUIItem).hasTail)
        assert((convertedList[5] as UiModel.ChatMessageUIItem).hasTail)

        assert((convertedList[0] as UiModel.ChatMessageUIItem).hasTimeStamp)
        assert(!(convertedList[1] as UiModel.ChatMessageUIItem).hasTimeStamp)
        assert(!(convertedList[2] as UiModel.ChatMessageUIItem).hasTimeStamp)
        assert((convertedList[3] as UiModel.ChatMessageUIItem).hasTimeStamp)
        assert(!(convertedList[4] as UiModel.ChatMessageUIItem).hasTimeStamp)
        assert(!(convertedList[5] as UiModel.ChatMessageUIItem).hasTimeStamp)
    }
}