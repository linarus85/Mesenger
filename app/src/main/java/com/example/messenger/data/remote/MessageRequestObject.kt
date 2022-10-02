package com.example.messenger.data.remote


data class MessageRequestObject(
    val recipientId: Long,
    val message: String,
)