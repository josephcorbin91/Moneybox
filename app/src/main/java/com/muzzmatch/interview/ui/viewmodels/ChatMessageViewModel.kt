package com.muzzmatch.interview.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muzzmatch.interview.data.entities.ChatMessage
import com.muzzmatch.interview.data.repository.ChatMessageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*

/** Time after which a fake response message is added to the conversation once a user has sent a message */
const val MESSAGE_RESPONSE_DELAY_TIME = 10000L

class ChatMessageViewModel @ViewModelInject constructor(
        private val chatMessageRepository: ChatMessageRepository,
) : ViewModel() {

    /** Listens to whenever table for a specific user in the database changes to update the view model */
    val chatMessage: LiveData<List<ChatMessage>> = chatMessageRepository.mostRecentMessages

    /** Listens to whenever a new answer is submitted */
    val chatMessageSubmitted: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onSubmitButtonClicked(newMessage: String) {
        viewModelScope.launch {
            if (newMessage.isNotEmpty()) {
                chatMessageSubmitted.value = newMessage
                chatMessageRepository.addMessage(
                        ChatMessage(
                                isMe = true,
                                message = newMessage,
                                userName = "Sarah"
                        )
                )

                delay(kotlin.random.Random.nextLong(1000L, 21000L))
                chatMessageRepository.addMessage(ChatMessage(isMe = false, message = "Ya sounds good see you there", date = Date.from(
                        Instant.now()), userName = "Sarah"))
            }
        }
    }
}