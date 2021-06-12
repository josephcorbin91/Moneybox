package com.muzzmatch.interview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.muzzmatch.interview.data.entities.ChatMessage
import com.muzzmatch.interview.data.repository.ChatMessageRepository
import com.muzzmatch.interview.ui.viewmodels.ChatMessageViewModel
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ChatMessageViewModelTest {

    private val liveData = MutableLiveData<List<ChatMessage>>()
    var chatMessageRepository = mockk<ChatMessageRepository>(relaxUnitFun = true) {
        every { mostRecentMessages } returns liveData
    }

    @InjectMockKs
    lateinit var viewModel: ChatMessageViewModel


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

    @Test
    fun `test chatMessage non-empty adds message to repository`() = runBlockingTest {

        // Given
        val mockedChatMessageSubmittedObserver = createChatMessageSubmittedObserver()
        viewModel.chatMessageSubmitted.observeForever(mockedChatMessageSubmittedObserver)

        // When
        viewModel.onSubmitButtonClicked("NewMessage")

        // Then
        coVerify { chatMessageRepository.addMessage(any()) }
        val chatMessageSubmittedResponseSlots = mutableListOf<String>()
        verify { mockedChatMessageSubmittedObserver.onChanged(capture(chatMessageSubmittedResponseSlots)) }
        assert(chatMessageSubmittedResponseSlots[0] == "NewMessage")
    }

    @Test
    fun `test chatMessage empty does not add message to repository`() = runBlockingTest {

        // Given
        val mockedChatMessageSubmittedObserver = createChatMessageSubmittedObserver()
        viewModel.chatMessageSubmitted.observeForever(mockedChatMessageSubmittedObserver)

        // When
        viewModel.onSubmitButtonClicked("")

        // Then
        coVerify(exactly = 0) { chatMessageRepository.addMessage(any()) }
        val chatMessageSubmittedResponseSlots = mutableListOf<String>()
        coVerify(exactly = 0) { mockedChatMessageSubmittedObserver.onChanged(capture(chatMessageSubmittedResponseSlots)) }
    }

    private fun createChatMessageSubmittedObserver(): Observer<String> = spyk(Observer { })
}