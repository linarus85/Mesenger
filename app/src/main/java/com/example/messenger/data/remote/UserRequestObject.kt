package com.example.messenger.data.remote

data class UserRequestObject(
    val username: String,
    val password: String,
    val phoneNumber: String ="",
)
