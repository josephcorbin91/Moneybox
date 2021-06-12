package com.muzzmatch.interview.data.entities

sealed class UiModel {
    data class ChatMessageUIItem(val chatMessage: ChatMessage) : UiModel() {
        var hasTail: Boolean = false
        var hasTimeStamp: Boolean = false
    }
}