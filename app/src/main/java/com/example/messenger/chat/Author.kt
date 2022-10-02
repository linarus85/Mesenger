package com.example.messenger.chat

import com.stfalcon.chatkit.commons.models.IUser

class Author(val authorId: Long, val username: String) : IUser {
    override fun getId(): String {
       return authorId.toString()
    }

    override fun getName(): String {
        return username
    }

    override fun getAvatar(): String?{
        return null
    }

}
